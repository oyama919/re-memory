package controllers

import forms.DictionaryForm.dictionaryForm
import javax.inject.{Inject, Singleton}
import models.DictionaryTag
import models.exception.AlreadyRegisteredException
import play.api.Configuration
import play.api.mvc._
import services.{DictionaryService, DictionaryTagService, TagService}
import util.TryUtil

import scala.util.{Failure, Success, Try}

@Singleton
class DictionaryController @Inject()(
                                      dictionaryService: DictionaryService,
                                      tagService: TagService,
                                      dictionaryTagService: DictionaryTagService,
                                      components: ControllerComponents,
                                      config: Configuration
                                    ) extends AbstractController(components) {

  def create: Action[AnyContent] = Action { implicit request =>
    dictionaryForm
      .bindFromRequest()
      .fold(
        error => BadRequest(ErrorMessage("FormError", s"${error.errors}").toJson),
        { dictionaryForm =>

          val result: Try[Unit] = for {
            maybeNewDictionary <- dictionaryService.findByTitle(dictionaryForm.title)
            newDictionary <- dictionaryService.createNewDictionary(maybeNewDictionary, dictionaryForm)
            newDictionaryId <- dictionaryService.create(newDictionary)
            tagIds <- TryUtil.sequence(tagService.createTagFromForm(dictionaryForm))
            dictionaryTags = DictionaryTag(newDictionaryId, tagIds)
            _ <- dictionaryTagService.createDictionaryTags(dictionaryTags)
          } yield ()

          result match {
            case Success(_) => Ok("success")
            case Failure(e: AlreadyRegisteredException) => BadRequest(ErrorMessage("FormError", s"$e").toJson)
            case Failure(e: RuntimeException) => InternalServerError(ErrorMessage("InternalServerError", s"$e").toJson)
            case Failure(e) => InternalServerError(ErrorMessage("InternalServerError",s"$e").toJson)
          }
        })
  }
}

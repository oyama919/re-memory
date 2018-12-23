package controllers

import forms.CreateDictionary.createDictionaryForm
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.mvc._
import services.DictionaryService
import java.time.ZonedDateTime
import models.Dictionary


@Singleton
class DictionaryController @Inject()(
  dictionaryService: DictionaryService,
  components: ControllerComponents,
  config: Configuration
) extends AbstractController(components) {

  def create: Action[AnyContent] = Action { implicit request =>
    createDictionaryForm
      .bindFromRequest()
      .fold(
        error => BadRequest(ErrorMessage(error + " FormError", "Invalid.Value").toJson),
        { create =>
          if(dictionaryService.findByTitle(create.title).getOrElse(None).isDefined) {
            BadRequest(ErrorMessage("FormError", "Exist.Title").toJson)
          }
          else {
            val now = ZonedDateTime.now()
            val dictionary = Dictionary(None, create.user_id, create.title, create.content, now, now)
            dictionaryService.create(dictionary)
              .map { _ => Ok("success") }
              .recover { case e: Exception => InternalServerError(ErrorMessage("InternalServerError").toJson) }
              .getOrElse(InternalServerError(ErrorMessage("InternalServerError").toJson))
          }
        })
  }
}

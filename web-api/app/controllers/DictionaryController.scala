package controllers

import java.time.ZonedDateTime

import forms.DictionaryForm.dictionaryForm
import javax.inject.{Inject, Singleton}
import models.{Dictionary, DictionaryTag, Tag}
import play.api.Configuration
import play.api.mvc._
import services.{DictionaryService, DictionaryTagService, TagService}

@Singleton
class DictionaryController @Inject()(
  dictionaryService: DictionaryService,
  tagService: TagService,
  dictionaryTagService: DictionaryTagService,
  components: ControllerComponents,
  config: Configuration
) extends AbstractController(components) {

  val now = ZonedDateTime.now()

  def create: Action[AnyContent] = Action { implicit request =>
    dictionaryForm
      .bindFromRequest()
      .fold(
        error => BadRequest(ErrorMessage("FormError", s"${error.errors}").toJson),
        { dictionaryForm =>

          val sameDictionaryTitle: Either[Throwable, Dictionary] = for {
            maybeDictionary <- dictionaryService.findByTitle(dictionaryForm.title).toEither
            dictionary <- maybeDictionary.toRight(new Exception("該当する辞書がありません"))
          } yield dictionary

          sameDictionaryTitle match {
            case Right(sameDictionary) => BadRequest(ErrorMessage("FormError", "Exist.Title").toJson)
            case Left(e: Exception) => {

              val dictionary = Dictionary(
                None, dictionaryForm.user_id, dictionaryForm.title,
                dictionaryForm.content, dictionaryForm.publish_setting, now, now)

              val result: Either[Throwable, Long] = for {
                id <- dictionaryService.create(dictionary).toEither
              } yield (id)

              result match {
//                case Right(id) if (dictionaryForm.tags.isDefined) =>
//                  for {
//                    result: Result <- createDictionaryTag(id, dictionaryForm.tags.get)
//                  } yield { result }
                case Right(_) => Ok("success")
                case Left(e) => InternalServerError(ErrorMessage(s"$e").toJson)
              }
            }
          }
        })
  }

  def createDictionaryTag(dictionaryId: Long, tags: Seq[String]): Seq[Result] =
    for {
      tagName <- tags
    } yield {
      val result: Either[Throwable, Tag] = for {
        maybeTag <- tagService.findByTitle(tagName).toEither
        tag <- maybeTag.toRight(new Exception("該当するタグがありません"))
      } yield (tag)
      result match {
        case Right(tag) if (tag.id.isDefined) =>
          val dictionaryTag = DictionaryTag(None, dictionaryId, tag.id.get, now, now)
          val result : Either[Throwable, Long] =
            for {
              id <- dictionaryTagService.create(dictionaryTag).toEither
            } yield (id)
          result match {
            case Right(_) => Ok("success")
            case Left(e) => InternalServerError(ErrorMessage(s"$e").toJson)
          }
        case Left(e) => InternalServerError(ErrorMessage(s"$e").toJson)
      }
    }
}

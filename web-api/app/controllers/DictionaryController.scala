package controllers

import java.time.ZonedDateTime

import forms.CreateDictionary.createDictionaryForm
import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.Dictionary
import play.api.Configuration
import play.api.mvc._
import services.DictionaryService

@Singleton
class DictionaryController @Inject()(
  dictionaryService: DictionaryService,
  components: ControllerComponents,
  config: Configuration
) extends AbstractController(components) {

  def show(dictionaryId: Long) = Action { implicit request =>

    val maybeDictionary = dictionaryService.findById(dictionaryId).getOrElse(None)

    if (!maybeDictionary.isDefined && maybeDictionary.getOrElse(None) == None) {
      BadRequest(ErrorMessage("NotFond", "Dictionary").toJson)
    } else if(!maybeDictionary.get.publish_setting) { // TODO sessionのユーザIDを使い自分の辞書は見れるようにする
      BadRequest(ErrorMessage("NotPublic", "Dictionary").toJson)
    } else {
      val d = maybeDictionary.get
      case class Data(id: Long, user_id: Long, title: String, content: String, publish_setting: Boolean)
      val responce = Data(d.id.getOrElse(1), d.user_id, d.title, d.content, d.publish_setting)
      Ok(responce.asJson.noSpaces)
    }
  }

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
            val dictionary = Dictionary(None, create.user_id, create.title, create.content, create.publish_setting, now, now)
            dictionaryService.create(dictionary)
              .map { _ => Ok("success") }
              .recover { case e: Exception => InternalServerError(ErrorMessage("InternalServerError").toJson) }
              .getOrElse(InternalServerError(ErrorMessage("InternalServerError").toJson))
          }
        })
  }
}

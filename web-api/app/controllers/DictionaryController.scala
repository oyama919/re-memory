package controllers

import forms.DictionaryForm.dictionaryForm
import javax.inject.{Inject, Singleton}
import models.DictionaryTag
import models.exception.AlreadyRegisteredException
import play.api.Configuration
import play.api.mvc._
import services.{DictionaryService, DictionaryTagService, TagService}
import util.TryUtil
import io.circe.generic.auto._
import io.circe.syntax._

import scala.util.{Failure, Success, Try}

@Singleton
class DictionaryController @Inject()(
                                      dictionaryService: DictionaryService,
                                      tagService: TagService,
                                      dictionaryTagService: DictionaryTagService,
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
      val response = Data(d.id.getOrElse(1), d.user_id, d.title, d.content, d.publish_setting)
      Ok(response.asJson.noSpaces)
    }
  }

  def create: Action[AnyContent] = Action { implicit request =>
    dictionaryForm
      .bindFromRequest()
      .fold(
        error => BadRequest(ErrorMessage("FormError", s"${error.errors}").toJson),
        { dictionaryForm =>

          val result: Try[Unit] = for { // 返すものがないためUnit Successのみ判定
            maybeExistDictionary <- dictionaryService.findByTitle(dictionaryForm.title) //　辞書をフォームのタイトルと重複した辞書があるか探してくる
            newDictionaryObject <- dictionaryService.newDictionary(maybeExistDictionary, dictionaryForm) // 重複辞書オプションとフォームのデータを引数に辞書オブジェクトを作成
            newDictionaryId <- dictionaryService.create(newDictionaryObject) // 辞書オブジェクトから新規辞書作成
            tagIds <- TryUtil.sequence(tagService.createTagFromForm(dictionaryForm)) // タグid取得 タグがなければ新規作成し戻り値のidを取得 Seq[Try[T]]をTry[Seq[T]]へ変換
            dictionaryTags = DictionaryTag(newDictionaryId, tagIds) // Seq[DictionaryTag]　タグのオブジェクトを取得
            _ <- dictionaryTagService.createDictionaryTags(dictionaryTags) // 辞書タグテーブル保存 値は使わないため _ で省略
          } yield () // ()単体はunit型を表す

          result match {
            case Success(_) => Ok("success")
            case Failure(e: AlreadyRegisteredException) => BadRequest(ErrorMessage("FormError", s"$e").toJson)
            case Failure(e: RuntimeException) => InternalServerError(ErrorMessage("InternalServerError", s"$e").toJson)
            case Failure(e) => InternalServerError(ErrorMessage("InternalServerError",s"$e").toJson)
          }
        })
  }
}

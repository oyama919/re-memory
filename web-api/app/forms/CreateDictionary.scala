package forms

import play.api.data.Form
import play.api.data.Forms._

case class CreateDictionary(user_id: Long, title: String, content: String, publish_setting: Boolean)

object CreateDictionary {
  val createDictionaryForm: Form[CreateDictionary] = Form {
    mapping(
      "user_id"     -> longNumber,
      "title"    -> nonEmptyText,
      "content"        -> nonEmptyText,
      "publish_setting" -> boolean
    )(CreateDictionary.apply)(CreateDictionary.unapply)
  }
}

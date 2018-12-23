package forms

import play.api.data.Form
import play.api.data.Forms._

case class CreateDictionary(user_id: Long, title: String, content: String)

object CreateDictionary {
  val createDictionaryForm: Form[CreateDictionary] = Form {
    mapping(
      "user_id"     -> longNumber,
      "title"    -> nonEmptyText,
      "content" -> nonEmptyText
    )(CreateDictionary.apply)(CreateDictionary.unapply)
  }
}

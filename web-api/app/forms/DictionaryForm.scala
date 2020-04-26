package forms

import play.api.data.Form
import play.api.data.Forms._

case class DictionaryForm(user_id: Long, title: String, content: String, publish_setting: Boolean, tags: Seq[String])

object DictionaryForm {
  val dictionaryForm: Form[DictionaryForm] = Form {
    mapping(
      "user_id" -> longNumber,
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "publish_setting" -> boolean,
      "tags" -> seq(text)
    )(DictionaryForm.apply)(DictionaryForm.unapply)
  }
}


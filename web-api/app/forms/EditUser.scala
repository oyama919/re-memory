package forms

import play.api.data.Form
import play.api.data.Forms._

case class EditUser(name: String, email: String, password: String, newPassword: Option[String], confirmPassword: Option[String])

object EditUser {
  val editForm: Form[EditUser] = Form {
   mapping(
     "name" -> nonEmptyText,
     "email" -> email,
     "password" -> nonEmptyText,
     "newPassword" -> optional(nonEmptyText),
     "confirmPassword" -> optional(nonEmptyText)
   )(EditUser.apply)(EditUser.unapply)
  }
}

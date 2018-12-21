package forms

import play.api.data.Form
import play.api.data.Forms._

case class SignUp(name: String, email: String, password: String)

object SignUp {
  val signUpForm: Form[SignUp] = Form {
    mapping(
      "name"     -> nonEmptyText,
      "email"    -> email,
      "password" -> nonEmptyText
    )(SignUp.apply)(SignUp.unapply)
  }
}

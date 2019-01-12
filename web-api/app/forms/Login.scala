package forms

import play.api.data.Form
import play.api.data.Forms._

case class Login(email: String, password: String)

object Login {
  val loginForm: Form[Login] = Form {
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(Login.apply)(Login.unapply)
  }
}

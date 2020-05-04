package controllers

import forms.Login.loginForm
import javax.inject.{Inject, Singleton}
import play.api.mvc._
import services.{PasswordService, UserService}

import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject()(
  userService: UserService,
  passwordService: PasswordService,
  cc: ControllerComponents
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def login() = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      error => BadRequest(ErrorMessage("FormError", s"${error.errors}").toJson),
      login => {
        userService
          .findByEmail(login.email)
          .map {
            maybeUser =>
              maybeUser
                .fold(BadRequest(ErrorMessage("Not Found", "NoAccount").toJson)) {
                  user =>
                    if (passwordService
                          .checkPassword(login.password, user.password)) {
                      Ok("success")
                        .withSession("user_email" -> login.email)
                    } else {
                      BadRequest(
                        ErrorMessage("FormError", "Invalid.Password").toJson
                      )
                    }
                }
          } recover {
          case e: Exception =>
            InternalServerError(ErrorMessage("InternalServerError").toJson)
        }
      }.getOrElse({
        InternalServerError(ErrorMessage("InternalServerError").toJson)
      })
    )
  }
}

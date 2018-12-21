package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import scala.concurrent.ExecutionContext
import services.{PasswordService, UserService}
import forms.Login.loginForm

@Singleton
class AuthController @Inject()(
  userService: UserService, passwordService: PasswordService, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

    def login() = Action { implicit request =>
      loginForm.bindFromRequest.fold(
        error => {
          BadRequest(ErrorMessage("FormError", "Invalid.Value").toJson)
        },
        login => {
          userService.findByEmail(login.email)
            .map { user =>
              val isRegisteredUser = passwordService.checkPassword(login.password, user.get.password)
              isRegisteredUser match {
                case true => Ok("success").withSession("user_email" -> login.email)
                case false => {
                  BadRequest(ErrorMessage("FormError", "Invalid.Password").toJson)
                }
              }
            } recover {
            case e: Exception => InternalServerError(ErrorMessage("InternalServerError").toJson)
          }
        }.getOrElse({
            InternalServerError(ErrorMessage("InternalServerError").toJson)
        }))
    }
}

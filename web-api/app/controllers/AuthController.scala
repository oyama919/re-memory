package controllers

import forms.Login
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import services.{AuthenticateService, UserService}

import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject()(userService: UserService,
                               authenticateService: AuthenticateService,
                               cc: ControllerComponents)
                              (implicit ec: ExecutionContext) extends AbstractController(cc) {
  private val loginForm: Form[Login] = Form {
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(Login.apply)(Login.unapply)
  }

  def login() = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      error => BadRequest("フォームエラー"),
      login => {
        userService.findByEmail(login.email)
          .map { user =>
            val isRegisteredUser = authenticateService.authenticate(login.password, user.get.password)
            isRegisteredUser match {
              case true => Ok("success").withSession("user_email" -> login.email)
              case false => BadRequest("認証エラー")
            }
          } recover {
          case e: Exception => InternalServerError("内部エラー")
        }
      }.getOrElse(InternalServerError("内部エラー")))
  }
}
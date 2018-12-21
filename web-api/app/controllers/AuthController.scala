package controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import io.circe.generic.auto._
import io.circe.syntax._
import services.{AuthenticateService, UserService}
import forms.Login

@Singleton
class AuthController @Inject()(
  userService: UserService, authenticateService: AuthenticateService, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {
    private val loginForm: Form[Login] = Form {
      mapping(
        "email" -> email,
        "password" -> nonEmptyText
      )(Login.apply)(Login.unapply)
    }

    def login() = Action { implicit request =>
      loginForm.bindFromRequest.fold(
        error => {
          BadRequest(ErrorResponseDTO("フォームエラー", "値が不正です").asJson.noSpaces)
        },
        login => {
          userService.findByEmail(login.email)
            .map { user =>
              val isRegisteredUser = authenticateService.authenticate(login.password, user.get.password)
              isRegisteredUser match {
                case true => Ok("success").withSession("user_email" -> login.email)
                case false => {
                  BadRequest(ErrorResponseDTO("認証エラー", "パスワードが間違っています").asJson.noSpaces)
                }
              }
            } recover {
            case e: Exception => {
              InternalServerError(ErrorResponseDTO("内部エラー", "不明なエラー").asJson.noSpaces)
            }
          }
        }.getOrElse({
            InternalServerError(ErrorResponseDTO("内部エラー", "不明なエラー").asJson.noSpaces)
        }))
    }
}
package controllers

import forms.SignUp.signUpForm
import javax.inject.{Inject, Singleton}
import models.User
import play.api.Configuration
import play.api.mvc._
import services.{PasswordService, UserService}

import scala.util.{Failure, Success, Try}

@Singleton
class SignUpController @Inject()(
  userService: UserService,
  passwordService: PasswordService,
  components: ControllerComponents,
  config: Configuration
) extends AbstractController(components) {

  def signup: Action[AnyContent] = Action { implicit request =>
    signUpForm
      .bindFromRequest()
      .fold(
        _ => BadRequest(ErrorMessage("FormError", "Invalid.Value").toJson),
        { signUp =>

          val result: Try[Long] = for {
            isNewUser <- userService.isNewUser(signUp) if isNewUser
            hashedPassword = passwordService.hashPassword(signUp.password)
            user = User(None, signUp.name, signUp.email, hashedPassword)
            userId <- userService.create(user)
          } yield userId

          result match {
            case Success(userId) => Ok("success").withSession("user_id" -> userId.toString)
            case Failure(e: Exception) => BadRequest(ErrorMessage(s"$e").toJson)
            case Failure(e) => InternalServerError(ErrorMessage(s"$e").toJson)
          }
        })
  }
}

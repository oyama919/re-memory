 package controllers
 import java.time.ZonedDateTime

 import javax.inject.{Inject, Singleton}
 import forms.SignUp
 import models.User
 import play.api.data.Form
 import play.api.data.Forms._
 import play.api.mvc._
 import play.api.Configuration
 import io.circe.generic.auto._
 import io.circe.syntax._
 import services.{AuthenticateService, UserService}

 @Singleton
 class SignUpController @Inject()(
     userService: UserService,
     authenticateService: AuthenticateService,
     components: ControllerComponents,
     config: Configuration
   ) extends AbstractController(components) {

   private val signUpForm: Form[SignUp] = Form {
     mapping(
       "name"     -> nonEmptyText,
       "email"    -> email,
       "password" -> nonEmptyText
     )(SignUp.apply)(SignUp.unapply)
   }

   def signup: Action[AnyContent] = Action { implicit request =>
     signUpForm
       .bindFromRequest()
       .fold(
         error => BadRequest(ErrorResponseDTO("フォームエラー", "値が不正です").asJson.noSpaces),
         {
           signup =>
           val now            = ZonedDateTime.now()
           val hashedPassword = authenticateService.hashPassword(signup.password)
           val user           = User(None, signup.name, signup.email, hashedPassword, now, now)
           userService
             .create(user)
             .map { _ =>
               Ok("success").withSession("user_email" -> user.email)
             }
             .recover {
               case e: Exception => InternalServerError(ErrorResponseDTO("内部エラー", "不明なエラー").asJson.noSpaces)
             }
             .getOrElse(InternalServerError(ErrorResponseDTO("内部エラー", "不明なエラー").asJson.noSpaces))
         })
   }
 }

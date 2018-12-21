 package controllers

 import java.time.ZonedDateTime
 import forms.SignUp.signUpForm
 import javax.inject.{Inject, Singleton}
 import models.User
 import play.api.Configuration
 import play.api.mvc._
 import services.{PasswordService, UserService}

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
         error => BadRequest(ErrorMessage("FormError", "Invalid.Value").toJson),
         {
           signup =>
           val now            = ZonedDateTime.now()
           val hashedPassword = passwordService.hashPassword(signup.password)
           val user           = User(None, signup.name, signup.email, hashedPassword, now, now)
           userService
             .create(user)
             .map { _ =>
               Ok("success").withSession("user_email" -> user.email)
             }
             .recover {
               case e: Exception => InternalServerError(ErrorMessage("InternalServerError").toJson)
             }
             .getOrElse(InternalServerError(ErrorMessage("InternalServerError").toJson))
         })
   }
 }

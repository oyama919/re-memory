package controllers

import javax.inject.{Inject, Singleton}
import models.User
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{PasswordService, UserService}
import scala.concurrent.ExecutionContext
import forms.EditUser.editForm

@Singleton
class UserController @Inject()(
   userService: UserService,
   passwordService: PasswordService,
   cc: ControllerComponents
 )
 (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def edit(userId: Long): Action[AnyContent] = Action { implicit request =>
   editForm
     .bindFromRequest()
     .fold(
       error => BadRequest(ErrorMessage("FormError", s"${error.errors}").toJson),
       { edit =>
           val maybeUser = userService.findById(userId).getOrElse(None)
           if (!maybeUser.isDefined && maybeUser.getOrElse(None) == None) {
             BadRequest(ErrorMessage("NotFond", "User").toJson)
           } else {
             val user = maybeUser.get
             if(userService.findByEmail(edit.email).getOrElse(None).isDefined
               && user.email != edit.email
             ) {
               BadRequest(ErrorMessage("FormError", "Exist.Email").toJson)
             }
             else if (userService.findByName(edit.name).getOrElse(None).isDefined
               && user.name != edit.name
             ) {
               BadRequest(ErrorMessage("FormError", "Exit.Name").toJson)
             }
             else {
               val hashedPassword = passwordService.hashPassword(edit.password)
               val user = User(Option(userId), edit.name, edit.email, hashedPassword)
               userService.edit(user)
                 .map { _ => Ok("success") }
                 .recover { case e: Exception => InternalServerError(ErrorMessage("InternalServerError").toJson) }
                 .getOrElse(InternalServerError(ErrorMessage("InternalServerError").toJson))
             }
           }
       })
  }
}

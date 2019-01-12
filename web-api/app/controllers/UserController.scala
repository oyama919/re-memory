package controllers

import javax.inject.{Inject, Singleton}
import models.User
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{PasswordService, UserService}
import scala.concurrent.ExecutionContext
import forms.EditUser.editForm
import forms.EditUser
import scala.util.{Failure, Success}

@Singleton
class UserController @Inject()(
   userService: UserService,
   passwordService: PasswordService,
   cc: ControllerComponents
 )
 (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def edit(userId: Long): Action[AnyContent] = Action { implicit request =>
//    request.session.get("user_id").fold { // TODO view側実装後検証
//      Unauthorized(ErrorMessage("Unauthorized", "NotSession").toJson)
//    }
//    { session =>
//      if(userId.toString.equals(session)) {
//        Unauthorized(ErrorMessage("Unauthorized", "WrongUser").toJson)
//      }
      editForm
        .bindFromRequest()
        .fold(
          error => BadRequest(ErrorMessage("FormError", s"${error.errors}").toJson),
          { edit =>
            userService.findById(userId).map {

              case None => BadRequest(ErrorMessage("NotFond", "User").toJson)

              case Some(user) if isDuplicateEmail(user, edit) => BadRequest(ErrorMessage("FormError", "Duplicate.Email").toJson)

              case Some(user) if isDuplicateName(user, edit) => BadRequest(ErrorMessage("FormError", "Duplicate.Name").toJson)

              case Some(user) if isMissMatchPassword(edit) => BadRequest(ErrorMessage("FormError", "Password.MissMatch").toJson)

              case _ => {
                val hashedPassword =
                    edit.newPassword match {
                      case Some(newPw) if newPw.equals(edit.confirmPassword.getOrElse(None)) =>
                        passwordService.hashPassword(newPw)
                      case None => passwordService.hashPassword(edit.password)
                    }
                val user = User(Option(userId), edit.name, edit.email, hashedPassword)
                userService.edit(user) match {
                  case Success(_) => Ok("success")
                  case Failure(e) => InternalServerError(ErrorMessage(s"$e").toJson)
                }
              }

            }.getOrElse(InternalServerError(ErrorMessage("InternalServerError").toJson))
          }
        )
//    }
  }

  private def isDuplicateEmail(user: User, editUser: EditUser): Boolean = {
    userService.findByEmail(editUser.email).map(_.isDefined && user.email != editUser.email) match {
      case Success(isDuplicateEmail) => isDuplicateEmail
      case Failure(e) => throw new Exception(e)
    }
  }

  private def isDuplicateName(user: User, editUser: EditUser): Boolean = {
    userService.findByName(editUser.name).map(_.isDefined && user.name != editUser.name) match {
      case Success(isDuplicateName) => isDuplicateName
      case Failure(e) => throw new Exception(e)
    }
  }
  private def isMissMatchPassword(editUser: EditUser): Boolean = {
    editUser.newPassword.isDefined &&
      (editUser.newPassword.get != editUser.confirmPassword.getOrElse(None))
  }
}

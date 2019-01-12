package services

import forms.SignUp
import javax.inject.Singleton
import scalikejdbc.{AutoSession, DBSession}

import scala.util.{Failure, Success, Try}
import models.User

@Singleton
class UserServiceImpl extends UserService {
  def create(user: User)(implicit dbSession: DBSession = AutoSession): Try[Long] =
    Try {
      User.create(user)
    }

  def edit(user: User)(implicit dbSession: DBSession = AutoSession): Try[Long] =
    Try {
      User.update(user)
    }

  def findByName(name: String)(implicit dbSession: DBSession = AutoSession): Try[Option[User]] =
    Try {
      User.where('name -> name).apply().headOption
    }

  def findByEmail(email: String)(implicit dbSession: DBSession = AutoSession): Try[Option[User]] =
    Try {
      User.where('email -> email).apply().headOption
    }

  def findById(id: Long)(implicit dbSession: DBSession): Try[Option[User]] =
    Try {
      User.findById(id)
    }

  def isNewUser(signUpFrom: SignUp): Try[Boolean] = {
    for {
      isNewUserEmail <- isNewUserEmail(signUpFrom.email)
      isNewUserName <- isNewUserName(signUpFrom.name)
    } yield isNewUserEmail && isNewUserName
  }


  private def isNewUserEmail(email: String): Try[Boolean] = {
    findByEmail(email).flatMap {
      case None => Success(true)
      case Some(user) => Failure(new Exception("Exit.Email"))
    }
  }

  private def isNewUserName(name: String): Try[Boolean] = {
    findByName(name).flatMap {
      case None => Success(true)
      case Some(user) => Failure(new Exception("Exit.Name"))
    }
  }

}

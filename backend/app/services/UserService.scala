package services

import forms.SignUp
import models.User
import scalikejdbc.{AutoSession, DBSession}

import scala.util.Try

trait UserService {
  def create(user: User)(implicit dbSession: DBSession = AutoSession): Try[Long]

  def edit(user: User)(implicit dbSession: DBSession = AutoSession): Try[Long]

  def findByName(name: String)(implicit dbSession: DBSession = AutoSession): Try[Option[User]]

  def findByEmail(email: String)(implicit dbSession: DBSession = AutoSession): Try[Option[User]]

  def findById(id: Long)(implicit dbSession: DBSession = AutoSession): Try[Option[User]]

  def isNewUser(signUpFrom: SignUp): Try[Boolean]


}

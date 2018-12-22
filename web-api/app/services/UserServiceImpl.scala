 package services

 import javax.inject.Singleton
 import models.User
 import scalikejdbc.{AutoSession, DBSession}
 import scala.util.Try

 @Singleton
 class UserServiceImpl extends UserService {
   def create(user: User)(implicit dbSession: DBSession = AutoSession): Try[Long] =
     Try { User.create(user) }

   def findByName(name: String)(implicit dbSession: DBSession = AutoSession): Try[Option[User]] =
     Try { User.where('name -> name).apply().headOption }

   def findByEmail(email: String)(implicit dbSession: DBSession = AutoSession): Try[Option[User]] =
     Try { User.where('email -> email).apply().headOption }
 }

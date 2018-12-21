package models

import java.time.ZonedDateTime
import scalikejdbc._
import skinny.orm._

case class User(
  id: Option[Long] = None,
  name: String,
  email: String,
  password: String,
  createAt: ZonedDateTime = ZonedDateTime.now(),
  updateAt: ZonedDateTime = ZonedDateTime.now()
)

 object User extends SkinnyCRUDMapper[User] {
   override def tableName = "users"
   override val columns = Seq("id", "name", "email", "password", "create_at", "update_at")
   override def defaultAlias: Alias[User] = createAlias("u")
   private def toNamedValues(record: User): Seq[(Symbol, Any)] = Seq(
     'name     -> record.name,
     'email    -> record.email,
     'password -> record.password,
     'createAt -> record.createAt,
     'updateAt -> record.updateAt
   )

   override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User =
     autoConstruct(rs, n)
   def create(user: User)(implicit session: DBSession): Long =
     createWithAttributes(toNamedValues(user): _*)
   def update(user: User)(implicit session: DBSession): Int =
     updateById(user.id.get).withAttributes(toNamedValues(user): _*)
 }

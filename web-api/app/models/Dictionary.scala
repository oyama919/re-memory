package models

import java.time.ZonedDateTime
import scalikejdbc._
import skinny.orm._

case class Dictionary(
  id: Option[Long] = None,
  user_id: Long,
  title: String,
  content: String,
  createAt: ZonedDateTime = ZonedDateTime.now(),
  updateAt: ZonedDateTime = ZonedDateTime.now(),
  user: Option[User] = None
)

object Dictionary extends SkinnyCRUDMapper[Dictionary] {
  override def tableName = "dictionaries"
  override val columns = Seq("id", "user_id", "title", "content", "create_at", "update_at")
  override def defaultAlias: Alias[Dictionary] = createAlias("d")

  belongsTo[User](User, (uf, u) => uf.copy(user = u)).byDefault

  private def toNamedValues(record: Dictionary): Seq[(Symbol, Any)] = Seq(
    'user_id     -> record.user_id,
    'title    -> record.title,
    'content -> record.content,
    'createAt -> record.createAt,
    'updateAt -> record.updateAt
  )

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Dictionary]): Dictionary =
    autoConstruct(rs, n, "user")
  def create(dictionary: Dictionary)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(dictionary): _*)
  def update(dictionary: Dictionary)(implicit session: DBSession): Int =
    updateById(dictionary.id.get).withAttributes(toNamedValues(dictionary): _*)
}

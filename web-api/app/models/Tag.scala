package models

import java.time.ZonedDateTime
import scalikejdbc._
import skinny.orm._

case class Tag(
  id: Option[Long] = None,
  title: String,
  createAt: ZonedDateTime = ZonedDateTime.now(),
  updateAt: ZonedDateTime = ZonedDateTime.now(),
)

object Tag extends SkinnyCRUDMapper[Tag] {
  override def tableName = "tags"
  override val columns = Seq("id", "title", "create_at", "update_at")
  override def defaultAlias: Alias[Tag] = createAlias("t")

  private def toNamedValues(record: Tag): Seq[(Symbol, Any)] = Seq(
    'title           -> record.title,
    'createAt        -> record.createAt,
    'updateAt        -> record.updateAt
  )

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Tag]): Tag =
    autoConstruct(rs, n)

  def create(tag: Tag)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(tag): _*)

  def update(tag: Tag)(implicit session: DBSession): Int =
    updateById(tag.id.get).withAttributes(toNamedValues(tag): _*)
}

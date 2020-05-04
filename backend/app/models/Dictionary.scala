package models

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import io.circe.{Encoder, Json}
import scalikejdbc._
import skinny.orm._

case class Dictionary(
  id: Option[Long] = None,
  user_id: Long,
  title: String,
  content: String,
  publish_setting: Boolean,
  createAt: ZonedDateTime = ZonedDateTime.now(),
  updateAt: ZonedDateTime = ZonedDateTime.now(),
  user: Option[User] = None
)

object Dictionary extends SkinnyCRUDMapper[Dictionary] {

  val f = DateTimeFormatter.ofPattern("yyyy/MM/dd")

  implicit object EncodeDictionary extends Encoder[Dictionary] {
    final def apply(d: Dictionary): Json = Json.obj(
      ("id", Json.fromLong(d.id.get)),
      ("user_id", Json.fromLong(d.user_id)),
      ("title", Json.fromString(d.title)),
      ("content", Json.fromString(d.content)),
      ("publish_setting", Json.fromBoolean(d.publish_setting)),
      ("createAt", Json.fromString(d.createAt.format(f))),
      ("updateAt", Json.fromString(d.updateAt.format(f))),
      ("user",
        Json.obj(
          ("id", Json.fromLong(d.user.get.id.get)),
          ("name", Json.fromString(d.user.get.name)),
          ("email", Json.fromString(d.user.get.email)),
        ))
    )
  }

  override def tableName = "dictionaries"

  override val columns = Seq("id",
    "user_id",
    "title",
    "content",
    "publish_setting",
    "create_at",
    "update_at")

  override def defaultAlias: Alias[Dictionary] = createAlias("d")

  belongsTo[User](User, (uf, u) => uf.copy(user = u)).byDefault

  private def toNamedValues(record: Dictionary): Seq[(Symbol, Any)] = Seq(
    'user_id -> record.user_id,
    'title -> record.title,
    'content -> record.content,
    'publish_setting -> record.publish_setting,
    'createAt -> record.createAt,
    'updateAt -> record.updateAt
  )

  override def extract(rs: WrappedResultSet,
    n: scalikejdbc.ResultName[Dictionary]): Dictionary =
    autoConstruct(rs, n, "user")

  def create(dictionary: Dictionary)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(dictionary): _*)

  def update(dictionary: Dictionary)(implicit session: DBSession): Int =
    updateById(dictionary.id.get).withAttributes(toNamedValues(dictionary): _*)
}

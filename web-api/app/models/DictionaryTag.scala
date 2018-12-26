package models

import java.time.ZonedDateTime
import scalikejdbc._
import skinny.orm._
import skinny.orm.feature._

case class DictionaryTag(
  id: Option[Long] = None,
  dictionaryId: Long,
  tagId: Long,
  createAt: ZonedDateTime = ZonedDateTime.now(),
  updateAt: ZonedDateTime = ZonedDateTime.now(),
  tag: Option[Tag] = None,
  dictionary: Option[Dictionary] = None
)

object DictionaryTag extends SkinnyCRUDMapper[DictionaryTag] {

  lazy val dictionary = Dictionary.createAlias("d")

  lazy val dictionaryRef = belongsToWithAliasAndFkAndJoinCondition[Dictionary](
    right = Dictionary -> dictionary,
    fk = "dictionary_id",
    on = sqls.eq(defaultAlias.dictionaryId, dictionary.id),
    merge = (dictionaryTag, getDictionary) => dictionaryTag.copy(dictionary = getDictionary)
  )

  lazy val tag = Tag.createAlias("t")

  lazy val tagRef = belongsToWithAliasAndFkAndJoinCondition[Tag](
    right = Tag -> tag,
    fk = "tagId",
    on = sqls.eq(defaultAlias.tagId, tag.id),
    merge = (dictionaryTag, getTag) => dictionaryTag.copy(tag = getTag)
  )

  lazy val allAssociations: CRUDFeatureWithId[Long, DictionaryTag] = joins(dictionaryRef, tagRef)

  override def tableName = "dictionary_tags"

  override def defaultAlias: Alias[DictionaryTag] = createAlias("dt")

  private def toNamedValues(record: DictionaryTag): Seq[(Symbol, Any)] = Seq(
    'dictionary_id   -> record.dictionaryId,
    'tag_id          -> record.tagId,
    'createAt        -> record.createAt,
    'updateAt        -> record.updateAt
  )

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[DictionaryTag]): DictionaryTag =
    autoConstruct(rs, n, "dictionary", "tag")

  def create(dictionary_tags: DictionaryTag)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(dictionary_tags): _*)

  def update(dictionary_tags: DictionaryTag)(implicit session: DBSession): Int =
    updateById(dictionary_tags.id.get).withAttributes(toNamedValues(dictionary_tags): _*)
}

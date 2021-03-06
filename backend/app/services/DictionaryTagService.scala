package services

import models.DictionaryTag
import scalikejdbc.{AutoSession, DBSession}

import scala.util.Try

trait DictionaryTagService {

  def create(dictionary: DictionaryTag)(implicit dbSession: DBSession = AutoSession): Try[Long]

  def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[DictionaryTag]]

  def findByDictionaryId(dictionaryId: String)(implicit dbSession: DBSession = AutoSession): Try[List[DictionaryTag]]

  def createDictionaryTags(dictionaryTags: Seq[DictionaryTag])(implicit dbSession: DBSession = AutoSession): Try[Seq[Long]]

  def editDictionaryTags(dictionaryTags: Seq[DictionaryTag], existsDictionaryTags: Seq[DictionaryTag])(implicit dbSession: DBSession = AutoSession): Try[Seq[Long]]
}

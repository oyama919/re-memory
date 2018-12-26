package services

import models.DictionaryTag
import scalikejdbc.{AutoSession, DBSession}

import scala.util.Try

trait DictionaryTagService {

  def create(dictionary: DictionaryTag)(implicit dbSession: DBSession = AutoSession): Try[Long]

  def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[DictionaryTag]]
}

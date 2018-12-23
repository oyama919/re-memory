package services

import models.Dictionary
import scalikejdbc.{AutoSession, DBSession}
import scala.util.Try

trait DictionaryService {
  def create(dictionary: Dictionary)(implicit dbSession: DBSession = AutoSession): Try[Long]

  def findById(dictionary_id: Long)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]]

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]]
}

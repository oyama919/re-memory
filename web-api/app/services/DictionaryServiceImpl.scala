package services

import javax.inject.Singleton
import models.Dictionary
import scalikejdbc.{AutoSession, DBSession}
import scala.util.Try

@Singleton
class DictionaryServiceImpl extends DictionaryService {
  def create(dictionary: Dictionary)(implicit dbSession: DBSession = AutoSession): Try[Long] =
    Try { Dictionary.create(dictionary) }

  def findById(dictionary_id: Long)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]] =
    Try { Dictionary.where('id -> dictionary_id).apply().headOption }

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]] =
    Try { Dictionary.where('title -> title).apply().headOption }
}

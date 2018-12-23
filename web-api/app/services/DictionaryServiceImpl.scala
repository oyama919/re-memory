package services

import javax.inject.Singleton
import models.Dictionary
import scalikejdbc.{AutoSession, DBSession}
import scala.util.Try

@Singleton
class DictionaryServiceImpl extends DictionaryService {
  def create(dictionary: Dictionary)(implicit dbSession: DBSession = AutoSession): Try[Long] =
    Try { Dictionary.create(dictionary) }

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]] =
    Try { Dictionary.where('title -> title).apply().headOption }
}

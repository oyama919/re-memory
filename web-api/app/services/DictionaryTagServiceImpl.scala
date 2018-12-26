package services

import javax.inject.Singleton
import models.DictionaryTag
import scalikejdbc._

import scala.util.Try

@Singleton
class DictionaryTagServiceImpl extends DictionaryTagService {

  override def create(favoriteMicroPost: DictionaryTag)(implicit dbSession: DBSession = AutoSession): Try[Long] = Try {
    DictionaryTag.create(favoriteMicroPost)
  }

  override def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[DictionaryTag]] = Try {
      DictionaryTag.where('id -> id).apply().headOption
  }
}

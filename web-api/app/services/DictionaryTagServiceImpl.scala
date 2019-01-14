package services

import javax.inject.Singleton
import models.DictionaryTag
import scalikejdbc._

import scala.util.Try

@Singleton
class DictionaryTagServiceImpl extends DictionaryTagService {

  override def create(dictionaryTag: DictionaryTag)(implicit dbSession: DBSession = AutoSession): Try[Long] = Try {
    DictionaryTag.create(dictionaryTag)
  }

  override def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[DictionaryTag]] = Try {
    DictionaryTag.where('id -> id).apply().headOption
  }

  def findByDictionaryId(dictionaryId: String)(implicit dbSession: DBSession = AutoSession): Try[List[DictionaryTag]] = Try {
    DictionaryTag.where('dictionary_id -> dictionaryId).apply()
  }

  override def createDictionaryTags(dictionaryTags: Seq[DictionaryTag])(implicit dbSession: DBSession = AutoSession): Try[Seq[Long]] = Try {
    dictionaryTags.map(dictionaryTag => DictionaryTag.create(dictionaryTag))
  }

  override def editDictionaryTags(dictionaryTags: Seq[DictionaryTag], existsDictionaryTags: Seq[DictionaryTag])(implicit dbSession: DBSession = AutoSession): Try[Seq[Long]] = Try {
    existsDictionaryTags.map(existsDictionaryTag => DictionaryTag.delete(existsDictionaryTag))
    dictionaryTags.map(dictionaryTag => DictionaryTag.create(dictionaryTag))
  }
}

package services

import javax.inject.Singleton
import models.Tag
import scalikejdbc._

import scala.util.Try

@Singleton
class TagServiceImpl extends TagService {

  override def create(favoriteMicroPost: Tag)(implicit dbSession: DBSession = AutoSession): Try[Long] = Try {
    Tag.create(favoriteMicroPost)
  }

  override def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Tag]] = Try {
      Tag.where('id -> id).apply().headOption
  }

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Tag]] = Try {
    Tag.where('title -> title).apply().headOption
  }
}

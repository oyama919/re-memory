package services

import forms.DictionaryForm
import javax.inject.Singleton
import models.Tag
import scalikejdbc._

import scala.util.{Success, Try}

@Singleton
class TagServiceImpl extends TagService {

  override def create(favoriteMicroPost: Tag)(implicit dbSession: DBSession = AutoSession): Try[Long] = Try {
    Tag.create(favoriteMicroPost)
  }

  override def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Tag]] = Try {
    Tag.where('id -> id).apply().headOption
  }

  override def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Tag]] = Try {
    Tag.where('title -> title).apply().headOption
  }

  override def createTag(maybeTag:Option[Tag],formTag:String):Try[Long] = {
    maybeTag match {
      case Some(tag) => Success(tag.id.get)
      case None => create(Tag(title = formTag))
    }
  }

  override def createTagFromForm(dictionaryForm:DictionaryForm):Seq[Try[Long]] = {
    dictionaryForm.tags.map(tag => findByTitle(tag)
      .flatMap(t => createTag(t, tag)))
  }
}

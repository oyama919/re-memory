package services

import forms.DictionaryForm
import models.Tag
import scalikejdbc.{AutoSession, DBSession}

import scala.util.Try

trait TagService {

  def create(dictionary: Tag)(implicit dbSession: DBSession = AutoSession): Try[Long]

  def findById(id: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Tag]]

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Tag]]

  def createTag(maybeTag: Option[Tag], formTag: String): Try[Long]

  def createTagFromForm(dictionaryForm:DictionaryForm):Seq[Try[Long]]

}

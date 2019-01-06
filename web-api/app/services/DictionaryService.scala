package services

import forms.DictionaryForm
import models.Dictionary
import scalikejdbc.{AutoSession, DBSession}

import scala.util.Try

trait DictionaryService {
  def create(dictionary: Dictionary)(implicit dbSession: DBSession = AutoSession): Try[Long]
  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]]
  def createNewDictionary(maybeNewDictionary: Option[Dictionary], dictionaryForm: DictionaryForm): Try[Dictionary]
}

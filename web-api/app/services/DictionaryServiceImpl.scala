package services

import forms.DictionaryForm
import javax.inject.Singleton
import models.Dictionary
import models.exception.AlreadyRegisteredException
import scalikejdbc.{AutoSession, DBSession}

import scala.util.{Failure, Success, Try}

@Singleton
class DictionaryServiceImpl extends DictionaryService {
  def create(dictionary: Dictionary)(implicit dbSession: DBSession = AutoSession): Try[Long] =
    Try {
      Dictionary.create(dictionary)
    }

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]] =
    Try {
      Dictionary.where('title -> title).apply().headOption
    }

  def createNewDictionary(maybeNewDictionary: Option[Dictionary], dictionaryForm: DictionaryForm): Try[Dictionary] = {
    maybeNewDictionary match {
      case Some(_) => Failure(new AlreadyRegisteredException("Exist.Title"))
      case None =>
        Success(Dictionary(None, dictionaryForm.user_id, dictionaryForm.title, dictionaryForm.content, dictionaryForm.publish_setting))
    }
  }
}

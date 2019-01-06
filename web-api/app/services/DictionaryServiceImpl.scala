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

  def findById(dictionary_id: Long)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]] =
    Try { Dictionary.where('id -> dictionary_id).apply().headOption }

  def findByTitle(title: String)(implicit dbSession: DBSession = AutoSession): Try[Option[Dictionary]] =
    Try {
      Dictionary.where('title -> title).apply().headOption
    }

  def newDictionary(maybeNewDictionary: Option[Dictionary], dictionaryForm: DictionaryForm): Try[Dictionary] = {
    maybeNewDictionary match {
      case Some(_) => Failure(new AlreadyRegisteredException("Exist.Title"))
      case None =>
        Success(Dictionary(None, dictionaryForm.user_id, dictionaryForm.title, dictionaryForm.content, dictionaryForm.publish_setting))
    }
  }
}

package controllers

import io.circe.generic.auto._
import io.circe.syntax._

case class ErrorMessage(val key: String, val message: String = "") {
  def toJson: String = ErrorMessage(key, message).asJson.noSpaces
}

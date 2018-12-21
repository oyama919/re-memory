package controllers

import io.circe.syntax._

case class ErrorMessage(val key: String, val message: String = "") {
  def toJson: String = ("key" -> key, "message" -> message).asJson.noSpaces
}

package controllers

import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  case class Data(id: Long, text: String, username: String, email: String)

  val data1 = Data(1, "test", "username", "test@example.com")

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(data1.asJson.noSpaces)
  }
}

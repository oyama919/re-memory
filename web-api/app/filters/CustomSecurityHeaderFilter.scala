package filters

import javax.inject.Inject

import akka.stream.Materializer
import play.api.http.HeaderNames

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.mvc._

class CustomSecurityHeaderFilter @Inject()(
  implicit
  val mat: Materializer
) extends Filter
    with HeaderNames {

  def apply(
    nextFilter: RequestHeader => Future[Result]
  )(requestHeader: RequestHeader): Future[Result] =
    nextFilter(requestHeader).map { result =>
      println(result)
      result.withHeaders(
        PRAGMA -> "no-cache",
        CACHE_CONTROL -> "no-cache, no-store",
        EXPIRES -> "-1"
      )
    }
}

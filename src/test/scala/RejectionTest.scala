import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import com.group.Rejection



class RejectionTest extends WordSpec with Matchers with ScalatestRouteTest {
  "The service required" should {
    "return a hello massage for GET requests to the hello path" in {
      Get("/hello") ~> Rejection.route ~> check {
        responseAs[String] shouldEqual "hello to akka-http"
      }
    }

    "return a 'PONG!' response for GET requests to /ping" in {
      Get("/ping") ~> Rejection.route ~> check {
        responseAs[String] shouldEqual "The path you requested [/ping] does not exist."
      }
    }
  }
}



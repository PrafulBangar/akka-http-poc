

import org.scalatest.{ Matchers, WordSpec }
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import com.group._

class TestKitExample extends WordSpec with Matchers with ScalatestRouteTest {

  "The service" should {

    "return a hello massage for GET requests to the root path" in {
      Get("/") ~> WebServerForDSL.route ~> check {
        responseAs[String] shouldEqual "Hello world"
      }
    }

    "return a 'PONG!' response for GET requests to /ping" in {

      Get("/ping") ~> WebServerForDSL.route ~> check {
        responseAs[String] shouldEqual "PONG!"
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/any") ~> WebServerForDSL.route ~> check {
        handled shouldBe false
      }
    }

    "error for crash" in {

      Get("/crash") ~> WebServerForDSL.route ~> check {
         assertTypeError("BOOM!")
      }
    }
  }
}

package com.group
import akka.http.javadsl.server.RejectionHandler._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import scala.io.StdIn
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import Directives._


case class ProperResponse(code: Int, message: String)

object Rejection {
 //def main(args: Array[String]) {
  implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher
    val route =
      path("hello") {
        get {
          complete("hello to akka-http")
        }
      }

    implicit def myRejectionHandler =
      RejectionHandler.newBuilder()
        .handleNotFound {
          extractUnmatchedPath { route =>
            complete((NotFound, s"The path you requested [$route] does not exist."))
          }
        }
        .result()


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  //}
}







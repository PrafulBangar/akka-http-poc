package com.group
import akka.http.javadsl.server.RejectionHandler._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import scala.io.StdIn
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._


object Rejection {
 def main(args: Array[String]) {
  implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher
    val route =
      path("hello") {
        get {
          complete("hello to akka-http")
        }
      }~
        path("divide") {
          complete((1 / 0).toString)
        }


   implicit def myExceptionHandler: ExceptionHandler =
     ExceptionHandler {
       case _: ArithmeticException =>
         extractUri { uri =>
           println(s"Request to $uri could not be handled normally")
           complete(HttpResponse(InternalServerError, entity = "Bad numbers, bad result!!!"))
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
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}







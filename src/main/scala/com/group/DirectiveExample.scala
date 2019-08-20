package com.group

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

object Directives extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher


  val route =
    path("order" / IntNumber) { id =>
      (get | put | post) { result =>
        result.complete(s"Received ${result.request.method.name} request for order $id")
      }
    } ~
      path("neworder" / IntNumber) { id =>
        (get | put | post) {
          extractMethod { m =>
            complete(s"Received ${m.name} request for order $id")
          }
        }
      }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
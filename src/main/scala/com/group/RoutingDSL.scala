package com.group

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpResponse }
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer



object WebServerForDSL  {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()


  val route =
    path("") {
      get {
       complete(HttpResponse(entity = "Hello world"))
      }
    } ~
      path("ping") {
        get {
          complete(HttpResponse(entity = "PONG!"))
        }
      } ~
      path("crash") {
        get {
          sys.error("BOOM!")
        }
      }
  Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

}


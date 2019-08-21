package com.group

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.ByteString
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol._



case class User(name: String, age: Int,sr:Int)

object User {
  implicit val userJsonFormat: RootJsonFormat[User] = jsonFormat3(User.apply)
}

object HighLevelAPI {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("MainHighLevelAPI")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    lazy val routes: Route =
     pathPrefix("users") {
        path("person1"){
          get {
            complete(
              User(name = "Praful Bangar", age = 21,sr=1)
            )
          }
        } ~
          path("person2"){
            get {
              complete(
                User(name = "Swapni Gosavi", age = 48,sr=2)
              )
            }
          } ~
          path("person3"){
            get {
              complete(
                User(name = "Anjali Sharma", age = 62,sr=3)
              )
            }
          }~
       path("unmarshelofbytestring"){
         get{
           val jsonByteString = ByteString("""{"name":"Hello"}""")
           val httpRequest = HttpRequest(HttpMethods.POST, entity = jsonByteString)
           complete(
            Unmarshal(httpRequest).to[String]
           )
         }
       }
     }
    Http().bindAndHandle(routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
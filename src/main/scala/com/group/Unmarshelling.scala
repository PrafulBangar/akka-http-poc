package com.group

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, MessageEntity}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.ByteString
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json._


object UnMarshalling {

  def main(args: Array[String]) {

    implicit val sys = ActorSystem("IntroductionToAkkaHttp")
    implicit val mat:Materializer = ActorMaterializer()


    val intFuture = Unmarshal("42").to[Int]
    val int = Await.result(intFuture, 1.second)
    println("int unmarshalling "+int)


    val boolFuture = Unmarshal("off").to[Boolean]
    val bool = Await.result(boolFuture, 1.second)
    println("off unmarshalling "+bool)


    val jsonByteString = ByteString("""{"name":"Hello"}""")
    val httpRequest = HttpRequest(HttpMethods.POST, entity = jsonByteString)
    val jsonDataUnmarshalledFuture = Unmarshal(httpRequest).to[String]
    val jsonDataUnmarshalled = Await.result(jsonDataUnmarshalledFuture, 1.second)
    println(jsonDataUnmarshalled)

    sys.terminate()

  }

}

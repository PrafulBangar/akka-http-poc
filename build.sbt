name := "Akka HTTP"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.23"
libraryDependencies +="com.typesafe.akka" %% "akka-stream" % "2.5.23"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.9"
libraryDependencies += "com.typesafe.akka" %% "akka-http-core" % "2.4.4"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.9"
libraryDependencies += "com.typesafe.akka" %% "akka-http-xml" % "10.1.9"
libraryDependencies += "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.0.3"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.19",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.9"
)

import com.typesafe.config.{Config, ConfigFactory}
import scala.collection.JavaConverters._

name := """re-memory"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.7"

val circeVersion = "0.9.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies ++= Seq(
  "org.scalikejdbc"        %% "scalikejdbc"                  % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-config"           % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-jsr310"           % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-test"             % "2.5.2" % Test,
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.6.+",
  "org.skinny-framework"   %% "skinny-orm"                   % "2.3.7",
  "ch.qos.logback"         % "logback-classic"               % "1.2.3",
  "mysql"                  % "mysql-connector-java"          % "6.0.6",
  "org.flywaydb"           %% "flyway-play"                  % "4.0.0"
)
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

lazy val envConfig = settingKey[Config]("env-config")

envConfig := {
  val env = sys.props.getOrElse("env", "dev")
  ConfigFactory.parseFile(file("env") / (env + ".conf"))
}

flywayLocations := envConfig.value.getStringList("flywayLocations").asScala
flywayDriver := envConfig.value.getString("jdbcDriver")
flywayUrl := envConfig.value.getString("jdbcUrl")
flywayUser := envConfig.value.getString("jdbcUserName")
flywayPassword := envConfig.value.getString("jdbcPassword")

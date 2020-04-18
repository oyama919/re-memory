import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

name := """re-memory"""
organization := "com.example"
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
val conf = ConfigFactory.parseFile(new File("./conf/application.conf"))
val circeVersion = "0.9.3"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.scalikejdbc"        %% "scalikejdbc"                  % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-config"           % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-jsr310"           % "2.5.2",
  "org.scalikejdbc"        %% "scalikejdbc-test"             % "2.5.2" % Test,
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.6.+",
  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "3.3.1",
  "org.skinny-framework"   %% "skinny-orm"                   % "2.3.7",
  "ch.qos.logback"         % "logback-classic"               % "1.2.3",
  "mysql"                  % "mysql-connector-java"          % "6.0.6",
  "org.flywaydb"           %% "flyway-play"                  % "4.0.0",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.springframework.security" % "spring-security-web" % "4.1.3.RELEASE"
)

flywayUrl := conf.getString("db.default.url")
flywayUser := conf.getString("db.default.user")
flywayPassword := conf.getString("db.default.password")
flywayDriver := conf.getString("db.default.driver")
flywayLocations := conf.getStringList("db.default.locations").asScala

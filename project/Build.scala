import sbt._
import sbt.Keys._

import com.typesafe.sbt.web.SbtWeb

object LiftProjectBuild extends Build {

  import BuildSettings._

  object Ver {
    val lift = "2.6"
    val lift_edition = "2-6"
    val jetty = "9.2.9.v20150224"
  }

  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  lazy val root = Project("SimpleProduct", file("."))
    .settings(liftAppSettings: _*)
    .settings(libraryDependencies ++=
      compile(
        "net.liftweb"       %% "lift-webkit"                   % Ver.lift,
        "net.liftweb"       %% "lift-mongodb-record"           % Ver.lift,
        "net.liftweb"       %% "lift-json"                     % Ver.lift,
        "net.liftmodules"   %% ("extras_"+Ver.lift_edition)    % "0.4",
        "net.databinder.dispatch" % "dispatch-core_2.10" % "0.11.0",
        "net.liftmodules"   %% ("mongoauth_"+Ver.lift_edition) % "0.6",
        "ch.qos.logback"    % "logback-classic"                % "1.1.2",
        "com.novus"         %% "salat"                         % "1.9.9",
        "org.specs2"        %% "specs2-core"                   % "3.6.4",
        "com.ning" % "async-http-client" % "1.7.0-RC1",
        "com.foursquare"    %% "rogue-field"                   % "2.5.0" intransitive(),
        "com.foursquare"    %% "rogue-core"                    % "2.5.1" intransitive(),
        "com.foursquare"    %% "rogue-lift"                    % "2.5.1" intransitive(),
        "com.foursquare"    %% "rogue-index"                   % "2.5.1" intransitive(),
        "org.eclipse.jetty" % "jetty-server"                   % Ver.jetty,
        "org.eclipse.jetty" % "jetty-webapp"                   % Ver.jetty,
        "org.ocpsoft.prettytime" % "prettytime" % "1.0.8.Final",
        "net.liftweb" %% "lift-mapper" % Ver.lift,
        "mysql" % "mysql-connector-java" % "5.1.12",
        "net.liftmodules"   %% ("omniauth_"+Ver.lift)  % "0.17",
        "net.ruippeixotog" %% "scala-scraper" % "0.1.2"
      ) ++
      provided(
        "org.webjars"       % "jquery"                         % "2.1.3",
        "org.webjars"       % "bootstrap"                      % "3.3.4"
      ) ++
      container("org.eclipse.jetty" % "jetty-webapp" % Ver.jetty) ++
      test("org.scalatest" %% "scalatest" % "2.2.4")
    )
    .enablePlugins(SbtWeb)
}

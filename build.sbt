import org.ensime.sbt.Plugin.Settings.ensimeConfig
import org.ensime.sbt.util.SExp._

name := "AttoPresentation"

description := "Presentation on Attoparsec"

organization in ThisBuild := "org.saegesser"

version in ThisBuild := "0.2"

scalaVersion in ThisBuild := "2.10.3"

ensimeConfig := sexp(
  key(":compiler-args"), sexp("-Ywarn-dead-code", "-Ywarn-shadowing"),
  key(":formatting-prefs"), sexp(
    key(":alignSingleLineCaseStatements"), true,
    key(":spaceInsideParentheses"), false
  )
)

licenses in ThisBuild += ("MIT", url("http://opensource.org/licenses/MIT"))

scalacOptions in ThisBuild ++= Seq(
	"-feature", 
	"-deprecation", 
	"-Ywarn-all", 
	"-Yno-adapted-args",
	"-Ywarn-value-discard", 
	"-Ywarn-numeric-widen",
	"-Ywarn-dead-code", 
	"-Xlint",
	"-Xfatal-warnings",
  "-unchecked"
)

lazy val intro = project.in(file("intro"))

lazy val traffic = project.in(file("traffic"))

//lazy val spire = project.in(file("spire")).dependsOn(core)

//lazy val example = project.in(file("example")).dependsOn(core, spire)

// Bintray
//seq(bintrayPublishSettings:_*)

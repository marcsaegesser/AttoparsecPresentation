import org.ensime.sbt.Plugin.Settings.ensimeConfig
import org.ensime.sbt.util.SExp._

name := "traffic"

ensimeConfig := sexp(
  key(":compiler-args"), sexp("-Ywarn-dead-code", "-Ywarn-shadowing"),
  key(":formatting-prefs"), sexp(
    key(":alignSingleLineCaseStatements"), true,
    key(":spaceInsideParentheses"), false
  )
)

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.0.2",
  "joda-time"      %  "joda-time"   % "2.3",
  "com.github.nscala-time" %% "nscala-time" % "0.8.0",
  "org.spire-math" %% "spire"       % "0.6.0",
  "org.scalacheck" %% "scalacheck"  % "1.10.1" % "test"
)

initialCommands :=
  """import scalaz._
     import Scalaz._
     import atto._
     import Atto._
     import org.saegesser.traffic._
     import Traffic._
     import TrafficParser._"""


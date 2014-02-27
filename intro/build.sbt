name := "intro"

//resolvers += "tpolecat" at "http://dl.bintray.com/tpolecat/maven"

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.0.2",
  "org.spire-math" %% "spire" % "0.6.0",
//  "org.tpolecat" %% "atto" % "0.1",
  "org.scalacheck" %% "scalacheck"  % "1.10.1" % "test"
)

initialCommands :=
  """import scalaz._
     import Scalaz._
     import atto._
     import Atto._"""


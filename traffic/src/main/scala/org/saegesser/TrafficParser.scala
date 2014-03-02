package org.saegesser.traffic

import scalaz._
import atto._
import Atto._

import org.joda.time._

object TrafficParser {
  import Traffic._
  import scalaz.syntax.applicative._

  val whiteSpace: Parser[List[Char]] = many1(elem(_.isWhitespace, "whiteSpace"))
  def quoted[A](p: Parser[A]): Parser[A] = (char('"') ~> p <~ char('"'))
  def commaSep[A](p: Parser[A]): Parser[List[A]] = sepBy(p, char(','))
  def whiteSep[A](p: Parser[A]): Parser[List[A]] = sepBy(p, whiteSpace)
  val quotedString: Parser[String] = quoted(stringOf(notChar('"')))

  object DateTimeBuilder {
    def apply(month: Int, day: Int, year: Int, hour: Int, min: Int, sec: Int) = new DateTime(year, month, day, hour, min, sec)
  }

  val dateTimeP = (int <~ char('/') |@| int <~ char('/') |@| int <~ char(' ') |@| int <~ char(':') |@| int <~ char(':') |@| int)(DateTimeBuilder.apply)

  val pointP = (double <~ char(',') |@| double)(LatLong.apply)
  val pointsP = opt(whiteSpace) ~> whiteSep(pointP) <~ opt(whiteSpace)

  val trafficP = for {
    id <- quoted(int) <~ whiteSpace
    speed <- quoted(float) <~ whiteSpace
    travelTime <- quoted(int) <~ whiteSpace
    _ <- quoted(int) <~ whiteSpace
    dateTime <- quoted(dateTimeP) <~ whiteSpace
    _ <- quoted(int) <~ whiteSpace
    points <- quoted(pointsP) <~ whiteSpace
    encodedLine <- quotedString <~ whiteSpace
    encodedLevels <- quotedString <~ whiteSpace
    owner <- quotedString <~ whiteSpace
    transcomId <- quotedString <~ whiteSpace
    borough <- quotedString <~ whiteSpace
    linkName <- quotedString <~ opt(whiteSpace)
  } yield TrafficData(id, speed, travelTime, dateTime, points, encodedLine, encodedLevels, owner, transcomId, borough, linkName)
}

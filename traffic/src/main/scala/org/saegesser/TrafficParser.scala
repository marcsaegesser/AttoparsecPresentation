package org.saegesser.traffic

import scalaz._
import atto._
import Atto._

import org.joda.time._

object TrafficParser {
  import Traffic._
  import scalaz.syntax.applicative._

  def whiteSpace: Parser[List[Char]] = many1(elem(_.isWhitespace, "whiteSpace"))
  def quoteWrapped[A](p: Parser[A]): Parser[A] = (char('"') ~> p <~ char('"'))
  def commaSep[A](p: Parser[A]): Parser[List[A]] = sepBy(p, char(','))
  def whiteSep[A](p: Parser[A]): Parser[List[A]] = sepBy(p, whiteSpace)
  val anyString: Parser[String] = many(notChar('"')).map(_.mkString)

  object DateTimeBuilder {
    def apply(month: Int, day: Int, year: Int, hour: Int, min: Int, sec: Int) = new DateTime(year, month, day, hour, min, sec)
  }

  val dateTimeP = (int <~ char('/') |@| int <~ char('/') |@| int <~ char(' ') |@| int <~ char(':') |@| int <~ char(':') |@| int)(DateTimeBuilder.apply)

  val pointP = (double <~ char(',') |@| double)(LatLong.apply)
  val pointsP = whiteSep(pointP)

  val trafficP = for {
    id <- quoteWrapped(int) <~ whiteSpace
    speed <- quoteWrapped(float) <~ whiteSpace
    travelTime <- quoteWrapped(int) <~ whiteSpace
    _ <- quoteWrapped(int) <~ whiteSpace
    dateTime <- quoteWrapped(dateTimeP) <~ whiteSpace
    _ <- quoteWrapped(int) <~ whiteSpace
    points <- quoteWrapped(pointsP) <~ whiteSpace
    encodedLine <- quoteWrapped(anyString) <~ whiteSpace
    encodedLevels <- quoteWrapped(anyString) <~ whiteSpace
    owner <- quoteWrapped(anyString) <~ whiteSpace
    transcomId <- quoteWrapped(anyString) <~ whiteSpace
    borough <- quoteWrapped(anyString) <~ whiteSpace
    linkName <- quoteWrapped(anyString) <~ opt(whiteSpace)
  } yield TrafficData(id, speed, travelTime, dateTime, points, encodedLine, encodedLevels, owner, transcomId, borough, linkName)
}

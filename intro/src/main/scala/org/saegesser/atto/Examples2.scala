package org.saegesser.atto

import scalaz._
import atto._
import Atto._

object Examples2 {
  // The following two parsers provide identical results.
  // The first parses the space separaters into a unused value
  val stringSepDigitP =
    for {
      first <- stringOf(letter)
      _ <- many1(spaceChar)
      second <- int
    } yield (first, second)

  // The second uses the discardRight combinator
  val stringDigitP =
    for {
      first <- stringOf(letter) <~ many1(spaceChar)
      second <- int
    } yield (first, second)

  import scalaz.syntax.applicative._
  case class Fubar(fu: String, bar: Int)
  val fubarP = ( stringOf(letter) <~ many1(spaceChar) |@| int){Fubar.apply}

  // Parse a list of comma separated list of ints and int ranges
  def commaSep[A](p: Parser[A]): Parser[List[A]] = sepBy(p, char(','))
  val intRangeP = (int <~ string("..") |@| int)(Range.apply) map (_.toList)
  val intP = int map (List(_))
  val intListP = commaSep(intRangeP | intP) map (_.flatten)
}

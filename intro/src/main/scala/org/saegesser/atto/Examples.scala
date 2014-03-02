package org.saegesser.atto

import scalaz._
import atto._
import Atto._

/** These are same examples of very simple parsers.  Paste this code into a Scala Worksheet in
  * Eclipse to experiment with them.
  */
object Examples {
  // Single element parsers
  char('a').parseOnly("abc") // Done(bc,a) -- Matches the single character 'a'
  char('.').parseOnly(".abc") // Done(abc,.) -- char matches any character, not just letters
  letter.parseOnly("abc") // Done(bc,a) -- matches letter 'a'
  notChar('a').parseOnly("def") // Done(ef,d)
  digit.parseOnly("123") // Done(23,1) -- digit matches any digit
  letterOrDigit.parseOnly("abc") // Done(bc,a)

  // Text parsers
  string("abc").parseOnly("abcdef") // Done(def,abc) -- Parses the string "abc"
  stringOf(char('a')).parseOnly("aaaaabbbbbcccc") // Done(bbbbbcccc,aaaaa) -- Match 0 or more repeated 'a'
  take(5).parseOnly("1234567890") // Done(67890,12345) -- Take the next 5 characters

  // Numeric parsers
  int.parseOnly("123DegF") // Done(DegF,123)
  double.parseOnly("123.4567 ft") // Done(ft, 123.4567)
  bigDecimal.parseOnly("123456789123456789.0987654321") // Done(,123456789123456789.0987654321)

  // Combinators
  // many
  many(char('a')).parseOnly("aaaaabcdefg") // Done(bcdefg, List(a, a, a, a, a)) -- Match zero or more 'a'
  many(char('a')).parseOnly("bcdefg") // Done(bcdefg, List()) -- No leading 'a' so get empty List[Char]
  many(char('a')).map(_.mkString).parseOnly("aaaaabcdefg") // Done(bcdefg, aaaaa) -- Map List[Char] to String
  many1(char('a')).parseOnly("aaaaabcdefg") // Done(bcdefg, List(a, a, a, a, a)) -- Match one or more 'a'
  many(char('a')).parseOnly("bcdefg") // Fail(bcdefg,List(),Failure reading:'a') -- No leading 'a' so Fail

  // andThen
  (stringOf(letter) ~ stringOf(digit)).parseOnly("abc123") // Done(,(abc,123)) -- Parses into a Tuple2
  (stringOf(letter) ~ many1(spaceChar) ~ int).parseOnly("abc   123") // Done(,((abc,List( ,  ,  )),123)) -- ((String, String), Int)

  // orElse
  (stringOf1(letter) | int).parseOnly("abc") // Done(,abc)
  (stringOf1(letter) | int).parseOnly("123") // Done(,123)

  // either
  (stringOf1(letter) || int).parseOnly("abc") // Done(,-\/(abc))
  (stringOf1(letter) || int).parseOnly("123") // Done(,\/-(123))

  // Choice
  (int ~ choice(char('F'), char('C'), char('K'))).parseOnly("100F") // Done(,(100,F))
  (int ~ choice(char('F'), char('C'), char('K'))).parseOnly("100C") // Done(,(100,C))

  // sepBy
  sepBy(stringOf(letterOrDigit), char(',')).parseOnly("abc,123,a1b2") // Done(,List(abc,123,a1b2))
  sepBy(stringOf(letterOrDigit), many(spaceChar) ~ char(',') ~ many(spaceChar)).parseOnly("abc, 123 , a1b2") // Done(,List(abc,123,a1b2))


  // For comprehension (flatMap and map)
  (for {
    first <- stringOf(letter)
    _ <- many1(spaceChar)
    second <- int
  } yield (first, second)).parseOnly("abc   123") // Done(,(abc,123))

  // Ignore left
  (for {
    first <- stringOf(letter) <~ many1(spaceChar)
    second <- int
  } yield (first, second)).parseOnly("abc   123") // Done(,(abc,123))
}

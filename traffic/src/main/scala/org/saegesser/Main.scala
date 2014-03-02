package org.saegesser.traffic

import scala.io._

object TrafficExample extends App {
  def trafficPrinter(data: Traffic.TrafficData) = println(data.toString)

  val trafficStream = new TrafficStream
  trafficStream.subscribe(trafficPrinter)

  val input = Source.fromFile("traffic/data.txt")
  input grouped(256) map (_.mkString) foreach { println("fubar"); trafficStream.feed(_) }
  trafficStream.done
}

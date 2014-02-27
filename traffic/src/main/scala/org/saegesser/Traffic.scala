package org.saegesser.traffic

import org.joda.time._

object Traffic {
  case class LatLong(lat: Double, long: Double)

  case class TrafficData(id: Int, speed: Float, travelTime: Int, dateTime: DateTime, points: List[LatLong], encodedPoints: String, encodedLevels: String, owner: String, transcomId: String, borough: String, linkName: String)
}

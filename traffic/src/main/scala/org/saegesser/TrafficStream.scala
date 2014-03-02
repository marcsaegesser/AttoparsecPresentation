package org.saegesser.traffic

import scalaz._
import atto._
import Atto._

class TrafficStream {
  import Traffic._
  import TrafficParser._
  type TrafficResultCallback = (TrafficData) => Unit

  private var subscriptions = List.empty[TrafficResultCallback]
  private var state = trafficP.parse("")

  def subscribe(cb: TrafficResultCallback) = subscriptions = cb :: subscriptions
  def publish(t: TrafficData) = subscriptions foreach { _(t) }

  import ParseResult._
  private def handler(result: ParseResult[TrafficData]) = result match {
    case Done(i, r) => publish(r); trafficP.parse(i)
    case r: ParseResult[TrafficData] => r
  }

  def feed(s: String) = state = handler(state.feed(s))

  def done() = state = handler(state.done)
}










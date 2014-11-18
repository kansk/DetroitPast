package com.github.wkennedy.detroitpast.rest

import com.foursquare.rogue.LatLong
import com.github.wkennedy.detroitpast.record.Place
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

object PlaceAPI extends RestHelper {

  def init() : Unit = {
    LiftRules.statelessDispatch.append(PlaceAPI)
  }

  val pos = LatLong(42.369512, -83.077464)
  val additionalInfo = Map("architect" -> "Albert Kahn")
  val somePlace = Place.createRecord.name("Fisher Building").year(1928).loc(pos).additionalInformation(additionalInfo).save(safe = true)

  // curl -H 'Content-type: text/json' http://127.0.0.1:8080/place
  serve {
    case "place" :: Nil JsonGet _ =>
      println(somePlace)
      Place.findAll : JValue
  }

}
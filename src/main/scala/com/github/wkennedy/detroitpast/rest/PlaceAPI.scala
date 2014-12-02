package com.github.wkennedy.detroitpast.rest

import com.foursquare.rogue.LatLong
import com.github.wkennedy.detroitpast.models.Place
import com.github.wkennedy.detroitpast.record.PlaceRecord
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
  //{"name":"Fisher Building","year":1928,"additionalInformation":{"architect":"Albert Kahn"},"lat":42.36,"long":-83.077}
  //  val somePlace = Place("Fisher Building", 1928, additionalInfo, 42.36, -83.077)
//  val somePlace = Place.createRecord.name("Fisher Building").year(1928).loc(pos).additionalInformation(additionalInfo).save(safe = true)

  serve {
    // curl -H 'Content-type: text/json' http://127.0.0.1:8080/place
    //    case "place" :: "id" :: placeId :: Nil JsonGet _ =>
          // val placeBox = PlaceRecord.find(placeId)

    case "placeAll" :: Nil JsonGet _ =>
      PlaceRecord.findAll : JValue

    //curl -d '{"name":"McDonalds","year":1965,"additionalInformation":{"architect":"Ronald McDonald"},"lat":32.36,"long":-93.077}' -X POST -H 'Content-type: application/json' http://127.0.0.1:8080/savePlace
    case "savePlace" :: Nil JsonPost json->request =>
      println(json)
      val place = json.extract[Place]
      val placeRecord = PlaceRecord.createRecord.name(place.name).year(place.year).loc(LatLong(place.lat, place.long)).additionalInformation(place.additionalInformation).save(safe = true)
      println(place)
      placeRecord : JValue
  }

}




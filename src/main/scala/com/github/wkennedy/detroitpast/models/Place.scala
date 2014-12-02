package com.github.wkennedy.detroitpast.models

import net.liftweb.json._
import net.liftweb.mongodb.{JsonObjectMeta, JsonObject}
import scala.language.implicitConversions

case class Place(name: String, year: Int, additionalInformation: Map[String, String], lat: Double, long: Double) extends JsonObject[Place] {
  def meta = Place
}
object Place extends JsonObjectMeta[Place] {
  implicit val formats = DefaultFormats
  implicit def toJson(place: Place): JValue =  Extraction decompose place
  implicit def toJson(places: Seq[Place]): JValue = Extraction decompose places
}


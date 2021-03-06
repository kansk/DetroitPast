package com.github.wkennedy.detroitpast.record

import com.foursquare.rogue._
import net.liftweb.json.{Extraction, DefaultFormats, JValue}
import net.liftweb.mongodb.{JsonObjectMeta, JsonObject}
import net.liftweb.mongodb.record.field.{MongoCaseClassField, MongoMapField, ObjectIdPk}
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.record.field.{OptionalIntField, StringField}

import scala.language.implicitConversions

class PlaceRecord private () extends MongoRecord[PlaceRecord] with ObjectIdPk[PlaceRecord] {
  def meta = PlaceRecord
  object name extends StringField(this, 1024)
  object year extends OptionalIntField(this)
  object additionalInformation extends MongoMapField[PlaceRecord, String](this)
  object loc extends MongoCaseClassField[PlaceRecord, LatLong](this)
}

object PlaceRecord extends PlaceRecord with MongoMetaRecord[PlaceRecord] {
  import net.liftweb.mongodb.BsonDSL._
  createIndex(loc.name -> "2d", unique=true)
  override def collectionName = "places"

  implicit def toJson(place: PlaceRecord): JValue = place.asJValue
  implicit def toJson(places: Seq[PlaceRecord]): JValue = places map { _.asJValue }
}

case class Place(name: String, year: Int, additionalInformation: Map[String, String], lat: Double, long: Double) extends JsonObject[Place] {
  def meta = Place
}

object Place extends JsonObjectMeta[Place] {
  implicit val formats = DefaultFormats
  implicit def toJson(place: Place): JValue =  Extraction decompose place
  implicit def toJson(places: Seq[Place]): JValue = Extraction decompose places
}

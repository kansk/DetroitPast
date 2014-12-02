package com.github.wkennedy.detroitpast.record

import com.foursquare.rogue._
import net.liftweb.json.JValue
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

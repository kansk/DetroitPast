package com.github.wkennedy.detroitpast.record

import net.liftweb.json.JValue
import net.liftweb.mongodb.record.field.{MongoMapField, MongoListField, ObjectIdPk, MongoCaseClassField}
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord, BsonMetaRecord, BsonRecord}
import net.liftweb.record.field.{OptionalIntField, IntField, StringField}
import com.foursquare.rogue._

import scala.language.implicitConversions

class Place private () extends MongoRecord[Place] with ObjectIdPk[Place] {
  def meta = Place
  object name extends StringField(this, 1024)
  object year extends OptionalIntField(this)
  object additionalInformation extends MongoMapField[Place, String](this)
  object loc extends MongoCaseClassField[Place, LatLong](this)
}

object Place extends Place with MongoMetaRecord[Place] {
  import net.liftweb.mongodb.BsonDSL._
  createIndex(loc.name -> "2d", unique=true)
  override def collectionName = "places"

  implicit def toJson(place: Place): JValue = place.asJValue

  implicit def toJson(places: Seq[Place]): JValue = places map { _.asJValue }
}

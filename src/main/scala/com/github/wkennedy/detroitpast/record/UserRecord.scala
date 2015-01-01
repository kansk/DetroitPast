package com.github.wkennedy.detroitpast.record

import com.wix.accord.Validator
import com.wix.accord.dsl._
import net.liftweb.common.Loggable
import net.liftweb.json._
import net.liftweb.mongodb.record.field.ObjectIdPk
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.mongodb.{JsonObject, JsonObjectMeta}
import net.liftweb.record.field.{EmailField, PasswordField, StringField}

import scala.language.implicitConversions

class UserRecord private() extends MongoRecord[UserRecord] with ObjectIdPk[UserRecord] with Loggable {
  def meta = UserRecord
  object firstName extends StringField(this, 1024)
  object lastName extends StringField(this, 1024)
  object email extends EmailField(this, 512)
  object password extends PasswordField(this)
  object role extends StringField(this, 128)
  object apiKey extends PasswordField(this)
}

object UserRecord extends UserRecord with MongoMetaRecord[UserRecord] {
  import net.liftweb.mongodb.BsonDSL._
  createIndex(email.name -> 1, unique=true)
  override def collectionName = "users"

  def uuid = java.util.UUID.randomUUID.toString

  implicit def toJson(user: UserRecord): JValue = user.asJValue
  implicit def toJson(users: Seq[UserRecord]): JValue = users map { _.asJValue }
}

case class User(firstName: Option[String], lastName: Option[String], email: String, password: String, role: Option[String]) extends JsonObject[User] {
  def meta = User
}

object User extends JsonObjectMeta[User] {
  implicit val formats = DefaultFormats
  implicit def toJson(place: Place): JValue = Extraction decompose place
  implicit def toJson(places: Seq[Place]): JValue = Extraction decompose places

  implicit val userValidator: Validator[User] =
  validator[User] {
    user =>
      user.email as "Email must have a value" is notEmpty
      user.password.length as "Password length must be greater than 5" should be > 5
  }
}
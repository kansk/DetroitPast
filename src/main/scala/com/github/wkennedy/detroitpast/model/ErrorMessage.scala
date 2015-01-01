package com.github.wkennedy.detroitpast.model

import net.liftweb.mongodb.{JsonObject, JsonObjectMeta}

case class ErrorMessage(userMessage:String, developerMessage: String, code: Int, additionalInfo: Option[String]) extends JsonObject[ErrorMessage] with Serializable {
  def meta = ErrorMessage
}

object ErrorMessage extends JsonObjectMeta[ErrorMessage] {

}


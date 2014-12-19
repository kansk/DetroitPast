package com.github.wkennedy.detroitpast.rest

import com.github.wkennedy.detroitpast.model.Role
import com.github.wkennedy.detroitpast.record.{User, UserRecord}
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

import scala.language.implicitConversions

object UserAPI extends RestHelper with Loggable {

  def init(): Unit = {
    LiftRules.statelessDispatch.append(UserAPI)
  }

  serve("api" / "v1" / "users" prefix {
    case Nil JsonPost json -> request =>
      val user = json.extract[User]
      val userRecord = UserRecord.createRecord.firstName(user.firstName).lastName(user.lastName).password(user.password).email(user.email).save(safe = true)
      userRecord: JValue

    case _ Options req =>
      "200": JValue
  })

  //TODO add validation
  serve("api" / "v1" / "users" / "register" prefix {
    case Nil JsonPost json -> request =>
      val user = json.extract[User]
      val userRecord = UserRecord.createRecord.firstName(user.firstName).lastName(user.lastName).password(user.password).email(user.email).role(Role.user.toString).save(safe = true)
      userRecord: JValue

    case _ Options req =>
      "200": JValue
  })

}

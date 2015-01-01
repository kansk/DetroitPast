package com.github.wkennedy.detroitpast.rest

import com.github.wkennedy.detroitpast.model.Role
import com.github.wkennedy.detroitpast.record.{User, UserRecord}
import com.wix.accord._
import net.liftweb.common._
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

  serve("api" / "v1" / "users" / "register" prefix {
    case Nil JsonPost json -> request =>
      val user = json.extract[User]
      (validate(user) match {
        case com.wix.accord.Failure(rules) =>
          val violationMessages = for (ruleViolation <- rules) yield ruleViolation.description.getOrElse("Unknown Validation Error")
          ParamFailure(violationMessages mkString "/",
            Empty, Empty, 500)
        case Success => val userRecord: JValue = UserRecord.createRecord
          .firstName(user.firstName).lastName(user.lastName).password(user.password).email(user.email).role(Role.user.toString).apiKey(UserRecord.uuid)
          .save(safe = true)
          Full(userRecord)
      }): Box[JValue]

    case _ Options req =>
      "200": JValue
  })

}

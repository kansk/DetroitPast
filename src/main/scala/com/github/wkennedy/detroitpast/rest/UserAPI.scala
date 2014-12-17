package com.github.wkennedy.detroitpast.rest

import com.github.wkennedy.detroitpast.model.Role
import com.github.wkennedy.detroitpast.record.{UserRecord, User}
import net.liftweb.http.LiftRules
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

object UserAPI extends RestHelper {

  def init(): Unit = {
    LiftRules.statelessDispatch.append(UserAPI)
  }

  serve("api" / "v1" / "users" prefix {
    case Nil JsonPost json -> request =>
      val user = json.extract[User]
      val userRecord = UserRecord.createRecord.firstName(user.firstName).lastName(user.lastName).password(user.password).email(user.email).save(safe = true)
      userRecord: JValue


  })

  serve("api" / "v1" / "users" / "register" prefix {
    case Nil JsonPost json -> request =>
      val user = json.extract[User]
      val userRecord = UserRecord.createRecord.firstName(user.firstName).lastName(user.lastName).password(user.password).email(user.email).role(Role.user.toString).save(safe = true)
      userRecord: JValue

    case _ Options req =>
      println("In OPTIONS")
      "200": JValue
  })

}

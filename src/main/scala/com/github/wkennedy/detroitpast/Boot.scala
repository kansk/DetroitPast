package com.github.wkennedy.detroitpast

import com.github.wkennedy.detroitpast.model.Role
import com.github.wkennedy.detroitpast.model.Role.rolesHierarchy
import com.github.wkennedy.detroitpast.record.UserRecord
import com.github.wkennedy.detroitpast.rest.{UserAPI, VersionHelper, PlaceAPI}
import com.mongodb.{MongoClient, ServerAddress}
import net.liftweb.common.{Full, Loggable, Empty}
import net.liftweb.http._
import net.liftweb.http.auth.{userRoles, AuthRole, HttpBasicAuthentication}
import net.liftweb.mongodb.MongoDB
import net.liftweb.util.DefaultConnectionIdentifier

class Boot extends Bootable with Loggable {

  System.setProperty("DEBUG.MONGO", "true")
  System.setProperty("DB.TRACE", "true")

  def boot() {

    connectDB()

    // Todo: create default admin
    //UserRecord.createRecord.firstName("Test").lastName("Guy3").email("testguy3@yahoo.com").password("test1234").role(Role.admin.toString).save(safe = true)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    LiftRules.supplementalHeaders.default.set(
      List(("X-Lift-Version", LiftRules.liftVersion),
        ("Access-Control-Allow-Origin", "*"),
        ("Access-Control-Allow-Credentials", "true"),
        ("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH"),
        ("Access-Control-Allow-Headers", "Cookie, Host, X-Forwarded-For, Accept-Charset, If-Modified-Since, Accept-Language, X-Forwarded-Port, Connection, X-Forwarded-Proto, User-Agent, Referer, Accept-Encoding, X-Requested-With, Authorization, Accept, Content-Type")
      ))

    //TODO finish URL security mapping
    def protection: LiftRules.HttpAuthProtectedResourcePF = {
//      case Req("api" :: "v1" :: "users" :: "authenticate" :: Nil, _, PostRequest) => rolesHierarchy.getRoleByName(Role.user.toString)
      case Req("api" :: "v1" :: "places" :: Nil, _, PostRequest) => rolesHierarchy.getRoleByName(Role.user.toString)
    }

    LiftRules.httpAuthProtectedResource.append {
      protection
    }

    //TODO bad password results in a S.? issue in Boot
    LiftRules.authentication = HttpBasicAuthentication("DetroitPast") {
      case (userEmail, userPass, _) =>
        logger.debug("Attempting to authenticate: " + userEmail)
        UserRecord.find("email", userEmail).map {
          userRecord =>
            if (userRecord.password.match_?(userPass)) {
              userRoles(AuthRole(userRecord.role.get))
              logger.debug("User: " + userEmail + " IS authenticated")
              true
            } else {
              logger.debug("User NOT authenticated")
              false
            }
        } openOr
          logger.debug("No user record found.  User NOT authenticated.")
        false
    }

    VersionHelper.init()
    UserAPI.init()
    PlaceAPI.init()
  }

  def connectDB(): Unit = {
    val server = new ServerAddress("127.0.0.1", 27017)
    MongoDB.defineDb(DefaultConnectionIdentifier, new MongoClient(server), "DetroitPastDB")
  }

}

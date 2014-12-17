package com.github.wkennedy.detroitpast

import com.github.wkennedy.detroitpast.model.Role
import com.github.wkennedy.detroitpast.record.UserRecord
import com.github.wkennedy.detroitpast.rest.{UserAPI, VersionHelper, PlaceAPI}
import com.mongodb.{MongoClient, ServerAddress}
import net.liftweb.common.Full
import net.liftweb.http._
import net.liftweb.http.auth.{userRoles, AuthRole, HttpBasicAuthentication}
import net.liftweb.http.js.yui.YUIArtifacts
import net.liftweb.http.provider.HTTPParam
import net.liftweb.mongodb.MongoDB
import net.liftweb.sitemap._
import net.liftweb.util.DefaultConnectionIdentifier

class Boot extends Bootable {

  System.setProperty("DEBUG.MONGO", "true")
  System.setProperty("DB.TRACE", "true")

  def boot() {

    connectDB()

    // Todo: create default admin
    // UserRecord.createRecord.firstName("John").lastName("Doe").email("john.doe@yahoo.com").password("test1234").role(Role.admin.toString).save(safe = true)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    val roles =
      AuthRole(Role.admin.toString).addRoles(
        AuthRole(Role.site_admin.toString).addRoles(
          AuthRole(Role.editor.toString).addRoles(
            AuthRole(Role.user.toString))))

    //TODO finish URL security mapping
//    LiftRules.httpAuthProtectedResource.prepend {
//      case Req("api" :: Nil, _, _) => roles.getRoleByName(Role.user.toString)
//      case Req("api" :: _, _, _) => roles.getRoleByName(Role.user.toString)
//    }
//
//    LiftRules.authentication = HttpBasicAuthentication("DetroitPast") {
//      case (userEmail, userPass, _) =>
//        UserRecord.find("email", userEmail).map {
//          userRecord =>
//            if (userRecord.password.match_?(userPass)) {
//              userRoles(AuthRole(userRecord.role.get))
//              true
//            } else {
//              false
//            }
//        } openOr false
//    }

    LiftRules.supplementalHeaders.default.set(
      List(("X-Lift-Version", LiftRules.liftVersion),
        ("Access-Control-Allow-Origin", "*"),
        ("Access-Control-Allow-Credentials", "true"),
        ("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH"),
        ("Access-Control-Allow-Headers", "Cookie, Host, X-Forwarded-For, Accept-Charset, If-Modified-Since, Accept-Language, X-Forwarded-Port, Connection, X-Forwarded-Proto, User-Agent, Referer, Accept-Encoding, X-Requested-With, Authorization, Accept, Content-Type")
      ))

    VersionHelper.init()
    PlaceAPI.init()
    UserAPI.init()
  }

  def connectDB(): Unit = {
    val server = new ServerAddress("127.0.0.1", 27017)
    MongoDB.defineDb(DefaultConnectionIdentifier, new MongoClient(server), "DetroitPastDB")
  }

}

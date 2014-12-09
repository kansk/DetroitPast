package com.github.wkennedy.detroitpast

import com.github.wkennedy.detroitpast.rest.{VersionHelper, PlaceAPI}
import com.mongodb.{MongoClient, ServerAddress}
import net.liftweb.http._
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

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    LiftRules.supplimentalHeaders = s => s.addHeaders(
      List(HTTPParam("X-Lift-Version", LiftRules.liftVersion),
        HTTPParam("Access-Control-Allow-Origin", "*"),
        HTTPParam("Access-Control-Allow-Credentials", "true"),
        HTTPParam("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH"),
        HTTPParam("Access-Control-Allow-Headers", "Cookie, Host, X-Forwarded-For, Accept-Charset, If-Modified-Since, Accept-Language, X-Forwarded-Port, Connection, X-Forwarded-Proto, User-Agent, Referer, Accept-Encoding, X-Requested-With, Authorization, Accept, Content-Type")
      ))

    VersionHelper.init()
    PlaceAPI.init()
  }

  def connectDB(): Unit = {
    val server = new ServerAddress("127.0.0.1", 27017)
    MongoDB.defineDb(DefaultConnectionIdentifier, new MongoClient(server), "DetroitPastDB")
  }

}

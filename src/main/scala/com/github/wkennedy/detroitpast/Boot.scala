package com.github.wkennedy.detroitpast

import com.github.wkennedy.detroitpast.rest.{VersionHelper, PlaceAPI}
import com.mongodb.{MongoClient, ServerAddress}
import net.liftweb.http._
import net.liftweb.mongodb.MongoDB
import net.liftweb.sitemap._
import net.liftweb.util.DefaultConnectionIdentifier

class Boot extends Bootable {

  System.setProperty("DEBUG.MONGO", "true")
  System.setProperty("DB.TRACE", "true")

  def boot() {

    connectDB()

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index",
      Menu.i("Watch some videos") / "videos"
    )

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries: _*))

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    VersionHelper.init()
    PlaceAPI.init()
  }

  def connectDB(): Unit = {
    val server = new ServerAddress("127.0.0.1", 27017)
    MongoDB.defineDb(DefaultConnectionIdentifier, new MongoClient(server), "DetroitPastDB")
  }

}

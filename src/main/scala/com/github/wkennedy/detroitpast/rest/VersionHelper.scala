package com.github.wkennedy.detroitpast.rest

import net.liftweb.http._
import net.liftweb.http.provider.HTTPRequest
import net.liftweb.http.rest.RestHelper

object VersionHelper extends RestHelper {

  //TODO add current and latest versions
  object currentVersion extends RequestVar("v1")
  object latestVersion extends RequestVar("v1")

  def init() {
    LiftRules.statelessDispatch.append(VersionHelper)

    LiftRules.statelessRewrite.prepend {
      case RewriteRequest(ParsePath(path, _, _, _), GetRequest, req) if !path(1).startsWith("v") =>
        req match {
          case request: HTTPRequest =>
            val newPath = insertAt("v1", 1, path)
            //TODO pull version from accept header to inject in URL
            println(request.header("Accept"))
            RewriteResponse(newPath)
        }
      }
  }

  def insertAt[A](e: A, n: Int, ls: List[A]): List[A] = ls.splitAt(n) match {
    case (pre, post) => pre ::: e :: post
  }

}

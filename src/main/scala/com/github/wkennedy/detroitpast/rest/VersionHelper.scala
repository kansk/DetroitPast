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
      case RewriteRequest(ParsePath(path, _, _, _), _, req) if path.isDefinedAt(1) && !path(1).startsWith("v") =>
        req match {
          case request: HTTPRequest =>
            val acceptType = request.header("Accept").openOr("application/json;version=1");println(acceptType)
            val index = acceptType.lastIndexOf("version=")
            var versionNumber = "v1";println(index);println(versionNumber)
            if(index != -1) {
              versionNumber = "v" + acceptType.slice(index + 8, acceptType.length);println(versionNumber)
            }
            val newPath = insertAt(versionNumber, 1, path);println(newPath)
            RewriteResponse(newPath)
        }
      }
  }

  def insertAt[A](e: A, n: Int, ls: List[A]): List[A] = ls.splitAt(n) match {
    case (pre, post) => pre ::: e :: post
  }
}

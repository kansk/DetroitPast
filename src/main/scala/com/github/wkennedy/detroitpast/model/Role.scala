package com.github.wkennedy.detroitpast.model

object Role extends Enumeration {
  type Role = Value
  /*
  Admin roles
   */
  val admin = Value("admin")
  val editor = Value("editor")
  val site_admin = Value("site_admin")

  /*
  User roles
   */
  val user = Value("user")
}

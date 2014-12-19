package com.github.wkennedy.detroitpast.model

import net.liftweb.http.auth.AuthRole

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

  val rolesHierarchy =
    AuthRole(Role.admin.toString).addRoles(
      AuthRole(Role.site_admin.toString).addRoles(
        AuthRole(Role.editor.toString).addRoles(
          AuthRole(Role.user.toString))))
}

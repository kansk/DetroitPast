name := "DetroitPast"

version := "1.0"

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

jetty()

parallelExecution in Test := false

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb"     %% "lift-webkit"            % liftVersion withJavadoc() withSources(),
    "net.liftweb" %% "lift-mongodb-record" % liftVersion,
    "com.foursquare" % "rogue-field_2.11"         % "2.4.0" intransitive(),
    "com.foursquare" % "rogue-core_2.11"          % "2.4.0" intransitive(),
    "com.foursquare" % "rogue-lift_2.11" % "2.4.0" intransitive(),
    "com.foursquare" % "rogue-index_2.11"         % "2.4.0" intransitive(),
    "ch.qos.logback"   % "logback-classic"        % "1.1.2",
    "org.specs2"      %% "specs2" % "2.4.11"       % "test",
    "org.scalaz.stream" %% "scalaz-stream" % "0.5a"
  )
}
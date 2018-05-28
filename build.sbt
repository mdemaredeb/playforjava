name := "warehouse"

organization := "probandoPlay"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.google.guava" % "guava" % "14.0",
  filters,
  "ws.securesocial" %% "securesocial" % "2.1.4"
  //"ws.securesocial" %% "securesocial" % "master-SNAPSHOT"
)

resolvers += Resolver.url("sbt-plugin-snapshots",
  url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/")
)(Resolver.ivyStylePatterns)

play.Project.playJavaSettings

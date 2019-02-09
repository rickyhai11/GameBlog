name := """GameBlog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

javacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-parameters",
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Werror"
)

crossScalaVersions := Seq("2.11.12", "2.12.7")

libraryDependencies += guice
libraryDependencies += jdbc

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

// Web dependencies
libraryDependencies += "org.webjars.bower" % "jquery" % "3.2.1"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"

// JBCEncryption library
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.11.1" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "3.1.3" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

javaOptions in Test += "-Dconfig.file=conf/test.conf"
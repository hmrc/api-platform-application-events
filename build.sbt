import sbt._
import sbt.Keys._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import bloop.integrations.sbt.BloopDefaults

lazy val scala212 = "2.12.15"
lazy val scala213 = "2.13.8"
lazy val supportedScalaVersions = List(scala212, scala213)

lazy val library = (project in file("."))
  .settings(
    crossScalaVersions := supportedScalaVersions,
  )
  .settings(
    name := "api-platform-application-events",
    majorVersion                     := 0,
    scalaVersion                     := scala213,
    targetJvm := "jvm-1.8",
    libraryDependencies ++= LibraryDependencies()
  )
  .settings(
    ScoverageSettings()
  )
  .settings(
    Compile / unmanagedResourceDirectories += baseDirectory.value / "app" / "resources"
  )

  .configs(Test)
  .settings(inConfig(Test)(Defaults.testSettings): _*)
  .settings(inConfig(Test)(BloopDefaults.configSettings))
  .settings(
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT")
  )

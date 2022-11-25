import sbt._
import sbt.Keys._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import bloop.integrations.sbt.BloopDefaults

val appName = "api-platform-application-events"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin)
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
    majorVersion                     := 0,
    scalaVersion                     := "2.13.8",
    libraryDependencies ++= LibraryDependencies()
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

import sbt._

object LibraryDependencies {
  def apply() = compileDependencies ++ testDependencies

  lazy val compileDependencies = Seq(
    "uk.gov.hmrc"       %% "api-platform-application-domain" % "0.21.0-SNAPSHOT"
  )

  lazy val testDependencies = Seq(
    "org.scalactic"       %% "scalactic"               % "3.2.14"  % "test",
    "org.scalatest"       %% "scalatest"               % "3.2.14"  % "test",
    "com.vladsch.flexmark" % "flexmark-all"            % "0.62.2"  % "test",
    "org.mockito"         %% "mockito-scala-scalatest" % "1.17.22" % "test"
  )
}

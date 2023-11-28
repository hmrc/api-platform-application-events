import sbt._

object LibraryDependencies {
  def apply() = compileDependencies ++ testDependencies

  lazy val compileDependencies = Seq(
    "uk.gov.hmrc"       %% "api-platform-application-domain" % "0.29.0"
  )

  lazy val testDependencies = Seq(
    "com.vladsch.flexmark"  % "flexmark-all"            % "0.62.2",
    "org.mockito"          %% "mockito-scala-scalatest" % "1.17.29",
    "org.scalactic"        %% "scalactic"               % "3.2.17",
    "org.scalatest"        %% "scalatest"               % "3.2.17"
  ).map(_ % "test")
}

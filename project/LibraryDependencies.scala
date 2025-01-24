import sbt._

object LibraryDependencies {

  lazy val commonDomainVersion      = "0.18.0"
  lazy val applicationDomainVersion = "0.71.0"

  def apply() = compileDependencies ++ testDependencies

  lazy val compileDependencies = Seq(
    "uk.gov.hmrc"       %% "api-platform-application-domain" % applicationDomainVersion
  )

  lazy val testDependencies = Seq(
    "com.vladsch.flexmark"  % "flexmark-all"                             % "0.62.2",
    "org.mockito"          %% "mockito-scala-scalatest"                  % "1.17.29",
    "org.scalactic"        %% "scalactic"                                % "3.2.17",
    "org.scalatest"        %% "scalatest"                                % "3.2.17",
    "uk.gov.hmrc"          %% "api-platform-application-domain-fixtures" % applicationDomainVersion
  ).map(_ % "test")
}

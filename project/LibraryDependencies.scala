import sbt._

object LibraryDependencies {

  val commonDomainVersion = "1.3.0"
  val applicationDomainVersion = "1.6.0-SNAPSHOT"

  def apply(scalaVersion: String) = compileDependencies(scalaVersion) ++ testDependencies(scalaVersion)

  private def compileDependencies(scalaVersion: String) = Seq(
    "uk.gov.hmrc"       %% "api-platform-common-domain" % commonDomainVersion % "provided",
    "uk.gov.hmrc"       %% "api-platform-application-domain" % applicationDomainVersion % "provided"
  )

  private def testDependencies(scalaVersion: String) = (
    Seq(
      "uk.gov.hmrc"          %% "api-platform-common-domain-fixtures" % commonDomainVersion,
      "uk.gov.hmrc"          %% "api-platform-application-domain-fixtures" % applicationDomainVersion
    ).map(_ % "provided")
    ++ Seq(
      "com.vladsch.flexmark"  % "flexmark-all"                             % "0.62.2",
      "org.scalatest"        %% "scalatest"                                % "3.2.19"
      ) ++ (
        CrossVersion.partialVersion(scalaVersion) match {
          case Some((2,_)) => Seq("org.mockito" %% "mockito-scala-scalatest" % "2.2.1")
          case _           => Seq("org.scalatestplus" %% "mockito-5-18" % "3.2.19.0")
        }
    ).map(_ % "test")
  )
}

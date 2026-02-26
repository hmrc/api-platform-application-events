import sbt._

object LibraryDependencies {

  val applicationDomainVersion = "1.0.0-SNAPSHOT"

  def apply(scalaVersion: String) = compileDependencies(scalaVersion) ++ testDependencies(scalaVersion)

  def compileDependencies(scalaVersion: String) = Seq(
    "uk.gov.hmrc"       %% "api-platform-application-domain" % applicationDomainVersion
  )

  def testDependencies(scalaVersion: String) = Seq(
    "uk.gov.hmrc"          %% "api-platform-application-domain-fixtures" % applicationDomainVersion,
    "com.vladsch.flexmark"  % "flexmark-all"                             % "0.62.2",
    "org.scalatest"        %% "scalatest"                                % "3.2.19",
    ) ++ (
      CrossVersion.partialVersion(scalaVersion) match {
        case Some((2,_)) => Seq("org.mockito" %% "mockito-scala-scalatest" % "2.0.0")
        case _           => Seq("org.scalatestplus" %% "mockito-5-18" % "3.2.19.0")
      }
  ).map(_ % "test")
}

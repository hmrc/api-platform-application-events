import sbt._

object LibraryDependencies {

  val commonDomainVersion = "1.4.0"
  val applicationDomainVersion = "1.6.0"

  // Cut down and simplified from other libraries as this provides no fixtures
  
  def apply = 
    compileDependencies ++
    fixturesDependencies.map(_ % "test") ++ 
    testDependencies


  private def compileDependencies = Seq(
    "uk.gov.hmrc"       %% "api-platform-common-domain" % commonDomainVersion % "provided",
    "uk.gov.hmrc"       %% "api-platform-application-domain" % applicationDomainVersion % "provided"
  )

  private def fixturesDependencies = Seq(
    "uk.gov.hmrc"             %% "api-platform-common-domain-fixtures" % commonDomainVersion,
    "uk.gov.hmrc"             %% "api-platform-application-domain-fixtures" % applicationDomainVersion
  )

  private def testDependencies = Seq.empty[ModuleID]
    .map(_ % "test")
}

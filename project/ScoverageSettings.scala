import scoverage.ScoverageKeys
  
object ScoverageSettings {
  def apply() = Seq(
    ScoverageKeys.coverageExcludedPackages := Seq(
      "<empty>",
      """uk\.gov\.hmrc\.BuildInfo""" ,
    ).mkString(";"),
    ScoverageKeys.coverageMinimumStmtTotal := 70, //82, // TODO - replace asap
    ScoverageKeys.coverageFailOnMinimum := true,
    ScoverageKeys.coverageHighlighting := true
  )
}

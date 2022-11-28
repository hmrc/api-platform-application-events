package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class TermsAndConditionsLocationJsonFormattersSpec extends JsonFormattersSpec {

  "TermsAndConditionsLocationJsonFormatters" when {

    import TermsAndConditionsLocationJsonFormatters._

    "given a location of none provided" should {
      "produce json" in {
        testToJson[TermsAndConditionsLocation](TermsAndConditionsLocations.NoneProvided)(
          ("termsAndConditionsType" -> "noneProvided")
        )
      }

      "read json" in {
        testFromJson[TermsAndConditionsLocation]("""{"termsAndConditionsType":"noneProvided"}""")(TermsAndConditionsLocations.NoneProvided)
      }
    }

    "given location of in desktop software provided" should {
      "produce json" in {
        testToJson[TermsAndConditionsLocation](TermsAndConditionsLocations.InDesktopSoftware)(
          ("termsAndConditionsType" -> "inDesktop")
        )
      }

      "read json" in {
        testFromJson[TermsAndConditionsLocation]("""{"termsAndConditionsType":"inDesktop"}""")(TermsAndConditionsLocations.InDesktopSoftware)
      }
    }
    
    "given location of url provided" should {
      "produce json" in {
        testToJson[TermsAndConditionsLocation](TermsAndConditionsLocations.Url("aUrl"))(
          ("termsAndConditionsType" -> "url"),
          ("value" -> "aUrl")
        )
      }

      "read json" in {
        testFromJson[TermsAndConditionsLocation]("""{"termsAndConditionsType":"url","value":"aUrl"}""")(TermsAndConditionsLocations.Url("aUrl"))
      }
    }
  }
}

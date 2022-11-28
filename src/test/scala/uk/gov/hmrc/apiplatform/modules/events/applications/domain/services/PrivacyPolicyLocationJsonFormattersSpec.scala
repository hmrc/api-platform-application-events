package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class PrivacyPolicyLocationJsonFormattersSpec extends JsonFormattersSpec {

  "PrivacyPolicyLocationJsonFormatters" when {

    import PrivacyPolicyLocationJsonFormatters._

    "given a policy of none provided" should {
      "produce json" in {
        testToJson[PrivacyPolicyLocation](PrivacyPolicyLocations.NoneProvided)(
          ("privacyPolicyType" -> "noneProvided")
        )
      }

      "read json" in {
        testFromJson[PrivacyPolicyLocation]("""{"privacyPolicyType":"noneProvided"}""")(PrivacyPolicyLocations.NoneProvided)
      }
    }

    "given policy of in desktop software provided" should {
      "produce json" in {
        testToJson[PrivacyPolicyLocation](PrivacyPolicyLocations.InDesktopSoftware)(
          ("privacyPolicyType" -> "inDesktop")
        )
      }

      "read json" in {
        testFromJson[PrivacyPolicyLocation]("""{"privacyPolicyType":"inDesktop"}""")(PrivacyPolicyLocations.InDesktopSoftware)
      }
    }
    
    "given policy of url provided" should {
      "produce json" in {
        testToJson[PrivacyPolicyLocation](PrivacyPolicyLocations.Url("aUrl"))(
          ("privacyPolicyType" -> "url"),
          ("value" -> "aUrl")
        )
      }

      "read json" in {
        testFromJson[PrivacyPolicyLocation]("""{"privacyPolicyType":"url","value":"aUrl"}""")(PrivacyPolicyLocations.Url("aUrl"))
      }
    }
  }
}

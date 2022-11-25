package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class PrivacyPolicyLocationJsonFormattersSpec extends AnyWordSpec with Matchers {

  "PrivacyPolicyLocationJsonFormatters" when {

    import PrivacyPolicyLocationJsonFormatters._

    def testToJson(in: PrivacyPolicyLocation)(fields: (String, String)*) = {
      Json.toJson(in) shouldBe Json.obj(
        fields.map[(String, Json.JsValueWrapper)] { case (k, v) => (k -> JsString(v)) }: _*
      )
    }

    def testFromJson(text: String)(expected: PrivacyPolicyLocation) = {
      Json.parse(text).validate[PrivacyPolicyLocation] shouldBe JsSuccess(expected)
    }

    "given a policy of none provided" should {
      "produce json" in {
        testToJson(PrivacyPolicyLocations.NoneProvided)(
          ("privacyPolicyType" -> "noneProvided")
        )
      }

      "read json" in {
        testFromJson("""{"privacyPolicyType":"noneProvided"}""")(PrivacyPolicyLocations.NoneProvided)
      }
    }

    "given policy of in desktop software provided" should {
      "produce json" in {
        testToJson(PrivacyPolicyLocations.InDesktopSoftware)(
          ("privacyPolicyType" -> "inDesktop")
        )
      }

      "read json" in {
        testFromJson("""{"privacyPolicyType":"inDesktop"}""")(PrivacyPolicyLocations.InDesktopSoftware)
      }
    }
    
    "given policy of url provided" should {
      "produce json" in {
        testToJson(PrivacyPolicyLocations.Url("aUrl"))(
          ("privacyPolicyType" -> "url"),
          ("value" -> "aUrl")
        )
      }

      "read json" in {
        testFromJson("""{"privacyPolicyType":"url","value":"aUrl"}""")(PrivacyPolicyLocations.Url("aUrl"))
      }
    }
  }
}

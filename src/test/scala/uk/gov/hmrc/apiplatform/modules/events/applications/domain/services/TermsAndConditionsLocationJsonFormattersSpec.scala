package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class TermsAndConditionsLocationJsonFormattersSpec extends AnyWordSpec with Matchers {

  "TermsAndConditionsLocationJsonFormatters" when {

    import TermsAndConditionsLocationJsonFormatters._

    def testToJson(in: TermsAndConditionsLocation)(fields: (String, String)*) = {
      Json.toJson(in) shouldBe Json.obj(
        fields.map[(String, Json.JsValueWrapper)] { case (k, v) => (k -> JsString(v)) }: _*
      )
    }

    def testFromJson(text: String)(expected: TermsAndConditionsLocation) = {
      Json.parse(text).validate[TermsAndConditionsLocation] shouldBe JsSuccess(expected)
    }

    "given a location of none provided" should {
      "produce json" in {
        testToJson(TermsAndConditionsLocations.NoneProvided)(
          ("termsAndConditionsType" -> "noneProvided")
        )
      }

      "read json" in {
        testFromJson("""{"termsAndConditionsType":"noneProvided"}""")(TermsAndConditionsLocations.NoneProvided)
      }
    }

    "given location of in desktop software provided" should {
      "produce json" in {
        testToJson(TermsAndConditionsLocations.InDesktopSoftware)(
          ("termsAndConditionsType" -> "inDesktop")
        )
      }

      "read json" in {
        testFromJson("""{"termsAndConditionsType":"inDesktop"}""")(TermsAndConditionsLocations.InDesktopSoftware)
      }
    }
    
    "given location of url provided" should {
      "produce json" in {
        testToJson(TermsAndConditionsLocations.Url("aUrl"))(
          ("termsAndConditionsType" -> "url"),
          ("value" -> "aUrl")
        )
      }

      "read json" in {
        testFromJson("""{"termsAndConditionsType":"url","value":"aUrl"}""")(TermsAndConditionsLocations.Url("aUrl"))
      }
    }
  }
}

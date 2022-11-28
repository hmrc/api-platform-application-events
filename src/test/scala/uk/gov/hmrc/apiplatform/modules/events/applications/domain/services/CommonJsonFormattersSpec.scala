package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.LaxEmailAddress
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventId
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId

class CommonJsonFormattersSpec extends JsonFormattersSpec {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")
  val bobSmithUserName = "bob smith"
  "CommonJsonFormatters" when {

    import CommonJsonFormatters._

    "given an lax applicationId" should {
      "produce json" in {
        Json.toJson(ApplicationId("quark")) shouldBe JsString("quark")
      }

      "read json" in {
        Json.parse(""" "quark" """).as[ApplicationId] shouldBe ApplicationId("quark")
      }
    }

    "given an lax email address" should {
      "produce json" in {
        Json.toJson(LaxEmailAddress("quark")) shouldBe JsString("quark")
      }

      "read json" in {
        Json.parse(""" "quark" """).as[LaxEmailAddress] shouldBe LaxEmailAddress("quark")
      }
    }

    "given an event id" should {
      val uuid = java.util.UUID.randomUUID

      "produce json" in {
        Json.toJson(EventId(uuid)) shouldBe JsString(uuid.toString)
      }

      "read json" in {
        Json.parse(s""" "${uuid.toString}" """).as[EventId] shouldBe EventId(uuid)
      }
    }
  }
}

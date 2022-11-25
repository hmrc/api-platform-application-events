package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.{JsString, Json}
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.OldStyleActors
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.OldStyleActor

class OldStyleActorJsonFormattersSpec extends AnyWordSpec with Matchers {

  "OldStyleActorJsonFormatters" when {

    import OldStyleActorJsonFormatters._

    "given a gatekeeper user" should {
      "produce json" in {
        val actor: OldStyleActor = OldStyleActors.GatekeeperUser("bob smith")

        Json.toJson(actor) shouldBe Json.obj(
          "actorType" -> JsString("GATEKEEPER"),
          "id" -> JsString("bob smith")
        )
      }
    }

    "given a collaborator actor" should {
      "produce json" in {
        val actor: OldStyleActor = OldStyleActors.Collaborator("bob@smith.com")

        Json.toJson(actor) shouldBe Json.obj(
          "actorType" -> JsString("COLLABORATOR"),
          "id" -> JsString("bob@smith.com")
        )
      }
    }

    "given a scheduled job actor" should {
      "produce json" in {
        val actor: OldStyleActor = OldStyleActors.ScheduledJob("DeleteAllAppsBwaHaHa")

        Json.toJson(actor) shouldBe Json.obj(
          "actorType" -> JsString("SCHEDULED_JOB"),
          "id" -> JsString("DeleteAllAppsBwaHaHa")
        )
      }
    }
  }
}

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.OptionValues
import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actor
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actors
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.LaxEmailAddress

class ActorJsonFormattersSpec extends JsonFormattersSpec with OptionValues {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")
  val bobSmithUserName = "bob smith"

  "ActorJsonFormatters" when {

    import ActorJsonFormatters._

    "given a gatekeeper user" should {
      "produce json" in {
        testToJson[Actor](Actors.GatekeeperUser(bobSmithUserName))(("actorType" -> "GATEKEEPER"), ("user" -> bobSmithUserName))
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"GATEKEEPER","user":"bob smith"}""")(Actors.GatekeeperUser(bobSmithUserName))
      }
    }

    "given a collaborator actor" should {
      "produce json" in {
        testToJson[Actor](Actors.Collaborator(bobSmithEmailAddress))(
          ("actorType" -> "COLLABORATOR"),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"COLLABORATOR","email":"bob@smith.com"}""")(Actors.Collaborator(bobSmithEmailAddress))
      }
    }

    "given a scheduled job actor" should {
      "produce json" in {
        testToJson[Actor](Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))(
          ("actorType" -> "SCHEDULED_JOB"),
          ("jobId" -> "DeleteAllAppsBwaHaHa")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"SCHEDULED_JOB","jobId":"DeleteAllAppsBwaHaHa"}""")(Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))
      }
    }

    "given bad json" should {
      "fail accordingly" in {
        val text = """{"actorType":"NOT_VALID","jobId":"DeleteAllAppsBwaHaHa"}"""
        Json.parse(text).validate[Actor] match {
          case JsError(errs) =>
            errs.size shouldBe 1
            errs.map(_._1).headOption.value.toString shouldBe "/actorType"
            errs.map(_._2).headOption.value.toString should include("NOT_VALID is not a recognised actorType")
          case _             => fail()
        }
      }
    }
  }
}

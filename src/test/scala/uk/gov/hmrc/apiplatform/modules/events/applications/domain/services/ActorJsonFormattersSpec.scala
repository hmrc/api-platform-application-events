package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.OptionValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actor
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actors
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.LaxEmailAddress

class ActorJsonFormattersSpec extends AnyWordSpec with Matchers with OptionValues {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")
  val bobSmithUserName = "bob smith"

  "ActorJsonFormatters" when {

    import ActorJsonFormatters._

    def testToJson(actor: Actor)(fields: (String, String)*) = {
      Json.toJson(actor) shouldBe Json.obj(
        fields.map[(String, Json.JsValueWrapper)] { case (k, v) => (k -> JsString(v)) }: _*
      )
    }

    def testFromJson(text: String)(expected: Actor) = {
      Json.parse(text).validate[Actor] shouldBe JsSuccess(expected)
    }

    "given a gatekeeper user" should {
      "produce json" in {
        testToJson(Actors.GatekeeperUser(bobSmithUserName))(("actorType" -> "GATEKEEPER"), ("user" -> bobSmithUserName))
      }

      "read json" in {
        testFromJson("""{"actorType":"GATEKEEPER","user":"bob smith"}""")(Actors.GatekeeperUser(bobSmithUserName))
      }
    }

    "given a collaborator actor" should {
      "produce json" in {
        testToJson(Actors.Collaborator(bobSmithEmailAddress))(
          ("actorType" -> "COLLABORATOR"),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson("""{"actorType":"COLLABORATOR","email":"bob@smith.com"}""")(Actors.Collaborator(bobSmithEmailAddress))
      }
    }

    "given a scheduled job actor" should {
      "produce json" in {
        testToJson(Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))(
          ("actorType" -> "SCHEDULED_JOB"),
          ("jobId" -> "DeleteAllAppsBwaHaHa")
        )
      }

      "read json" in {
        testFromJson("""{"actorType":"SCHEDULED_JOB","jobId":"DeleteAllAppsBwaHaHa"}""")(Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))
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

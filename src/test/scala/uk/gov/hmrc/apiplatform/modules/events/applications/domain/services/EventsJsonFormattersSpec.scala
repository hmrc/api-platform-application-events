package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.Json
import scala.annotation.nowarn
      
      
@nowarn
class EventsJsonFormattersSpec extends AnyWordSpec with Matchers {
  val eventId = EventId.random

  "EventsJsonFormatters" when {
    import EventsJsonFormatters._

    "given a team member added event" should {

      "convert from json" in {
        val jsonText = raw"""
              {"id": "${eventId.value}",
              |"applicationId": "akjhjkhjshjkhksaih",
              |"eventDateTime": "2014-01-01T13:13:34.441Z",
              |"eventType": "TEAM_MEMBER_ADDED",
              |"actor":{"id": "123454654", "actorType": "GATEKEEPER"},
              |"teamMemberEmail": "bob@bob.com",
              |"teamMemberRole": "ADMIN"}""".stripMargin

        val evt = Json.parse(jsonText).as[AbstractApplicationEvent]

        evt shouldBe a [TeamMemberAddedEvent]
      }
    }

    "given a collaborated removed event" should {
      "convert from json" in {
        val jsonText = raw"""
              {"id": "${eventId.value}",
              |"applicationId": "akjhjkhjshjkhksaih",
              |"eventDateTime": "2014-01-01T13:13:34.441Z",
              |"eventType": "COLLABORATOR_REMOVED",
              |"actor":{"user": "123454654", "actorType": "GATEKEEPER"},
              |"collaborator": {
              |  "id": "1234",
              |  "email": "bob@smith.com",
              |  "role": "ADMINISTRATOR"
              |},
              |"verifiedAdminsToEmail": []
              |}""".stripMargin

        val evt = Json.parse(jsonText).as[AbstractApplicationEvent]

        evt shouldBe a [CollaboratorRemoved]
      }
    }
  }
}

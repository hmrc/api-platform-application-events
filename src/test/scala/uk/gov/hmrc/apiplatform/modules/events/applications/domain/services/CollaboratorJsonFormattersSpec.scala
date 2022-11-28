package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class CollaboratorJsonFormattersSpec extends JsonFormattersSpec {

  val anId = "12345"
  val anEmail = LaxEmailAddress("bob@smith.com")

  "CollaboratorsJsonFormatters" when {

    import CollaboratorJsonFormatters._

    "given an administrator" should {
      "produce json" in {
        testToJson[Collaborator](Collaborators.Administrator(anId, anEmail))(
          ("role" -> "ADMINISTRATOR"),
          ("id" -> anId),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Collaborator]("""{"role":"ADMINISTRATOR","id":"12345","email":"bob@smith.com"}""")(Collaborators.Administrator(
          anId,
          anEmail
        ))
      }
    }

    "given an developer" should {
      "produce json" in {
        testToJson[Collaborator](Collaborators.Developer(anId, anEmail))(
          ("role" -> "DEVELOPER"),
          ("id" -> anId),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Collaborator]("""{"role":"DEVELOPER","id":"12345","email":"bob@smith.com"}""")(Collaborators.Developer(
          anId,
          anEmail
        ))
      }
    }
  }
}

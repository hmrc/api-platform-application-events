package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class CollaboratorJsonFormattersSpec extends AnyWordSpec with Matchers {

  val anId = "12345"
  val anEmail = LaxEmailAddress("bob@smith.com")

  "CollaboratorsJsonFormatters" when {

    import CollaboratorJsonFormatters._

    def testToJson(in: Collaborator)(fields: (String, String)*) = {
      Json.toJson(in) shouldBe Json.obj(
        fields.map[(String, Json.JsValueWrapper)] { case (k, v) => (k -> JsString(v)) }: _*
      )
    }

    def testFromJson(text: String)(expected: Collaborator) = {
      Json.parse(text).validate[Collaborator] shouldBe JsSuccess(expected)
    }

    "given an administrator" should {
      "produce json" in {
        testToJson(Collaborators.Administrator(anId, anEmail))(
          ("role" -> "ADMINISTRATOR"),
          ("id" -> anId),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson("""{"role":"ADMINISTRATOR","id":"12345","email":"bob@smith.com"}""")(Collaborators.Administrator(
          anId,
          anEmail
        ))
      }
    }

    "given an developer" should {
      "produce json" in {
        testToJson(Collaborators.Developer(anId, anEmail))(
          ("role" -> "DEVELOPER"),
          ("id" -> anId),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson("""{"role":"DEVELOPER","id":"12345","email":"bob@smith.com"}""")(Collaborators.Developer(
          anId,
          anEmail
        ))
      }
    }
  }
}

/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.apiplatform.modules.applications.domain.services

import play.api.libs.json.{JsString, Json}

import uk.gov.hmrc.apiplatform.modules.applications.domain.models._
import uk.gov.hmrc.apiplatform.modules.common.domain.models.LaxEmailAddress
import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec
import uk.gov.hmrc.apiplatform.modules.developers.domain.models.UserId

class CollaboratorJsonFormattersSpec extends JsonFormattersSpec {

  val anId     = UserId.random
  val idAsText = anId.value.toString()
  val anEmail  = LaxEmailAddress("bob@smith.com")

  "CollaboratorJsonFormatters" when {

    import CollaboratorJsonFormatters._

    "given an administrator" should {
      "produce json" in {
        testToJson[Collaborator](Collaborators.Administrator(anId, anEmail))(
          ("role"  -> "ADMINISTRATOR"),
          ("id"    -> idAsText),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Collaborator](s"""{"role":"ADMINISTRATOR","id":"$idAsText","email":"bob@smith.com"}""")(Collaborators.Administrator(
          anId,
          anEmail
        ))
      }
    }

    "given an developer" should {
      "produce json" in {
        testToJson[Collaborator](Collaborators.Developer(anId, anEmail))(
          ("role"  -> "DEVELOPER"),
          ("id"    -> idAsText),
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Collaborator](s"""{"role":"DEVELOPER","id":"$idAsText","email":"bob@smith.com"}""")(Collaborators.Developer(
          anId,
          anEmail
        ))
      }
    }
  }
  "CollaboratorsRoleJsonFormatters" when {

    import CollaboratorJsonFormatters._

    "given administrator role" should {
      "produce json" in {
        Json.toJson[Collaborators.Role](Collaborators.Roles.ADMINISTRATOR) shouldBe JsString("ADMINISTRATOR")
      }

      "read json" in {
        testFromJson[Collaborators.Role](s""""ADMINISTRATOR"""")(Collaborators.Roles.ADMINISTRATOR)
      }
    }

    "given developer role" should {
      "produce json" in {
        Json.toJson[Collaborators.Role](Collaborators.Roles.DEVELOPER) shouldBe JsString("DEVELOPER")
      }

      "read json" in {
        testFromJson[Collaborators.Role](s""""DEVELOPER"""")(Collaborators.Roles.DEVELOPER)
      }
    }
  }
}

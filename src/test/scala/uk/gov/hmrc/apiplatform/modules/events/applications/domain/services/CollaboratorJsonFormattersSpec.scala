/*
 * Copyright 2022 HM Revenue & Customs
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

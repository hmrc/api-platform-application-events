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

package uk.gov.hmrc.apiplatform.modules.commands.domain.services

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.applications.domain.models._
import uk.gov.hmrc.apiplatform.modules.commands.applications.domain.models._
import uk.gov.hmrc.apiplatform.modules.commands.applications.domain.services.ApplicationCommandJsonFormatters
import uk.gov.hmrc.apiplatform.modules.common.domain.models.LaxEmailAddress
import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec
import uk.gov.hmrc.apiplatform.modules.common.utils.FixedClock
import uk.gov.hmrc.apiplatform.modules.developers.domain.models.UserId
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actors

class ApplicationCommandJsonFormattersSpec extends JsonFormattersSpec {

  val aUserId     = UserId.random
  val idAsText    = aUserId.value.toString()
  val anEmail     = LaxEmailAddress("bob@smith.com")
  val anAppId     = ApplicationId.random
  val appIdAsText = anAppId.value.toString()
  val anActor     = Actors.GatekeeperUser("bob smith")
  val aTimestamp  = "2022-04-25T09:24:18.447Z"

  "ApplicationCommandJsonFormatters" when {

    import ApplicationCommandJsonFormatters._

    "given an AddCollaborator command" should {
      val addMe = Collaborators.Administrator(aUserId, anEmail)
      val cmd   = AddCollaborator(anActor, addMe, Set.empty, FixedClock.instant)

      "produce json" in {
        // actor: Actor,  collaborator: Collaborator, adminsToEmail:Set[LaxEmailAddress], timestamp: Instant
        Json.toJson[ApplicationCommand](cmd).toString shouldBe
          s"""{"actor":{"user":"bob smith","actorType":"GATEKEEPER"},"collaborator":{"userId":"${idAsText}","emailAddress":"${anEmail.value}","role":"ADMINISTRATOR"},"adminsToEmail":[],"timestamp":"$aTimestamp","updateType":"addCollaborator"}"""
      }

      "read json" in {
        testFromJson[ApplicationCommand](
          s"""{"actor":{"user":"bob smith","actorType":"GATEKEEPER"},"collaborator":{"userId":"${idAsText}","emailAddress":"${anEmail.value}","role":"ADMINISTRATOR"},"adminsToEmail":[],"timestamp":"$aTimestamp","updateType":"addCollaborator"}"""
        )(cmd)
      }
    }

    "given an AddCollaboratorRequest command" should {
      val cmd = AddCollaboratorRequest(anActor, anEmail, Collaborators.Roles.ADMINISTRATOR, FixedClock.instant)

      "produce json" in {
        Json.toJson[ApplicationCommand](cmd).toString shouldBe
          s"""{"actor":{"user":"bob smith","actorType":"GATEKEEPER"},"collaboratorEmail":"${anEmail.value}","collaboratorRole":"ADMINISTRATOR","timestamp":"$aTimestamp","updateType":"addCollaboratorRequest"}"""
      }

      "read json" in {
        testFromJson[ApplicationCommand](
          s"""{"actor":{"user":"bob smith","actorType":"GATEKEEPER"},"collaboratorEmail":"${anEmail.value}","collaboratorRole":"ADMINISTRATOR","timestamp":"$aTimestamp","updateType":"addCollaboratorRequest"}"""
        )(cmd)
      }
    }
  }
}

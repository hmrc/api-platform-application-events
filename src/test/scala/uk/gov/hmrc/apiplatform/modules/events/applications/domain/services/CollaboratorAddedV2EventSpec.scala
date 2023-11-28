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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import play.api.libs.json.Json
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.Collaborator

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.CollaboratorAddedV2
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class CollaboratorAddedV2EventSpec extends EventSpec {

  "CollaboratorAddedV2" should {
    import EventsInterServiceCallJsonFormatters._

    val collaboratorAddedV2: ApplicationEvent = CollaboratorAddedV2(anEventId, anAppId, anInstant, appCollaborator, developerCollaborator)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"collaborator":{"userId":"${developerCollaborator.userId.value.toString()}","emailAddress":"${developerCollaborator.emailAddress.text}","role":"DEVELOPER"},"eventType":"COLLABORATOR_ADDED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[CollaboratorAddedV2]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(collaboratorAddedV2).toString()
      eventJSonString shouldBe jsonText
    }

    "display CollaboratorAddedV2 correctly" in {
      testDisplay(
        collaboratorAddedV2,
        EventTags.TEAM_MEMBER,
        "Collaborator Added",
        List(s"${developerCollaborator.emailAddress.text} was added as a ${Collaborator.describeRole(developerCollaborator)}")
      )
    }
  }

}

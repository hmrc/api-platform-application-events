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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.TeamMemberAddedEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class TeamMemberAddedEventSpec extends EventSpec {

  "TeamMemberAddedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val teamMemberAddedEvent: ApplicationEvent =
      TeamMemberAddedEvent(anEventId, anAppId, anInstant, appCollaborator, developerCollaborator.emailAddress, Collaborator.describeRole(developerCollaborator))

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"${instantText}","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"teamMemberEmail":"${developerCollaborator.emailAddress.text}","teamMemberRole":"DEVELOPER","eventType":"TEAM_MEMBER_ADDED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[TeamMemberAddedEvent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(teamMemberAddedEvent).toString()
      eventJSonString shouldBe jsonText
    }

    "display TeamMemberAddedEvent correctly" in {
      testDisplay(
        teamMemberAddedEvent,
        EventTags.TEAM_MEMBER,
        "Collaborator Added",
        List(s"${developerCollaborator.emailAddress.text} was added as a ${Collaborator.describeRole(developerCollaborator)}")
      )
    }
  }

}

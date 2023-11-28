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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.TeamMemberRemovedEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class TeamMemberRemovedEventSpec extends EventSpec {

  "TeamMemberRemovedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val teamMemberRemovedEvent: ApplicationEvent =
      TeamMemberRemovedEvent(anEventId, anAppId, anInstant, appCollaborator, developerCollaborator.emailAddress, Collaborator.describeRole(developerCollaborator))

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"teamMemberEmail":"${developerCollaborator.emailAddress.text}","teamMemberRole":"DEVELOPER","eventType":"TEAM_MEMBER_REMOVED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[TeamMemberRemovedEvent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(teamMemberRemovedEvent).toString()
      eventJSonString shouldBe jsonText
    }

    "display TeamMemberRemovedEvent correctly" in {
      testDisplay(
        teamMemberRemovedEvent,
        EventTags.TEAM_MEMBER,
        "Collaborator Removed",
        List(s"${developerCollaborator.emailAddress.text} was removed")
      )
    }
  }

}

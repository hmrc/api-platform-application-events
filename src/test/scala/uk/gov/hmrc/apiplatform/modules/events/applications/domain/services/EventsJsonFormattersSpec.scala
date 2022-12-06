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

import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import play.api.libs.json.Json
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId

class EventsJsonFormattersSpec extends JsonFormattersSpec {
  val eventId = EventId.random
  val anAppId = ApplicationId.random
  val appIdText = anAppId.value.toString()

  "EventsInterServiceCallJsonFormatters" when {
    import EventsInterServiceCallJsonFormatters._

    "given an old style team member added event" should {
      "convert from json" in {
        val jsonText = raw"""
              {"id": "${eventId.value}",
                            |"applicationId": "$appIdText",
                            |"eventDateTime": "2014-01-01T13:13:34.441Z",
                            |"eventType": "TEAM_MEMBER_ADDED",
                            |"actor":{"id": "123454654", "actorType": "GATEKEEPER"},
                            |"teamMemberEmail": "bob@bob.com",
                            |"teamMemberRole": "ADMIN"}""".stripMargin

        val evt = Json.parse(jsonText).as[AbstractApplicationEvent]

        evt shouldBe a[TeamMemberAddedEvent]
      }
    }

    "given a new style collaborator removed event" should {
      "convert from json" in {
        val jsonText = raw"""
              {"id": "${eventId.value}",
                            |"applicationId": "$appIdText",
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

        evt shouldBe a[CollaboratorRemoved]
      }
    }
  }
}

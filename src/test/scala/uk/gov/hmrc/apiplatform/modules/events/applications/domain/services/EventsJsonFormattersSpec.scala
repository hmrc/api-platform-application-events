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
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.{ApplicationId, ClientId}
import uk.gov.hmrc.apiplatform.modules.common.utils.JsonFormattersSpec
import uk.gov.hmrc.apiplatform.modules.developers.domain.models.UserId
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import play.api.libs.json.JsString
import uk.gov.hmrc.apiplatform.modules.common.domain.models._

import java.time.Instant
import java.time.format.DateTimeFormatter
import uk.gov.hmrc.apiplatform.modules.common.utils.FixedClock
import uk.gov.hmrc.apiplatform.modules.submissions.domain.models.SubmissionId

class EventsJsonFormattersSpec extends JsonFormattersSpec {
  val eventId   = EventId.random
  val anAppId   = ApplicationId.random
  val appIdText = anAppId.value.toString()
  val instant = FixedClock.instant
  val instantText = FixedClock.now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

  // Whilst we can read Z or no Z we ouput a Z
  implicit class JsonZoneSyntax(in: String) {
    def butWithZ = in.replaceAll(instantText, s"${instantText}Z")
  }

  "EventsInterServiceCallJsonFormatters" when {
    import EventsInterServiceCallJsonFormatters._

    "given an event id" should {
      val uuid = java.util.UUID.randomUUID

      "produce json" in {
        Json.toJson(EventId(uuid)) shouldBe JsString(uuid.toString)
      }

      "read json" in {
        Json.parse(s""" "${uuid.toString}" """).as[EventId] shouldBe EventId(uuid)
      }
    }

    "given an old style team member added event" should {
      "convert from json with timezone Z" in {
        val jsonText = raw"""
              {"id": "${eventId.value}",
                            |"applicationId": "$appIdText",
                            |"eventDateTime": "2014-01-01T13:13:34.441Z",
                            |"eventType": "TEAM_MEMBER_ADDED",
                            |"actor":{"id": "123454654", "actorType": "GATEKEEPER"},
                            |"teamMemberEmail": "bob@bob.com",
                            |"teamMemberRole": "ADMIN"}""".stripMargin

        val evt = Json.parse(jsonText).as[ApplicationEvent]

        evt shouldBe a[TeamMemberAddedEvent]
      }

      "convert from json without timezone Z" in {
        val jsonText = raw"""
              {"id": "${eventId.value}",
                            |"applicationId": "$appIdText",
                            |"eventDateTime": "2014-01-01T13:13:34.441",
                            |"eventType": "TEAM_MEMBER_ADDED",
                            |"actor":{"id": "123454654", "actorType": "GATEKEEPER"},
                            |"teamMemberEmail": "bob@bob.com",
                            |"teamMemberRole": "ADMIN"}""".stripMargin

        val evt = Json.parse(jsonText).as[ApplicationEvent]

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
                            |  "userId": "${UserId.random.value.toString()}",
                            |  "emailAddress": "bob@smith.com",
                            |  "role": "ADMINISTRATOR"
                            |},
                            |"verifiedAdminsToEmail": []
                            |}""".stripMargin

        val evt = Json.parse(jsonText).as[ApplicationEvent]

        evt shouldBe a[CollaboratorRemovedV2]
      }
    }


    "given a new add client secret event" should {
      val jsonText =
        raw"""{"id":"${eventId.value}","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"dog@dog.com"},"clientSecretId":"someClientId","clientSecretName":"someClientSecretName","eventType":"CLIENT_SECRET_ADDED_V2"}"""

      "convert from json" in {

        val evt = Json.parse(jsonText).as[ApplicationEvent]

        evt shouldBe a[ClientSecretAddedV2]
      }

      "convert to correctJson" in {
        val event: ApplicationEvent = ClientSecretAddedV2(eventId, anAppId, instant, Actors.AppCollaborator(LaxEmailAddress("dog@dog.com")), "someClientId", "someClientSecretName")

        val eventJSonString = Json.toJson(event).toString()
        eventJSonString shouldBe jsonText.butWithZ
      }
    }

    "given a new remove client secret event" should {
      val jsonText =
        raw"""{"id":"${eventId.value}","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"dog@dog.com"},"clientSecretId":"someClientId","clientSecretName":"someClientSecretName","eventType":"CLIENT_SECRET_REMOVED_V2"}"""

      "convert from json" in {

        val evt = Json.parse(jsonText).as[ApplicationEvent]

        evt shouldBe a[ClientSecretRemovedV2]
      }

      "convert to correctJson" in {
        val event: ApplicationEvent = ClientSecretRemovedV2(eventId, anAppId, instant, Actors.AppCollaborator(LaxEmailAddress("dog@dog.com")), "someClientId", "someClientSecretName")

        val eventJSonString = Json.toJson(event).toString()
        eventJSonString shouldBe jsonText.butWithZ
      }
    }


    "given a new application deleted by gatekeeper event" should {
      val clientId = ClientId.random
      val jsonText =
        raw"""{"id":"${eventId.value}","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"user":"someUser"},"clientId":"${clientId.value}","wso2ApplicationName":"someApplicationName","reasons":"some reason or other","requestingAdminEmail":"dog@dog.com","eventType":"APPLICATION_DELETED_BY_GATEKEEPER"}"""

      "convert from json" in {

        val evt = Json.parse(jsonText).as[ApplicationEvent]

        evt shouldBe a[ApplicationDeletedByGatekeeper]
      }

      "convert to correctJson" in {
        val event: ApplicationEvent = ApplicationDeletedByGatekeeper(eventId, anAppId, instant, Actors.GatekeeperUser("someUser"), clientId, "someApplicationName", "some reason or other", LaxEmailAddress("dog@dog.com"))

        val eventJSonString = Json.toJson(event).toString()
        eventJSonString shouldBe jsonText.butWithZ
      }
    }

    "given a ResponsibleIndividualChanged event" should {
    val submissionId = SubmissionId.random
    val now = Instant.now
    val nowText = DateTimeFormatter.ISO_INSTANT.format(now)
    val actor = Actors.GatekeeperUser("Dave")
    
    val jsonText = raw"""{
                      |"id":"${eventId.value}",
                      |"applicationId":"$appIdText",
                      |"eventDateTime":"$nowText",
                      |"actor":{"id":"123454654","user":"Dave","actorType":"GATEKEEPER"},
                      |"previousResponsibleIndividualName":"Bob",
                      |"previousResponsibleIndividualEmail":"Bob@Smith.com",
                      |"newResponsibleIndividualName":"Fred",
                      |"newResponsibleIndividualEmail":"Fred@Smith.com",
                      |"submissionId":"${submissionId.value}",
                      |"submissionIndex": 1,
                      |"code":"1234",
                      |"requestingAdminName":"Dave",
                      |"requestingAdminEmail":"Dave@Smith.com",
                      |"teamMemberRole":"ADMIN",
                      |"eventType":"RESPONSIBLE_INDIVIDUAL_CHANGED"
                      |}""".stripMargin.stripLineEnd

      val evt: ApplicationEvent = 
          ResponsibleIndividualChanged(
            eventId,
            anAppId,
            now,
            actor,
            "Bob",
            LaxEmailAddress("Bob@Smith.com"),
            "Fred",
            LaxEmailAddress("Fred@Smith.com"),
            submissionId,
            1,
            "123",
            "Dave",
            LaxEmailAddress("Dave@Smith.com")
        )

      "convert from json" in {
        val evtOut = Json.parse(jsonText).as[ApplicationEvent]

        evtOut shouldBe a[ResponsibleIndividualChanged]
      }

      "write to json" in {
        Json.toJson(evt).prettifier shouldBe Json.parse(jsonText).prettifier
      }
    }
  }
}

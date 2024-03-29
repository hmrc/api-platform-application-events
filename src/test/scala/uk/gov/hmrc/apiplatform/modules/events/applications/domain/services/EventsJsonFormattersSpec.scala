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

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneOffset}

import play.api.libs.json.{JsString, Json}
import uk.gov.hmrc.apiplatform.modules.common.domain.models._
import uk.gov.hmrc.apiplatform.modules.common.utils.{BaseJsonFormattersSpec, FixedClock}
import uk.gov.hmrc.apiplatform.modules.applications.submissions.domain.models.SubmissionId

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class EventsJsonFormattersSpec extends BaseJsonFormattersSpec {
  val eventId            = EventId.random
  val anAppId            = ApplicationId.random
  val appIdText          = anAppId.value.toString()
  val sixMillisInNanos   = 6 * 1000 * 1000
  val anInstant: Instant = LocalDateTime.of(2020, 1, 2, 3, 4, 5, sixMillisInNanos).toInstant(ZoneOffset.UTC)
  val instantText        = "2020-01-02T03:04:05.006"

  "EventsInterServiceCallJsonFormatters" when {
    import EventsInterServiceCallJsonFormatters._
    import ApplicationEvents._

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

    "given a new terms of use passed event" should {
      val submissionId = SubmissionId.random
      val jsonText     =
        raw"""{"id":"${eventId.value}","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"some-user@example.com","actorType":"COLLABORATOR"},"submissionId":"${submissionId.value}","submissionIndex":0,"eventType":"TERMS_OF_USE_PASSED"}"""

      "convert from json" in {

        val evt = Json.parse(jsonText).as[ApplicationEvent]

        evt shouldBe a[TermsOfUsePassed]
        evt.eventDateTime shouldBe anInstant
      }

      "convert to correctJson" in {
        val event: ApplicationEvent = TermsOfUsePassed(eventId, anAppId, anInstant, Actors.AppCollaborator(LaxEmailAddress("some-user@example.com")), submissionId, 0)

        val eventJSonString = Json.toJson(event).toString()
        eventJSonString shouldBe jsonText
      }
    }

    "given a ResponsibleIndividualChanged event" should {
      val submissionId = SubmissionId.random
      val now          = Instant.now
      val nowText      = DateTimeFormatter.ISO_INSTANT.format(now)

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

      "convert from json" in {
        val evtOut = Json.parse(jsonText).as[ApplicationEvent]

        evtOut shouldBe a[ResponsibleIndividualChanged]
      }
    }

    "given a ResponsibleIndividualDeclinedOrDidNotVerify event" should {
      val submissionId = SubmissionId.random
      val now          = Instant.now
      val nowText      = DateTimeFormatter.ISO_INSTANT.format(now)
      val actor        = Actors.GatekeeperUser("Dave")

      val jsonText = raw"""{
                          |"id":"${eventId.value}",
                          |"applicationId":"$appIdText",
                          |"eventDateTime":"$nowText",
                          |"actor":{"id":"123454654","user":"Dave","actorType":"GATEKEEPER"},
                          |"responsibleIndividualName":"Bob",
                          |"responsibleIndividualEmail":"Bob@Smith.com",
                          |"submissionId":"${submissionId.value}",
                          |"submissionIndex": 1,
                          |"code":"1234",
                          |"requestingAdminName":"Dave",
                          |"requestingAdminEmail":"Dave@Smith.com",
                          |"eventType":"RESPONSIBLE_INDIVIDUAL_DECLINED_OR_DID_NOT_VERIFY"
                          |}""".stripMargin.stripLineEnd

      val evt: ApplicationEvent =
        ResponsibleIndividualDeclinedOrDidNotVerify(
          eventId,
          anAppId,
          now,
          actor,
          "Bob",
          LaxEmailAddress("Bob@Smith.com"),
          submissionId,
          1,
          "123",
          "Dave",
          LaxEmailAddress("Dave@Smith.com")
        )

      "convert from json" in {
        val evtOut = Json.parse(jsonText).as[ApplicationEvent]

        evtOut shouldBe a[ResponsibleIndividualDeclinedOrDidNotVerify]
      }

      "write to json" in {
        Json.toJson(evt).prettifier shouldBe Json.parse(jsonText).prettifier
      }
    }

    "dump some json" in {
      import LaxEmailAddress.StringSyntax

      val e: ApplicationEvent =
        ApiSubscribedV2(EventId.random, ApplicationId.random, FixedClock.instant, Actors.AppCollaborator("bob".toLaxEmail), ApiContext("bob"), ApiVersionNbr("1.0"))

      Json.toJson[Actor](e.actor).toString
      val txt                 = Json.toJson(e).toString
      val e2                  = Json.parse(txt).as[ApplicationEvent]
      e shouldBe e2
    }
  }
}

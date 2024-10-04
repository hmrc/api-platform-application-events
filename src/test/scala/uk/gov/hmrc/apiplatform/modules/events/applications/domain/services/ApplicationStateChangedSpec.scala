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
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.State

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.ApplicationStateChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApplicationStateChangedSpec extends EventSpec {

  "ApplicationStateChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val applicationStateChanged: ApplicationEvent = ApplicationStateChanged(
      anEventId,
      anAppId,
      anInstant,
      appCollaborator,
      State.TESTING.toString,
      State.PENDING_GATEKEEPER_APPROVAL.toString,
      requestingAdminName,
      requestingEmail
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"oldAppState":"${State.TESTING.toString}","newAppState":"${State.PENDING_GATEKEEPER_APPROVAL.toString}","requestingAdminName":"$requestingAdminName","requestingAdminEmail":"${requestingEmail.text}","eventType":"APPLICATION_STATE_CHANGED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationStateChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(applicationStateChanged).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApplicationStateChanged correctly" in {
      testDisplay(
        applicationStateChanged,
        EventTags.SUBSCRIPTION,
        "Application State changed",
        List(State.TESTING.toString, State.PENDING_GATEKEEPER_APPROVAL.toString, requestingAdminName, requestingEmail.text)
      )
    }
  }

}

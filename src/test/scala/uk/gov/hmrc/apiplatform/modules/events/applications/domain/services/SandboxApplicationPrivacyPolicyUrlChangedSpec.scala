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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.SandboxApplicationPrivacyPolicyUrlChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class SandboxApplicationPrivacyPolicyUrlChangedSpec extends EventSpec {

  "SandboxApplicationPrivacyPolicyUrlChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val oldPrivacyPolicyUrl = "http://someplace.com/old"
    val newPrivacyPolicyUrl = "http://someplace.com/new"

    val event: ApplicationEvent =
      SandboxApplicationPrivacyPolicyUrlChanged(anEventId, anAppId, anInstant, appCollaborator, Some(oldPrivacyPolicyUrl), newPrivacyPolicyUrl)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"${instantText}","actor":{"email":"${appCollaborator.email}"},"oldPrivacyPolicyUrl":"${oldPrivacyPolicyUrl}","privacyPolicyUrl":"${newPrivacyPolicyUrl}","eventType":"SANDBOX_APPLICATION_PRIVACY_POLICY_URL_CHANGED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[SandboxApplicationPrivacyPolicyUrlChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "convert to correct Meta Data" in {
      event.asMetaData()
      event.asMetaData()._1 shouldBe "Application Privacy Policy Url Changed"
      event.asMetaData()._2 shouldBe List("From: http://someplace.com/old", "To: http://someplace.com/new")
    }

    "display event correctly" in {
      testDisplay(
        event,
        EventTags.PRIVACY_POLICY,
        "Application Privacy Policy Url Changed",
        List(s"From: ${oldPrivacyPolicyUrl}", s"To: ${newPrivacyPolicyUrl}")
      )
    }
  }

}

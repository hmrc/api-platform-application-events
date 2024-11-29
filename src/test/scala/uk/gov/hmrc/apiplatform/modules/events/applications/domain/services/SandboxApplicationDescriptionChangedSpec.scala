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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.SandboxApplicationDescriptionChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class SandboxApplicationDescriptionChangedSpec extends EventSpec {

  "SandboxApplicationDescriptionChangedEvent" when {
    import EventsInterServiceCallJsonFormatters._

    "we have an old description" should {
      val oldDescription = Some("Adrians App")
      val newDescription = "Bobs App"

      val event: ApplicationEvent =
        SandboxApplicationDescriptionChanged(anEventId, anAppId, anInstant, appCollaborator, oldDescription, newDescription)

      val jsonText =
        raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"${instantText}","actor":{"email":"${appCollaborator.email}"},"oldDescription":"${oldDescription.get}","description":"${newDescription}","eventType":"SANDBOX_APPLICATION_DESCRIPTION_CHANGED"}"""

      "convert from json" in {
        Json.parse(jsonText).as[ApplicationEvent] shouldBe a[SandboxApplicationDescriptionChanged]
      }

      "convert to correctJson" in {
        val eventJSonString = Json.toJson(event).toString()
        eventJSonString shouldBe jsonText
      }

      "display event correctly" in {
        testDisplay(
          event,
          EventTags.APP_NAME,
          "Application Description Changed",
          List(s"From: ${oldDescription.get}", s"To: ${newDescription}")
        )
      }
    }

    "we have no existing description" should {
      val newDescription = "Bobs App"

      val event: ApplicationEvent =
        SandboxApplicationDescriptionChanged(anEventId, anAppId, anInstant, appCollaborator, None, newDescription)

      val jsonText =
        raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"${instantText}","actor":{"email":"${appCollaborator.email}"},"description":"${newDescription}","eventType":"SANDBOX_APPLICATION_DESCRIPTION_CHANGED"}"""

      "convert from json" in {
        Json.parse(jsonText).as[ApplicationEvent] shouldBe a[SandboxApplicationDescriptionChanged]
      }

      "convert to correctJson" in {
        val eventJSonString = Json.toJson(event).toString()
        eventJSonString shouldBe jsonText
      }

      "display event correctly" in {
        testDisplay(
          event,
          EventTags.APP_NAME,
          "Application Description Changed",
          List(s"To: ${newDescription}")
        )
      }
    }
  }

}

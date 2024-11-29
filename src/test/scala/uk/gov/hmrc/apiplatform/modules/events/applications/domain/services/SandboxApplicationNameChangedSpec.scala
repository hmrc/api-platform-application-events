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
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.ApplicationName

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.SandboxApplicationNameChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class SandboxApplicationNameChangedSpec extends EventSpec {

  "SandboxApplicationNameChangedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val oldName = ApplicationName("Adrians App")
    val newName = ApplicationName("Bobs App")

    val event: ApplicationEvent =
      SandboxApplicationNameChanged(anEventId, anAppId, anInstant, appCollaborator, oldName, newName)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"${instantText}","actor":{"email":"${appCollaborator.email}"},"oldName":"${oldName}","newName":"${newName}","eventType":"SANDBOX_APPLICATION_NAME_CHANGED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[SandboxApplicationNameChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display event correctly" in {
      testDisplay(
        event,
        EventTags.APP_NAME,
        "Application Name Changed",
        List(s"From: ${oldName}", s"To: ${newName}")
      )
    }
  }
}

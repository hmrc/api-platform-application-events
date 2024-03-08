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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.SandboxApplicationTermsAndConditionsUrlChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class SandboxApplicationTermsAndConditionsUrlChangedSpec extends EventSpec {

  "SandboxApplicationTermsAndConditionsUrlChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val oldTermsAndConditionsUrl = "http://someplace.com/old"
    val newTermsAndConditionsUrl = "http://someplace.com/new"

    val event: ApplicationEvent =
      SandboxApplicationTermsAndConditionsUrlChanged(anEventId, anAppId, anInstant, appCollaborator, oldTermsAndConditionsUrl, newTermsAndConditionsUrl)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"email":"bob@example.com"},"oldTermsAndConditionsUrl":"${oldTermsAndConditionsUrl}","termsAndConditionsUrl":"${newTermsAndConditionsUrl}","eventType":"SANDBOX_APPLICATION_TERMS_AND_CONDITIONS_URL_CHANGED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[SandboxApplicationTermsAndConditionsUrlChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display event correctly" in {
      testDisplay(
        event,
        EventTags.TERMS_AND_CONDITIONS,
        "Application Term and Conditions Url Changed",
        List(s"From: ${oldTermsAndConditionsUrl}", s"To: ${newTermsAndConditionsUrl}")
      )
    }
  }

}

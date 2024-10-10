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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.RedirectUriChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class RedirectUriChangedSpec extends EventSpec {

  "RedirectUriChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = RedirectUriChanged(anEventId, anAppId, anInstant, appCollaborator, toChangeRedirectUri, aRedirectUri)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"oldRedirectUri":"${toChangeRedirectUri.uri}","newRedirectUri":"${aRedirectUri.uri}","eventType":"REDIRECT_URI_CHANGED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[RedirectUriChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display RedirectUriChanged correctly" in {
      testDisplay(
        event,
        EventTags.REDIRECT_URIS,
        "Redirect URI changed",
        List(s"Original:", aRedirectUri.uri, "Replaced with:", toChangeRedirectUri.uri)
      )
    }
  }

}

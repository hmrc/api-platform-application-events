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
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.{RedirectUriAdded, RedirectUrisUpdatedEvent}
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class RedirectUrisUpdatedEventSpec extends EventSpec {

  "RedirectUrisUpdatedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = RedirectUrisUpdatedEvent(anEventId, anAppId, anInstant, appCollaborator, toChangeRedirectUri.uri, aRedirectUri.uri)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"oldRedirectUris":"${toChangeRedirectUri.uri}","newRedirectUris":"${aRedirectUri.uri}","eventType":"REDIRECT_URIS_UPDATED"}"""

      "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[RedirectUrisUpdatedEvent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display RedirectUrisUpdatedEvent correctly" in {
      testDisplay(
        event,
        EventTags.REDIRECT_URIS,
        "Redirect URI updated",
        List(s"Original:", toChangeRedirectUri.uri, "Replaced with:", aRedirectUri.uri)
      )
    }
  }

}

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
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.ClientSecretRemovedEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ClientSecretRemovedEventSpec extends EventSpec {

  "ClientSecretRemovedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val clientSecretRemoved: ApplicationEvent = ClientSecretRemovedEvent(anEventId, anAppId, anInstant, appCollaborator, aClientSecretId)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"clientSecretId":"someClientId","eventType":"CLIENT_SECRET_REMOVED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ClientSecretRemovedEvent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(clientSecretRemoved).toString()
      eventJSonString shouldBe jsonText
    }

    "display ClientSecretRemovedEvent correctly" in {
      testDisplay(clientSecretRemoved, EventTags.CLIENT_SECRET, "Client Secret Removed", List(s"Id: $aClientSecretId"))
    }
  }

}

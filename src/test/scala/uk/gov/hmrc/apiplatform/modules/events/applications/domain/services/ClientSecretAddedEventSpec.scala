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
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.{ClientSecretAddedEvent, ClientSecretAddedV2}
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ClientSecretAddedEventSpec extends EventSpec {

    "ClientSecretAdded" should {
        import EventsInterServiceCallJsonFormatters._

        val clientSecretAdded: ApplicationEvent = ClientSecretAddedEvent(anEventId, anAppId, anInstant, appCollaborator, aClientSecretId)

        val jsonText = raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"clientSecretId":"someClientId","eventType":"CLIENT_SECRET_ADDED"}"""

        "convert from json" in {
            val evt = Json.parse(jsonText).as[ApplicationEvent]

            evt shouldBe a[ClientSecretAddedEvent]
        }

        "convert to correctJson" in {

            val eventJSonString = Json.toJson(clientSecretAdded).toString()
            eventJSonString shouldBe jsonText
        }

        "display ClientSecretRemoved correctly" in {
            testDisplay(clientSecretAdded, EventTags.CLIENT_SECRET, "Client Secret Added", List(s"Id: $aClientSecretId"))
        }
    }
  
}

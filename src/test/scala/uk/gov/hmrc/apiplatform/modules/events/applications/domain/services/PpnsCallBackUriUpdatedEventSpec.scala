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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.PpnsCallBackUriUpdatedEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class PpnsCallBackUriUpdatedEventSpec extends EventSpec {

  "PpnsCallBackUriUpdatedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val ppnsCallBackUriUpdatedEvent: ApplicationEvent =
      PpnsCallBackUriUpdatedEvent(anEventId, anAppId, anInstant, appCollaborator, boxId, boxName, oldCallbackUrl, newCallbackUrl)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"boxId":"${boxId}","boxName":"${boxName}","oldCallbackUrl":"$oldCallbackUrl","newCallbackUrl":"$newCallbackUrl","eventType":"PPNS_CALLBACK_URI_UPDATED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[PpnsCallBackUriUpdatedEvent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(ppnsCallBackUriUpdatedEvent).toString()
      eventJSonString shouldBe jsonText
    }

    "display PpnsCallBackUriUpdatedEvent correctly" in {
      testDisplay(
        ppnsCallBackUriUpdatedEvent,
        EventTags.PPNS_CALLBACK,
        "Ppns CallBackUri Updated",
        List(s"boxName: $boxName", s"oldCallBackUrl: $oldCallbackUrl", s"newCallBackUrl: $newCallbackUrl")
      )
    }
  }

}

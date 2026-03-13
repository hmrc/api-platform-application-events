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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.ApiSubscribedEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApiSubscribedEventSpec extends EventSpec {

  "ApiSubscribedEvent" should {
    import EventsInterServiceCallJsonFormatters._

    val apiSubscribedEvent: ApplicationEvent = ApiSubscribedEvent(anEventId, anAppId, anInstant, appCollaborator, context.value, version.value)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"context":"${context.value}","version":"${version.value}","eventType":"API_SUBSCRIBED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApiSubscribedEvent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(apiSubscribedEvent).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApiSubscribedEvent correctly" in {
      testDisplay(apiSubscribedEvent, EventTags.SUBSCRIPTION, "Api Subscribed", List(s"${context.value}", s"${version.value}"))
    }
  }

}

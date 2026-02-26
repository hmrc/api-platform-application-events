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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.ApiSubscribedV2
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, ApplicationEvents, EventSpec, EventTags}

class ApiSubscribedV2Spec extends EventSpec {

  "ApiSubscribedV2" should {
    import EventsInterServiceCallJsonFormatters._

    val apiSubscribedV2: ApplicationEvent = ApplicationEvents.ApiSubscribedV2(anEventId, anAppId, anInstant, appCollaborator, context, version)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"context":"${context.value}","version":"${version.value}","eventType":"API_SUBSCRIBED_V2"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApiSubscribedV2]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(apiSubscribedV2).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApiSubscribedV2 correctly" in {
      testDisplay(apiSubscribedV2, EventTags.SUBSCRIPTION, "Api Subscribed", List(s"${context.value}", s"${version.value}"))
    }
  }

}

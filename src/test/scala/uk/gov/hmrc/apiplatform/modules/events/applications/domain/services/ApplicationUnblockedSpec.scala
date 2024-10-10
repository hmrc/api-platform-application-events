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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApplicationUnblockedSpec extends EventSpec {

  "ApplicationUnblocked" should {
    import EventsInterServiceCallJsonFormatters._

    val gkUserStr = gkCollaborator.user

    val applicationUnblocked: ApplicationEvent = ApplicationUnblocked(
      anEventId,
      anAppId,
      anInstant,
      gkCollaborator
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"user":"$gkUserStr"},"eventType":"APPLICATION_UNBLOCKED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationUnblocked]
    }

    "convert to correct Json" in {
      val eventJSonString = Json.toJson(applicationUnblocked).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApplicationUnblocked correctly" in {
      testDisplay(applicationUnblocked, EventTags.APP_LIFECYCLE, "Application unblocked", List.empty)
    }
  }
}

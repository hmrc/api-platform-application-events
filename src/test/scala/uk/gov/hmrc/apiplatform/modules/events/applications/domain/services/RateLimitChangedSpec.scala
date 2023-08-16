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

import uk.gov.hmrc.apiplatform.modules.applications.domain.models.RateLimitTier
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.RateLimitChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec}

class RateLimitChangedSpec extends EventSpec {
  "RateLimitChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = RateLimitChanged(anEventId, anAppId, anInstant, gkCollaborator, RateLimitTier.BRONZE, RateLimitTier.PLATINUM)

    val json =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"user":"someUser"},"oldRateLimit":"BRONZE","newRateLimit":"PLATINUM","eventType":"RATE_LIMIT_CHANGED"}"""

    "convert from json" in {
      val result = Json.parse(json).as[RateLimitChanged]

      result shouldBe a[RateLimitChanged]
    }

    "convert to correctJson" in {
      val result = Json.toJson(event).toString()
      result shouldBe json
    }
  }
}

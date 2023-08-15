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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.GrantLengthChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class GrantLengthChangedSpec extends EventSpec {

  private val oldGrantLengthInDays = 180
  private val newGrantLengthInDays = 260

  "GrantLengthChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = GrantLengthChanged(anEventId, anAppId, anInstant, gkCollaborator, oldGrantLengthInDays, newGrantLengthInDays)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"someUser"},"oldGrantLengthInDays":$oldGrantLengthInDays,"newGrantLengthInDays":$newGrantLengthInDays,"eventType":"GRANT_LENGTH_CHANGED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[GrantLengthChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display GrantLengthChanged correctly" in {
      testDisplay(
        event,
        EventTags.GRANT_LENGTH,
        "Grant Length Changed",
        List(s"old grant length $oldGrantLengthInDays days", s"new grant length $newGrantLengthInDays days")
      )
    }
  }

}

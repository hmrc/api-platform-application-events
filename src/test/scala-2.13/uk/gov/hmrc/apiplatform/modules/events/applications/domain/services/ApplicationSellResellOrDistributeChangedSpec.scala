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
import uk.gov.hmrc.apiplatform.modules.applications.access.domain.models.SellResellOrDistribute

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApplicationSellResellOrDistributeChangedSpec extends EventSpec {

  "ApplicationSellResellOrDistributeChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val applicationSellResellOrDistributeChanged: ApplicationEvent = ApplicationSellResellOrDistributeChanged(
      anEventId,
      anAppId,
      anInstant,
      appCollaborator,
      Some(SellResellOrDistribute("Yes")),
      SellResellOrDistribute("No")
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}"},"oldSellResellOrDistribute":"Yes","newSellResellOrDistribute":"No","eventType":"APPLICATION_SELL_RESELL_OR_DISTRIBUTE_CHANGED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationSellResellOrDistributeChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(applicationSellResellOrDistributeChanged).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApplicationSellResellOrDistributeChanged correctly" in {
      testDisplay(
        applicationSellResellOrDistributeChanged,
        EventTags.TERMS_OF_USE,
        "Application SellResellOrDistribute Changed",
        List("Old SellResellOrDistribute: Yes", "New SellResellOrDistribute: No")
      )
    }
  }

}

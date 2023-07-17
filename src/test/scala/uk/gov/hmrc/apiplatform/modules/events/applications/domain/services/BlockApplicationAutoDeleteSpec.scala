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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.BlockApplicationAutoDelete
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, ApplicationEvents, EventSpec, EventTags}

class BlockApplicationAutoDeleteSpec extends EventSpec {

  "BlockApplicationAutoDelete" should {
    import EventsInterServiceCallJsonFormatters._

    val blockApplicationAutoDelete: ApplicationEvent = ApplicationEvents.BlockApplicationAutoDelete(anEventId, anAppId, anInstant, gkCollaborator)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"someUser"},"eventType":"BLOCK_APPLICATION_AUTO_DELETE"}""".stripMargin

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[BlockApplicationAutoDelete]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(blockApplicationAutoDelete).toString()
      eventJSonString shouldBe jsonText
    }

    "display BlockApplicationAutoDelete correctly" in {
      testDisplay(
        blockApplicationAutoDelete,
        EventTags.APP_LIFECYCLE,
        "Application auto delete blocked",
        List(s"Changed by: ${gkCollaborator.user}", s"Application Id: ${anAppId.value}")
      )
    }
  }

}

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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.AllowApplicationAutoDelete
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, ApplicationEvents, EventSpec, EventTags}

class AllowApplicationAutoDeleteSpec extends EventSpec {

  "AllowApplicationAutoDelete" should {
    import EventsInterServiceCallJsonFormatters._

    val allowApplicationAutoDelete: ApplicationEvent = ApplicationEvents.AllowApplicationAutoDelete(anEventId, anAppId, anInstant, gkCollaborator)

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"someUser"},"eventType":"ALLOW_APPLICATION_AUTO_DELETE"}""".stripMargin

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[AllowApplicationAutoDelete]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(allowApplicationAutoDelete).toString()
      eventJSonString shouldBe jsonText
    }

    "display AllowApplicationAutoDelete correctly" in {
      testDisplay(
        allowApplicationAutoDelete,
        EventTags.APP_LIFECYCLE,
        "Application auto delete allowed",
        List(s"Changed by: ${gkCollaborator.user}", s"Application Id: ${anAppId.value}")
      )
    }
  }

}

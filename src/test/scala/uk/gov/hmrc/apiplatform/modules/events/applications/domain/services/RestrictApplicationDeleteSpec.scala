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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.RestrictApplicationDelete
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, ApplicationEvents, EventSpec, EventTags}

class RestrictApplicationDeleteSpec extends EventSpec {

  val defaultReasons = "No reason given"

  "RestrictApplicationDelete" should {
    import EventsInterServiceCallJsonFormatters._

    val restrictApplicationDeleteWithReasons: ApplicationEvent        = ApplicationEvents.RestrictApplicationDelete(anEventId, anAppId, anInstant, gkCollaborator, reasons)
    val restrictApplicationDeleteWithDefaultReasons: ApplicationEvent = ApplicationEvents.RestrictApplicationDelete(anEventId, anAppId, anInstant, gkCollaborator, defaultReasons)

    val jsonTextWithReasons =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"user":"A surname"},"reasons":"Some reasons here","eventType":"RESTRICT_APPLICATION_DELETE"}""".stripMargin

    val jsonTextWithoutReasons =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"user":"A surname"},"eventType":"RESTRICT_APPLICATION_DELETE"}""".stripMargin

    "convert from json without reasons gives event with default reasons" in {
      val evt = Json.parse(jsonTextWithoutReasons).as[ApplicationEvent]

      evt match {
        case (e: RestrictApplicationDelete) => {
          e.reasons shouldBe defaultReasons
          e.applicationId shouldBe anAppId
          e.actor shouldBe gkCollaborator
          e.id shouldBe anEventId
        }
        case _                              => fail()
      }
    }

    "convert from json with reasons gives event with reasons" in {
      val evt = Json.parse(jsonTextWithReasons).as[ApplicationEvent]

      evt match {
        case (e: RestrictApplicationDelete) => {
          e.reasons shouldBe reasons
          e.applicationId shouldBe anAppId
          e.actor shouldBe gkCollaborator
          e.id shouldBe anEventId
        }
        case _                              => fail()
      }
    }

    "convert to json with reasons" in {

      val eventJSonString = Json.toJson(restrictApplicationDeleteWithReasons).toString()
      eventJSonString shouldBe jsonTextWithReasons
    }

    "display RestrictApplicationDelete with reasons correctly" in {
      testDisplay(
        restrictApplicationDeleteWithReasons,
        EventTags.APP_LIFECYCLE,
        "Application delete restricted",
        List(s"Reason(s) given as: ${reasons}")
      )
    }

    "display RestrictApplicationDelete with default reasons correctly" in {
      testDisplay(
        restrictApplicationDeleteWithDefaultReasons,
        EventTags.APP_LIFECYCLE,
        "Application delete restricted",
        List(s"Reason(s) given as: ${defaultReasons}")
      )
    }
  }

}

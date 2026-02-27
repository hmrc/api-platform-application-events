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

import play.api.libs.json.{JsError, Json}
import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventTag

class EventTagJsonFormattersSpec extends BaseJsonFormattersSpec {

  "EventTagJsonFormatters" when {

    "given an APP_NAME" should {
      "produce json" in {
        val tag: EventTag = EventTag.AppName
        testToJson[EventTag](tag)("description" -> "Application name", "type" -> "APP_NAME")
      }

      "read json" in {
        testFromJson[EventTag](""""APP_NAME"""")(EventTag.AppName)
      }

      "read complex json" in {
        testFromJson[EventTag](
          """{"type":"APP_NAME", "description": "Application name"}"""
        )(EventTag.AppName)
      }

      "fail to read invalid text json" in {
        Json.parse(""""BOBBINS"""").validate[EventTag] should matchPattern { case JsError(_) => }
      }
      "fail to read invalid typed json" in {
        Json.parse("""1234""").validate[EventTag] should matchPattern { case JsError(_) => }
      }
    }
  }
}

/*
 * Copyright 2022 HM Revenue & Customs
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

import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventTag
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventTags
import play.api.libs.json.JsString
import play.api.libs.json.Json
import play.api.libs.json.JsError

class EventTagJsonFormattersSpec extends JsonFormattersSpec {

  "EventTagJsonFormatters" when {

    import EventTagJsonFormatters._

    "given an APP_NAME" should {
      "produce json" in {
        val tag: EventTag = EventTags.APP_NAME
        Json.toJson(tag) shouldBe JsString("APP_NAME")
      }

      "read json" in {
        testFromJson[EventTag](""""APP_NAME"""")(EventTags.APP_NAME)
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

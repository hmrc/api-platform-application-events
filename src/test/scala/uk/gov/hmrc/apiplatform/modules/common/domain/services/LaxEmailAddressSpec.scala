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

package uk.gov.hmrc.apiplatform.modules.common.domain.services

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.domain.models.LaxEmailAddress

class LaxEmailAddressSpec extends JsonFormattersSpec {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")

  "LaxEmailAddress Json Formatting" when {

    "given an lax email address" should {
      "produce json" in {
        Json.toJson(LaxEmailAddress("quark")) shouldBe JsString("quark")
      }

      "read json" in {
        Json.parse(""" "quark" """).as[LaxEmailAddress] shouldBe LaxEmailAddress("quark")
      }
    }
  }
}

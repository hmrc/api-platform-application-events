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

package uk.gov.hmrc.apiplatform.modules.apis.domain.models

import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec
import play.api.libs.json.Json
import play.api.libs.json.JsString

class ApiIdentifierSpec extends JsonFormattersSpec {
  val aContext = ApiContext("c1")
  val aVersion = ApiVersion("1.0")
  val anApiIdentifer = ApiIdentifier(aContext, aVersion)

  "ApiContext" should {
    "convert to json" in {

      Json.toJson(aContext) shouldBe JsString("c1")
    }

    "read from json" in {
      testFromJson[ApiContext](""""c1"""")(aContext)
    }
  }

  "ApiVersion" should {
    "convert to json" in {

      Json.toJson(aVersion) shouldBe JsString("1.0")
    }

    "read from json" in {
      testFromJson[ApiVersion](""""1.0"""")(aVersion)
    }
  }

  "ApiIdentifier" should {

    "convert to json" in {

      testToJson(anApiIdentifer)(("context" -> "c1"), ("version" -> "1.0"))
    }

    "read from json" in {
      testFromJson[ApiIdentifier]("""{"context":"c1","version":"1.0"}""")(anApiIdentifer)
    }
  }
}

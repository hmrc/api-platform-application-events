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

import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.LaxEmailAddress
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventId
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId

class CommonJsonFormattersSpec extends JsonFormattersSpec {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")
  val bobSmithUserName = "bob smith"
  "CommonJsonFormatters" when {

    import CommonJsonFormatters._

    "given an lax applicationId" should {
      "produce json" in {
        Json.toJson(ApplicationId("quark")) shouldBe JsString("quark")
      }

      "read json" in {
        Json.parse(""" "quark" """).as[ApplicationId] shouldBe ApplicationId("quark")
      }
    }

    "given an lax email address" should {
      "produce json" in {
        Json.toJson(LaxEmailAddress("quark")) shouldBe JsString("quark")
      }

      "read json" in {
        Json.parse(""" "quark" """).as[LaxEmailAddress] shouldBe LaxEmailAddress("quark")
      }
    }

    "given an event id" should {
      val uuid = java.util.UUID.randomUUID

      "produce json" in {
        Json.toJson(EventId(uuid)) shouldBe JsString(uuid.toString)
      }

      "read json" in {
        Json.parse(s""" "${uuid.toString}" """).as[EventId] shouldBe EventId(uuid)
      }
    }
  }
}

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

package uk.gov.hmrc.apiplatform.modules.applications.domain.services

import uk.gov.hmrc.apiplatform.modules.applications.domain.models.{PrivacyPolicyLocation, PrivacyPolicyLocations}
import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec

class PrivacyPolicyLocationJsonFormattersSpec extends JsonFormattersSpec {

  "PrivacyPolicyLocationJsonFormatters" when {

    import PrivacyPolicyLocationJsonFormatters._

    "given a policy of none provided" should {
      "produce json" in {
        testToJson[PrivacyPolicyLocation](PrivacyPolicyLocations.NoneProvided)(
          ("privacyPolicyType" -> "noneProvided")
        )
      }

      "read json" in {
        testFromJson[PrivacyPolicyLocation]("""{"privacyPolicyType":"noneProvided"}""")(PrivacyPolicyLocations.NoneProvided)
      }
    }

    "given policy of in desktop software provided" should {
      "produce json" in {
        testToJson[PrivacyPolicyLocation](PrivacyPolicyLocations.InDesktopSoftware)(
          ("privacyPolicyType" -> "inDesktop")
        )
      }

      "read json" in {
        testFromJson[PrivacyPolicyLocation]("""{"privacyPolicyType":"inDesktop"}""")(PrivacyPolicyLocations.InDesktopSoftware)
      }
    }

    "given policy of url provided" should {
      "produce json" in {
        testToJson[PrivacyPolicyLocation](PrivacyPolicyLocations.Url("aUrl"))(
          ("privacyPolicyType" -> "url"),
          ("value"             -> "aUrl")
        )
      }

      "read json" in {
        testFromJson[PrivacyPolicyLocation]("""{"privacyPolicyType":"url","value":"aUrl"}""")(
          PrivacyPolicyLocations.Url("aUrl")
        )
      }
    }
  }
}

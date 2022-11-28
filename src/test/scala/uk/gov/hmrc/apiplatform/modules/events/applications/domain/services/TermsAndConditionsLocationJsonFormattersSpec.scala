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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

class TermsAndConditionsLocationJsonFormattersSpec extends JsonFormattersSpec {

  "TermsAndConditionsLocationJsonFormatters" when {

    import TermsAndConditionsLocationJsonFormatters._

    "given a location of none provided" should {
      "produce json" in {
        testToJson[TermsAndConditionsLocation](TermsAndConditionsLocations.NoneProvided)(
          ("termsAndConditionsType" -> "noneProvided")
        )
      }

      "read json" in {
        testFromJson[TermsAndConditionsLocation]("""{"termsAndConditionsType":"noneProvided"}""")(TermsAndConditionsLocations.NoneProvided)
      }
    }

    "given location of in desktop software provided" should {
      "produce json" in {
        testToJson[TermsAndConditionsLocation](TermsAndConditionsLocations.InDesktopSoftware)(
          ("termsAndConditionsType" -> "inDesktop")
        )
      }

      "read json" in {
        testFromJson[TermsAndConditionsLocation]("""{"termsAndConditionsType":"inDesktop"}""")(TermsAndConditionsLocations.InDesktopSoftware)
      }
    }
    
    "given location of url provided" should {
      "produce json" in {
        testToJson[TermsAndConditionsLocation](TermsAndConditionsLocations.Url("aUrl"))(
          ("termsAndConditionsType" -> "url"),
          ("value" -> "aUrl")
        )
      }

      "read json" in {
        testFromJson[TermsAndConditionsLocation]("""{"termsAndConditionsType":"url","value":"aUrl"}""")(TermsAndConditionsLocations.Url("aUrl"))
      }
    }
  }
}

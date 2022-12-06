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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId
import java.time.LocalDateTime

class EventTagsSpec extends AnyWordSpec with Matchers with OptionValues {

  "EventTags" when {
    "given APP_NAME" should {
      "convert to text" in {
        EventTags.APP_NAME.toString() shouldBe "APP_NAME"
      }

      "convert from text" in {
        EventTags.fromString("APP_NAME").value shouldBe EventTags.APP_NAME
      }

      "describe it" in {
        EventTags.describe(EventTags.APP_NAME) shouldBe "Application Name"
      }

      "Correctly tag event" in {
        val evt = ProductionAppNameChangedEvent(
          EventId.random,
          ApplicationId.random,
          LocalDateTime.now(),
          Actors.Unknown,
          "Old",
          "New",
          LaxEmailAddress("bob")
        )

        EventTags.tag(evt) shouldBe EventTags.APP_NAME
      }
    }
  }
  "given rubbish" should {
    "find none for convert from text" in {
      EventTags.fromString("BOBBINS") shouldBe None
    }
  }

}

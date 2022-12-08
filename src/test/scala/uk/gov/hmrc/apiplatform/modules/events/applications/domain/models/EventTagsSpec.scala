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
    "converting to and from description" should {
      "work for ALL event tags" in {
        EventTags.ALL.foreach(et => EventTags.fromDescription(et.description) shouldBe Some(et))
      }
      "work for ALL descriptions" in {
        EventTags.ALL.map(et => et.description).foreach(d => EventTags.fromDescription(d).map(_.description) shouldBe Some(d))
      }

      "reject rubbish" in {
        EventTags.fromDescription("BOBBINS") shouldBe None
      }
    }

    "converting to and from toString" should {
      "work for ALL event tags" in {
        EventTags.ALL.foreach(et => EventTags.fromString(et.toString()) shouldBe Some(et))
      }
      "work for ALL toStrings" in {
        EventTags.ALL.map(et => et.toString()).foreach(s => EventTags.fromString(s).map(_.toString()) shouldBe Some(s))
      }

      "reject rubbish" in {
        EventTags.fromString("BOBBINS") shouldBe None
      }
    }

    "given an application name change" should {
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
}

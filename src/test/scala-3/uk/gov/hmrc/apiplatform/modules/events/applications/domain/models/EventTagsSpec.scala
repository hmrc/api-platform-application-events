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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.models

import uk.gov.hmrc.apiplatform.modules.common.domain.models.{Actors, ApplicationIdFixtures}
import uk.gov.hmrc.apiplatform.modules.common.utils.{BaseJsonFormattersSpec, FixedClock}
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.{ApplicationName, CollaboratorFixtures}

class EventTagsSpec extends BaseJsonFormattersSpec with ApplicationIdFixtures with CollaboratorFixtures with FixedClock {

  "EventTags" when {
    "converting to and from description" should {
      "work for ALL event tags" in {
        EventTag.values.foreach(et => EventTag.fromDescription(et.description) shouldBe Some(et))
      }
      "work for ALL descriptions" in {
        EventTag.values.map(et => et.description).foreach(d => EventTag.fromDescription(d).map(_.description) shouldBe Some(d))
      }

      "reject rubbish" in {
        EventTag.fromDescription("BOBBINS") shouldBe None
      }
    }

    "converting to and from toString" should {
      "work for ALL event tags" in {
        EventTag.values.foreach(et => EventTag.apply(et.toString()) shouldBe Some(et))
      }
      "work for ALL toStrings" in {
        EventTag.values.map(et => et.toString()).foreach(s => EventTag.apply(s).map(_.toString()) shouldBe Some(s))
      }

      "reject rubbish" in {
        EventTag.apply("BOBBINS") shouldBe None
      }
    }

    "given an application name change" should {
      "Correctly tag event" in {
        val evt = ApplicationEvents.ProductionAppNameChangedEvent(
          EventId.random,
          applicationIdOne,
          instant,
          Actors.Unknown,
          ApplicationName("Old"),
          ApplicationName("New"),
          emailOne
        )

        EventTag.tag(evt) shouldBe EventTag.AppName
      }
    }

    "json handling" should {
      "read correctly when only a JsString" in {
        testFromJson[EventTag](s""" "APP_LIFECYCLE" """)(EventTag.AppLifecycle)
      }

      "read correctly when only a JsString from toString" in {
        testFromJson[EventTag](s""" "${EventTag.AppLifecycle.toString}" """)(EventTag.AppLifecycle)
      }

      "read correctly when an object with a type field" in {
        testFromJson[EventTag](s"""{ "description": "Application lifecycle", "type": "APP_LIFECYCLE" }""")(EventTag.AppLifecycle)
      }

      "write correctly" in {
        testToJson[EventTag](EventTag.AppLifecycle)("description" -> "Application lifecycle", "type" -> "APP_LIFECYCLE")
      }
    }
  }
}

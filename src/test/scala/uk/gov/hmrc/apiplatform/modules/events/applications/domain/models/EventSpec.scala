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

import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.apiplatform.modules.common.utils.{JsonTestUtils, EventTestData}
import org.scalatest.matchers.should.Matchers
import org.scalatest.Inside
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.services.EventToDisplay

abstract class EventSpec extends AnyWordSpec with Matchers with Inside with JsonTestUtils with EventTestData {
  
  def testDisplay(applicationEvent: ApplicationEvent, expectedEventTag: EventTag, expectedEventType: String, expectedTextItems: List[String]) ={
    inside(EventToDisplay.display(applicationEvent)) {
      case DisplayEvent(_, _, _, _, eventTag, eventType, meta) => eventType shouldBe expectedEventType
        expectedTextItems.foreach(item => withClue(s"expected text $item not found in metaData for $eventType") {
          meta.find(_.contains(item)) should not be empty
          eventTag shouldBe expectedEventTag
      })
    }
  }
}

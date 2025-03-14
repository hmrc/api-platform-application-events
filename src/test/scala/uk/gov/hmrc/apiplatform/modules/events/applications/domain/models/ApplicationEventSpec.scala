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

import java.time.Instant
import java.time.temporal.ChronoUnit

import uk.gov.hmrc.apiplatform.modules.common.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.{CollaboratorFixtures, CoreApplicationFixtures}

class ApplicationEventSpec extends EventSpec with CoreApplicationFixtures with CollaboratorFixtures {

  "AbstractApplicationEvent" when {
    val aRole = "ADMINISTRATOR"

    "Ordering a collection of events" should {
      "Sort the later ones first" in {
        val time1 = Instant.now()
        val time2 = time1.minus(2, ChronoUnit.DAYS)
        val time3 = time1.minus(3, ChronoUnit.DAYS)

        val e1 = ApplicationEvents.TeamMemberRemovedEvent(EventId.random, applicationIdOne, time1, Actors.Unknown, emailOne, aRole)
        val e2 = ApplicationEvents.TeamMemberAddedEvent(EventId.random, applicationIdOne, time2, Actors.Unknown, emailOne, aRole)
        val e3 = ApplicationEvents.CollaboratorAddedV2(EventId.random, applicationIdOne, time3, Actors.Unknown, developerCollaborator)

        val es = List[ApplicationEvent](e2, e3, e1)

        es.sorted shouldBe List(e1, e2, e3)
      }
    }
  }
}

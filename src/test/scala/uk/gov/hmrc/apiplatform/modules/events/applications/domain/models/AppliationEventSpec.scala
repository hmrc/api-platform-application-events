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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import uk.gov.hmrc.apiplatform.modules.applications.domain.models.{ApplicationId, Collaborators}
import uk.gov.hmrc.apiplatform.modules.common.domain.models.{Actors, LaxEmailAddress}
import uk.gov.hmrc.apiplatform.modules.developers.domain.models.UserId

class AppliationEventSpec extends AnyWordSpec with Matchers {

  "AbstractApplicationEvent" when {
    val anActor       = Actors.Unknown
    val anEmail       = LaxEmailAddress("bob@smith.com")
    val aRole         = "ADMINISTRATOR"
    val appId         = ApplicationId.random
    val aUserId       = UserId.random
    val aCollaborator = Collaborators.Developer(aUserId, anEmail)

    "Ordering a collection of events" should {
      "Sort the later ones first" in {
        val time1 = Instant.now()
        val time2 = time1.minus(2, ChronoUnit.DAYS)
        val time3 = time1.minus(3, ChronoUnit.DAYS)

        val e1 = TeamMemberRemovedEvent(EventId.random, appId, time1, anActor, anEmail, aRole)
        val e2 = TeamMemberAddedEvent(EventId.random, appId, time2, anActor, anEmail, aRole)
        val e3 = CollaboratorAddedV2(EventId.random, appId, time3, anActor, aCollaborator, Set.empty)

        val es = List[ApplicationEvent](e2, e3, e1)

        es.sorted shouldBe List(e1, e2, e3)
      }
    }
  }
}

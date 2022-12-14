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

import org.scalatest.OptionValues
import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actor
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.Actors
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.LaxEmailAddress
import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec

class ActorJsonFormattersSpec extends JsonFormattersSpec with OptionValues {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")
  val bobSmithUserName     = "bob smith"

  "ActorJsonFormatters" when {

    import ActorJsonFormatters._

    "given a gatekeeper user" should {
      "produce json" in {
        testToJson[Actor](Actors.GatekeeperUser(bobSmithUserName))(
          ("actorType" -> "GATEKEEPER"),
          ("user"      -> bobSmithUserName)
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"GATEKEEPER","user":"bob smith"}""")(Actors.GatekeeperUser(bobSmithUserName))
      }
    }

    "given a collaborator actor" should {
      "produce json" in {
        testToJson[Actor](Actors.Collaborator(bobSmithEmailAddress))(
          ("actorType" -> "COLLABORATOR"),
          ("email"     -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"COLLABORATOR","email":"bob@smith.com"}""")(Actors.Collaborator(bobSmithEmailAddress))
      }
    }

    "given a scheduled job actor" should {
      "produce json" in {
        testToJson[Actor](Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))(
          ("actorType" -> "SCHEDULED_JOB"),
          ("jobId"     -> "DeleteAllAppsBwaHaHa")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"SCHEDULED_JOB","jobId":"DeleteAllAppsBwaHaHa"}""")(Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))
      }
    }

    "given an unknown actor" should {
      "produce json" in {
        testToJson[Actor](Actors.Unknown)(
          ("actorType" -> "UNKNOWN")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"UNKNOWN"}""")(Actors.Unknown)
      }
    }

    "given bad json" should {
      "fail accordingly" in {
        val text = """{"actorType":"NOT_VALID","jobId":"DeleteAllAppsBwaHaHa"}"""
        Json.parse(text).validate[Actor] match {
          case JsError(errs) =>
            errs.size shouldBe 1
            errs.map(_._1).headOption.value.toString shouldBe "/actorType"
            errs.map(_._2).headOption.value.toString should include("NOT_VALID is not a recognised actorType")
          case _             => fail()
        }
      }
    }
  }
}

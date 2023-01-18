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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import play.api.libs.json.{JsString, Json}

import uk.gov.hmrc.apiplatform.modules.common.domain.services.JsonFormattersSpec
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{OldStyleActor, OldStyleActors}

class OldStyleActorJsonFormattersSpec extends JsonFormattersSpec {

  "OldStyleActorJsonFormatters" when {

    import OldStyleActorJsonFormatters._

    "given a gatekeeper user" should {
      "produce json" in {
        val actor: OldStyleActor = OldStyleActors.GatekeeperUser("bob smith")

        Json.toJson(actor) shouldBe Json.obj(
          "actorType" -> JsString("GATEKEEPER"),
          "id"        -> JsString("bob smith")
        )
      }
    }

    "given a collaborator actor" should {
      "produce json" in {
        val actor: OldStyleActor = OldStyleActors.Collaborator("bob@smith.com")

        Json.toJson(actor) shouldBe Json.obj(
          "actorType" -> JsString("COLLABORATOR"),
          "id"        -> JsString("bob@smith.com")
        )
      }
    }

    "given a scheduled job actor" should {
      "produce json" in {
        val actor: OldStyleActor = OldStyleActors.ScheduledJob("DeleteAllAppsBwaHaHa")

        Json.toJson(actor) shouldBe Json.obj(
          "actorType" -> JsString("SCHEDULED_JOB"),
          "id"        -> JsString("DeleteAllAppsBwaHaHa")
        )
      }
    }

    "given an unknown actor" should {
      "produce json" in {
        testToJson[OldStyleActor](OldStyleActors.Unknown)(
          ("actorType" -> "UNKNOWN")
        )
      }

      "read json" in {
        testFromJson[OldStyleActor]("""{"actorType":"UNKNOWN"}""")(OldStyleActors.Unknown)
      }
    }
  }
}

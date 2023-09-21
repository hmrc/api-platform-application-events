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

import play.api.libs.json.Json

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.IPAllowlistCIDRBlockChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec}

class IPAllowlistCIDRBlockChangedSpec extends EventSpec {
  val oldIPAllowList = List("1.2.3.4/0")
  val oldIPAllowListAsStringArray = oldIPAllowList.mkString("\"", "\",\"", "\"")
  val newIPAllowList = List("1.2.3.4/0","5.6.7.8/0")
  val newIPAllowListAsStringArray = newIPAllowList.mkString("\"", "\",\"", "\"")

  "IPAllowlistCIDRBlockChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = IPAllowlistCIDRBlockChanged(anEventId, anAppId, anInstant, appCollaborator, oldIPAllowList, newIPAllowList)

    val json =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"oldIPAllowlist":[${oldIPAllowListAsStringArray}],"newIPAllowlist":[${newIPAllowListAsStringArray}],"eventType":"IP_ALLOWLIST_CIDR_BLOCK_CHANGED"}"""

    "convert from json" in {
      val result = Json.parse(json).as[IPAllowlistCIDRBlockChanged]

      result shouldBe a[IPAllowlistCIDRBlockChanged]
    }

    "convert to correctJson" in {
      val result = Json.toJson(event).toString()
      result shouldBe json
    }
  }
}

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
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.CidrBlock

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.IpAllowlistCidrBlockChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec}

class IPAllowlistCIDRBlockChangedSpec extends EventSpec {
  val oldIpAllowList              = List(CidrBlock("1.2.3.4/0"))
  val oldIpAllowListAsStringArray = oldIpAllowList.mkString("\"", "\",\"", "\"")
  val newIpAllowList              = List(CidrBlock("1.2.3.4/0"), CidrBlock("5.6.7.8/0"))
  val newIpAllowListAsStringArray = newIpAllowList.mkString("\"", "\",\"", "\"")

  "IPAllowlistCIDRBlockChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = IpAllowlistCidrBlockChanged(anEventId, anAppId, anInstant, appCollaborator, true, oldIpAllowList, newIpAllowList)

    val json =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"${instantText}","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"required":true,"oldIpAllowlist":[${oldIpAllowListAsStringArray}],"newIpAllowlist":[${newIpAllowListAsStringArray}],"eventType":"IP_ALLOWLIST_CIDR_BLOCK_CHANGED"}"""

    "convert from json" in {
      val result = Json.parse(json).as[IpAllowlistCidrBlockChanged]

      result shouldBe a[IpAllowlistCidrBlockChanged]
    }

    "convert to correctJson" in {
      val result = Json.toJson(event).toString()
      result shouldBe json
    }
  }
}

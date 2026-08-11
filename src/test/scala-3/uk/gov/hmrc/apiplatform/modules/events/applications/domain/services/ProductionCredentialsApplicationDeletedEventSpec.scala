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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.*
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTag}

class ProductionCredentialsApplicationDeletedEventSpec extends EventSpec {

  "ProductionCredentialsApplicationDeleted" should {
    import EventsInterServiceCallJsonFormatters.given

    val productionCredentialsApplicationDeleted: ApplicationEvent = ProductionCredentialsApplicationDeleted(anEventId, anAppId, anInstant, gkCollaborator, aClientId, "bob", reasons)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"user":"A surname","actorType":"GATEKEEPER"},"clientId":"${aClientId.value}","wso2ApplicationName":"bob","reasons":"$reasons","eventType":"PRODUCTION_CREDENTIALS_APPLICATION_DELETED"}"""
    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ProductionCredentialsApplicationDeleted]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(productionCredentialsApplicationDeleted).toString()
      eventJSonString shouldBe jsonText
    }
    "display ProductionCredentialsApplicationDeleted correctly" in {
      testDisplay(productionCredentialsApplicationDeleted, EventTag.TermsOfUse, "Application credentials deleted", List(reasons))
    }
  }
}

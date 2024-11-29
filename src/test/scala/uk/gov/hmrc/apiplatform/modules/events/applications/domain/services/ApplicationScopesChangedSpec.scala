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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApplicationScopesChangedSpec extends EventSpec {

  "ApplicationScopesChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val gkUserStr = gkCollaborator.user
    val oldScopes = Set("scope01", "scope02")
    val newScopes = Set("scope01", "scope03")

    val applicationScopesChanged: ApplicationEvent = ApplicationScopesChanged(
      anEventId,
      anAppId,
      anInstant,
      gkCollaborator,
      oldScopes,
      newScopes
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"user":"$gkUserStr"},"oldScopes":["scope01","scope02"],"newScopes":["scope01","scope03"],"eventType":"APPLICATION_SCOPES_CHANGED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationScopesChanged]
    }

    "convert to correct Json" in {
      val eventJSonString = Json.toJson(applicationScopesChanged).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApplicationScopesChanged correctly" in {
      testDisplay(applicationScopesChanged, EventTags.SCOPES, "Application scopes changed", List("Old scopes: scope01, scope02", "New scopes: scope01, scope03"))
    }
  }
}

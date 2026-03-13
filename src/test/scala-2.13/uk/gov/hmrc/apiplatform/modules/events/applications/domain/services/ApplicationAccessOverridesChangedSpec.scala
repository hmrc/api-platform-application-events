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
import uk.gov.hmrc.apiplatform.modules.applications.access.domain.models.OverrideFlag

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApplicationAccessOverridesChangedSpec extends EventSpec {

  "ApplicationAccessOverridesChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val gkUserStr    = gkCollaborator.user
    val oldOverrides = Set[OverrideFlag](OverrideFlag.PersistLogin, OverrideFlag.OriginOverride("origin01"))
    val newOverrides = Set[OverrideFlag](
      OverrideFlag.PersistLogin,
      OverrideFlag.SuppressIvForIndividuals(Set("scope01", "scope02")),
      OverrideFlag.GrantWithoutConsent(Set("scope03", "scope04", "scope05"))
    )

    val applicationAccessOverridesChanged: ApplicationEvent = ApplicationAccessOverridesChanged(
      anEventId,
      anAppId,
      anInstant,
      gkCollaborator,
      oldOverrides,
      newOverrides
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"user":"$gkUserStr"},"oldOverrides":[{"overrideType":"PERSIST_LOGIN_AFTER_GRANT"},{"origin":"origin01","overrideType":"ORIGIN_OVERRIDE"}],"newOverrides":[{"overrideType":"PERSIST_LOGIN_AFTER_GRANT"},{"scopes":["scope01","scope02"],"overrideType":"SUPPRESS_IV_FOR_INDIVIDUALS"},{"scopes":["scope03","scope04","scope05"],"overrideType":"GRANT_WITHOUT_TAXPAYER_CONSENT"}],"eventType":"APPLICATION_ACCESS_OVERRIDES_CHANGED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationAccessOverridesChanged]
    }

    "convert to correct Json" in {
      val eventJSonString = Json.toJson(applicationAccessOverridesChanged).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApplicationAccessOverridesChanged correctly" in {
      testDisplay(
        applicationAccessOverridesChanged,
        EventTags.SCOPES,
        "Application access overrides changed",
        List(
          "Old overrides: PersistLogin, OriginOverride(origin01)",
          "New overrides: PersistLogin, SuppressIvForIndividuals(scope01, scope02), GrantWithoutConsent(scope03, scope04, scope05)"
        )
      )
    }
  }
}

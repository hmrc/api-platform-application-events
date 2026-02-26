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
import uk.gov.hmrc.apiplatform.modules.common.domain.models.OrganisationIdFixtures

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ApplicationLinkedToOrganisationSpec extends EventSpec with OrganisationIdFixtures {

  "ApplicationLinkedToOrganisation" should {
    import EventsInterServiceCallJsonFormatters._

    val applicationLinkedToOrganisation: ApplicationEvent = ApplicationLinkedToOrganisation(
      anEventId,
      anAppId,
      anInstant,
      appCollaborator,
      organisationIdOne
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"organisationId":"$organisationIdOne","eventType":"APPLICATION_LINKED_TO_ORGANISATION"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationLinkedToOrganisation]
    }

    "convert to correct Json" in {
      val eventJSonString = Json.toJson(applicationLinkedToOrganisation).toString()
      eventJSonString shouldBe jsonText
    }

    "display ApplicationLinkedToOrganisation correctly" in {
      testDisplay(applicationLinkedToOrganisation, EventTags.APP_LIFECYCLE, "Application linked to organisation", List(s"Organisation Id: $organisationIdOne"))
    }
  }
}

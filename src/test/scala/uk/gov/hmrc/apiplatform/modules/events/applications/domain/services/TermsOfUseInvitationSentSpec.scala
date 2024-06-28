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

import java.time.{LocalDateTime, ZoneOffset}

import play.api.libs.json.Json

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class TermsOfUseInvitationSentSpec extends EventSpec {

  "TermsOfUseInvitationSent" should {
    import EventsInterServiceCallJsonFormatters._

    val gkUserStr        = gkCollaborator.user
    val dueBy            = LocalDateTime.of(2020, 2, 1, 3, 4, 5, 0).toInstant(ZoneOffset.UTC)
    val dueByText        = "2020-02-01T03:04:05.000"
    val dueByDisplayText = "01 February 2020"

    val termsOfUseInvitationSent: ApplicationEvent = TermsOfUseInvitationSent(
      anEventId,
      anAppId,
      anInstant,
      gkCollaborator,
      dueBy
    )

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"$gkUserStr"},"dueBy":"${dueByText}","eventType":"TERMS_OF_USE_INVITATION_SENT"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[TermsOfUseInvitationSent]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(termsOfUseInvitationSent).toString()
      eventJSonString shouldBe jsonText
    }
    "display TermsOfUseInvitationSent correctly" in {
      testDisplay(termsOfUseInvitationSent, EventTags.APP_LIFECYCLE, "Terms of Use Invitation Sent", List(gkUserStr, dueByDisplayText))
    }
  }

}

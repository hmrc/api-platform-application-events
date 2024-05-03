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

class ApplicationApprovalRequestSubmittedSpec extends EventSpec {

  "ApplicationApprovalRequestSubmitted" should {
    import EventsInterServiceCallJsonFormatters._

    val gkUserStr = gkCollaborator.user

    val applicationApprovalRequestSubmitted: ApplicationEvent = ApplicationApprovalRequestSubmitted(
      anEventId,
      anAppId,
      anInstant,
      gkCollaborator,
      submissionId,
      submissionIndex,
      requestingAdminName,
      requestingEmail
    )

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"$gkUserStr"},"submissionId":"${submissionId.value}","submissionIndex":$submissionIndex,"requestingAdminName":"$requestingAdminName","requestingAdminEmail":"${requestingEmail.text}","eventType":"APPLICATION_APPROVAL_REQUEST_SUBMITTED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ApplicationApprovalRequestSubmitted]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(applicationApprovalRequestSubmitted).toString()
      eventJSonString shouldBe jsonText
    }
    "display ApplicationApprovalRequestSubmitted correctly" in {
      testDisplay(applicationApprovalRequestSubmitted, EventTags.APP_LIFECYCLE, "Application Approval Request Submitted", List(gkUserStr, requestingEmail.text))
    }
  }

}

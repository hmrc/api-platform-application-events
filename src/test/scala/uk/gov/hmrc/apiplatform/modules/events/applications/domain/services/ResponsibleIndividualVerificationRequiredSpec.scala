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
import uk.gov.hmrc.apiplatform.modules.common.domain.models.ActorType

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class ResponsibleIndividualVerificationRequiredSpec extends EventSpec {

  "ResponsibleIndividualVerificationRequired" should {
    import EventsInterServiceCallJsonFormatters._

    val appName = "App name"

    val responsibleIndividualVerificationRequired: ApplicationEvent = ResponsibleIndividualVerificationRequired(
      anEventId,
      anAppId,
      anInstant,
      appCollaborator,
      appName,
      requestingAdminName,
      requestingEmail,
      responsibleIndividualName,
      responsibleIndividualEmail,
      submissionId,
      submissionIndex,
      verificationId
    )

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$anAppId","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email.text}","actorType":"${ActorType.COLLABORATOR}"},"applicationName":"App name","responsibleIndividualName":"$responsibleIndividualName","responsibleIndividualEmail":"${responsibleIndividualEmail.text.toLowerCase}","submissionId":"${submissionId.value}","submissionIndex":$submissionIndex,"verificationId":"$verificationId","requestingAdminName":"$requestingAdminName","requestingAdminEmail":"${requestingEmail.text}","eventType":"RESPONSIBLE_INDIVIDUAL_VERIFICATION_REQUIRED"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ResponsibleIndividualVerificationRequired]
    }

    "convert to correctJson" in {

      val eventJson = Json.toJson(responsibleIndividualVerificationRequired)
      eventJson shouldBe Json.parse(jsonText)
    }

    "display ResponsibleIndividualVerificationRequired correctly" in {
      testDisplay(
        responsibleIndividualVerificationRequired,
        EventTags.TERMS_OF_USE,
        "Responsible Individual verification required",
        List(submissionId.value.toString, responsibleIndividualName, responsibleIndividualEmail.text, requestingEmail.text)
      )
    }
  }

}

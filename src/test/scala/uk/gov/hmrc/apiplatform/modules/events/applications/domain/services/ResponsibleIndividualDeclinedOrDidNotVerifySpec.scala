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

class ResponsibleIndividualDeclinedOrDidNotVerifySpec extends EventSpec {

  "ResponsibleIndividualDeclinedOrDidNotVerify" should {
    import EventsInterServiceCallJsonFormatters._

    val gkUserStr = gkCollaborator.user

    val responsibleIndividualDeclinedOrDidNotVerify: ApplicationEvent = ResponsibleIndividualDeclinedOrDidNotVerify(
      anEventId,
      anAppId,
      anInstant,
      gkCollaborator,
      responsibleIndividualName,
      responsibleIndividualEmail,
      submissionId,
      submissionIndex,
      riCode,
      requestingAdminName,
      requestingEmail
    )

    val jsonText =
      raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"$gkUserStr","actorType":"${ActorType.GATEKEEPER}"},"responsibleIndividualName":"$responsibleIndividualName","responsibleIndividualEmail":"${responsibleIndividualEmail.text.toLowerCase}","submissionId":"${submissionId.value}","submissionIndex":$submissionIndex,"code":"$riCode","requestingAdminName":"$requestingAdminName","requestingAdminEmail":"${requestingEmail.text}","eventType":"RESPONSIBLE_INDIVIDUAL_DECLINED_OR_DID_NOT_VERIFY"}"""

    "convert from json" in {
      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[ResponsibleIndividualDeclinedOrDidNotVerify]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(responsibleIndividualDeclinedOrDidNotVerify).toString()
      eventJSonString shouldBe jsonText
    }
    "display ResponsibleIndividualDeclinedOrDidNotVerify correctly" in {
      testDisplay(
        responsibleIndividualDeclinedOrDidNotVerify,
        EventTags.APP_LIFECYCLE,
        "Responsible Individual Declined Or Did Not Verify",
        List(submissionId.value.toString, responsibleIndividualName, responsibleIndividualEmail.text, requestingEmail.text)
      )
    }
  }

}

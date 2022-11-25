/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.models

import java.time.LocalDateTime
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId

case class ResponsibleIndividualChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    previousResponsibleIndividualName: String,
    previousResponsibleIndividualEmail: LaxEmailAddress,
    newResponsibleIndividualName: String,
    newResponsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    code: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ResponsibleIndividualChangedToSelf(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    previousResponsibleIndividualName: String,
    previousResponsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ResponsibleIndividualSet(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    responsibleIndividualName: String,
    responsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    code: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ApplicationStateChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldAppState: String,
    newAppState: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ResponsibleIndividualVerificationStarted(
    id: EventId,
    applicationId: ApplicationId,
    applicationName: String,
    eventDateTime: LocalDateTime,
    actor: Actor,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress,
    responsibleIndividualName: String,
    responsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    verificationId: String
  ) extends ApplicationEvent

case class ResponsibleIndividualDeclined(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    responsibleIndividualName: String,
    responsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    code: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ResponsibleIndividualDeclinedUpdate(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    responsibleIndividualName: String,
    responsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    code: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ResponsibleIndividualDidNotVerify(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    responsibleIndividualName: String,
    responsibleIndividualEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    code: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ApplicationApprovalRequestDeclined(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    decliningUserName: String,
    decliningUserEmail: LaxEmailAddress,
    submissionId: String,
    submissionIndex: Int,
    reasons: String,
    requestingAdminName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

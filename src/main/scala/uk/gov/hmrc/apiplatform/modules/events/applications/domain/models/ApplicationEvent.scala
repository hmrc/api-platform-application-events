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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.models

import java.time.LocalDateTime

import uk.gov.hmrc.apiplatform.modules.apis.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.domain.models._

// scalastyle:off number.of.types

sealed trait AbstractApplicationEvent {
  def id: EventId
  def applicationId: ApplicationId
  def eventDateTime: LocalDateTime
}

sealed trait OldStyleApplicationEvent extends AbstractApplicationEvent {
  def actor: OldStyleActor
}

sealed trait ApplicationEvent extends AbstractApplicationEvent {
  def actor: Actor
}

object AbstractApplicationEvent {

  implicit val orderEvents: Ordering[AbstractApplicationEvent] = new Ordering[AbstractApplicationEvent]() {

    override def compare(x: AbstractApplicationEvent, y: AbstractApplicationEvent): Int =
      y.eventDateTime.compareTo(x.eventDateTime)
  }
}

case class PpnsCallBackUriUpdatedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    boxId: String,
    boxName: String,
    oldCallbackUrl: String,
    newCallbackUrl: String
  ) extends OldStyleApplicationEvent

case class RedirectUrisUpdated(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldRedirectUris: List[String],
    newRedirectUris: List[String]
  ) extends ApplicationEvent

/** DEPRECATED Use RedirectUrisUpdated instead
  */
case class RedirectUrisUpdatedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    oldRedirectUris: String,
    newRedirectUris: String
  ) extends OldStyleApplicationEvent

case class ProductionAppNameChangedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldAppName: String,
    newAppName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ProductionAppPrivacyPolicyLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldLocation: PrivacyPolicyLocation,
    newLocation: PrivacyPolicyLocation
  ) extends ApplicationEvent

case class ProductionLegacyAppPrivacyPolicyLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldUrl: String,
    newUrl: String
  ) extends ApplicationEvent

case class ProductionAppTermsConditionsLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldLocation: TermsAndConditionsLocation,
    newLocation: TermsAndConditionsLocation
  ) extends ApplicationEvent

case class ProductionLegacyAppTermsConditionsLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldUrl: String,
    newUrl: String
  ) extends ApplicationEvent

case class ClientSecretAdded(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    clientSecretId: String,
    clientSecretName: String
  ) extends ApplicationEvent

case class ClientSecretRemoved(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    clientSecretId: String,
    clientSecretName: String
  ) extends ApplicationEvent

/** DEPRECATED Use ClientSecretAdded instead
  */
case class ClientSecretAddedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    clientSecretId: String
  ) extends OldStyleApplicationEvent

/** DEPRECATED Use ClientSecretRemoved instead
  */
case class ClientSecretRemovedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    clientSecretId: String
  ) extends OldStyleApplicationEvent

case class CollaboratorAdded(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    collaborator: Collaborator,
    verifiedAdminsToEmail: Set[LaxEmailAddress]
  ) extends ApplicationEvent

case class CollaboratorRemoved(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    collaborator: Collaborator,
    verifiedAdminsToEmail: Set[LaxEmailAddress]
  ) extends ApplicationEvent

/** DEPRECATED Use CollaboratorAdded instead
  */
case class TeamMemberAddedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    teamMemberEmail: LaxEmailAddress,
    teamMemberRole: String
  ) extends OldStyleApplicationEvent

/** DEPRECATED Use CollaboratorRemoved instead
  */
case class TeamMemberRemovedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    teamMemberEmail: LaxEmailAddress,
    teamMemberRole: String
  ) extends OldStyleApplicationEvent

case class ApiSubscribed(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    apiIdentifier: ApiIdentifier
  ) extends ApplicationEvent

case class ApiUnsubscribed(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    apiIdentifier: ApiIdentifier
  ) extends ApplicationEvent

/** DEPRECATED Use ApiSubscribed instead
  */
case class ApiSubscribedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    context: String,
    version: String
  ) extends OldStyleApplicationEvent

/** DEPRECATED Use ApiUnsubscribed instead
  */
case class ApiUnsubscribedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    context: String,
    version: String
  ) extends OldStyleApplicationEvent

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

case class ApplicationDeleted(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    clientId: ClientId,
    wso2ApplicationName: String,
    reasons: String
  ) extends ApplicationEvent

case class ApplicationDeletedByGatekeeper(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    clientId: ClientId,
    wso2ApplicationName: String,
    reasons: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ProductionCredentialsApplicationDeleted(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    clientId: ClientId,
    wso2ApplicationName: String,
    reasons: String
  ) extends ApplicationEvent

// scalastyle:on number.of.types

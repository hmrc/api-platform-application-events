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

import java.time.Instant
import uk.gov.hmrc.apiplatform.modules.apis.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.{ApplicationId, RedirectUri, _}
import uk.gov.hmrc.apiplatform.modules.common.domain.models.{Actor, Actors, LaxEmailAddress}
import uk.gov.hmrc.apiplatform.modules.submissions.domain.models.SubmissionId

sealed trait ApplicationEvent {
  def id: EventId
  def applicationId: ApplicationId
  def eventDateTime: Instant
  def actor: Actor
}

object ApplicationEvent {

  implicit val orderEvents: Ordering[ApplicationEvent] = new Ordering[ApplicationEvent]() {

    override def compare(x: ApplicationEvent, y: ApplicationEvent): Int =
      y.eventDateTime.compareTo(x.eventDateTime)
  }
}

object ApplicationEvents {
  case class RedirectUriAdded(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      newRedirectUri: RedirectUri
    ) extends ApplicationEvent

  case class RedirectUriChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldRedirectUri: RedirectUri,
      newRedirectUri: RedirectUri
    ) extends ApplicationEvent

  case class RedirectUriDeleted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      deletedRedirectUri: RedirectUri
    ) extends ApplicationEvent

  case class PpnsCallBackUriUpdatedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      boxId: String,
      boxName: String,
      oldCallbackUrl: String,
      newCallbackUrl: String
    ) extends ApplicationEvent

  case class RedirectUrisUpdatedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldRedirectUris: List[String],
      newRedirectUris: List[String]
    ) extends ApplicationEvent

  case class ProductionAppNameChangedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldAppName: String,
      newAppName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ProductionAppPrivacyPolicyLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldLocation: PrivacyPolicyLocation,
      newLocation: PrivacyPolicyLocation
    ) extends ApplicationEvent

  case class ProductionLegacyAppPrivacyPolicyLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldUrl: String,
      newUrl: String
    ) extends ApplicationEvent

  case class ProductionAppTermsConditionsLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldLocation: TermsAndConditionsLocation,
      newLocation: TermsAndConditionsLocation
    ) extends ApplicationEvent

  case class ProductionLegacyAppTermsConditionsLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldUrl: String,
      newUrl: String
    ) extends ApplicationEvent

  case class ClientSecretAddedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      clientSecretId: String,
      clientSecretName: String
    ) extends ApplicationEvent

  case class ClientSecretRemovedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      clientSecretId: String,
      clientSecretName: String
    ) extends ApplicationEvent

  case class CollaboratorAddedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      collaborator: Collaborator,
    ) extends ApplicationEvent

  case class CollaboratorRemovedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      collaborator: Collaborator,
    ) extends ApplicationEvent

  case class ApiSubscribedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: ApiContext,
      version: ApiVersion
    ) extends ApplicationEvent

  case class ApiUnsubscribedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: ApiContext,
      version: ApiVersion
    ) extends ApplicationEvent

  case class ResponsibleIndividualChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      previousResponsibleIndividualName: String,
      previousResponsibleIndividualEmail: LaxEmailAddress,
      newResponsibleIndividualName: String,
      newResponsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      code: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ResponsibleIndividualChangedToSelf(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      previousResponsibleIndividualName: String,
      previousResponsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ResponsibleIndividualSet(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      responsibleIndividualName: String,
      responsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      code: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ApplicationStateChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldAppState: String,
      newAppState: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ResponsibleIndividualVerificationStarted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      applicationName: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress,
      responsibleIndividualName: String,
      responsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      verificationId: String
    ) extends ApplicationEvent

  case class ResponsibleIndividualDeclined(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      responsibleIndividualName: String,
      responsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      code: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ResponsibleIndividualDeclinedUpdate(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      responsibleIndividualName: String,
      responsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      code: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ResponsibleIndividualDidNotVerify(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      responsibleIndividualName: String,
      responsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      code: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ResponsibleIndividualDeclinedOrDidNotVerify(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      responsibleIndividualName: String,
      responsibleIndividualEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      code: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ApplicationApprovalRequestDeclined(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      decliningUserName: String,
      decliningUserEmail: LaxEmailAddress,
      submissionId: SubmissionId,
      submissionIndex: Int,
      reasons: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class TermsOfUsePassed(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      submissionId: SubmissionId,
      submissionIndex: Int
    ) extends ApplicationEvent

  case class ApplicationDeleted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientId: ClientId,
      wso2ApplicationName: String,
      reasons: String
    ) extends ApplicationEvent

  case class ApplicationDeletedByGatekeeper(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      clientId: ClientId,
      wso2ApplicationName: String,
      reasons: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent

  case class ProductionCredentialsApplicationDeleted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientId: ClientId,
      wso2ApplicationName: String,
      reasons: String
    ) extends ApplicationEvent

  // *** DEPRECATED EVENTS ***

  /** DEPRECATED Use RedirectUrisUpdated instead
    */
  case class RedirectUrisUpdatedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldRedirectUris: String,
      newRedirectUris: String
    ) extends ApplicationEvent

  /** DEPRECATED Use ClientSecretAdded instead
    */
  case class ClientSecretAddedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientSecretId: String
    ) extends ApplicationEvent

  /** DEPRECATED Use ClientSecretRemoved instead
    */
  case class ClientSecretRemovedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientSecretId: String
    ) extends ApplicationEvent

  /** DEPRECATED Use CollaboratorAdded instead
    */
  case class TeamMemberAddedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      teamMemberEmail: LaxEmailAddress,
      teamMemberRole: String
    ) extends ApplicationEvent

  /** DEPRECATED Use CollaboratorRemoved instead
    */
  case class TeamMemberRemovedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      teamMemberEmail: LaxEmailAddress,
      teamMemberRole: String
    ) extends ApplicationEvent

  /** DEPRECATED Use ApiSubscribedV2 instead
    */
  case class ApiSubscribedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: String,
      version: String
    ) extends ApplicationEvent

  /** DEPRECATED Use ApiUnsubscribedV2 instead
    */
  case class ApiUnsubscribedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: String,
      version: String
    ) extends ApplicationEvent

  // scalastyle:on number.of.types
}
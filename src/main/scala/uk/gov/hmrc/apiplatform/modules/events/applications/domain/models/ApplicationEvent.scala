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

import uk.gov.hmrc.apiplatform.modules.common.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.submissions.domain.models.{SubmissionId, _}

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvent.{MetaData, ifDefined}

sealed trait ApplicationEvent {
  def id: EventId
  def applicationId: ApplicationId
  def eventDateTime: Instant
  def actor: Actor

  def asMetaData(): MetaData
}

object ApplicationEvent {

  def ifDefined[T](fn: T => String)(value: Option[T]): List[String] = value.map(fn).toList

  implicit val orderEvents: Ordering[ApplicationEvent] = new Ordering[ApplicationEvent]() {

    override def compare(x: ApplicationEvent, y: ApplicationEvent): Int =
      y.eventDateTime.compareTo(x.eventDateTime)
  }

// ------------------

  type MetaData = (String, List[String])

  type AuditData = Map[String, String]

  trait AsAuditData[T] {
    def asAuditData(t: T): Option[AuditData]
  }

  trait LowPriorityAsAuditData {

    implicit object LowPriorityEventAuditData extends AsAuditData[ApplicationEvent] {

      def asAuditData(t: ApplicationEvent): Option[AuditData] = {
        None
      }
    }
  }
  object AuditData extends LowPriorityAsAuditData

}

object ApplicationEvents {

  case class GrantLengthChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      oldGrantLengthInDays: Int,
      newGrantLengthInDays: Int
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Grant Length Changed", List(s"old grant length $oldGrantLengthInDays days", s"new grant length $newGrantLengthInDays days"))
  }

  case class RedirectUriAdded(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      newRedirectUri: RedirectUri
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Redirect URI Added", List(s"New Redirect Uri: ${newRedirectUri.uri}"))
  }

  case class RedirectUriChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldRedirectUri: RedirectUri,
      newRedirectUri: RedirectUri
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Redirect URI changed", List(s"Original: ${oldRedirectUri.uri}", s"Replaced with: ${newRedirectUri.uri}"))
  }

  case class RedirectUriDeleted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      deletedRedirectUri: RedirectUri
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Redirect URI deleted", List(s"Removed Uri: ${deletedRedirectUri.uri}"))
  }

  case class PpnsCallBackUriUpdatedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      boxId: String,
      boxName: String,
      oldCallbackUrl: String,
      newCallbackUrl: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Ppns CallBackUri Updated", List(s"boxName: ${boxName}", s"oldCallBackUrl: ${oldCallbackUrl}", s"newCallBackUrl: ${newCallbackUrl}"))
  }

  case class RedirectUrisUpdatedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldRedirectUris: List[RedirectUri],
      newRedirectUris: List[RedirectUri]
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Redirect Uris Updated", List(s"""oldRedirectUris: ${oldRedirectUris.mkString(",")}""", s"""newRedirectUris: ${newRedirectUris.mkString(",")}"""))
  }

  case class ProductionAppNameChangedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldAppName: String,
      newAppName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Production Application Name Changed", List(s"From: $oldAppName", s"To: $newAppName", s"Requested by ${requestingAdminEmail.text}"))
  }

  case class ProductionAppPrivacyPolicyLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldLocation: PrivacyPolicyLocation,
      newLocation: PrivacyPolicyLocation
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Privacy Policy Changed", List(s"From: ${oldLocation.describe()}", s"To: ${newLocation.describe()}"))
  }

  case class ProductionLegacyAppPrivacyPolicyLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldUrl: String,
      newUrl: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Privacy Policy Changed", List(s"From: $oldUrl", s"To: $newUrl"))
  }

  case class ProductionAppTermsConditionsLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldLocation: TermsAndConditionsLocation,
      newLocation: TermsAndConditionsLocation
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("T&Cs Changed", List(s"From: ${oldLocation.describe()}", s"To: ${newLocation.describe()}"))
  }

  case class ProductionLegacyAppTermsConditionsLocationChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldUrl: String,
      newUrl: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("T&Cs Changed", List(s"From: $oldUrl", s"To: $newUrl"))
  }

  case class ClientSecretAddedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      clientSecretId: String,
      clientSecretName: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Client Secret Added", List(s"Name: $clientSecretName"))
  }

  case class ClientSecretRemovedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      clientSecretId: String,
      clientSecretName: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Client Secret Removed", List(s"Name: $clientSecretName"))
  }

  case class CollaboratorAddedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      collaborator: Collaborator
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Collaborator Added", List(s"${collaborator.emailAddress.text} was added as a ${Collaborator.describeRole(collaborator)}"))
  }

  case class CollaboratorRemovedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      collaborator: Collaborator
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Collaborator Removed", List(s"${collaborator.emailAddress.text} was removed as a ${Collaborator.describeRole(collaborator)}"))
  }

  case class ApiSubscribedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: ApiContext,
      version: ApiVersionNbr
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Api Subscribed", List(s"API ${context.value} v${version.value}"))
  }

  case class ApiUnsubscribedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: ApiContext,
      version: ApiVersionNbr
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Api Unsubscribed", List(s"API ${context.value} v${version.value}"))
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual changed",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"From Responsible Individual: ${previousResponsibleIndividualName} @ ${previousResponsibleIndividualEmail.text}",
        s"To Reponsible Individual: ${newResponsibleIndividualName} @ ${newResponsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual changed to self",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"From Responsible Individual: ${previousResponsibleIndividualName} @ ${previousResponsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual Set",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"To Reponsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

  case class ApplicationStateChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      oldAppState: String,
      newAppState: String,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Application State changed",
      List(
        s"From ${oldAppState} to ${newAppState}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual verification started",
      List(
        s"Application Name: ${applicationName}",
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"For Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

  case class ResponsibleIndividualVerificationRequired(
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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual verification required",
      List(
        s"Application Name: ${applicationName}",
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"For Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual declined",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"Rejected by Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual declined update",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"Rejected by Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual did not verify",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"Not verified by Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Responsible Individual Declined Or Did Not Verify",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
        s"From Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Application Approval Request Declined",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Declined by ${decliningUserName} @ ${decliningUserEmail.text}",
        s"Reason(s) given as $reasons",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class ApplicationApprovalRequestGranted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      submissionId: SubmissionId,
      submissionIndex: Int,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Application Approval Request Granted",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"granted by ${actor.user}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class ApplicationApprovalRequestGrantedWithWarnings(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      submissionId: SubmissionId,
      submissionIndex: Int,
      warnings: String,
      escalatedTo: Option[String],
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Application Approval Request Granted with warnings",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"granted by ${actor.user}",
        s"warnings: ${warnings}" + escalatedTo.fold("")(to => s" escalatedTo: $to"),
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class TermsOfUseApprovalGranted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      submissionId: SubmissionId,
      submissionIndex: Int,
      reasons: String,
      escalatedTo: Option[String],
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Terms of Use Approval Granted",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"granted by ${actor.user}",
        s"reasons: ${reasons}" + escalatedTo.fold("")(to => s" escalatedTo: $to"),
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class ApplicationApprovalRequestSubmitted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      submissionId: SubmissionId,
      submissionIndex: Int,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Application Approval Request Submitted",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Actioned by ${actor.email}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class TermsOfUseApprovalSubmitted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      submissionId: SubmissionId,
      submissionIndex: Int,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Terms of Use Approval Request Submitted",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Actioned by ${actor.email}",
        s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class RequesterEmailVerificationResent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      submissionId: SubmissionId,
      submissionIndex: Int,
      requestingAdminName: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Requester Verification Email Resent",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Actioned by ${actor.user}",
        s"Requester ${requestingAdminName} @ ${requestingAdminEmail.text}"
      )
    )
  }

  case class TermsOfUseInvitationSent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      submissionId: SubmissionId,
      submissionIndex: Int
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "Terms of Use Invitation Sent",
      List(
        s"Submission Id: ${submissionId.value} - ${submissionIndex}",
        s"Actioned by ${actor.user}"
      )
    )
  }

  case class TermsOfUsePassed(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      submissionId: SubmissionId,
      submissionIndex: Int
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Terms of Use Passed", List(s"Submission Id: ${submissionId.value} - ${submissionIndex}"))
  }

  case class ApplicationDeleted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientId: ClientId,
      wso2ApplicationName: String,
      reasons: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application deleted", List(s"Reason(s) given as ${reasons}"))
  }

  case class ApplicationDeletedByGatekeeper(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      clientId: ClientId,
      wso2ApplicationName: String,
      reasons: String,
      requestingAdminEmail: LaxEmailAddress
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Deleted", List(s"Reason(s) given as $reasons", s"Requested by ${requestingAdminEmail.text}"))
  }

  case class ProductionCredentialsApplicationDeleted(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientId: ClientId,
      wso2ApplicationName: String,
      reasons: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application credentials deleted", List(s"Reason(s) given as $reasons"))
  }

  case class AllowApplicationAutoDelete(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      reasons: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application auto delete allowed", List(s"Reason(s) given as: ${reasons}"))
  }

  case class BlockApplicationAutoDelete(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      reasons: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application auto delete blocked", List(s"Reason(s) given as: ${reasons}"))
  }

  case class RateLimitChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      oldRateLimit: RateLimitTier,
      newRateLimit: RateLimitTier
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Rate Limit Changed", List(s"From: $oldRateLimit", s"To: $newRateLimit"))
  }

  case class IpAllowlistCidrBlockChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      required: Boolean,
      oldIpAllowlist: List[CidrBlock],
      newIpAllowlist: List[CidrBlock]
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = (
      "IP Allowlist Changed",
      List(
        s"Required: $required",
        s"From: ${if (oldIpAllowlist.isEmpty) "None" else oldIpAllowlist.mkString(",")}",
        s"To: ${if (newIpAllowlist.isEmpty) "None" else newIpAllowlist.mkString(",")}"
      )
    )
  }

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
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Redirect URI updated", List(s"Original: $oldRedirectUris", s"Replaced with: $newRedirectUris"))
  }

  /** DEPRECATED Use ClientSecretAdded instead
    */
  case class ClientSecretAddedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientSecretId: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Client Secret Added", List(s"Id: $clientSecretId"))
  }

  /** DEPRECATED Use ClientSecretRemoved instead
    */
  case class ClientSecretRemovedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      clientSecretId: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Client Secret Removed", List(s"Id: $clientSecretId"))
  }

  /** DEPRECATED Use CollaboratorAdded instead
    */
  case class TeamMemberAddedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      teamMemberEmail: LaxEmailAddress,
      teamMemberRole: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Collaborator Added", List(s"${teamMemberEmail.text} was added as a $teamMemberRole"))
  }

  /** DEPRECATED Use CollaboratorRemoved instead
    */
  case class TeamMemberRemovedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      teamMemberEmail: LaxEmailAddress,
      teamMemberRole: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Collaborator Removed", List(s"${teamMemberEmail.text} was removed"))
  }

  /** DEPRECATED Use ApiSubscribedV2 instead
    */
  case class ApiSubscribedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: String,
      version: String
    ) extends ApplicationEvent {
    def asMetaData(): MetaData = ("Api Subscribed", List(s"API ${context} v${version}"))
  }

  /** DEPRECATED Use ApiUnsubscribedV2 instead
    */
  case class ApiUnsubscribedEvent(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: String,
      version: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Api Unsubscribed", List(s"API ${context} v${version}"))
  }

  case class SandboxApplicationNameChanged(id: EventId, applicationId: ApplicationId, eventDateTime: Instant, actor: Actors.AppCollaborator, oldName: String, newName: String)
      extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application Name Changed", List(s"From: $oldName", s"To: $newName"))
  }

  case class SandboxApplicationDescriptionChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      oldDescription: Option[String],
      description: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application Description Changed", ifDefined[String](x => s"From: $x")(oldDescription) ++ List(s"To: $description"))
  }

  case class SandboxApplicationPrivacyPolicyUrlChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      oldPrivacyPolicyUrl: Option[String],
      privacyPolicyUrl: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application Privacy Policy Url Changed", ifDefined[String](x => s"From: $x")(oldPrivacyPolicyUrl) ++ List(s"To: $privacyPolicyUrl"))
  }

  case class SandboxApplicationTermsAndConditionsUrlChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      oldTermsAndConditionsUrl: Option[String],
      termsAndConditionsUrl: String
    ) extends ApplicationEvent {

    def asMetaData(): MetaData =
      ("Application Term and Conditions Url Changed", ifDefined[String](x => s"From: $x")(oldTermsAndConditionsUrl) ++ List(s"To: $termsAndConditionsUrl"))
  }

  case class SandboxApplicationDescriptionCleared(id: EventId, applicationId: ApplicationId, eventDateTime: Instant, actor: Actors.AppCollaborator, oldDescription: String)
      extends ApplicationEvent {
    def asMetaData(): MetaData = ("Application Description Cleared", List(s"From: $oldDescription"))
  }

  case class SandboxApplicationPrivacyPolicyUrlRemoved(id: EventId, applicationId: ApplicationId, eventDateTime: Instant, actor: Actors.AppCollaborator, oldPrivacyPolicyUrl: String)
      extends ApplicationEvent {

    def asMetaData(): MetaData = ("Application Privacy Policy Url Removed", List(s"From: $oldPrivacyPolicyUrl"))
  }

  case class SandboxApplicationTermsAndConditionsUrlRemoved(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.AppCollaborator,
      oldTermsAndConditionsUrl: String
    ) extends ApplicationEvent {
    def asMetaData(): MetaData = ("Application Term and Conditions Url Removed", List(s"From: ${oldTermsAndConditionsUrl}"))
  }
}
// scalastyle:on number.of.types

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
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.{RedirectUri, _}
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

  import ApplicationEvents._

  def asMetaData(evt: ApplicationEvent): (String, List[String]) = {
    val tuple = evt match {
      case ApiSubscribedEvent(_, _, _, _, context, version)                                                         => ("Api Subscribed", List(s"API ${context} v${version}"))
      case ApiSubscribedV2(_, _, _, _, context, version)                                                            => ("Api Subscribed", List(s"API ${context.value} v${version.value}"))
      case ApiUnsubscribedEvent(_, _, _, _, context, version)                                                       => ("Api Unsubscribed", List(s"API ${context} v${version}"))
      case ApiUnsubscribedV2(_, _, _, _, context, version)                                                          => ("Api Unsubscribed", List(s"API ${context.value} v${version.value}"))
      case CollaboratorAddedV2(_, _, _, _, collaborator)                                                            =>
        ("Collaborator Added", List(s"${collaborator.emailAddress.text} was added as a ${Collaborator.describeRole(collaborator)}"))
      case CollaboratorRemovedV2(_, _, _, _, collaborator)                                                          =>
        ("Collaborator Removed", List(s"${collaborator.emailAddress.text} was removed as a ${Collaborator.describeRole(collaborator)}"))
      case TeamMemberAddedEvent(_, _, _, _, teamMemberEmail, teamMemberRole)                                        => ("Collaborator Added", List(s"${teamMemberEmail.text} was added as a $teamMemberRole"))
      case TeamMemberRemovedEvent(_, _, _, _, teamMemberEmail, teamMemberRole)                                      => ("Collaborator Removed", List(s"${teamMemberEmail.text} was removed"))
      case ClientSecretAddedV2(_, _, _, _, clientSecretId, clientSecretName)                                        => ("Client Secret Added", List(s"Name: $clientSecretName"))
      case ClientSecretRemovedV2(_, _, _, _, clientSecretId, clientSecretName)                                      => ("Client Secret Removed", List(s"Name: $clientSecretName"))
      case ClientSecretAddedEvent(_, _, _, _, clientSecretId)                                                       => ("Client Secret Added", List(s"Id: $clientSecretId"))
      case ClientSecretRemovedEvent(_, _, _, _, clientSecretId)                                                     => ("Client Secret Removed", List(s"Id: $clientSecretId"))
      case GrantLengthChanged(_, _, _, _, oldGrantLengthInDays, newGrantLengthInDays)                               =>
        ("Grant Length Changed", List(s"old grant length $oldGrantLengthInDays days", s"new grant length $newGrantLengthInDays days"))
      case PpnsCallBackUriUpdatedEvent(_, _, _, _, boxId, boxName, oldCallbackUrl, newCallbackUrl)                  =>
        ("Ppns CallBackUri Updated", List(s"boxName: ${boxName}", s"oldCallBackUrl: ${oldCallbackUrl}", s"newCallBackUrl: ${newCallbackUrl}"))
      case RedirectUrisUpdatedV2(_, _, _, _, oldRedirectUris: List[String], newRedirectUris: List[String])          =>
        ("Redirect Uris Updated", List(s"""oldRedirectUris: ${oldRedirectUris.mkString(",")}""", s"""newRedirectUris: ${newRedirectUris.mkString(",")}"""))
      case RedirectUriAdded(_, _, _, _, newRedirectUri)                                                             => ("Redirect URI Added", List(s"New Redirect Uri: ${newRedirectUri.uri}"))
      case RedirectUriDeleted(_, _, _, _, deletedRedirectUri)                                                       => ("Redirect URI deleted", List(s"Removed Uri: ${deletedRedirectUri.uri}"))
      case RedirectUriChanged(_, _, _, _, oldRedirectUri, newRedirectUri)                                           =>
        ("Redirect URI changed", List(s"Original: ${oldRedirectUri.uri}", s"Replaced with: ${newRedirectUri.uri}"))
      case RedirectUrisUpdatedEvent(_, _, _, _, oldRedirectUris, newRedirectUris)                                   =>
        ("Redirect URI updated", List(s"Original: $oldRedirectUris", s"Replaced with: $newRedirectUris"))
      case ApplicationDeletedByGatekeeper(_, _, _, _, _, _, reasons, requestingAdminEmail)                          =>
        ("Deleted", List(s"Reason(s) given as $reasons", s"Requested by ${requestingAdminEmail.text}"))
      case AllowApplicationAutoDelete(_, _, _, _, reasons)                                                          =>
        ("Application auto delete allowed", List(s"Reason(s) given as: ${reasons}"))
      case BlockApplicationAutoDelete(_, _, _, _, reasons)                                                          =>
        ("Application auto delete blocked", List(s"Reason(s) given as: ${reasons}"))
      // $COVERAGE-OFF$
      case ResponsibleIndividualChanged(
            _,
            _,
            _,
            _,
            previousResponsibleIndividualName,
            previousResponsibleIndividualEmail,
            newResponsibleIndividualName,
            newResponsibleIndividualEmail,
            submissionId,
            submissionIndex,
            _,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual changed",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"From Responsible Individual: ${previousResponsibleIndividualName} @ ${previousResponsibleIndividualEmail.text}",
            s"To Reponsible Individual: ${newResponsibleIndividualName} @ ${newResponsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualChangedToSelf(
            _,
            _,
            _,
            _,
            previousResponsibleIndividualName,
            previousResponsibleIndividualEmail,
            submissionId,
            submissionIndex,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual changed to self",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"From Responsible Individual: ${previousResponsibleIndividualName} @ ${previousResponsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualDeclined(
            _,
            _,
            _,
            _,
            responsibleIndividualName,
            responsibleIndividualEmail,
            submissionId,
            submissionIndex,
            _,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual declined",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"Rejected by Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualDeclinedUpdate(
            _,
            _,
            _,
            _,
            responsibleIndividualName,
            responsibleIndividualEmail,
            submissionId,
            submissionIndex,
            _,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual declined update",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"Rejected by Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualDidNotVerify(
            _,
            _,
            _,
            _,
            responsibleIndividualName,
            responsibleIndividualEmail,
            submissionId,
            submissionIndex,
            _,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual did not verify",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"Not verified by Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualDeclinedOrDidNotVerify(
            _,
            _,
            _,
            _,
            responsibleIndividualName,
            responsibleIndividualEmail,
            submissionId,
            submissionIndex,
            code,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual Declined Or Did Not Verify",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"From Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualSet(
            _,
            _,
            _,
            _,
            responsibleIndividualName,
            responsibleIndividualEmail,
            submissionId,
            submissionIndex,
            code,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Responsible Individual Set",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"To Reponsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
          )
        )
      case ResponsibleIndividualVerificationStarted(
            _,
            _,
            _,
            _,
            applicationName,
            requestingAdminName,
            requestingAdminEmail,
            responsibleIndividualName,
            responsibleIndividualEmail,
            submissionId,
            submissionIndex,
            verificationId
          ) =>
        (
          "Responsible Individual verification started",
          List(
            s"Application Name: ${applicationName}",
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}",
            s"For Responsible Individual: ${responsibleIndividualName} @ ${responsibleIndividualEmail.text}"
          )
        )
      case ApplicationStateChanged(_, _, _, _, oldAppState, newAppState, requestingAdminName, requestingAdminEmail) =>
        (
          "State changed",
          List(
            s"From ${oldAppState} to ${newAppState}",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
          )
        )
      case ApplicationApprovalRequestDeclined(
            _,
            _,
            _,
            _,
            decliningUserName,
            decliningUserEmail,
            submissionId,
            submissionIndex,
            reasons,
            requestingAdminName,
            requestingAdminEmail
          ) =>
        (
          "Approval request declined",
          List(
            s"Submission Id: ${submissionId.value} - ${submissionIndex}",
            s"Declined by ${decliningUserName} @ ${decliningUserEmail.text}",
            s"Reason(s) given as $reasons",
            s"Requested by ${requestingAdminName} @ ${requestingAdminEmail.text}"
          )
        )
      case TermsOfUsePassed(_, _, _, _, submissionId, submissionIndex)                                              => ("Terms of Use passed", List(s"Submission Id: ${submissionId.value} - ${submissionIndex}"))
      case ProductionCredentialsApplicationDeleted(_, _, _, _, _, _, reasons)                                       => ("Application credentials deleted", List(s"Reason(s) given as $reasons"))
      case ApplicationDeleted(_, _, _, _, _, _, reasons)                                                            => ("Application deleted", List(s"Reason(s) given as ${reasons}"))
      case ProductionAppNameChangedEvent(_, _, _, _, oldName, newName, requestingAdminEmail)                        =>
        ("Name Changed", List(s"From: $oldName", s"To: $newName", s"Requested by ${requestingAdminEmail.text}"))
      case ProductionAppPrivacyPolicyLocationChanged(_, _, _, _, oldLocation, newLocation)                          =>
        ("Privacy Policy Changed", List(s"From: ${oldLocation.describe()}", s"To: ${newLocation.describe()}"))
      case ProductionLegacyAppPrivacyPolicyLocationChanged(_, _, _, _, oldUrl, newUrl)                              => ("Privacy Policy Changed", List(s"From: $oldUrl", s"To: $newUrl"))
      case ProductionAppTermsConditionsLocationChanged(_, _, _, _, oldLocation, newLocation)                        =>
        ("T&Cs Changed", List(s"From: ${oldLocation.describe()}", s"To: ${newLocation.describe()}"))
      case ProductionLegacyAppTermsConditionsLocationChanged(_, _, _, _, oldUrl, newUrl)                            => ("T&Cs Changed", List(s"From: $oldUrl", s"To: $newUrl"))
      case RateLimitChanged(_, _, _, _, oldRateLimit, newRateLimit)                                                 => ("Rate Limit Changed", List(s"From: $oldRateLimit", s"To: $newRateLimit"))
      // case _ => ("Unspecified", List("Not details"))    // TO REMOVE ONCE WE'VE PROVED THIS OUT
      // $COVERAGE-ON$
    }

    tuple
  }
}

object ApplicationEvents {

  case class GrantLengthChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      oldGrantLengthInDays: Int,
      newGrantLengthInDays: Int
    ) extends ApplicationEvent

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
      collaborator: Collaborator
    ) extends ApplicationEvent

  case class CollaboratorRemovedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      collaborator: Collaborator
    ) extends ApplicationEvent

  case class ApiSubscribedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: ApiContext,
      version: ApiVersionNbr
    ) extends ApplicationEvent

  case class ApiUnsubscribedV2(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actor,
      context: ApiContext,
      version: ApiVersionNbr
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

  case class AllowApplicationAutoDelete(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      reasons: String
    ) extends ApplicationEvent

  case class BlockApplicationAutoDelete(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      reasons: String
    ) extends ApplicationEvent

  case class RateLimitChanged(
      id: EventId,
      applicationId: ApplicationId,
      eventDateTime: Instant,
      actor: Actors.GatekeeperUser,
      oldRateLimit: RateLimitTier,
      newRateLimit: RateLimitTier
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

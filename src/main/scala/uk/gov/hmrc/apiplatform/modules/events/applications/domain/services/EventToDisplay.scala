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

import uk.gov.hmrc.apiplatform.modules.applications.domain.models.Collaborator
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

object EventToDisplay {

  def display(evt: ApplicationEvent): DisplayEvent = {
    val (eventType, metaData) = evt match {
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
      case ApplicationDeletedByGatekeeper(_, _, _, _, _, _, reasons, requestingAdminEmail)                          =>
        ("Deleted", List(s"Reason(s) given as $reasons", s"Requested by ${requestingAdminEmail.text}"))
      case ProductionAppNameChangedEvent(_, _, _, _, oldName, newName, requestingAdminEmail)                        =>
        ("Name Changed", List(s"From: $oldName", s"To: $newName", s"Requested by ${requestingAdminEmail.text}"))
      case ProductionAppPrivacyPolicyLocationChanged(_, _, _, _, oldLocation, newLocation)                          =>
        ("Privacy Policy Changed", List(s"From: ${oldLocation.describe()}", s"To: ${newLocation.describe()}"))
      case ProductionLegacyAppPrivacyPolicyLocationChanged(_, _, _, _, oldUrl, newUrl)                              => ("Privacy Policy Changed", List(s"From: $oldUrl", s"To: $newUrl"))
      case ProductionAppTermsConditionsLocationChanged(_, _, _, _, oldLocation, newLocation)                        =>
        ("T&Cs Changed", List(s"From: ${oldLocation.describe()}", s"To: ${newLocation.describe()}"))
      case ProductionLegacyAppTermsConditionsLocationChanged(_, _, _, _, oldUrl, newUrl)                            => ("T&Cs Changed", List(s"From: $oldUrl", s"To: $newUrl"))
      // case _ => ("Unspecified", List("Not details"))    // TO REMOVE ONCE WE'VE PROVED THIS OUT
    }
    DisplayEvent(evt.id, evt.applicationId, evt.eventDateTime, evt.actor, EventTags.tag(evt).description, eventType, metaData)
  }
}

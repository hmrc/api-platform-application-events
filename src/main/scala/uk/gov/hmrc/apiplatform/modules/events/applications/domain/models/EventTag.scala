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

sealed trait EventTag

object EventTags {
  case object SUBSCRIPTION extends EventTag
  case object COLLABORATOR extends EventTag
  case object CLIENT_SECRET extends EventTag
  case object PPNS_CALLBACK extends EventTag
  case object REDIRECT_URIS extends EventTag
  case object APP_NAME extends EventTag
  case object TERMS_OF_USE extends EventTag
  case object POLICY_LOCATION extends EventTag

  def tag(evt: AbstractApplicationEvent): EventTag = evt match {
    case _: ApiSubscribedEvent | _: ApiSubscribed | _: ApiUnsubscribedEvent | _: ApiUnsubscribed => SUBSCRIPTION
    case _: CollaboratorAdded | _: CollaboratorRemoved | _: TeamMemberAddedEvent | _: TeamMemberRemovedEvent     =>
      COLLABORATOR
    case _: ClientSecretAdded | _: ClientSecretRemoved | _: ClientSecretAddedEvent | _: ClientSecretRemovedEvent =>
      CLIENT_SECRET
    case _: PpnsCallBackUriUpdatedEvent                       => PPNS_CALLBACK
    case _: RedirectUrisUpdated | _: RedirectUrisUpdatedEvent => REDIRECT_URIS
    case _: ResponsibleIndividualChanged |
        _: ResponsibleIndividualChangedToSelf |
        _: ResponsibleIndividualDeclined |
        _: ResponsibleIndividualDeclinedUpdate |
        _: ResponsibleIndividualDidNotVerify |
        _: ResponsibleIndividualSet |
        _: ResponsibleIndividualVerificationStarted |
        _: ApplicationStateChanged |
        _: ApplicationApprovalRequestDeclined => TERMS_OF_USE
    case _: ProductionAppNameChangedEvent                     => APP_NAME
    case _: ProductionAppPrivacyPolicyLocationChanged |
        _: ProductionAppTermsConditionsLocationChanged |
        _: ProductionLegacyAppPrivacyPolicyLocationChanged |
        _: ProductionLegacyAppTermsConditionsLocationChanged => POLICY_LOCATION
  }

  def fromString(tag: String): Option[EventTag] = tag match {
    case "COLLABORATOR"    => Some(COLLABORATOR)
    case "CLIENT_SECRET"   => Some(CLIENT_SECRET)
    case "PPNS_CALLBACK"   => Some(PPNS_CALLBACK)
    case "REDIRECT_URIS"   => Some(REDIRECT_URIS)
    case "TERMS_OF_USE"    => Some(TERMS_OF_USE)
    case "APP_NAME"        => Some(APP_NAME)
    case "POLICY_LOCATION" => Some(POLICY_LOCATION)
    case _                 => None
  }

  def describe(tag: EventTag): String = tag match {
    case SUBSCRIPTION    => "Subscription"
    case COLLABORATOR    => "Collaborator"
    case CLIENT_SECRET   => "Client Secret"
    case PPNS_CALLBACK   => "PPNS Callback"
    case REDIRECT_URIS   => "Redirect URI"
    case TERMS_OF_USE    => "Terms of Use"
    case APP_NAME        => "Application Name"
    case POLICY_LOCATION => "Policy Locations"
  }
}

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

sealed abstract class EventTag(val description: String)

object EventTags {
  case object SUBSCRIPTION         extends EventTag("API subscription")
  case object APP_NAME             extends EventTag("Application name")
  case object PPNS_CALLBACK        extends EventTag("Callback URL")
  case object CLIENT_SECRET        extends EventTag("Client secret")
  case object PRIVACY_POLICY       extends EventTag("Privacy Policy URL")
  case object TEAM_MEMBER          extends EventTag("Team member")
  case object TERMS_AND_CONDITIONS extends EventTag("Terms and Conditions URL")
  case object REDIRECT_URIS        extends EventTag("Redirect URL")
  case object TERMS_OF_USE         extends EventTag("Terms of Use")
  case object APP_LIFECYCLE        extends EventTag("Application lifecycle")

  val ALL = Set(SUBSCRIPTION, APP_NAME, PPNS_CALLBACK, CLIENT_SECRET, PRIVACY_POLICY, TERMS_AND_CONDITIONS, TEAM_MEMBER, REDIRECT_URIS, TERMS_OF_USE, APP_LIFECYCLE)

  /*
   * Used for display purposes
   */
  private val lookupDescription: Map[String, EventTag] = ALL.map(et => et.description -> et).toMap

  def fromDescription(text: String): Option[EventTag] =
    lookupDescription.get(text)

  /*
   * Used for Json only
   */
  private val lookupName: Map[String, EventTag] = ALL.map(et => et.toString() -> et).toMap

  def fromString(text:String): Option[EventTag] =
    lookupName.get(text)


  /*
   * Resolve event to an eventTag
   */
  def tag(evt: AbstractApplicationEvent): EventTag = evt match {
    case _: ApiSubscribedEvent |
        _: ApiSubscribed |
        _: ApiUnsubscribedEvent |
        _: ApiUnsubscribed => SUBSCRIPTION
    case _: CollaboratorAdded |
        _: CollaboratorRemoved |
        _: TeamMemberAddedEvent |
        _: TeamMemberRemovedEvent => TEAM_MEMBER
    case _: ClientSecretAdded |
        _: ClientSecretRemoved |
        _: ClientSecretAddedEvent |
        _: ClientSecretRemovedEvent => CLIENT_SECRET
    case _: PpnsCallBackUriUpdatedEvent   => PPNS_CALLBACK
    case _: RedirectUrisUpdated |
        _: RedirectUrisUpdatedEvent => REDIRECT_URIS
    case _: ResponsibleIndividualChanged |
        _: ResponsibleIndividualChangedToSelf |
        _: ResponsibleIndividualDeclined |
        _: ResponsibleIndividualDeclinedUpdate |
        _: ResponsibleIndividualDidNotVerify |
        _: ResponsibleIndividualSet |
        _: ResponsibleIndividualVerificationStarted |
        _: ApplicationStateChanged |
        _: ApplicationApprovalRequestDeclined |
        _: ProductionCredentialsApplicationDeleted => TERMS_OF_USE
    case _: ApplicationDeleted |
        _: ApplicationDeletedByGatekeeper => APP_LIFECYCLE
    case _: ProductionAppNameChangedEvent => APP_NAME
    case _: ProductionAppPrivacyPolicyLocationChanged |
        _: ProductionLegacyAppPrivacyPolicyLocationChanged => PRIVACY_POLICY
    case _: ProductionAppTermsConditionsLocationChanged |
        _: ProductionLegacyAppTermsConditionsLocationChanged => TERMS_AND_CONDITIONS
  }
}

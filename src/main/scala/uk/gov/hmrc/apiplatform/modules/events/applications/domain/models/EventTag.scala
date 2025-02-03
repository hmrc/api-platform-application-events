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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._

sealed abstract class EventTag(val description: String)

object EventTags {
  case object SUBSCRIPTION         extends EventTag("API subscription")
  case object APP_NAME             extends EventTag("Application name")
  case object PPNS_CALLBACK        extends EventTag("Callback URL")
  case object CLIENT_SECRET        extends EventTag("Client secret")
  case object GRANT_LENGTH         extends EventTag("Grant Length")
  case object PRIVACY_POLICY       extends EventTag("Privacy Policy URL")
  case object TEAM_MEMBER          extends EventTag("Team member")
  case object TERMS_AND_CONDITIONS extends EventTag("Terms and Conditions URL")
  case object REDIRECT_URIS        extends EventTag("Redirect URL")
  case object TERMS_OF_USE         extends EventTag("Terms of Use")
  case object APP_LIFECYCLE        extends EventTag("Application lifecycle")
  case object RATE_LIMIT           extends EventTag("Rate Limit")
  case object IP_ALLOWLIST         extends EventTag("IP Allowlist")
  case object SCOPES               extends EventTag("Scopes")

  val ALL = Set(
    SUBSCRIPTION,
    APP_NAME,
    PPNS_CALLBACK,
    CLIENT_SECRET,
    GRANT_LENGTH,
    PRIVACY_POLICY,
    TERMS_AND_CONDITIONS,
    TEAM_MEMBER,
    REDIRECT_URIS,
    TERMS_OF_USE,
    APP_LIFECYCLE,
    RATE_LIMIT,
    IP_ALLOWLIST,
    SCOPES
  )

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

  def fromString(text: String): Option[EventTag] =
    lookupName.get(text)

  /*
   * Resolve event to an eventTag
   */
  // scalastyle:off cyclomatic.complexity
  def tag(evt: ApplicationEvent): EventTag = evt match {
    case _: ApiSubscribedEvent |
        _: ApiSubscribedV2 |
        _: ApiUnsubscribedEvent |
        _: ApiUnsubscribedV2 => SUBSCRIPTION
    case _: CollaboratorAddedV2 |
        _: CollaboratorRemovedV2 |
        _: TeamMemberAddedEvent |
        _: TeamMemberRemovedEvent => TEAM_MEMBER
    case _: ClientSecretAddedV2 |
        _: ClientSecretRemovedV2 |
        _: ClientSecretAddedEvent |
        _: ClientSecretRemovedEvent => CLIENT_SECRET
    case _: GrantLengthChanged          => GRANT_LENGTH
    case _: PpnsCallBackUriUpdatedEvent => PPNS_CALLBACK
    case _: LoginRedirectUrisUpdatedV2 |
        _: LoginRedirectUriAdded |
        _: LoginRedirectUriDeleted |
        _: LoginRedirectUriChanged |
        _: LoginRedirectUrisUpdatedEvent => REDIRECT_URIS
    case _: PostLogoutRedirectUrisUpdated |
        _: PostLogoutRedirectUriAdded |
        _: PostLogoutRedirectUriDeleted |
        _: PostLogoutRedirectUriChanged => REDIRECT_URIS
    case _: ResponsibleIndividualChanged |
        _: ResponsibleIndividualChangedToSelf |
        _: ResponsibleIndividualDeclined |
        _: ResponsibleIndividualDeclinedUpdate |
        _: ResponsibleIndividualDidNotVerify |
        _: ResponsibleIndividualDeclinedOrDidNotVerify |
        _: ResponsibleIndividualSet |
        _: ResponsibleIndividualVerificationStarted |
        _: ResponsibleIndividualVerificationRequired |
        _: ApplicationStateChanged |
        _: ApplicationApprovalRequestDeclined |
        _: ApplicationApprovalRequestGranted |
        _: ApplicationApprovalRequestGrantedWithWarnings |
        _: ApplicationSellResellOrDistributeChanged |
        _: ApplicationApprovalRequestSubmitted |
        _: TermsOfUseApprovalSubmitted |
        _: RequesterEmailVerificationResent |
        _: TermsOfUseApprovalGranted |
        _: TermsOfUseInvitationSent |
        _: TermsOfUsePassed |
        _: ProductionCredentialsApplicationDeleted => TERMS_OF_USE
    case _: ApplicationDeleted |
        _: ApplicationDeletedByGatekeeper |
        _: AllowApplicationAutoDelete |
        _: BlockApplicationAutoDelete |
        _: AllowApplicationDelete |
        _: RestrictApplicationDelete |
        _: ApplicationBlocked |
        _: ApplicationUnblocked => APP_LIFECYCLE
    case _: ProductionAppPrivacyPolicyLocationChanged |
        _: SandboxApplicationPrivacyPolicyUrlChanged |
        _: SandboxApplicationPrivacyPolicyUrlRemoved |
        _: ProductionLegacyAppPrivacyPolicyLocationChanged => PRIVACY_POLICY
    case _: ProductionAppTermsConditionsLocationChanged |
        _: SandboxApplicationTermsAndConditionsUrlChanged |
        _: SandboxApplicationTermsAndConditionsUrlRemoved |
        _: ProductionLegacyAppTermsConditionsLocationChanged => TERMS_AND_CONDITIONS
    case _: RateLimitChanged            => RATE_LIMIT
    case _: IpAllowlistCidrBlockChanged => IP_ALLOWLIST
    case _: ProductionAppNameChangedEvent |
        _: SandboxApplicationNameChanged |
        _: SandboxApplicationDescriptionChanged |
        _: SandboxApplicationDescriptionCleared => APP_NAME
    case _: ApplicationScopesChanged |
        _: ApplicationAccessOverridesChanged => SCOPES
  }
  // scalastyle:on cyclomatic.complexity
}

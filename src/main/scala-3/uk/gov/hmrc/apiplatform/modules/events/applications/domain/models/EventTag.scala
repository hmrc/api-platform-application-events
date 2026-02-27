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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.*

enum EventTag(val description: String) {
  case Subscription       extends EventTag("API subscription")
  case AppName            extends EventTag("Application name")
  case PpnsCallback       extends EventTag("Callback URL")
  case ClientSecret       extends EventTag("Client secret")
  case GrantLength        extends EventTag("Grant Length")
  case PrivacyPolicy      extends EventTag("Privacy Policy URL")
  case TeamMember         extends EventTag("Team member")
  case TermsAndConditions extends EventTag("Terms and Conditions URL")
  case RedirectUris       extends EventTag("Redirect URL")
  case TermsOfUse         extends EventTag("Terms of Use")
  case AppLifecycle       extends EventTag("Application lifecycle")
  case RateLimit          extends EventTag("Rate Limit")
  case IpAllowlist        extends EventTag("IP Allowlist")
  case Scopes             extends EventTag("Scopes")
  case Organisation       extends EventTag("Organisation")
}

object EventTag {
  /*
   * Used for display purposes
   */
  def fromDescription(text: String): Option[EventTag] = EventTag.values.find(_.description == text)

  /*
   * Used for Json only
   */
  def apply(text: String): Option[EventTag] = EventTag.values.find(_.toString.equalsIgnoreCase(text))

  import play.api.libs.json.*
  import uk.gov.hmrc.apiplatform.modules.common.domain.services.EnumJsonHelper.*

  given Format[EventTag] = new Format[EventTag] {

    override def writes(o: EventTag): JsValue = Json.obj("description" -> o.description, "type" -> o.asScreamingSnakeCase)

    override def reads(json: JsValue): JsResult[EventTag] = {
      (json match {
        case JsString(t)   => EventTag.apply(fromScreamingSnakeCase(t))
        case JsObject(obj) => obj.get("type").flatMap(_ match {
            case JsString(t) => EventTag.apply(fromScreamingSnakeCase(t))
            case _           => None
          })
        case _             => None
      })
        .fold[JsResult[EventTag]](JsError(s"Cannot find event tag from $json"))(JsSuccess(_))
    }
  }

  /*
   * Resolve event to an eventTag
   */
  def tag(evt: ApplicationEvent): EventTag = evt match {
    case _: ApiSubscribedEvent |
        _: ApiSubscribedV2 |
        _: ApiUnsubscribedEvent |
        _: ApiUnsubscribedV2 => EventTag.Subscription
    case _: CollaboratorAddedV2 |
        _: CollaboratorRemovedV2 |
        _: TeamMemberAddedEvent |
        _: TeamMemberRemovedEvent => EventTag.TeamMember
    case _: ClientSecretAddedV2 |
        _: ClientSecretRemovedV2 |
        _: ClientSecretAddedEvent |
        _: ClientSecretRemovedEvent => EventTag.ClientSecret
    case _: GrantLengthChanged              => EventTag.GrantLength
    case _: PpnsCallBackUriUpdatedEvent     => EventTag.PpnsCallback
    case _: LoginRedirectUrisUpdatedV2 |
        _: LoginRedirectUriAdded |
        _: LoginRedirectUriDeleted |
        _: LoginRedirectUriChanged |
        _: LoginRedirectUrisUpdatedEvent => EventTag.RedirectUris
    case _: PostLogoutRedirectUrisUpdated |
        _: PostLogoutRedirectUriAdded |
        _: PostLogoutRedirectUriDeleted |
        _: PostLogoutRedirectUriChanged => EventTag.RedirectUris
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
        _: ProductionCredentialsApplicationDeleted => EventTag.TermsOfUse
    case _: ApplicationDeleted |
        _: ApplicationDeletedByGatekeeper |
        _: AllowApplicationAutoDelete |
        _: BlockApplicationAutoDelete |
        _: AllowApplicationDelete |
        _: RestrictApplicationDelete |
        _: ApplicationBlocked |
        _: ApplicationUnblocked => EventTag.AppLifecycle
    case _: ProductionAppPrivacyPolicyLocationChanged |
        _: SandboxApplicationPrivacyPolicyUrlChanged |
        _: SandboxApplicationPrivacyPolicyUrlRemoved |
        _: ProductionLegacyAppPrivacyPolicyLocationChanged => EventTag.PrivacyPolicy
    case _: ProductionAppTermsConditionsLocationChanged |
        _: SandboxApplicationTermsAndConditionsUrlChanged |
        _: SandboxApplicationTermsAndConditionsUrlRemoved |
        _: ProductionLegacyAppTermsConditionsLocationChanged => EventTag.TermsAndConditions
    case _: RateLimitChanged                => EventTag.RateLimit
    case _: IpAllowlistCidrBlockChanged     => EventTag.IpAllowlist
    case _: ProductionAppNameChangedEvent |
        _: SandboxApplicationNameChanged |
        _: SandboxApplicationDescriptionChanged |
        _: SandboxApplicationDescriptionCleared => EventTag.AppName
    case _: ApplicationScopesChanged |
        _: ApplicationAccessOverridesChanged => EventTag.Scopes
    case _: ApplicationLinkedToOrganisation => EventTag.Organisation
  }
}

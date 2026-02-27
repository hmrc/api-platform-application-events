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

import java.time.Instant

import play.api.libs.functional.syntax.{toAlternativeOps, toFunctionalBuilderOps}
import play.api.libs.json.*
import uk.gov.hmrc.play.json.Union
import uk.gov.hmrc.apiplatform.modules.common.domain.models.*
import uk.gov.hmrc.apiplatform.modules.common.domain.services.InstantJsonFormatter
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.*

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.*
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.*

/*
 * We are not using companion object based formatters because of the need to provide formatters with different time formatting (.e. for mongo)
 */
abstract class EventsJsonFormatters(instantFormatter: Format[Instant]) {

  import Actor.given

  private given Format[Instant] = instantFormatter

  // scalastyle:off number.of.types
  // scalastyle:off number.of.methods

  given OFormat[CollaboratorAddedV2]   = Json.format[CollaboratorAddedV2]
  given OFormat[CollaboratorRemovedV2] = Json.format[CollaboratorRemovedV2]

  given OFormat[TeamMemberAddedEvent]   = Json.format[TeamMemberAddedEvent]
  given OFormat[TeamMemberRemovedEvent] = Json.format[TeamMemberRemovedEvent]

  given OFormat[ClientSecretAddedV2]   = Json.format[ClientSecretAddedV2]
  given OFormat[ClientSecretRemovedV2] = Json.format[ClientSecretRemovedV2]

  given OFormat[ClientSecretAddedEvent]   = Json.format[ClientSecretAddedEvent]
  given OFormat[ClientSecretRemovedEvent] = Json.format[ClientSecretRemovedEvent]

  given OFormat[ApiSubscribedV2]   = Json.format[ApiSubscribedV2]
  given OFormat[ApiUnsubscribedV2] = Json.format[ApiUnsubscribedV2]

  given OFormat[ApiSubscribedEvent]   = Json.format[ApiSubscribedEvent]
  given OFormat[ApiUnsubscribedEvent] = Json.format[ApiUnsubscribedEvent]

  given OFormat[GrantLengthChanged] = Json.format[GrantLengthChanged]

  given OFormat[ProductionAppNameChangedEvent]             = Json.format[ProductionAppNameChangedEvent]
  given OFormat[ProductionAppPrivacyPolicyLocationChanged] = Json.format[ProductionAppPrivacyPolicyLocationChanged]

  given OFormat[ProductionLegacyAppPrivacyPolicyLocationChanged] =
    Json.format[ProductionLegacyAppPrivacyPolicyLocationChanged]

  given OFormat[ProductionAppTermsConditionsLocationChanged] =
    Json.format[ProductionAppTermsConditionsLocationChanged]

  given OFormat[ProductionLegacyAppTermsConditionsLocationChanged] =
    Json.format[ProductionLegacyAppTermsConditionsLocationChanged]

  given OFormat[ResponsibleIndividualSet] =
    Json.format[ResponsibleIndividualSet]

  given OFormat[ResponsibleIndividualChanged]       = Json.format[ResponsibleIndividualChanged]
  given OFormat[ResponsibleIndividualChangedToSelf] = Json.format[ResponsibleIndividualChangedToSelf]
  given OFormat[ApplicationStateChanged]            = Json.format[ApplicationStateChanged]

  given OFormat[ResponsibleIndividualVerificationStarted]    = Json.format[ResponsibleIndividualVerificationStarted]
  given OFormat[ResponsibleIndividualVerificationRequired]   = Json.format[ResponsibleIndividualVerificationRequired]
  given OFormat[ResponsibleIndividualDeclined]               = Json.format[ResponsibleIndividualDeclined]
  given OFormat[ResponsibleIndividualDeclinedUpdate]         = Json.format[ResponsibleIndividualDeclinedUpdate]
  given OFormat[ResponsibleIndividualDidNotVerify]           = Json.format[ResponsibleIndividualDidNotVerify]
  given OFormat[ResponsibleIndividualDeclinedOrDidNotVerify] = Json.format[ResponsibleIndividualDeclinedOrDidNotVerify]

  given OFormat[ApplicationApprovalRequestDeclined] = Json.format[ApplicationApprovalRequestDeclined]
  given OFormat[ApplicationApprovalRequestGranted]  = Json.format[ApplicationApprovalRequestGranted]

  given OFormat[ApplicationApprovalRequestGrantedWithWarnings] =
    Json.format[ApplicationApprovalRequestGrantedWithWarnings]
  given OFormat[ApplicationSellResellOrDistributeChanged]      = Json.format[ApplicationSellResellOrDistributeChanged]
  given OFormat[ApplicationApprovalRequestSubmitted]           = Json.format[ApplicationApprovalRequestSubmitted]
  given OFormat[TermsOfUseApprovalSubmitted]                   = Json.format[TermsOfUseApprovalSubmitted]
  given OFormat[RequesterEmailVerificationResent]              = Json.format[RequesterEmailVerificationResent]
  given OFormat[TermsOfUseApprovalGranted]                     = Json.format[TermsOfUseApprovalGranted]
  given OFormat[TermsOfUseInvitationSent]                      = Json.format[TermsOfUseInvitationSent]
  given OFormat[TermsOfUsePassed]                              = Json.format[TermsOfUsePassed]

  given OFormat[ApplicationDeleted]                      = Json.format[ApplicationDeleted]
  given OFormat[ApplicationDeletedByGatekeeper]          = Json.format[ApplicationDeletedByGatekeeper]
  given OFormat[ProductionCredentialsApplicationDeleted] = Json.format[ProductionCredentialsApplicationDeleted]

  given OFormat[ApplicationBlocked]                = Json.format[ApplicationBlocked]
  given OFormat[ApplicationUnblocked]              = Json.format[ApplicationUnblocked]
  given OFormat[ApplicationScopesChanged]          = Json.format[ApplicationScopesChanged]
  given OFormat[ApplicationAccessOverridesChanged] = Json.format[ApplicationAccessOverridesChanged]

  given OFormat[LoginRedirectUrisUpdatedEvent] = Json.format[LoginRedirectUrisUpdatedEvent]
  given OFormat[LoginRedirectUrisUpdatedV2]    = Json.format[LoginRedirectUrisUpdatedV2]
  given OFormat[LoginRedirectUriAdded]         = Json.format[LoginRedirectUriAdded]
  given OFormat[LoginRedirectUriChanged]       = Json.format[LoginRedirectUriChanged]
  given OFormat[LoginRedirectUriDeleted]       = Json.format[LoginRedirectUriDeleted]

  given OFormat[PostLogoutRedirectUrisUpdated] = Json.format[PostLogoutRedirectUrisUpdated]
  given OFormat[PostLogoutRedirectUriAdded]    = Json.format[PostLogoutRedirectUriAdded]
  given OFormat[PostLogoutRedirectUriChanged]  = Json.format[PostLogoutRedirectUriChanged]
  given OFormat[PostLogoutRedirectUriDeleted]  = Json.format[PostLogoutRedirectUriDeleted]

  given OFormat[PpnsCallBackUriUpdatedEvent] = Json.format[PpnsCallBackUriUpdatedEvent]

  given OFormat[RateLimitChanged] = Json.format[RateLimitChanged]

  given OFormat[IpAllowlistCidrBlockChanged] = Json.format[IpAllowlistCidrBlockChanged]

  private given allowApplicationAutoDeleteReads: Reads[AllowApplicationAutoDelete] = (
    (JsPath \ "id").read[EventId] and
      (JsPath \ "applicationId").read[ApplicationId] and
      (JsPath \ "eventDateTime").read[Instant] and
      (JsPath \ "actor").read[Actors.GatekeeperUser] and
      ((JsPath \ "reasons").read[String] or Reads.pure("No reason given"))
  )(AllowApplicationAutoDelete.apply _)

  given allowApplicationAutoDeleteFormats: OFormat[AllowApplicationAutoDelete] = {
    OFormat(allowApplicationAutoDeleteReads, Json.writes[AllowApplicationAutoDelete])
  }

  private given blockApplicationAutoDeleteReads: Reads[BlockApplicationAutoDelete] = (
    (JsPath \ "id").read[EventId] and
      (JsPath \ "applicationId").read[ApplicationId] and
      (JsPath \ "eventDateTime").read[Instant] and
      (JsPath \ "actor").read[Actors.GatekeeperUser] and
      ((JsPath \ "reasons").read[String] or Reads.pure("No reason given"))
  )(BlockApplicationAutoDelete.apply _)

  given OFormat[BlockApplicationAutoDelete] = {
    OFormat(blockApplicationAutoDeleteReads, Json.writes[BlockApplicationAutoDelete])
  }

  private given allowApplicationDeleteReads: Reads[AllowApplicationDelete] = (
    (JsPath \ "id").read[EventId] and
      (JsPath \ "applicationId").read[ApplicationId] and
      (JsPath \ "eventDateTime").read[Instant] and
      (JsPath \ "actor").read[Actors.GatekeeperUser] and
      ((JsPath \ "reasons").read[String] or Reads.pure("No reason given"))
  )(AllowApplicationDelete.apply _)

  given OFormat[AllowApplicationDelete] = {
    OFormat(allowApplicationDeleteReads, Json.writes[AllowApplicationDelete])
  }

  given restrictApplicationDeleteReads: Reads[RestrictApplicationDelete] = (
    (JsPath \ "id").read[EventId] and
      (JsPath \ "applicationId").read[ApplicationId] and
      (JsPath \ "eventDateTime").read[Instant] and
      (JsPath \ "actor").read[Actors.GatekeeperUser] and
      ((JsPath \ "reasons").read[String] or Reads.pure("No reason given"))
  )(RestrictApplicationDelete.apply _)

  given OFormat[RestrictApplicationDelete] = {
    OFormat(restrictApplicationDeleteReads, Json.writes[RestrictApplicationDelete])
  }

  given OFormat[SandboxApplicationNameChanged] = {
    Json.format[SandboxApplicationNameChanged]
  }

  given OFormat[SandboxApplicationDescriptionChanged] = {
    Json.format[SandboxApplicationDescriptionChanged]
  }

  given OFormat[SandboxApplicationPrivacyPolicyUrlChanged] = {
    Json.format[SandboxApplicationPrivacyPolicyUrlChanged]
  }

  given OFormat[SandboxApplicationTermsAndConditionsUrlChanged] = {
    Json.format[SandboxApplicationTermsAndConditionsUrlChanged]
  }

  given OFormat[SandboxApplicationDescriptionCleared] = {
    Json.format[SandboxApplicationDescriptionCleared]
  }

  given OFormat[SandboxApplicationPrivacyPolicyUrlRemoved] = {
    Json.format[SandboxApplicationPrivacyPolicyUrlRemoved]
  }

  given OFormat[SandboxApplicationTermsAndConditionsUrlRemoved] = {
    Json.format[SandboxApplicationTermsAndConditionsUrlRemoved]
  }

  given OFormat[ApplicationLinkedToOrganisation] = {
    Json.format[ApplicationLinkedToOrganisation]
  }

  private sealed trait EventType

  private object EventTypes {
    case object COLLABORATOR_ADDED   extends EventType
    case object COLLABORATOR_REMOVED extends EventType

    case object TEAM_MEMBER_ADDED   extends EventType
    case object TEAM_MEMBER_REMOVED extends EventType

    case object CLIENT_SECRET_ADDED    extends EventType
    case object CLIENT_SECRET_ADDED_V2 extends EventType

    case object CLIENT_SECRET_REMOVED_V2 extends EventType
    case object CLIENT_SECRET_REMOVED    extends EventType

    case object API_SUBSCRIBED_V2   extends EventType
    case object API_UNSUBSCRIBED_V2 extends EventType

    case object API_SUBSCRIBED                                    extends EventType
    case object API_UNSUBSCRIBED                                  extends EventType
    case object GRANT_LENGTH_CHANGED                              extends EventType
    case object RESPONSIBLE_INDIVIDUAL_SET                        extends EventType
    case object RESPONSIBLE_INDIVIDUAL_CHANGED                    extends EventType
    case object RESPONSIBLE_INDIVIDUAL_CHANGED_TO_SELF            extends EventType
    case object RESPONSIBLE_INDIVIDUAL_VERIFICATION_STARTED       extends EventType
    case object RESPONSIBLE_INDIVIDUAL_VERIFICATION_REQUIRED      extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED                   extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED_UPDATE            extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DID_NOT_VERIFY             extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED_OR_DID_NOT_VERIFY extends EventType

    case object APPLICATION_APPROVAL_REQUEST_DECLINED              extends EventType
    case object APPLICATION_APPROVAL_REQUEST_GRANTED               extends EventType
    case object APPLICATION_APPROVAL_REQUEST_GRANTED_WITH_WARNINGS extends EventType
    case object APPLICATION_SELL_RESELL_OR_DISTRIBUTE_CHANGED      extends EventType
    case object APPLICATION_APPROVAL_REQUEST_SUBMITTED             extends EventType
    case object TERMS_OF_USE_APPROVAL_REQUEST_SUBMITTED            extends EventType
    case object REQUESTER_EMAIL_VERIFICATION_RESENT                extends EventType
    case object TERMS_OF_USE_APPROVAL_GRANTED                      extends EventType
    case object TERMS_OF_USE_INVITATION_SENT                       extends EventType
    case object TERMS_OF_USE_PASSED                                extends EventType
    case object APPLICATION_STATE_CHANGED                          extends EventType

    case object APPLICATION_DELETED                        extends EventType
    case object APPLICATION_DELETED_BY_GATEKEEPER          extends EventType
    case object PRODUCTION_CREDENTIALS_APPLICATION_DELETED extends EventType
    case object ALLOW_APPLICATION_AUTO_DELETE              extends EventType
    case object BLOCK_APPLICATION_AUTO_DELETE              extends EventType
    case object ALLOW_APPLICATION_DELETE                   extends EventType
    case object RESTRICT_APPLICATION_DELETE                extends EventType

    case object PROD_APP_PRIVACY_POLICY_LOCATION_CHANGED          extends EventType
    case object PROD_APP_TERMS_CONDITIONS_LOCATION_CHANGED        extends EventType
    case object PROD_LEGACY_APP_PRIVACY_POLICY_LOCATION_CHANGED   extends EventType
    case object PROD_LEGACY_APP_TERMS_CONDITIONS_LOCATION_CHANGED extends EventType
    case object PROD_APP_NAME_CHANGED                             extends EventType

    case object REDIRECT_URIS_UPDATED    extends EventType
    case object REDIRECT_URIS_UPDATED_V2 extends EventType
    case object REDIRECT_URI_ADDED       extends EventType
    case object REDIRECT_URI_CHANGED     extends EventType
    case object REDIRECT_URI_DELETED     extends EventType

    case object POST_LOGOUT_REDIRECT_URIS_UPDATED extends EventType
    case object POST_LOGOUT_REDIRECT_URI_ADDED    extends EventType
    case object POST_LOGOUT_REDIRECT_URI_CHANGED  extends EventType
    case object POST_LOGOUT_REDIRECT_URI_DELETED  extends EventType

    case object PPNS_CALLBACK_URI_UPDATED extends EventType

    case object RATE_LIMIT_CHANGED extends EventType

    case object IP_ALLOWLIST_CIDR_BLOCK_CHANGED extends EventType

    case object SANDBOX_APPLICATION_NAME_CHANGED                     extends EventType
    case object SANDBOX_APPLICATION_DESCRIPTION_CHANGED              extends EventType
    case object SANDBOX_APPLICATION_PRIVACY_POLICY_URL_CHANGED       extends EventType
    case object SANDBOX_APPLICATION_TERMS_AND_CONDITIONS_URL_CHANGED extends EventType
    case object SANDBOX_APPLICATION_DESCRIPTION_CLEARED              extends EventType
    case object SANDBOX_APPLICATION_PRIVACY_POLICY_URL_REMOVED       extends EventType
    case object SANDBOX_APPLICATION_TERMS_AND_CONDITIONS_URL_REMOVED extends EventType

    case object APPLICATION_BLOCKED                  extends EventType
    case object APPLICATION_UNBLOCKED                extends EventType
    case object APPLICATION_SCOPES_CHANGED           extends EventType
    case object APPLICATION_ACCESS_OVERRIDES_CHANGED extends EventType

    case object APPLICATION_LINKED_TO_ORGANISATION extends EventType

    // scalastyle:on number.of.types
    // scalastyle:on number.of.methods
  }

  given OFormat[ApplicationEvent] = Union.from[ApplicationEvent]("eventType")
    .and[ProductionAppNameChangedEvent](EventTypes.PROD_APP_NAME_CHANGED.toString)
    .and[ProductionAppPrivacyPolicyLocationChanged](EventTypes.PROD_APP_PRIVACY_POLICY_LOCATION_CHANGED.toString)
    .and[ProductionLegacyAppPrivacyPolicyLocationChanged](
      EventTypes.PROD_LEGACY_APP_PRIVACY_POLICY_LOCATION_CHANGED.toString
    )
    .and[ProductionAppTermsConditionsLocationChanged](EventTypes.PROD_APP_TERMS_CONDITIONS_LOCATION_CHANGED.toString)
    .and[ProductionLegacyAppTermsConditionsLocationChanged](
      EventTypes.PROD_LEGACY_APP_TERMS_CONDITIONS_LOCATION_CHANGED.toString
    )
    .and[ResponsibleIndividualSet](EventTypes.RESPONSIBLE_INDIVIDUAL_SET.toString)
    .and[ResponsibleIndividualChanged](EventTypes.RESPONSIBLE_INDIVIDUAL_CHANGED.toString)
    .and[ResponsibleIndividualChangedToSelf](EventTypes.RESPONSIBLE_INDIVIDUAL_CHANGED_TO_SELF.toString)
    .and[ApplicationStateChanged](EventTypes.APPLICATION_STATE_CHANGED.toString)
    .and[ResponsibleIndividualVerificationStarted](EventTypes.RESPONSIBLE_INDIVIDUAL_VERIFICATION_STARTED.toString)
    .and[ResponsibleIndividualVerificationRequired](EventTypes.RESPONSIBLE_INDIVIDUAL_VERIFICATION_REQUIRED.toString)
    .and[ResponsibleIndividualDeclined](EventTypes.RESPONSIBLE_INDIVIDUAL_DECLINED.toString)
    .and[ResponsibleIndividualDeclinedUpdate](EventTypes.RESPONSIBLE_INDIVIDUAL_DECLINED_UPDATE.toString)
    .and[ResponsibleIndividualDidNotVerify](EventTypes.RESPONSIBLE_INDIVIDUAL_DID_NOT_VERIFY.toString)
    .and[ResponsibleIndividualDeclinedOrDidNotVerify](EventTypes.RESPONSIBLE_INDIVIDUAL_DECLINED_OR_DID_NOT_VERIFY.toString)
    .and[ApplicationApprovalRequestDeclined](EventTypes.APPLICATION_APPROVAL_REQUEST_DECLINED.toString)
    .and[ApplicationApprovalRequestGranted](EventTypes.APPLICATION_APPROVAL_REQUEST_GRANTED.toString)
    .and[ApplicationApprovalRequestGrantedWithWarnings](EventTypes.APPLICATION_APPROVAL_REQUEST_GRANTED_WITH_WARNINGS.toString)
    .and[ApplicationSellResellOrDistributeChanged](EventTypes.APPLICATION_SELL_RESELL_OR_DISTRIBUTE_CHANGED.toString)
    .and[ApplicationApprovalRequestSubmitted](EventTypes.APPLICATION_APPROVAL_REQUEST_SUBMITTED.toString)
    .and[TermsOfUseApprovalSubmitted](EventTypes.TERMS_OF_USE_APPROVAL_REQUEST_SUBMITTED.toString)
    .and[TermsOfUseApprovalGranted](EventTypes.TERMS_OF_USE_APPROVAL_GRANTED.toString)
    .and[RequesterEmailVerificationResent](EventTypes.REQUESTER_EMAIL_VERIFICATION_RESENT.toString)
    .and[TermsOfUseInvitationSent](EventTypes.TERMS_OF_USE_INVITATION_SENT.toString)
    .and[TermsOfUsePassed](EventTypes.TERMS_OF_USE_PASSED.toString)
    .and[ApplicationDeleted](EventTypes.APPLICATION_DELETED.toString)
    .and[ApplicationDeletedByGatekeeper](EventTypes.APPLICATION_DELETED_BY_GATEKEEPER.toString)
    .and[ProductionCredentialsApplicationDeleted](EventTypes.PRODUCTION_CREDENTIALS_APPLICATION_DELETED.toString)
    .and[AllowApplicationAutoDelete](EventTypes.ALLOW_APPLICATION_AUTO_DELETE.toString)
    .and[BlockApplicationAutoDelete](EventTypes.BLOCK_APPLICATION_AUTO_DELETE.toString)
    .and[AllowApplicationDelete](EventTypes.ALLOW_APPLICATION_DELETE.toString)
    .and[RestrictApplicationDelete](EventTypes.RESTRICT_APPLICATION_DELETE.toString)
    .and[ApplicationBlocked](EventTypes.APPLICATION_BLOCKED.toString)
    .and[ApplicationUnblocked](EventTypes.APPLICATION_UNBLOCKED.toString)
    .and[ApplicationScopesChanged](EventTypes.APPLICATION_SCOPES_CHANGED.toString)
    .and[ApplicationAccessOverridesChanged](EventTypes.APPLICATION_ACCESS_OVERRIDES_CHANGED.toString)
    .and[ApiSubscribedV2](EventTypes.API_SUBSCRIBED_V2.toString)
    .and[ApiUnsubscribedV2](EventTypes.API_UNSUBSCRIBED_V2.toString)
    .and[ClientSecretAddedV2](EventTypes.CLIENT_SECRET_ADDED_V2.toString)
    .and[ClientSecretRemovedV2](EventTypes.CLIENT_SECRET_REMOVED_V2.toString)
    .and[CollaboratorAddedV2](EventTypes.COLLABORATOR_ADDED.toString)
    .and[CollaboratorRemovedV2](EventTypes.COLLABORATOR_REMOVED.toString)
    .and[GrantLengthChanged](EventTypes.GRANT_LENGTH_CHANGED.toString)
    .and[LoginRedirectUrisUpdatedEvent](EventTypes.REDIRECT_URIS_UPDATED.toString)
    .and[LoginRedirectUrisUpdatedV2](EventTypes.REDIRECT_URIS_UPDATED_V2.toString)
    .and[LoginRedirectUriAdded](EventTypes.REDIRECT_URI_ADDED.toString)
    .and[LoginRedirectUriChanged](EventTypes.REDIRECT_URI_CHANGED.toString)
    .and[LoginRedirectUriDeleted](EventTypes.REDIRECT_URI_DELETED.toString)
    .and[PostLogoutRedirectUrisUpdated](EventTypes.POST_LOGOUT_REDIRECT_URIS_UPDATED.toString)
    .and[PostLogoutRedirectUriAdded](EventTypes.POST_LOGOUT_REDIRECT_URI_ADDED.toString)
    .and[PostLogoutRedirectUriChanged](EventTypes.POST_LOGOUT_REDIRECT_URI_CHANGED.toString)
    .and[PostLogoutRedirectUriDeleted](EventTypes.POST_LOGOUT_REDIRECT_URI_DELETED.toString)
    .and[PpnsCallBackUriUpdatedEvent](EventTypes.PPNS_CALLBACK_URI_UPDATED.toString)
    .and[ApiSubscribedEvent](EventTypes.API_SUBSCRIBED.toString)
    .and[ApiUnsubscribedEvent](EventTypes.API_UNSUBSCRIBED.toString)
    .and[ClientSecretAddedEvent](EventTypes.CLIENT_SECRET_ADDED.toString)
    .and[ClientSecretRemovedEvent](EventTypes.CLIENT_SECRET_REMOVED.toString)
    .and[TeamMemberAddedEvent](EventTypes.TEAM_MEMBER_ADDED.toString)
    .and[TeamMemberRemovedEvent](EventTypes.TEAM_MEMBER_REMOVED.toString)
    .and[RateLimitChanged](EventTypes.RATE_LIMIT_CHANGED.toString)
    .and[IpAllowlistCidrBlockChanged](EventTypes.IP_ALLOWLIST_CIDR_BLOCK_CHANGED.toString)
    .and[SandboxApplicationNameChanged](EventTypes.SANDBOX_APPLICATION_NAME_CHANGED.toString)
    .and[SandboxApplicationDescriptionChanged](EventTypes.SANDBOX_APPLICATION_DESCRIPTION_CHANGED.toString)
    .and[SandboxApplicationPrivacyPolicyUrlChanged](EventTypes.SANDBOX_APPLICATION_PRIVACY_POLICY_URL_CHANGED.toString)
    .and[SandboxApplicationTermsAndConditionsUrlChanged](EventTypes.SANDBOX_APPLICATION_TERMS_AND_CONDITIONS_URL_CHANGED.toString)
    .and[SandboxApplicationDescriptionCleared](EventTypes.SANDBOX_APPLICATION_DESCRIPTION_CLEARED.toString)
    .and[SandboxApplicationPrivacyPolicyUrlRemoved](EventTypes.SANDBOX_APPLICATION_PRIVACY_POLICY_URL_REMOVED.toString)
    .and[SandboxApplicationTermsAndConditionsUrlRemoved](EventTypes.SANDBOX_APPLICATION_TERMS_AND_CONDITIONS_URL_REMOVED.toString)
    .and[ApplicationLinkedToOrganisation](EventTypes.APPLICATION_LINKED_TO_ORGANISATION.toString)
    .format
}

object EventsInterServiceCallJsonFormatters extends EventsJsonFormatters(InstantJsonFormatter.NoTimeZone.instantNoTimeZoneFormat)

/*
 *  For mongo use the following
 *
 *  object EventsMongoJsonFormatters extends EventsJsonFormatters {
 *     implicit val instantJsonFormatter = MongoJavatimeFormats.instantFormat
 *  }
 *
 */

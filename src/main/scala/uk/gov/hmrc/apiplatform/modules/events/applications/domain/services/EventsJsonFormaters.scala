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

import play.api.libs.json.{Format, Json, OFormat}
import uk.gov.hmrc.play.json.Union

import uk.gov.hmrc.apiplatform.modules.common.domain.services.InstantFormatter
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import  uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._

abstract class EventsJsonFormatters(instantFormatter: Format[Instant]) {

  private implicit val fmt = instantFormatter

  // scalastyle:off number.of.types
  // scalastyle:off number.of.methods

  implicit val collaboratorAddedFormats   = Json.format[CollaboratorAddedV2]
  implicit val collaboratorRemovedFormats = Json.format[CollaboratorRemovedV2]

  implicit val teamMemberAddedEventFormats   = Json.format[TeamMemberAddedEvent]
  implicit val teamMemberRemovedEventFormats = Json.format[TeamMemberRemovedEvent]

  implicit val clientSecretAddedFormats   = Json.format[ClientSecretAddedV2]
  implicit val clientSecretRemovedFormats = Json.format[ClientSecretRemovedV2]

  implicit val clientSecretAddedEventFormats   = Json.format[ClientSecretAddedEvent]
  implicit val clientSecretRemovedEventFormats = Json.format[ClientSecretRemovedEvent]

  implicit val apiSubscribedFormats   = Json.format[ApiSubscribedV2]
  implicit val apiUnsubscribedFormats = Json.format[ApiUnsubscribedV2]

  implicit val apiSubscribedEventFormats   = Json.format[ApiSubscribedEvent]
  implicit val apiUnsubscribedEventFormats = Json.format[ApiUnsubscribedEvent]

  implicit val productionAppNameChangedEventFormats             = Json.format[ProductionAppNameChangedEvent]
  implicit val productionAppPrivacyPolicyLocationChangedFormats = Json.format[ProductionAppPrivacyPolicyLocationChanged]

  implicit val productionLegacyAppPrivacyPolicyLocationChangedFormats =
    Json.format[ProductionLegacyAppPrivacyPolicyLocationChanged]

  implicit val productionAppTermsConditionsLocationChangedFormats =
    Json.format[ProductionAppTermsConditionsLocationChanged]

  implicit val productionLegacyAppTermsConditionsLocationChangedFormats =
    Json.format[ProductionLegacyAppTermsConditionsLocationChanged]
  implicit val responsibleIndividualSetFormats                          = Json.format[ResponsibleIndividualSet]

  implicit val responsibleIndividualChangedFormats       = Json.format[ResponsibleIndividualChanged]
  implicit val responsibleIndividualChangedToSelfFormats = Json.format[ResponsibleIndividualChangedToSelf]
  implicit val applicationStateChangedFormats            = Json.format[ApplicationStateChanged]

  implicit val responsibleIndividualVerificationStartedFormats    = Json.format[ResponsibleIndividualVerificationStarted]
  implicit val responsibleIndividualDeclinedFormats               = Json.format[ResponsibleIndividualDeclined]
  implicit val responsibleIndividualDeclinedUpdateFormats         = Json.format[ResponsibleIndividualDeclinedUpdate]
  implicit val responsibleIndividualDidNotVerifyFormats           = Json.format[ResponsibleIndividualDidNotVerify]
  implicit val responsibleIndividualDeclinedOrDidNotVerifyFormats = Json.format[ResponsibleIndividualDeclinedOrDidNotVerify]

  implicit val applicationApprovalRequestDeclinedFormats = Json.format[ApplicationApprovalRequestDeclined]
  implicit val termsOfUsePassedFormats                   = Json.format[TermsOfUsePassed]

  implicit val applicationDeletedFormats                      = Json.format[ApplicationDeleted]
  implicit val applicationDeletedByGatekeeperFormats          = Json.format[ApplicationDeletedByGatekeeper]
  implicit val productionCredentialsApplicationDeletedFormats = Json.format[ProductionCredentialsApplicationDeleted]

  implicit val redirectUrisUpdatedEventFormats    = Json.format[RedirectUrisUpdatedEvent]
  implicit val redirectUrisUpdatedFormats         = Json.format[RedirectUrisUpdatedV2]
  implicit val redirectUriAddedFormats            = Json.format[RedirectUriAdded]
  implicit val redirectUriChangedFormats            = Json.format[RedirectUriChanged]
  implicit val redirectUriDeletedFormats            = Json.format[RedirectUriDeleted]
  implicit val ppnsCallBackUriUpdatedEventFormats = Json.format[PpnsCallBackUriUpdatedEvent]

  private sealed trait EventType

  private object EventTypes {

    case object COLLABORATOR_ADDED  extends EventType
    case object COLLABORATOR_REMOVED extends EventType

    case object TEAM_MEMBER_ADDED   extends EventType
    case object TEAM_MEMBER_REMOVED extends EventType

    case object CLIENT_SECRET_ADDED    extends EventType
    case object CLIENT_SECRET_ADDED_V2 extends EventType

    case object CLIENT_SECRET_REMOVED_V2 extends EventType
    case object CLIENT_SECRET_REMOVED    extends EventType

    case object API_SUBSCRIBED_V2   extends EventType
    case object API_UNSUBSCRIBED_V2 extends EventType

    case object API_SUBSCRIBED   extends EventType
    case object API_UNSUBSCRIBED extends EventType

    case object RESPONSIBLE_INDIVIDUAL_SET                        extends EventType
    case object RESPONSIBLE_INDIVIDUAL_CHANGED                    extends EventType
    case object RESPONSIBLE_INDIVIDUAL_CHANGED_TO_SELF            extends EventType
    case object RESPONSIBLE_INDIVIDUAL_VERIFICATION_STARTED       extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED                   extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED_UPDATE            extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DID_NOT_VERIFY             extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED_OR_DID_NOT_VERIFY extends EventType

    case object APPLICATION_APPROVAL_REQUEST_DECLINED extends EventType
    case object TERMS_OF_USE_PASSED                   extends EventType
    case object APPLICATION_STATE_CHANGED             extends EventType

    case object APPLICATION_DELETED                        extends EventType
    case object APPLICATION_DELETED_BY_GATEKEEPER          extends EventType
    case object PRODUCTION_CREDENTIALS_APPLICATION_DELETED extends EventType

    case object PROD_APP_PRIVACY_POLICY_LOCATION_CHANGED          extends EventType
    case object PROD_APP_TERMS_CONDITIONS_LOCATION_CHANGED        extends EventType
    case object PROD_LEGACY_APP_PRIVACY_POLICY_LOCATION_CHANGED   extends EventType
    case object PROD_LEGACY_APP_TERMS_CONDITIONS_LOCATION_CHANGED extends EventType
    case object PROD_APP_NAME_CHANGED                             extends EventType

    case object REDIRECT_URIS_UPDATED                             extends EventType
    case object REDIRECT_URIS_UPDATED_V2                          extends EventType
    case object REDIRECT_URI_ADDED                          extends EventType
    case object REDIRECT_URI_CHANGED                          extends EventType
    case object REDIRECT_URI_DELETED                          extends EventType

    case object PPNS_CALLBACK_URI_UPDATED                         extends EventType
    // scalastyle:on number.of.types
    // scalastyle:on number.of.methods
  }


  implicit val abstractApplicationEventFormats: OFormat[ApplicationEvent] = Union.from[ApplicationEvent]("eventType")
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
    .and[ResponsibleIndividualDeclined](EventTypes.RESPONSIBLE_INDIVIDUAL_DECLINED.toString)
    .and[ResponsibleIndividualDeclinedUpdate](EventTypes.RESPONSIBLE_INDIVIDUAL_DECLINED_UPDATE.toString)
    .and[ResponsibleIndividualDidNotVerify](EventTypes.RESPONSIBLE_INDIVIDUAL_DID_NOT_VERIFY.toString)
    .and[ResponsibleIndividualDeclinedOrDidNotVerify](EventTypes.RESPONSIBLE_INDIVIDUAL_DECLINED_OR_DID_NOT_VERIFY.toString)
    .and[ApplicationApprovalRequestDeclined](EventTypes.APPLICATION_APPROVAL_REQUEST_DECLINED.toString)
    .and[TermsOfUsePassed](EventTypes.TERMS_OF_USE_PASSED.toString)
    .and[ApplicationDeleted](EventTypes.APPLICATION_DELETED.toString)
    .and[ApplicationDeletedByGatekeeper](EventTypes.APPLICATION_DELETED_BY_GATEKEEPER.toString)
    .and[ProductionCredentialsApplicationDeleted](EventTypes.PRODUCTION_CREDENTIALS_APPLICATION_DELETED.toString)
    .and[ApiSubscribedV2](EventTypes.API_SUBSCRIBED_V2.toString)
    .and[ApiUnsubscribedV2](EventTypes.API_UNSUBSCRIBED_V2.toString)
    .and[ClientSecretAddedV2](EventTypes.CLIENT_SECRET_ADDED_V2.toString)
    .and[ClientSecretRemovedV2](EventTypes.CLIENT_SECRET_REMOVED_V2.toString)
    .and[CollaboratorAddedV2](EventTypes.COLLABORATOR_ADDED.toString)
    .and[CollaboratorRemovedV2](EventTypes.COLLABORATOR_REMOVED.toString)
    .and[RedirectUrisUpdatedEvent](EventTypes.REDIRECT_URIS_UPDATED.toString)
    .and[RedirectUrisUpdatedV2](EventTypes.REDIRECT_URIS_UPDATED_V2.toString)
    .and[RedirectUriAdded](EventTypes.REDIRECT_URI_ADDED.toString)
    .and[RedirectUriChanged](EventTypes.REDIRECT_URI_CHANGED.toString)
    .and[RedirectUriDeleted](EventTypes.REDIRECT_URI_DELETED.toString)
    .and[PpnsCallBackUriUpdatedEvent](EventTypes.PPNS_CALLBACK_URI_UPDATED.toString)
    .and[ApiSubscribedEvent](EventTypes.API_SUBSCRIBED.toString)
    .and[ApiUnsubscribedEvent](EventTypes.API_UNSUBSCRIBED.toString)
    .and[ClientSecretAddedEvent](EventTypes.CLIENT_SECRET_ADDED.toString)
    .and[ClientSecretRemovedEvent](EventTypes.CLIENT_SECRET_REMOVED.toString)
    .and[TeamMemberAddedEvent](EventTypes.TEAM_MEMBER_ADDED.toString)
    .and[TeamMemberRemovedEvent](EventTypes.TEAM_MEMBER_REMOVED.toString)
    .format
}
object EventsInterServiceCallJsonFormatters extends EventsJsonFormatters(InstantFormatter.NoTimeZone.format)

/*
 *  For mongo use the following
 *
 *  object EventsMongoJsonFormatters extends EventsJsonFormatters {
 *     implicit val instantJsonFormatter = MongoJavatimeFormats.instantFormat
 *  }
 *
 */

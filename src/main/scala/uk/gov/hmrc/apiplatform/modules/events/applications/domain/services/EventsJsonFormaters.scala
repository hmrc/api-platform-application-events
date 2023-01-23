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

import java.time.LocalDateTime

import play.api.libs.json.{EnvReads, EnvWrites, Format, Json, OFormat}
import uk.gov.hmrc.play.json.Union

import uk.gov.hmrc.apiplatform.modules.applications.domain.services._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._

import uk.gov.hmrc.apiplatform.modules.applications.domain.services.CollaboratorJsonFormatters

abstract class EventsJsonFormatters(localDateTimeFormats: Format[LocalDateTime]) extends ActorJsonFormatters
    with OldStyleActorJsonFormatters with CollaboratorJsonFormatters
    with PrivacyPolicyLocationJsonFormatters with TermsAndConditionsLocationJsonFormatters with CommonJsonFormatters {

  // scalastyle:off number.of.types
  // scalastyle:off number.of.methods
  private implicit val fmt = localDateTimeFormats

  implicit val collaboratorAddedFormats   = Json.format[CollaboratorAdded]
  implicit val collaboratorRemovedFormats = Json.format[CollaboratorRemoved]

  implicit val teamMemberAddedEventFormats   = Json.format[TeamMemberAddedEvent]
  implicit val teamMemberRemovedEventFormats = Json.format[TeamMemberRemovedEvent]

  implicit val clientSecretAddedFormats   = Json.format[ClientSecretAdded]
  implicit val clientSecretRemovedFormats = Json.format[ClientSecretRemoved]

  implicit val clientSecretAddedEventFormats   = Json.format[ClientSecretAddedEvent]
  implicit val clientSecretRemovedEventFormats = Json.format[ClientSecretRemovedEvent]

  implicit val apiSubscribedFormats   = Json.format[ApiSubscribed]
  implicit val apiUnsubscribedFormats = Json.format[ApiUnsubscribed]

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

  implicit val responsibleIndividualVerificationStartedFormats = Json.format[ResponsibleIndividualVerificationStarted]
  implicit val responsibleIndividualDeclinedFormats            = Json.format[ResponsibleIndividualDeclined]
  implicit val responsibleIndividualDeclinedUpdateFormats      = Json.format[ResponsibleIndividualDeclinedUpdate]
  implicit val responsibleIndividualDidNotVerifyFormats        = Json.format[ResponsibleIndividualDidNotVerify]

  implicit val applicationApprovalRequestDeclinedFormats = Json.format[ApplicationApprovalRequestDeclined]

  implicit val applicationDeletedFormats                      = Json.format[ApplicationDeleted]
  implicit val applicationDeletedByGatekeeperFormats          = Json.format[ApplicationDeletedByGatekeeper]
  implicit val productionCredentialsApplicationDeletedFormats = Json.format[ProductionCredentialsApplicationDeleted]

  implicit val redirectUrisUpdatedEventFormats    = Json.format[RedirectUrisUpdatedEvent]
  implicit val redirectUrisUpdatedFormats         = Json.format[RedirectUrisUpdated]
  implicit val ppnsCallBackUriUpdatedEventFormats = Json.format[PpnsCallBackUriUpdatedEvent]

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

    case object API_SUBSCRIBED   extends EventType
    case object API_UNSUBSCRIBED extends EventType

    case object RESPONSIBLE_INDIVIDUAL_SET                  extends EventType
    case object RESPONSIBLE_INDIVIDUAL_CHANGED              extends EventType
    case object RESPONSIBLE_INDIVIDUAL_CHANGED_TO_SELF      extends EventType
    case object RESPONSIBLE_INDIVIDUAL_VERIFICATION_STARTED extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED             extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DECLINED_UPDATE      extends EventType
    case object RESPONSIBLE_INDIVIDUAL_DID_NOT_VERIFY       extends EventType

    case object APPLICATION_APPROVAL_REQUEST_DECLINED extends EventType
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
    case object PPNS_CALLBACK_URI_UPDATED                         extends EventType
    // scalastyle:on number.of.types
    // scalastyle:on number.of.methods
  }

  implicit val abstractApplicationEventFormats: OFormat[AbstractApplicationEvent] = Union.from[AbstractApplicationEvent]("eventType")
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
    .and[ApplicationApprovalRequestDeclined](EventTypes.APPLICATION_APPROVAL_REQUEST_DECLINED.toString)
    .and[ApplicationDeleted](EventTypes.APPLICATION_DELETED.toString)
    .and[ApplicationDeletedByGatekeeper](EventTypes.APPLICATION_DELETED_BY_GATEKEEPER.toString)
    .and[ProductionCredentialsApplicationDeleted](EventTypes.PRODUCTION_CREDENTIALS_APPLICATION_DELETED.toString)
    .and[ApiSubscribed](EventTypes.API_SUBSCRIBED_V2.toString)
    .and[ApiUnsubscribed](EventTypes.API_UNSUBSCRIBED_V2.toString)
    .and[ClientSecretAdded](EventTypes.CLIENT_SECRET_ADDED_V2.toString)
    .and[ClientSecretRemoved](EventTypes.CLIENT_SECRET_REMOVED_V2.toString)
    .and[CollaboratorAdded](EventTypes.COLLABORATOR_ADDED.toString)
    .and[CollaboratorRemoved](EventTypes.COLLABORATOR_REMOVED.toString)
    .and[RedirectUrisUpdatedEvent](EventTypes.REDIRECT_URIS_UPDATED.toString)
    .and[RedirectUrisUpdated](EventTypes.REDIRECT_URIS_UPDATED_V2.toString)
    .and[PpnsCallBackUriUpdatedEvent](EventTypes.PPNS_CALLBACK_URI_UPDATED.toString)
    .and[ApiSubscribedEvent](EventTypes.API_SUBSCRIBED.toString)
    .and[ApiUnsubscribedEvent](EventTypes.API_UNSUBSCRIBED.toString)
    .and[ClientSecretAddedEvent](EventTypes.CLIENT_SECRET_ADDED.toString)
    .and[ClientSecretRemovedEvent](EventTypes.CLIENT_SECRET_REMOVED.toString)
    .and[TeamMemberAddedEvent](EventTypes.TEAM_MEMBER_ADDED.toString)
    .and[TeamMemberRemovedEvent](EventTypes.TEAM_MEMBER_REMOVED.toString)
    .format
}

object LocalDateTimeFormatter extends EnvWrites with EnvReads {
  import play.api.libs.json._

  implicit val writer: Writes[LocalDateTime] = DefaultLocalDateTimeWrites

  implicit val reader: Reads[LocalDateTime] = DefaultLocalDateTimeReads

  implicit val format: Format[LocalDateTime] = Format(reader, writer)
}

object EventsInterServiceCallJsonFormatters extends EventsJsonFormatters(LocalDateTimeFormatter.format)

/*
 *  For mongo use the following
 *
 *  object EventsMongoJsonFormatters extends EventsJsonFormatters {
 *     implicit val localDateTimeFormats = MongoJavatimeFormats.localDateTimeFormat
 *  }
 *
 */

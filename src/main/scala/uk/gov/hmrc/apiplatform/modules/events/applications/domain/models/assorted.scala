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

import java.time.LocalDateTime
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId

case class PpnsCallBackUriUpdatedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    boxId: String,
    boxName: String,
    oldCallbackUrl: String,
    newCallbackUrl: String
  ) extends OldStyleApplicationEvent

case class RedirectUrisUpdatedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    oldRedirectUris: String,
    newRedirectUris: String
  ) extends OldStyleApplicationEvent

case class ProductionAppNameChangedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldAppName: String,
    newAppName: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ProductionAppPrivacyPolicyLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldLocation: PrivacyPolicyLocation,
    newLocation: PrivacyPolicyLocation,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ProductionLegacyAppPrivacyPolicyLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldUrl: String,
    newUrl: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ProductionAppTermsConditionsLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldLocation: TermsAndConditionsLocation,
    newLocation: TermsAndConditionsLocation,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

case class ProductionLegacyAppTermsConditionsLocationChanged(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    oldUrl: String,
    newUrl: String,
    requestingAdminEmail: LaxEmailAddress
  ) extends ApplicationEvent

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
import uk.gov.hmrc.apiplatform.modules.apis.domain.models.ApiIdentifier
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.ApplicationId

case class ApiSubscribed(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    apiIdentifier: ApiIdentifier
  ) extends ApplicationEvent

case class ApiUnsubscribed(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: Actor,
    apiIdentifier: ApiIdentifier
  ) extends ApplicationEvent

@deprecated("please use new event ApiSubscribed", "Oct 2022")
case class ApiSubscribedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    context: String,
    version: String
  ) extends OldStyleApplicationEvent

@deprecated("please use new event ApiUnsubscribed", "Oct 2022")
case class ApiUnsubscribedEvent(
    id: EventId,
    applicationId: ApplicationId,
    eventDateTime: LocalDateTime,
    actor: OldStyleActor,
    context: String,
    version: String
  ) extends OldStyleApplicationEvent

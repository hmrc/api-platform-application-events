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

package uk.gov.hmrc.apiplatform.modules.commands.applications.domain.services

import play.api.libs.json.Json

import uk.gov.hmrc.apiplatform.modules.commands.applications.domain.models._
import uk.gov.hmrc.play.json.Union
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.services._

import uk.gov.hmrc.apiplatform.modules.applications.domain.services.CollaboratorJsonFormatters

trait ApplicationCommandJsonFormatters extends ActorJsonFormatters with CollaboratorJsonFormatters {
  
  implicit val addCollaboratorFormatter              = Json.format[AddCollaborator]
  implicit val addCollaboratorUpdateRequestFormatter = Json.format[AddCollaboratorRequest]
  implicit val removeCollaboratorFormatter           = Json.format[RemoveCollaborator]
  implicit val removeCollaboratorRequestFormatter    = Json.format[RemoveCollaboratorRequest]
  implicit val subscribeToApiFormatter               = Json.format[SubscribeToApi]
  implicit val unsubscribeFromApiFormatter           = Json.format[UnsubscribeFromApi]
  implicit val updateRedirectUrisFormatter           = Json.format[UpdateRedirectUris]

  implicit val applicationUpdateFormatter = Union.from[ApplicationCommand]("updateType")
    .and[AddCollaboratorRequest]("addCollaboratorRequest")
    .and[AddCollaborator]("addCollaborator")
    .and[RemoveCollaborator]("removeCollaborator")
    .and[RemoveCollaboratorRequest]("removeCollaboratorRequest")
    .and[SubscribeToApi]("subscribeToApi")
    .and[UnsubscribeFromApi]("unsubscribeFromApi")
    .and[UpdateRedirectUris]("updateRedirectUris")
    .format

}

object ApplicationCommandJsonFormatters extends ApplicationCommandJsonFormatters

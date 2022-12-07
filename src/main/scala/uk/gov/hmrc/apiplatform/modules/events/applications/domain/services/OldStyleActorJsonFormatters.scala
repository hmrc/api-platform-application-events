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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import play.api.libs.json._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import uk.gov.hmrc.play.json.Union

trait OldStyleActorJsonFormatters {
  private sealed trait ActorType

  private object ActorTypes {
    case object COLLABORATOR  extends ActorType
    case object GATEKEEPER    extends ActorType
    case object SCHEDULED_JOB extends ActorType
    case object UNKNOWN       extends ActorType
  }

  implicit val oldStyleActorsCollaboratorJF   = Json.format[OldStyleActors.Collaborator]
  implicit val oldStyleActorsGatekeeperUserJF = Json.format[OldStyleActors.GatekeeperUser]
  implicit val oldStyleActorsScheduledJobJF   = Json.format[OldStyleActors.ScheduledJob]
  implicit val oldStyleActorsUnknownJF        = Json.format[OldStyleActors.Unknown.type]

  implicit val formatOldStyleActor: OFormat[OldStyleActor] = Union.from[OldStyleActor]("actorType")
    .and[OldStyleActors.Collaborator](ActorTypes.COLLABORATOR.toString)
    .and[OldStyleActors.GatekeeperUser](ActorTypes.GATEKEEPER.toString)
    .and[OldStyleActors.ScheduledJob](ActorTypes.SCHEDULED_JOB.toString)
    .and[OldStyleActors.Unknown.type](ActorTypes.UNKNOWN.toString)
    .format
}

object OldStyleActorJsonFormatters extends OldStyleActorJsonFormatters

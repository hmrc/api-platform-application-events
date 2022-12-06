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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import uk.gov.hmrc.play.json.Union
import play.api.libs.json.{Json, OFormat}

trait CollaboratorJsonFormatters extends CommonJsonFormatters {
  private sealed trait Role

  private object Roles {
    case object ADMINISTRATOR extends Role
    case object DEVELOPER extends Role
  }

  implicit val administratorJf = Json.format[Collaborators.Administrator]
  implicit val developersJf = Json.format[Collaborators.Developer]

  implicit val collaboratorJf: OFormat[Collaborator] = Union.from[Collaborator]("role")
    .and[Collaborators.Administrator](Roles.ADMINISTRATOR.toString)
    .and[Collaborators.Developer](Roles.DEVELOPER.toString)
    .format
}

object CollaboratorJsonFormatters extends CollaboratorJsonFormatters with CommonJsonFormatters

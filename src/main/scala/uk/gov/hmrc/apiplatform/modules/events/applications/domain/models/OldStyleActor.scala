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

/** OldStyleActor refers to actors who triggered the older events created before rework in 2022
  *
  * The gatekeeper users typically have an id of "admin@gatekeeper"
  *
  * These should NEVER be used on newer events and deliberately are not part of any class hierarchy with the Actor
  * trait.
  */
@Deprecated
sealed trait OldStyleActor

object OldStyleActors {

  /** A third party developer as a collaborator on an app
    *
    * @param id
    *   the developers email address
    */
  case class Collaborator(id: String) extends OldStyleActor

  /** A gatekeeper stride user (typically SDST)
    *
    * @param id
    *   the stride users fullname in theory but in practice always "admin@gatekeeper"
    */
  case class GatekeeperUser(id: String) extends OldStyleActor

  /** An automated job
    *
    * @param id
    *   the job name or instance of the job possibly as a UUID
    */
  case class ScheduledJob(id: String) extends OldStyleActor

  /** Unknown source - probably 3rd party code such as PPNS invocations
    *
    * @param id
    *   the job name or instance of the job possibly as a UUID
    */
  case object Unknown extends OldStyleActor
}

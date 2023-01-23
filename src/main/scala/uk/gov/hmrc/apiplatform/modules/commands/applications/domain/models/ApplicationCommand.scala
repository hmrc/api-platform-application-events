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

package uk.gov.hmrc.apiplatform.modules.commands.applications.domain.models

import java.time.Instant
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.domain.models._
import uk.gov.hmrc.apiplatform.modules.developers.domain.models._
import uk.gov.hmrc.apiplatform.modules.apis.domain.models._

import uk.gov.hmrc.apiplatform.common.domain.models.LaxEmailAddress

sealed trait ApplicationCommand {
  def timestamp: Instant
}

case class AddClientSecret(actor: Actor, secretValue: String, clientSecret: ClientSecret, timestamp: Instant) extends ApplicationCommand
case class RemoveClientSecret(actor: Actor, clientSecretId: String, timestamp: Instant) extends ApplicationCommand
case class AddCollaborator(actor: Actor,  collaborator: Collaborator, adminsToEmail:Set[LaxEmailAddress], timestamp: Instant) extends ApplicationCommand
case class RemoveCollaborator(actor: Actor,  collaborator: Collaborator, adminsToEmail:Set[LaxEmailAddress], timestamp: Instant) extends ApplicationCommand
case class ChangeProductionApplicationPrivacyPolicyLocation(instigator: UserId, timestamp: Instant, newLocation: PrivacyPolicyLocation) extends ApplicationCommand
case class ChangeProductionApplicationTermsAndConditionsLocation(instigator: UserId, timestamp: Instant, newLocation: TermsAndConditionsLocation) extends ApplicationCommand
case class ChangeResponsibleIndividualToSelf(instigator: UserId, timestamp: Instant, name: String, email: LaxEmailAddress) extends ApplicationCommand
case class ChangeResponsibleIndividualToOther(code: String, timestamp: Instant) extends ApplicationCommand
case class VerifyResponsibleIndividual(instigator: UserId, timestamp: Instant, requesterName: String, riName: String, riEmail: LaxEmailAddress) extends ApplicationCommand
case class DeclineResponsibleIndividual(code: String, timestamp: Instant) extends ApplicationCommand
case class DeclineResponsibleIndividualDidNotVerify(code: String, timestamp: Instant) extends ApplicationCommand
case class DeleteApplicationByCollaborator(instigator: UserId, reasons: String, timestamp: Instant) extends ApplicationCommand
case class DeleteProductionCredentialsApplication(jobId: String, reasons: String, timestamp: Instant) extends ApplicationCommand
case class DeleteUnusedApplication(jobId: String, authorisationKey: String, reasons: String, timestamp: Instant) extends ApplicationCommand
case class SubscribeToApi(actor: Actor, apiIdentifier: ApiIdentifier, timestamp: Instant) extends ApplicationCommand
case class UnsubscribeFromApi(actor: Actor, apiIdentifier: ApiIdentifier, timestamp: Instant) extends ApplicationCommand
case class UpdateRedirectUris(actor: Actor, oldRedirectUris: List[String], newRedirectUris: List[String], timestamp: Instant) extends ApplicationCommand

// Frontend to APM
case class AddCollaboratorRequest(actor: Actor, collaboratorEmail: LaxEmailAddress, collaboratorRole: Collaborators.Role, timestamp: Instant) extends ApplicationCommand
case class RemoveCollaboratorRequest(actor: Actor, collaboratorEmail: LaxEmailAddress, collaboratorRole: Collaborators.Role, timestamp: Instant) extends ApplicationCommand

trait GatekeeperSpecificApplicationCommand extends ApplicationCommand {
  def gatekeeperUser: String
}

case class ChangeProductionApplicationName(instigator: UserId, timestamp: Instant, gatekeeperUser: String, newName: String) extends GatekeeperSpecificApplicationCommand
case class DeclineApplicationApprovalRequest(gatekeeperUser: String, reasons: String, timestamp: Instant) extends GatekeeperSpecificApplicationCommand
case class DeleteApplicationByGatekeeper(gatekeeperUser: String, requestedByEmailAddress: LaxEmailAddress, reasons: String, timestamp: Instant) extends GatekeeperSpecificApplicationCommand


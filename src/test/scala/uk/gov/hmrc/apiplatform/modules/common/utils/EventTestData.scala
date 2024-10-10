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

package uk.gov.hmrc.apiplatform.modules.common.utils

import java.time.{Instant, LocalDateTime, ZoneOffset}
import java.util.UUID

import uk.gov.hmrc.apiplatform.modules.common.domain.models.LaxEmailAddress.StringSyntax
import uk.gov.hmrc.apiplatform.modules.common.domain.models._
import uk.gov.hmrc.apiplatform.modules.applications.core.domain.models.{CollaboratorFixtures, CoreApplicationFixtures, RedirectUri}
import uk.gov.hmrc.apiplatform.modules.applications.submissions.domain.models.SubmissionId

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventId

trait EventTestData extends CoreApplicationFixtures with CollaboratorFixtures with ApiIdentifierFixtures with ActorFixtures with FixedClock {
  val anEventId          = EventId.random
  val anAppId            = applicationIdOne
  val appIdText          = anAppId.toString()
  val sixMillisInNanos   = 6 * 1000 * 1000
  val anInstant: Instant = LocalDateTime.of(2020, 1, 2, 3, 4, 5, sixMillisInNanos).toInstant(ZoneOffset.UTC)
  val instantText        = "2020-01-02T03:04:05.006"

  val context = apiContextOne
  val version = apiVersionNbrOne

  val reasons = "Some reasons here"

  val actorCollaboratorEmail = collaboratorActorOne.email
  val appCollaborator        = collaboratorActorOne
  val gkCollaborator         = gatekeeperActorOne
  val aClientSecretId        = "someClientId"
  val aClientSecretName      = "someClientSecretName"
  val aClientId              = clientIdOne

  val requestingEmail     = "fred@example.com".toLaxEmail
  val requestingAdminName = "fred example"

  val responsibleIndividualEmail = "captainsensible@example.com".toLaxEmail
  val responsibleIndividualName  = "captain sensible"
  val riCode                     = "1234"
  val verificationId             = "5678"

  val warnings                   = "some warning or other"
  val escalatedTo                = Some("super-user")
  val adminCollaboratorEmail     = "admin@example.com".toLaxEmail
  val developerCollaboratorEmail = "developer@example.com".toLaxEmail

  val developerCollaborator     = developerOne
  val administratorCollaborator = adminOne

  val boxId          = UUID.randomUUID().toString
  val boxName        = "BoxName2"
  val oldCallbackUrl = "http://old.com/boo"
  val newCallbackUrl = "http://new.com/yey"

  val aRedirectUri: RedirectUri        = RedirectUri.unsafeApply("http://localhost:8000/some")
  val toChangeRedirectUri: RedirectUri = RedirectUri.unsafeApply("http://localhost:8000/different")

  val submissionId    = SubmissionId(UUID.randomUUID())
  val submissionIndex = 1

}

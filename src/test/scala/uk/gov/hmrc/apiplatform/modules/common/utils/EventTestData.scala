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

import uk.gov.hmrc.apiplatform.modules.apis.domain.models.{ApiContext, ApiVersion}
import uk.gov.hmrc.apiplatform.modules.applications.domain.models._
import uk.gov.hmrc.apiplatform.modules.common.domain.models.Actors
import uk.gov.hmrc.apiplatform.modules.common.domain.models.LaxEmailAddress.StringSyntax
import uk.gov.hmrc.apiplatform.modules.developers.domain.models.UserId
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventId

trait EventTestData {
  val anEventId          = EventId.random
  val anAppId            = ApplicationId.random
  val appIdText          = anAppId.value.toString()
  val sixMillisInNanos   = 6 * 1000 * 1000
  val anInstant: Instant = LocalDateTime.of(2020, 1, 2, 3, 4, 5, sixMillisInNanos).toInstant(ZoneOffset.UTC)
  val instantText        = "2020-01-02T03:04:05.006"

  val context = ApiContext.random
  val version = ApiVersion.random

  val reasons = "Some reasons here"

  val actorCollaboratorEmail = "bob@example.com".toLaxEmail
  val appCollaborator        = Actors.AppCollaborator(actorCollaboratorEmail)
  val gkCollaborator         = Actors.GatekeeperUser("someUser")
  val aClientSecretId        = "someClientId"
  val aClientSecretName      = "someClientSecretName"
  val aClientId              = ClientId.random

  val requestingEmail            = "fred@example.com".toLaxEmail
  val adminCollaboratorEmail     = "admin@example.com".toLaxEmail
  val developerCollaboratorEmail = "developer@example.com".toLaxEmail

  val developerCollaborator     = Collaborators.Developer(UserId.random, developerCollaboratorEmail)
  val administratorCollaborator = Collaborators.Administrator(UserId.random, adminCollaboratorEmail)

  val boxId          = UUID.randomUUID().toString
  val boxName        = "BoxName2"
  val oldCallbackUrl = "http://old.com/boo"
  val newCallbackUrl = "http://new.com/yey"

  val aRedirectUri: RedirectUri        = RedirectUri.unsafeApply("http://localhost:8000/some")
  val toChangeRedirectUri: RedirectUri = RedirectUri.unsafeApply("http://localhost:8000/different")

}

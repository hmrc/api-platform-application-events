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

import play.api.libs.json.Json

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.LoginRedirectUriDeleted
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class LoginRedirectUriDeletedSpec extends EventSpec {

  "LoginRedirectUriAdded" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = LoginRedirectUriDeleted(anEventId, anAppId, anInstant, appCollaborator, toChangeRedirectUri)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"deletedRedirectUri":"${toChangeRedirectUri.uri}","eventType":"REDIRECT_URI_DELETED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[LoginRedirectUriDeleted]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display LoginRedirectUriDeleted correctly" in {
      testDisplay(
        event,
        EventTags.REDIRECT_URIS,
        "Login Redirect URI deleted",
        List(s"Removed Uri:", toChangeRedirectUri.uri)
      )
    }
  }

}

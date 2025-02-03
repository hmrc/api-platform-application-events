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

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents.PostLogoutRedirectUriChanged
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{ApplicationEvent, EventSpec, EventTags}

class PostLogoutRedirectUriChangedSpec extends EventSpec {

  "PostLogoutRedirectUriChanged" should {
    import EventsInterServiceCallJsonFormatters._

    val event: ApplicationEvent = PostLogoutRedirectUriChanged(anEventId, anAppId, anInstant, appCollaborator, toChangePostLogoutRedirectUri, aPostLogoutRedirectUri)

    val jsonText =
      raw"""{"id":"$anEventId","applicationId":"$appIdText","eventDateTime":"$instantText","actor":{"email":"${appCollaborator.email}","actorType":"COLLABORATOR"},"oldRedirectUri":"${toChangePostLogoutRedirectUri.uri}","newRedirectUri":"${aPostLogoutRedirectUri.uri}","eventType":"POST_LOGOUT_REDIRECT_URI_CHANGED"}"""

    "convert from json" in {

      val evt = Json.parse(jsonText).as[ApplicationEvent]

      evt shouldBe a[PostLogoutRedirectUriChanged]
    }

    "convert to correctJson" in {

      val eventJSonString = Json.toJson(event).toString()
      eventJSonString shouldBe jsonText
    }

    "display PostLogoutRedirectUriChanged correctly" in {
      testDisplay(
        event,
        EventTags.REDIRECT_URIS,
        "Post Logout Redirect URI changed",
        List(s"Original:", aPostLogoutRedirectUri.uri, "Replaced with:", toChangePostLogoutRedirectUri.uri)
      )
    }
  }

}

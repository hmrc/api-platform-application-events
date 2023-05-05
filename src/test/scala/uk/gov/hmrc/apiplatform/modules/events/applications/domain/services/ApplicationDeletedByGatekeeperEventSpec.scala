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
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventSpec
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventTags

class ApplicationDeletedByGatekeeperEventSpec extends EventSpec {

    "ApplicationDeletedByGatekeeper" should {
        import EventsInterServiceCallJsonFormatters._

        val applicationDeletedByGatekeeper: ApplicationEvent = ApplicationDeletedByGatekeeper(anEventId, anAppId, anInstant, gkCollaborator, aClientId, "bob", reasons, requestingEmail)

        val jsonText = raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"user":"someUser"},"clientId":"${aClientId.value}","wso2ApplicationName":"bob","reasons":"$reasons","requestingAdminEmail":"${requestingEmail.text}","eventType":"APPLICATION_DELETED_BY_GATEKEEPER"}"""

        "convert from json" in {
            val evt = Json.parse(jsonText).as[ApplicationEvent]

            evt shouldBe a[ApplicationDeletedByGatekeeper]
        }

        "convert to correctJson" in {

            val eventJSonString = Json.toJson(applicationDeletedByGatekeeper).toString()
            eventJSonString shouldBe jsonText
        }
        "display ApplicationDeletedByGatekeeper correctly" in {
            testDisplay(applicationDeletedByGatekeeper, EventTags.APP_LIFECYCLE, "Deleted", List(reasons, requestingEmail.text))
        }
    }
  
}

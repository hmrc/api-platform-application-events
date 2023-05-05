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
import uk.gov.hmrc.apiplatform.modules.applications.domain.models.Collaborator
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvent
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.ApplicationEvents._
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventSpec
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.EventTags

class CollaboratorRemovedV2EventSpec extends EventSpec {

    "CollaboratorRemovedV2" should {
        import EventsInterServiceCallJsonFormatters._

    

        val collaboratorRemovedV2: ApplicationEvent = CollaboratorRemovedV2(anEventId, anAppId, anInstant, appCollaborator, developerCollaborator)

        //val jsonText = raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"$instantText","actor":{"email":"bob@example.com"},"clientSecretId":"someClientId","clientSecretName":"someClientSecretName","eventType":"CLIENT_SECRET_REMOVED_V2"}"""
     val jsonText = raw"""{"id":"${anEventId.value}","applicationId":"${anAppId.value}","eventDateTime":"${instantText}","actor":{"email":"bob@example.com","actorType":"COLLABORATOR"},"collaborator":{"userId":"${developerCollaborator.userId.value.toString()}","emailAddress":"${developerCollaborator.emailAddress.text}","role":"DEVELOPER"},"eventType":"COLLABORATOR_REMOVED"}"""
        "convert from json" in {

            val evt = Json.parse(jsonText).as[ApplicationEvent]

            evt shouldBe a[CollaboratorRemovedV2]
        }

        "convert to correctJson" in {

            val eventJSonString = Json.toJson(collaboratorRemovedV2).toString()
            eventJSonString shouldBe jsonText
        }

        "display CollaboratorRemovedV2 correctly" in {
            testDisplay(collaboratorRemovedV2, EventTags.TEAM_MEMBER, "Collaborator Removed", List(s"${developerCollaborator.emailAddress.text} was removed as a ${Collaborator.describeRole(developerCollaborator)}"))
        }
    }
  
}

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
import uk.gov.hmrc.play.json.Union
import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{EventTag, EventTags}

trait EventTagJsonFormatters {
  implicit val eventTagAppNameJf = Json.format[EventTags.APP_NAME.type]
  implicit val eventTagClientSecretJf = Json.format[EventTags.CLIENT_SECRET.type]
  implicit val eventTagCollaboratorJf = Json.format[EventTags.COLLABORATOR.type]
  implicit val eventTagPolicyLocationJf = Json.format[EventTags.POLICY_LOCATION.type]
  implicit val eventTagPpnsCallbackJf = Json.format[EventTags.PPNS_CALLBACK.type]
  implicit val eventTagRedirectUrisJf = Json.format[EventTags.REDIRECT_URIS.type]
  implicit val eventTagSubscriptionJf = Json.format[EventTags.SUBSCRIPTION.type]
  implicit val eventTagToUJf = Json.format[EventTags.TERMS_OF_USE.type]

  implicit val formatEventTag: OFormat[EventTag] = Union.from[EventTag]("eventTag")
  .and[EventTags.APP_NAME.type](EventTags.APP_NAME.toString)
  .and[EventTags.CLIENT_SECRET.type](EventTags.CLIENT_SECRET.toString)
    .and[EventTags.COLLABORATOR.type](EventTags.COLLABORATOR.toString)
    .and[EventTags.POLICY_LOCATION.type](EventTags.POLICY_LOCATION.toString)
    .and[EventTags.PPNS_CALLBACK.type](EventTags.PPNS_CALLBACK.toString)
    .and[EventTags.REDIRECT_URIS.type](EventTags.REDIRECT_URIS.toString)
    .and[EventTags.SUBSCRIPTION.type](EventTags.SUBSCRIPTION.toString)
    .and[EventTags.TERMS_OF_USE.type](EventTags.TERMS_OF_USE.toString)
    .format
}

object EventTagJsonFormatters extends EventTagJsonFormatters
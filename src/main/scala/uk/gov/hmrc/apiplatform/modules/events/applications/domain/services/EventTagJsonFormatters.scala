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

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.events.applications.domain.models.{EventTag, EventTags}

trait EventTagJsonFormatters {

  implicit val formatEventTag2: Format[EventTag] = new Format[EventTag] {

    override def writes(o: EventTag): JsValue = Json.obj("description" -> o.description, "type" -> o.toString)

    override def reads(json: JsValue): JsResult[EventTag] = {
      (json match {
        case JsString(text) => EventTags.fromString(text)
        case JsObject(obj)  => obj.get("type").flatMap(_ match {
            case JsString(t) => EventTags.fromString(t)
            case _           => None
          })
        case _              => None
      })
        .fold[JsResult[EventTag]](JsError(s"Cannot find event tag from $json"))(JsSuccess(_))
    }

  }
}

object EventTagJsonFormatters extends EventTagJsonFormatters

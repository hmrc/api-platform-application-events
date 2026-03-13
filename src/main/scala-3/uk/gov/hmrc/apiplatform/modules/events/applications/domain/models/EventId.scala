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

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.models

import java.util.UUID
import scala.util.control.Exception.*

opaque type EventId = UUID

object EventId {

  extension (e: EventId) {
    def value: UUID = e
  }

  def apply(raw: UUID): EventId           = raw
  def apply(raw: String): Option[EventId] = allCatch.opt(UUID.fromString(raw))

  def unsafeApply(raw: String): EventId = UUID.fromString(raw)

  def random: EventId = EventId(UUID.randomUUID())

  import play.api.libs.json.*
  given Format[EventId] = Format(Reads.UUIDReader(true), Writes.UuidWrites)
}

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

package uk.gov.hmrc.apiplatform.common.utils

import java.time.temporal.ChronoUnit
import java.time.{Clock, Instant, LocalDateTime, ZoneOffset}

trait FixedClock {

  val utc = ZoneOffset.UTC

  val clock = Clock.fixed(Instant.ofEpochMilli(1650878658447L), utc)

  def clockMinusHours(hours: Long) = {
    val newInstant = LocalDateTime
      .ofInstant(clock.instant(), utc)
      .minusHours(hours)
      .toInstant(utc)
    Clock.fixed(newInstant, utc)
  }
}

object FixedClock extends FixedClock {
  val now = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS)

  val instant = now.toInstant(ZoneOffset.UTC)
}

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class EventDisplayFormattersSpec extends AnyWordSpec with Matchers {
  "EventDisplayFormatters" should {
    "display correctly" in {
      EventToDisplay.display(evt)
    }
  }
}

package uk.gov.hmrc.apiplatform.modules.events.applications.domain.services

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

trait JsonFormattersSpec extends AnyWordSpec with Matchers {

  def testToJson[T](in: T)(fields: (String, String)*)(implicit wrt: Writes[T]) = {
    val f: Seq[(String, JsValue)] = fields.map { case (k, v) => (k -> JsString(v)) }
    Json.toJson(in) shouldBe JsObject(f)
  }

  def testFromJson[T](text: String)(expected: T)(implicit rdr: Reads[T]) = {
    Json.parse(text).validate[T] shouldBe JsSuccess(expected)
  }
}
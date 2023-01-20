/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.apiplatform.modules.applications.domain.models

import uk.gov.hmrc.apiplatform.common.utils.EnumJson

object Role extends Enumeration {
  type Role = Value
  val DEVELOPER, ADMINISTRATOR = Value

  implicit val formatRole = EnumJson.enumFormat(Role)
}

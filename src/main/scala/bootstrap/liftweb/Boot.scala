package bootstrap.liftweb

import com.griya.labs.simple.api.{CategoryRestApi, ProductRestApi}
import com.sun.xml.internal.bind.v2.model.core.ErrorHandler
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules

class Boot extends Loggable {
  def boot {
    LiftRules.dispatch.append(CategoryRestApi)
    LiftRules.dispatch.append(ProductRestApi)
    logger.info("Run")
  }
}


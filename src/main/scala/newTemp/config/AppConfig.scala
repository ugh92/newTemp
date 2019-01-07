package newTemp.config
import java.net.URL

import newTemp.config.Environment.Environment

class AppConfig(environment: Environment) extends ServiceConfig {
  override protected def env: Environment = environment

  lazy val authLoginStubUrl = new URL(baseUrl("auth-login-stub"))

}

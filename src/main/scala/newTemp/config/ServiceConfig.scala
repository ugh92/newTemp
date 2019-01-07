package newTemp.config

import java.io.File

import com.typesafe.config.ConfigFactory
import newTemp.config.Environment._
import play.api.Configuration

trait ServiceConfig {

  protected lazy val rootServices = "microservice.services"

  protected def env: Environment

  protected def runEnvironmentConfiguration: Configuration = Configuration(
    ConfigFactory.load("application")
  )

  protected lazy val defaultProtocol: String = {
    runEnvironmentConfiguration.get[String](s"$rootServices.protocol")
  }

  def baseUrl(serviceName: String): String = {
    lazy val protocol = getConfString(s"$serviceName.protocol", defaultProtocol)
    lazy val host = getConfString(s"$serviceName.host", throw new RuntimeException(s"Could not find config $serviceName.host"))
    lazy val port = getConfInt(s"$serviceName.port", throw new RuntimeException(s"Could not find config $serviceName.port"))

    env match {
      case Local => s"$protocol://$host:$port"
      case Dev => "https://www.development.tax.service.gov.uk"
      case Qa => "https://www.qa.tax.service.gov.uk"
      case Staging => "https://www.staging.tax.service.gov.uk"
      case _ => throw new IllegalArgumentException(s"Environment '$env' not known")
    }
  }

  def getConfString(confKey: String, defString: => String): String = {
    runEnvironmentConfiguration.get[String](s"$rootServices.$confKey")
  }

  def getConfInt(confKey: String, defInt: => Int): Int = {
    runEnvironmentConfiguration.get[Int](s"$rootServices.$confKey")
  }

}

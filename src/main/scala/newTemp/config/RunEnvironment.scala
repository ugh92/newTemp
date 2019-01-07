package newTemp.config

import newTemp.config.Environment.Environment

object RunEnvironment {

  private val envProperty: String = Option(System.getProperty("environment")).getOrElse("local").toLowerCase

  lazy val env: Environment = {
    envProperty match {
      case "local" => Environment.Local
      case "dev" => Environment.Dev
      case "qa" => Environment.Qa
      case "staging" => Environment.Staging
      case _ => throw new IllegalArgumentException(s"Environment '$envProperty' not known")
    }
  }
}

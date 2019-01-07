package newTemp.pages

import cucumber.api.scala.{EN, ScalaDsl}
import newTemp.config.{AppConfig, RunEnvironment, SingletonDriver}
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers
import org.scalatest.selenium.{Page, WebBrowser}

trait WebPage extends Page with WebBrowser with Matchers with ScalaDsl with EN {

  implicit val driver: WebDriver = SingletonDriver.getInstance()

  protected def config: AppConfig = new AppConfig(RunEnvironment.env)

  override val url: String = ""

}

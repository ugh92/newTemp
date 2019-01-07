package newTemp.config

import java.net.URL

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxOptions}
import org.openqa.selenium.remote.{BrowserType, DesiredCapabilities, LocalFileDetector, RemoteWebDriver}

import scala.collection.JavaConverters._

object SingletonDriver {

  private val systemProperties = System.getProperties
  private var instanceOption: Option[WebDriver] = None
  private var baseWindowHandleOption : Option[String] = None

  def getInstance(): WebDriver = instanceOption getOrElse initialiseBrowser()

  private def initialiseBrowser(): WebDriver = {
    val instance = newWebDriver()
    baseWindowHandleOption = Some(instance.getWindowHandle)
    instanceOption = Some(instance)
    instance
  }


  def newWebDriver(): WebDriver = {
    val selectedDriver = Option(systemProperties.getProperty("browser", "chrome")).map(_.toLowerCase) match {
      case Some("firefox") ⇒ createFirefoxDriver()
      case Some("chrome") ⇒ createChromeDriver(false)
      case Some("remote-chrome") => createRemoteChrome()
      case Some("headless") ⇒ createChromeDriver(true)
      case Some("zapChrome") => createZapChromeDriver()
      case Some(other) ⇒ throw new IllegalArgumentException(s"Unrecognised browser: $other")
      case None ⇒ throw new IllegalArgumentException("No browser set")
    }
    selectedDriver
  }

  private val isJsEnabled: Boolean = true

  private def createFirefoxDriver(): WebDriver = {

    val capabilities = DesiredCapabilities.firefox()
    val options = new FirefoxOptions()
    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setBrowserName(BrowserType.FIREFOX)

    options.merge(capabilities)
    new FirefoxDriver(options)
  }

  private def createChromeDriver(headless: Boolean): WebDriver = {

    val downloadFilePath = "."
    val capabilities = DesiredCapabilities.chrome()
    val options: ChromeOptions = new ChromeOptions()
    options.addArguments("test-type")
    options.addArguments("--disable-gpu")
    options.addArguments("start-maximized")
    options.setExperimentalOption("prefs", Map(
      "download.default_directory" -> downloadFilePath,
      "download.prompt_for_download" -> "false").asJava)

    if (headless) options.addArguments("--headless")

    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setCapability(ChromeOptions.CAPABILITY, options)

    options.merge(capabilities)
    new ChromeDriver(options)
  }

  private def createRemoteChrome(): WebDriver = {
    val options: ChromeOptions = new ChromeOptions()
    if(RunEnvironment.env.equals(Environment.Qa)) {
      val squidProxyConnectionString: String = sys.env("SQUID_PROXY_CONNECTION_STRING")
      options.addArguments(s"--proxy-server=$squidProxyConnectionString")
    }
    val newRemote = new RemoteWebDriver(new URL(s"http://localhost:4444/wd/hub"), options)
    newRemote.setFileDetector(new LocalFileDetector)
    newRemote
  }

  private def createZapChromeDriver(): WebDriver = {
    val capabilities = DesiredCapabilities.chrome()
    val options: ChromeOptions = new ChromeOptions()
    options.addArguments("test-type")
    options.addArguments("--proxy-server=http://localhost:11000")

    capabilities.setCapability(ChromeOptions.CAPABILITY, options)

    options.merge(capabilities)
    new ChromeDriver(options)
  }

}

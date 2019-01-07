package newTemp.runner

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("src/test/resources/features"),
  glue = Array("newTemp.stepdefs"),
  plugin = Array("pretty", "html:target/cucumber", "json:target/cucumber.json")
)
class Runner {

}

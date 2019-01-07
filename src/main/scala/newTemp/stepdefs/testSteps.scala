package newTemp.stepdefs

import newTemp.pages.{TestPage, WebPage}

class testSteps extends WebPage {

  Given("""^navigate to stub$""") { () =>
    go to TestPage
  }

}

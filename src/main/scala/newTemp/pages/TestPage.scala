package newTemp.pages

object TestPage extends WebPage {

  val relativeUrl: String = "/auth-login-stub/gg-sign-in"
  override val url: String = s"${config.authLoginStubUrl}$relativeUrl"

}

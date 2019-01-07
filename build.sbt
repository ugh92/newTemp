name := "newTemp"

version := "0.1"

scalaVersion := "2.11.8"

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers += "hmrc-releases" at "https://artefacts.tax.service.gov.uk/artifactory/hmrc-releases/"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5",
  "org.seleniumhq.selenium" % "selenium-java" % "3.14.0",
  "info.cukes" % "cucumber-junit" % "1.2.5",
  "info.cukes" % "cucumber-picocontainer" % "1.2.5",
  "com.typesafe.play" %% "play-test" % "2.6.13",
  "info.cukes" %% "cucumber-scala" % "1.2.5"
)
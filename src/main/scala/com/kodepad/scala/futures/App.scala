package com.kodepad.scala.futures

import com.kodepad.scala.futures.cicd.Cicd._
import com.kodepad.scala.futures.log.Logger

object App {
  def main(args: Array[String]): Unit = {
    Logger.info("Starting deployment")
    val code = codeCheckout()
    val jar = runBuilds(code)
    val resource = copyResources(code)
    val unitTests = runUnitTests(jar)(2)
    val integrationTests = runIntegrationTests(jar)(4)
    val coverageReport = testCoverageReport(unitTests, integrationTests)
    val tarball = createTarball(jar, resource, coverageReport)
    deploy(tarball)
  }
}

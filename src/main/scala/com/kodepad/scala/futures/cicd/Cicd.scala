package com.kodepad.scala.futures.cicd

import com.kodepad.scala.futures.log.Logger
import com.kodepad.scala.futures.vo._

object Cicd {
  def codeCheckout(): Code = {
    Thread.sleep(10_000)
    Logger.info("codeCheckout completed!")
    Code()
  }

  def copyResources(code: Code): Resource = {
    Thread.sleep(10_000)
    Logger.info("copyResources completed!")
    Resource(code)
  }

  def runBuilds(code: Code): Jar = {
    Thread.sleep(30_000)
    Logger.info("runBuilds completed!")
    Jar(code)
  }

  def runIntegrationTests(jar: Jar)(testCount: Int): Seq[IntegrationTest] = {
    val integrationTests = (1 to testCount).map(test =>{
      Thread.sleep(60_000)
      Logger.info(s"runIntegrationTest ${test} completed!")
      IntegrationTest(jar)
    })

    integrationTests
  }

  def runUnitTests(jar: Jar)(testCount: Int): Seq[UnitTest] = {
    val unitTests = (1 to testCount).map(test => {
      Thread.sleep(20_000)
      Logger.info(s"runUnitTest ${test} completed!")
      UnitTest(jar)
    })

    unitTests
  }

  def testCoverageReport(unitTests: Seq[UnitTest], integrationTests: Seq[IntegrationTest]): CoverageReport = {
    Thread.sleep(5_000)
    Logger.info("testCoverageReport completed!")
    CoverageReport(unitTests, integrationTests)
  }

  def createTarball(jar: Jar, resource: Resource, coverageReport: CoverageReport): Tarball = {
    Thread.sleep(15_000)
    Logger.info("createTarball completed!")
    Tarball(jar, resource, coverageReport)
  }

  def deploy(tarball: Tarball): Unit = {
    Thread.sleep(20_000)
    Logger.info("deployment completed!")
  }
}

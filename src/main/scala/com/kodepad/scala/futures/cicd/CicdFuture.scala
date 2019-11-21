package com.kodepad.scala.futures.cicd

import com.kodepad.scala.futures.log.Logger
import com.kodepad.scala.futures.vo._

import scala.concurrent.{ExecutionContext, Future}

object CicdFuture {
  def codeCheckout()(implicit executionContext: ExecutionContext): Future[Code] = Future {
    Thread.sleep(10_000)
    Logger.info("codeCheckout completed!")
    Code()
  }

  def copyResources(codeFuture: Future[Code])(implicit executionContext: ExecutionContext): Future[Resource] = {
    for(
      code <- codeFuture
    ) yield {
      Thread.sleep(10_000)
      Logger.info("copyResources completed!")
      Resource(code)
    }
  }

  def runBuilds(codeFuture: Future[Code])(implicit executionContext: ExecutionContext): Future[Jar] = {
    for(
      code <- codeFuture
    ) yield {
      Thread.sleep(30_000)
      Logger.info("runBuilds completed!")
      Jar(code)
    }
  }

  def runIntegrationTests(jarFuture: Future[Jar])(testCount: Int)(implicit executionContext: ExecutionContext): Seq[Future[IntegrationTest]] = {
    val integrationTestFutures = (1 to testCount).map(test => {
      for (
        jar <- jarFuture
      ) yield {
        Thread.sleep(60_000)
        Logger.info(s"runIntegrationTest ${test} completed!")
        IntegrationTest(jar)
      }
    })

    integrationTestFutures
  }

  def runUnitTests(jarFuture: Future[Jar])(testCount: Int)(implicit executionContext: ExecutionContext): Seq[Future[UnitTest]] = {
    val unitTestFutures = (1 to testCount).map(test => {
      for (
        jar <- jarFuture
      ) yield {
        Thread.sleep(20_000)
        Logger.info(s"runUnitTest ${test} completed!")
        UnitTest(jar)
      }
    })

    unitTestFutures
  }

  def testCoverageReport(unitTestFutures: Seq[Future[UnitTest]], integrationTestFutures: Seq[Future[IntegrationTest]])(implicit executionContext: ExecutionContext): Future[CoverageReport] = {
    val unitTestsFuture = Future.sequence(unitTestFutures)
    val integrationTestsFuture = Future.sequence(integrationTestFutures)
    for (
      unitTests <- unitTestsFuture;
      integrationTests <- integrationTestsFuture
    ) yield {
      Thread.sleep(5_000)
      Logger.info("testCoverageReport completed!")
      CoverageReport(unitTests, integrationTests)
    }
  }

  def createTarball(jarFuture: Future[Jar], resourceFuture: Future[Resource], coverageReportFuture: Future[CoverageReport])(implicit executionContext: ExecutionContext): Future[Tarball] = {
    for (
      jar <- jarFuture;
      resource <- resourceFuture;
      coverageReport <- coverageReportFuture
    ) yield {
      Thread.sleep(15_000)
      Logger.info("createTarball completed!")
      Tarball(jar, resource, coverageReport)
    }
  }

  def deploy(tarballFuture: Future[Tarball])(implicit executionContext: ExecutionContext): Future[Unit] = {
    for(
      tarball <- tarballFuture
    ) yield {
      Thread.sleep(20_000)
      Logger.info("deployment completed!")
    }
  }

  def apply()(implicit executionContext: ExecutionContext): Unit = {
  }
}

package com.kodepad.scala.futures

import java.util.concurrent.Executors

import com.kodepad.scala.futures.cicd.CicdFuture._
import com.kodepad.scala.futures.log.Logger

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object FutureApp {
  def main(args: Array[String]): Unit = {
    val workers = if(args.length > 0) args(0).toInt else 1
    val executorService = Executors.newFixedThreadPool(workers)
    implicit val executionContext = ExecutionContext.fromExecutorService(executorService)

    Logger.info("Starting deployment")
    val code = codeCheckout()
    val jar = runBuilds(code)
    val resource = copyResources(code)
    val unitTests = runUnitTests(jar)(2)
    val integrationTests = runIntegrationTests(jar)(4)
    val coverageReport = testCoverageReport(unitTests, integrationTests)
    val tarball = createTarball(jar, resource, coverageReport)
    val deployment = deploy(tarball)

    Await.ready(deployment, Duration.Inf)
    executorService.shutdown()
  }
}

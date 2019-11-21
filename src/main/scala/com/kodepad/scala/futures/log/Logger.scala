package com.kodepad.scala.futures.log

import java.time.LocalDateTime

object Logger {
  private def log(msg: String): Unit = {
    val currentTimestamp = LocalDateTime.now()
    val threadName = Thread.currentThread.getName
    println(s"[${currentTimestamp}] [${threadName}] ${msg}")
  }

  def info(msg: String): Unit = {
    log(s"[INFO] ${msg}")
  }

  def debug(msg: String): Unit = {
    log(s"[DEBUG] ${msg}")
  }
}

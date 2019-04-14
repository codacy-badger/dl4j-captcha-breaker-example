package com.datart.captcha_breaker.examples.dl4j.input

import scala.util.Try

trait InputArgsParser {
  def parseInputArgs(args: Array[String]): (String, Int)
}

object InputArgsParserImpl {
  private val defaultNumEpochs    = 400
  private val defaultResourcesDir = "./src/main/resources"

  def parseInputArgs(args: Array[String]): (Int, String) = {
    args.toList match {
      case nEpochs :: Nil =>
        (parseNumberOfEpochs(nEpochs), defaultResourcesDir)
      case nEpochs :: resDir :: _ =>
        (parseNumberOfEpochs(nEpochs), resDir)
      case _ =>
        (defaultNumEpochs, defaultResourcesDir)
    }
  }

  private def parseNumberOfEpochs(nEpochs: String): Int = {
    Try(nEpochs.toInt)
      .fold(
        _ => defaultNumEpochs, { parsedNumEpochs =>
          if (parsedNumEpochs > 0) {
            parsedNumEpochs
          } else {
            defaultNumEpochs
          }
        }
      )
  }
}

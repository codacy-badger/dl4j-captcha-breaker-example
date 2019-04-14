package com.datart.captcha_breaker.examples.dl4j

import java.io.File

import com.datart.captcha_breaker.examples.dl4j.breaker.CaptchaBreakerImpl
import com.datart.captcha_breaker.examples.dl4j.input.InputArgsParserImpl.parseInputArgs
import com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl
import com.typesafe.scalalogging.StrictLogging
import javax.imageio.ImageIO

object Main extends App with StrictLogging {

  val (numEpochs, resourcesDir) = parseInputArgs(args)

  logger.info(s"Run arguments established: resources dir - $resourcesDir, number of learning epochs: $numEpochs.")

  private val modelTrainer = new ModelTrainerImpl()
  private val (trainedModel, captchaTest) = modelTrainer.trainModel(
    directory = new File(s"$resourcesDir/letters"),
    numEpochs = numEpochs
  )
  private val captchaTestAccuracy = modelTrainer.accuracy(captchaTest, trainedModel)

  logger.info(s"Trained model accuracy on testing set is: $captchaTestAccuracy.")

  private val captchaBreaker = new CaptchaBreakerImpl(trainedModel)

  private val captchaImages = new File(
    s"$resourcesDir/captcha_images"
  ).listFiles().toSeq

  private val inputSize =
    captchaImages.size

  val (captchaAccuracy, solvedCorrectlyCounter) = checkAccuracyOnCaptchaImages

  logger.info(
    s"Trained model accuracy on testing set of captcha images (5 alphanums) is: $captchaAccuracy ($solvedCorrectlyCounter/$inputSize correctly predicted).")

  private def checkAccuracyOnCaptchaImages: (Double, Int) = {
    val extensionLength = 4
    val solvedCorrectlyCounter =
      captchaImages
        .map(file => (file, ImageIO.read(file)))
        .map {
          case (file, captchaImage) =>
            (file.getName.dropRight(extensionLength), captchaBreaker.breakCaptcha(captchaImage))
        }
        .count {
          case (expected, predicted) =>
            expected.contains(predicted)
        }

    (solvedCorrectlyCounter * 1.0 / inputSize, solvedCorrectlyCounter)
  }
}

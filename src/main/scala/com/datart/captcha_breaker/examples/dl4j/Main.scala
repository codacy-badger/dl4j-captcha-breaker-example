package com.datart.captcha_breaker.examples.dl4j

import com.typesafe.scalalogging.StrictLogging
import better.files._
import com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl

object Main extends App with StrictLogging {
  private val modelTrainer = new ModelTrainerImpl()
  private val (trainedModel, captchaTest) = modelTrainer.trainModel(File(Resource.getUrl("letters")).toJava)
  private val captchaTestAccuracy = modelTrainer.accuracy(captchaTest, trainedModel)

  logger.info(s"Trained model accuracy on testing set is: $captchaTestAccuracy.")
}

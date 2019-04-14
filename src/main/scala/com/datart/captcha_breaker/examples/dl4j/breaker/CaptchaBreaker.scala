package com.datart.captcha_breaker.examples.dl4j.breaker

//scalastyle:off illegal.imports
import java.awt.image.BufferedImage
//scalastyle:on
import org.datavec.image.loader.ImageLoader
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.cpu.nativecpu.NDArray
import org.nd4j.linalg.dataset.DataSet

import scala.collection.JavaConverters._

trait CaptchaBreaker {
  def breakCaptcha(captchaImage: BufferedImage): String
}

class CaptchaBreakerImpl(trainedModel: MultiLayerNetwork) extends CaptchaBreaker {
  private val AllowedChars = "2346789ACDEFGHJKLNPQRTUVXYZ"

  private val labels                = AllowedChars.split("").toList.asJava
  private val lettersInCpatchaCount = 5
  private val height                = 32L
  private val width                 = 31L
  private val imageLoader           = new ImageLoader(height, width, 1)

  override def breakCaptcha(captchaImage: BufferedImage): String = {
    val imageDataSets = (1 to lettersInCpatchaCount)
      .map(getSubimage(lettersInCpatchaCount, captchaImage, _))
      .map(i => new DataSet(imageLoader.asRowVector(i), new NDArray()))

    imageDataSets
      .flatMap { ds =>
        ds.setLabelNames(labels)
        trainedModel
          .predict(ds)
          .asScala
          .toList
      }
      .take(lettersInCpatchaCount)
      .mkString
  }

  private def getSubimage(imagesCount: Int, stringImage: BufferedImage, idx: Int): BufferedImage = {
    val subimageWidth  = stringImage.getWidth / imagesCount
    val subimageHeight = stringImage.getHeight

    stringImage.getSubimage((idx - 1) * subimageWidth, 0, subimageWidth, subimageHeight)
  }
}

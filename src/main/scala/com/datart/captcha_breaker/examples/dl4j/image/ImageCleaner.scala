package com.datart.captcha_breaker.examples.dl4j.image

// scalastyle:off illegal.imports
import java.awt.Color
import java.awt.image.{BufferedImage, BufferedImageOp}

import com.jhlabs.image.{GaussianFilter, PointFilter, ReduceNoiseFilter, ThresholdFilter, UnsharpFilter}
//scalastyle:on

trait ImageCleaner {
  def clean(image: BufferedImage): BufferedImage
}

@SuppressWarnings(
  Array(
    "org.wartremover.warts.Null",
    "org.wartremover.warts.Equals",
    "org.wartremover.warts.Overloading",
    "org.wartremover.warts.Var",
    "org.wartremover.warts.While"
  )
)
object ImageCleanerImpl extends ImageCleaner {

  // scalastyle:off method.length magic.number null
  def clean(image: BufferedImage): BufferedImage = {
    val colorsToReplace =
      Seq(new Color(197, 197, 197), new Color(209, 213, 217))
    val replaceSimilarColor = new ReplaceSimilarColorFilter(colorsToReplace, new Color(255, 255, 255), 40)

    image
      .transform(new GaussianFilter)
      .transform(new UnsharpFilter)
      .transform(new ReduceNoiseFilter)
      .transform(replaceSimilarColor)
      .transform(new ThresholdFilter(190))
  }

  private implicit class BufferedImageOps(src: BufferedImage) {
    def transform(op: BufferedImageOp): BufferedImage = {
      op.filter(src, null)
    }
  }
  // scalastyle:on

  private class ReplaceSimilarColorFilter(oldColors: Seq[Color], newColor: Color, var threshold: Int)
      extends PointFilter {
    def this(oldColor: Color, newColor: Color, threshold: Int) =
      this(Seq(oldColor), newColor, threshold)

    private val newColorInt = newColor.getRGB

    def colorDistance(c1: Color, c2: Color): Double = {
      val rmean   = (c1.getRed + c2.getRed) / 2
      val r       = c1.getRed - c2.getRed
      val g       = c1.getGreen - c2.getGreen
      val b       = c1.getBlue - c2.getBlue
      val weightR = 2 + rmean / 256
      val weightG = 4.0
      val weightB = 2 + (255 - rmean) / 256
      Math.sqrt(weightR * r * r + weightG * g * g + weightB * b * b)
    }

    def filterRGB(x: Int, y: Int, rgb: Int): Int = {
      val currentColor  = new Color(rgb)
      var colorToReturn = rgb
      val it            = oldColors.iterator
      while (it.hasNext && colorToReturn == rgb) {
        if (colorDistance(currentColor, it.next) <= threshold) {
          colorToReturn = newColorInt
        }
      }
      colorToReturn
    }
  }
}

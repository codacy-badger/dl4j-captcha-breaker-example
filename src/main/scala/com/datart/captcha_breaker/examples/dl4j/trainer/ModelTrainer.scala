package com.datart.captcha_breaker.examples.dl4j.trainer

import java.io.{File => JFile}
import java.util.Random

import better.files._
import com.datart.captcha_breaker.examples.dl4j.image.ImageCleanerImpl.clean
import com.typesafe.scalalogging.StrictLogging
import javax.imageio.ImageIO
import org.datavec.api.io.filters.BalancedPathFilter
import org.datavec.api.io.labels._
import org.datavec.api.split.FileSplit
import org.datavec.image.recordreader._
import org.deeplearning4j.datasets.datavec._
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.inputs.InputType
import org.deeplearning4j.nn.conf.layers._
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.evaluation.classification.Evaluation
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler
import org.nd4j.linalg.learning.config.Sgd
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction

import scala.collection.JavaConverters._

trait ModelTrainer {
  def trainModel(directory: JFile, numEpochs: Int): (MultiLayerNetwork, RecordReaderDataSetIterator)
  def accuracy(dataSet: DataSetIterator, model: MultiLayerNetwork): Double
}

class ModelTrainerImpl extends ModelTrainer with StrictLogging {
  private val seed                  = 1L // for reproducibility
  private val width                 = 31L
  private val height                = 32L
  private val numInputs             = width * height
  private val numHidden             = 512 // size (number of neurons) of our hidden layer
  private val numOutputs            = 27 // chars from "2346789ACDEFGHJKLNPQRTUVXYZ"
  private val learningRate          = 0.01
  private val batchSize             = 128
  private val learningSetPercentage = 90D
  private val testingSetPercentage  = 10D
  private val printEveryIteration   = 1000

  override def trainModel(directory: JFile, numEpochs: Int): (MultiLayerNetwork, RecordReaderDataSetIterator) = {

    val randomGenerator       = new Random(seed)
    val resourcesDir          = cleanInputFiles(directory)
    val filesInDir            = new FileSplit(resourcesDir, randomGenerator)
    val labelMaker            = new ParentPathLabelGenerator()
    val pathFilter            = new BalancedPathFilter(randomGenerator, Array("jpeg"), labelMaker)
    val filesInDirSplit       = filesInDir.sample(pathFilter, learningSetPercentage, testingSetPercentage)
    val (trainData, testData) = (filesInDirSplit(0), filesInDirSplit(1))
    val trainDataRecordReader = new ImageRecordReader(height, width, 1, labelMaker)
    val testDataRecordReader  = new ImageRecordReader(height, width, 1, labelMaker)

    trainDataRecordReader.initialize(trainData)
    testDataRecordReader.initialize(testData)

    val captchaTrain = new RecordReaderDataSetIterator(trainDataRecordReader, batchSize, 1, numOutputs)
    val captchaTest  = new RecordReaderDataSetIterator(testDataRecordReader, batchSize, 1, numOutputs)

    val trainScaler = new ImagePreProcessingScaler(0, 1)
    trainScaler.fit(captchaTrain)
    captchaTrain.setPreProcessor(trainScaler)

    val testScaler = new ImagePreProcessingScaler(0, 1)
    testScaler.fit(captchaTest)
    captchaTest.setPreProcessor(testScaler)

    // define the neural network architecture
    val conf = createModelConf

    val model = new MultiLayerNetwork(conf)
    model.init()
    model.setListeners(new ScoreIterationListener(printEveryIteration))

    logger.info("Starting learning model...")

    // train the model
    (0 until numEpochs).foreach { epochNumber =>
      logger.info(s"Epoch number: $epochNumber/$numEpochs.")
      model.fit(captchaTrain)
    }

    logger.info("Learning model finished.")

    (model, captchaTest)
  }

  private def cleanInputFiles(directory: JFile): JFile = {

    val tempDir = File.newTemporaryDirectory()
    directory.toScala.children.foreach { letterDir =>
      val tmpLetterDir = tempDir.createChild(letterDir.name, asDirectory = true)
      letterDir.children.foreach { imageFile =>
        val outputFile = tmpLetterDir.createChild(imageFile.name)
        ImageIO.write(clean(ImageIO.read(imageFile.toJava)), "jpeg", outputFile.toJava)
      }
    }
    tempDir.toJava
  }

  private def createModelConf = {
    new NeuralNetConfiguration.Builder()
      .seed(seed)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .updater(new Sgd(learningRate))
      .weightInit(WeightInit.XAVIER) // random initialization of our weights
      .list                          // builder for creating stacked layers
      .layer(0,
             new DenseLayer.Builder() // define the hidden layer
               .nIn(numInputs)
               .nOut(numHidden)
               .activation(Activation.RELU)
               .build())
      .layer(1,
             new OutputLayer.Builder(LossFunction.MCXENT) // define loss and output layer
               .nIn(numHidden)
               .nOut(numOutputs)
               .activation(Activation.SOFTMAX)
               .build())
      .setInputType(InputType.convolutional(height, width, 1))
      .build()
  }

  // evaluate model performance
  def accuracy(dataSet: DataSetIterator, model: MultiLayerNetwork): Double = {
    val evaluator = new Evaluation(numOutputs)
    dataSet.reset()
    for (dataSet <- dataSet.asScala) {
      val output = model.output(dataSet.getFeatures)
      evaluator.eval(dataSet.getLabels, output)
    }

    evaluator.accuracy()
  }
}

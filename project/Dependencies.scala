import sbt._

object Dependencies {
  private val betterFiles     = "com.github.pathikrit"        %% "better-files"         % VersionsOf.betterFiles
  private val dl4jCore        = "org.deeplearning4j"          %  "deeplearning4j-core"  % VersionsOf.dl4j
  private val janino          = "org.codehaus.janino"         %  "janino"               % VersionsOf.janino
  private val logbackClassic  = "ch.qos.logback"              %  "logback-classic"      % VersionsOf.logbackClassic
  private val nd4jNative      = "org.nd4j"                    %  "nd4j-native-platform" % VersionsOf.dl4j
  private val scalaLogging    = "com.typesafe.scala-logging"  %% "scala-logging"        % VersionsOf.scalaLogging
  private val scalatest       = "org.scalatest"               %% "scalatest"            % VersionsOf.scalatest      % Test

  val all: Seq[ModuleID] = Seq(
    betterFiles,
    dl4jCore,
    janino,
    logbackClassic,
    nd4jNative,
    scalaLogging,
    scalatest
  )
}

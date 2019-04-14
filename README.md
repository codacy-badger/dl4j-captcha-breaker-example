# dl4j-captcha-breaker-example [![Build Status](https://travis-ci.com/eltherion/dl4j-captcha-breaker-example.svg?branch=master)](https://travis-ci.com/eltherion/dl4j-captcha-breaker-example)
Quick example of breaking simple captcha images with DL4J.

## Running application

### Requirements

* Java JDK 8 or higher installed and available on search path
* SBT 1.0.0 or higher installed and available on search path
* Git client installed and available on search path
* At least 2GB RAM available for the application
* Cloned repository for this project:

```bash
git clone https://github.com/eltherion/dl4j-captcha-breaker-example.git
```

### Starting application

For the sake of the easier startup default resource directory and number of learning epochs are provided. Data set is a real life sample of letters and captcha images.
To run application navigate to cloned repository folder and execute command:

```bash
cd /path/to/cloned/repository #this is mandatory
sbt run
```

Then learning and verifying model will happen. Please, be patient, as the whole process may take few hours. You should see output similar to that:

```bash
dl4j-captcha-breaker-example$ sbt "run 400"
[info] Loading settings for project global-plugins from idea.sbt ...
[info] Loading global plugins from /home/mmurawski/.sbt/1.0/plugins
[info] Loading settings for project dl4j-captcha-breaker-example-build from plugins.sbt ...
[info] Loading project definition from dl4j-captcha-breaker-example/project
[info] Loading settings for project dl4j-captcha-breaker-example from build.sbt ...
[info] Set current project to dl4j-captcha-breaker-example (in build file:dl4j-captcha-breaker-example/)
[info] Compiling 5 Scala sources to dl4j-captcha-breaker-example/target/scala-2.12/classes ...
[info] [info] Cleaning datadir [dl4j-captcha-breaker-example/target/scala-2.12/scoverage-data]
[info] [info] Beginning coverage instrumentation
[info] [info] Instrumentation completed [224 statements]
[info] [info] Wrote instrumentation file [dl4j-captcha-breaker-example/target/scala-2.12/scoverage-data/scoverage.coverage.xml]
[info] [info] Will write measurement data to [dl4j-captcha-breaker-example/target/scala-2.12/scoverage-data]
[info] Done compiling.
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.google.protobuf.UnsafeUtil (file:/home/mmurawski/.sbt/boot/scala-2.12.7/org.scala-sbt/sbt/1.2.8/protobuf-java-3.3.1.jar) to field java.nio.Buffer.address
WARNING: Please consider reporting this to the maintainers of com.google.protobuf.UnsafeUtil
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
[info] Packaging dl4j-captcha-breaker-example/target/scala-2.12/dl4j-captcha-breaker-example_2.12-1.0.jar ...
[info] Done packaging.
[info] Running com.datart.captcha_breaker.examples.dl4j.Main 400
00:42:38.758 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.Main$ - Run arguments established: resources dir - ./src/main/resources, number of learning epochs: 400.
00:44:58.428 [run-main-0] INFO org.datavec.image.recordreader.BaseImageRecordReader - ImageRecordReader: 27 label classes inferred using label generator ParentPathLabelGenerator
00:44:58.432 [run-main-0] INFO org.datavec.image.recordreader.BaseImageRecordReader - ImageRecordReader: 27 label classes inferred using label generator ParentPathLabelGenerator
00:44:58.457 [run-main-0] INFO org.nd4j.linalg.factory.Nd4jBackend - Loaded [CpuBackend] backend
00:44:58.813 [run-main-0] INFO org.nd4j.nativeblas.NativeOpsHolder - Number of threads used for NativeOps: 6
00:44:58.854 [run-main-0] INFO org.nd4j.nativeblas.Nd4jBlas - Number of threads used for BLAS: 6
00:44:58.856 [run-main-0] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Backend used: [CPU]; OS: [Linux]
00:44:58.856 [run-main-0] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Cores: [12]; Memory: [1.8GB];
00:44:58.856 [run-main-0] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Blas vendor: [MKL]
00:44:58.947 [run-main-0] INFO org.deeplearning4j.nn.multilayer.MultiLayerNetwork - Starting MultiLayerNetwork with WorkspaceModes set to [training: ENABLED; inference: ENABLED], cacheMode set to [NONE]
00:44:59.004 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Starting learning model...
00:44:59.009 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 0/400.
00:44:59.163 [ADSI prefetch thread] DEBUG org.nd4j.linalg.memory.abstracts.Nd4jWorkspace - Steps: 5
00:44:59.291 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 0 is 3.5982189178466797
00:45:09.677 [run-main-0] DEBUG org.deeplearning4j.datasets.iterator.AsyncDataSetIterator - Manually destroying ADSI workspace
00:45:09.678 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 1/400.
[...]
01:22:55.103 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 399/400.
01:22:55.104 [ADSI prefetch thread] DEBUG org.nd4j.linalg.memory.abstracts.Nd4jWorkspace - Steps: 5
01:22:56.977 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 129000 is 0.016332276165485382
01:22:59.836 [run-main-0] DEBUG org.deeplearning4j.datasets.iterator.AsyncDataSetIterator - Manually destroying ADSI workspace
01:22:59.836 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Learning model finished.
01:23:00.467 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.Main$ - Trained model accuracy on testing set is: 0.9461873638344227.
01:23:16.964 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.Main$ - Trained model accuracy on testing set of captcha images (5 alphanums) is: 0.23 (230/1000 correctly predicted).
[success] Total time: 2454 s, completed Apr 15, 2019, 1:23:17 AM
```

#### Alternative arguments and input

1. You can change how long model will be trained by providing custom positive integer indicating number of learning epochs. Default value is 400.

```bash
sbt "run epochs_number"
```

2. You can also provide custom directory with your input letter images and testing captcha images, BUT, as this is really quick example, there are several things required (or hardcoded):

You have to provide number of epochs and after that path to resources directory. Default resources directory is */path/to/cloned/repository/src/main/resources*

* Inside your resources directory you have to have **letters** directory with subdirectories for each class (letter or digit) as described [here: https://deeplearning4j.org/cn/archieved/simple-image-load-transform](https://deeplearning4j.org/cn/archieved/simple-image-load-transform)
* Currently only captcha images with 5 signs are resolved
* Currently you may provide learning set which has exactly 27 classes (in provided data set classes are as follows ***2346789ACDEFGHJKLNPQRTUVXYZ***) with images of 31 x 32 pixels
* Inside your resources directory you have to have **captcha_images** directory with set of captcha images named ***ABCDE.jpg***, where ***ABCDE*** is a solution for captcha inside file.

```bash
sbt "run epochs_number /path/to/resources/directory"
```

Unmatching parameters configuration are, as possible, converted and set with default values.

### Final thoughts

This is not a production ready solution, only presentation of great tool solving captcha from real website. At least several things could be done to improve it:

* full customization of learning process, number of classes, dimension of letters and length of captcha by a config file
* cleaning code out of warts

Let it be your starting point if you find it helpful. :-)
# dl4j-captcha-breaker-example
Quick example of breaking simple captcha images with DL4J.

## Running application

### Requirements

* Java JDK 8 or higher installed and available on search path
* SBT 1.0.0 or higher installed and available on search path
* Git client installed and available on search path
* At least 8GB RAM available for the application
* Cloned repository for this project:

```bash
git clone https://github.com/eltherion/dl4j-captcha-breaker-example.git
```

### Starting application

For the sake of the easier startup default resource directory and number of learning epochs are provided. Data set is a real life sample of letters and captcha images.
To run application navigate to cloned repository folder and execute command:

```bash
cd /path/to/cloned/repository #this is mandatory
sbt -mem 8192 run
```

Then learning and verifying model will happen. Please, be patient, as the whole process may take few hours. You should see output similar to that:

```bash
dl4j-captcha-breaker-example$ sbt -mem 8192 run
[info] Loading settings for project global-plugins from idea.sbt ...
[info] Loading global plugins from /home/mmurawski/.sbt/1.0/plugins
[info] Loading settings for project dl4j-captcha-breaker-example-build from plugins.sbt ...
[info] Loading project definition from dl4j-captcha-breaker-example/project
[info] Loading settings for project dl4j-captcha-breaker-example from build.sbt ...
[info] Set current project to dl4j-captcha-breaker-example (in build file:dl4j-captcha-breaker-example/)
[info] Packaging dl4j-captcha-breaker-example/target/scala-2.12/dl4j-captcha-breaker-example_2.12-1.0.jar ...
[info] Done packaging.
[info] Running com.datart.captcha_breaker.examples.dl4j.Main 
12:53:23.772 [run-main-0] INFO org.datavec.image.recordreader.BaseImageRecordReader - ImageRecordReader: 27 label classes inferred using label generator ParentPathLabelGenerator
12:53:23.833 [run-main-0] INFO org.datavec.image.recordreader.BaseImageRecordReader - ImageRecordReader: 27 label classes inferred using label generator ParentPathLabelGenerator
12:53:23.850 [run-main-0] INFO org.nd4j.linalg.factory.Nd4jBackend - Loaded [CpuBackend] backend
12:53:24.160 [run-main-0] INFO org.nd4j.nativeblas.NativeOpsHolder - Number of threads used for NativeOps: 6
12:53:24.197 [run-main-0] INFO org.nd4j.nativeblas.Nd4jBlas - Number of threads used for BLAS: 6
12:53:24.199 [run-main-0] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Backend used: [CPU]; OS: [Linux]
12:53:24.199 [run-main-0] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Cores: [12]; Memory: [3.6GB];
12:53:24.199 [run-main-0] INFO org.nd4j.linalg.api.ops.executioner.DefaultOpExecutioner - Blas vendor: [MKL]
12:53:24.282 [run-main-0] INFO org.deeplearning4j.nn.multilayer.MultiLayerNetwork - Starting MultiLayerNetwork with WorkspaceModes set to [training: ENABLED; inference: ENABLED], cacheMode set to [NONE]
12:53:24.332 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Starting learning model...
12:53:24.375 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 0/200.
12:53:24.528 [ADSI prefetch thread] DEBUG org.nd4j.linalg.memory.abstracts.Nd4jWorkspace - Steps: 5
12:53:24.656 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 0 is 3.5399603843688965
12:53:41.677 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 1000 is 2.9129018783569336
12:53:59.065 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 2000 is 2.7215099334716797
12:54:16.124 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 3000 is 2.565291404724121
12:54:20.027 [run-main-0] DEBUG org.deeplearning4j.datasets.iterator.AsyncDataSetIterator - Manually destroying ADSI workspace
12:54:20.027 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 1/200.
12:54:20.028 [ADSI prefetch thread] DEBUG org.nd4j.linalg.memory.abstracts.Nd4jWorkspace - Steps: 5
12:54:32.906 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 4000 is 2.297577142715454
12:54:49.791 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 5000 is 2.299525022506714
12:55:06.563 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 6000 is 2.366898536682129
12:55:13.921 [run-main-0] DEBUG org.deeplearning4j.datasets.iterator.AsyncDataSetIterator - Manually destroying ADSI workspace
12:55:13.921 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 2/200.
12:55:13.921 [ADSI prefetch thread] DEBUG org.nd4j.linalg.memory.abstracts.Nd4jWorkspace - Steps: 5
[...]
16:01:05.738 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Epoch number: 199/200.
16:01:05.738 [ADSI prefetch thread] DEBUG org.nd4j.linalg.memory.abstracts.Nd4jWorkspace - Steps: 5
16:01:26.581 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 643000 is 0.08678578585386276
16:01:49.095 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 644000 is 0.08569760620594025
16:02:12.899 [run-main-0] INFO org.deeplearning4j.optimize.listeners.ScoreIterationListener - Score at iteration 645000 is 0.09236510097980499
16:02:21.649 [run-main-0] DEBUG org.deeplearning4j.datasets.iterator.AsyncDataSetIterator - Manually destroying ADSI workspace
16:02:21.649 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.trainer.ModelTrainerImpl - Learning model finished.
16:02:28.453 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.Main$ - Trained model accuracy on testing set is: 0.950812884104084.
16:02:38.338 [run-main-0] INFO com.datart.captcha_breaker.examples.dl4j.Main$ - Trained model accuracy on testing set of captcha images (5 alphanums) is: 0.25985663082437277 (2610/10044 correctly predicted).
[success] Total time: 11510 s, completed Apr 14, 2019, 4:02:39 PM
```

#### Alternative arguments and input

1. You can change how long model will be trained by providing custom positive integer indicating number of learning epochs. Default value is 200.

```bash
sbt -mem 8192 "run epochs_number"
```

2. You can also provide custom directory with your input letter images and testing captcha images, BUT, as this is really quick example, there are several things required (or hardcoded):

You have to provide number of epochs and after that path to resources directory. Default resources directory is */path/to/cloned/repository/src/main/resources*

* Inside your resources directory you have to have **letters** directory with subdirectories for each class (letter or digit) as described [here: https://deeplearning4j.org/cn/archieved/simple-image-load-transform](https://deeplearning4j.org/cn/archieved/simple-image-load-transform)
* Currently only captcha images with 5 signs are resolved
* Currently you may provide learning set which has exactly 27 classes (in provided data set classes are as follows ***2346789ACDEFGHJKLNPQRTUVXYZ***) with images of 31 x 32 pixels
* Inside your resources directory you have to have **captcha_images** directory with set of captcha images named ***ABCDE.jpg***, where ***ABCDE*** is a solution for captcha inside file.

```bash
sbt -mem 8192 "run epochs_number /path/to/resources/directory"
```

Unmatching parameters configuration are, as possible, converted and set with default values.

### Final thoughts

This is not a production ready solution, only presentation of great tool solving captcha from real website. At least several things could be done to improve it:

* full customization of learning process, number of classes, dimension of letters and length of captcha by a config file
* preprocessing letters and captcha images - deleting noise, distortions and thresholding to only black and white colors

Let it be your starting point if you find it helpful. :-)
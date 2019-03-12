# General
This github repository contains the programming project which accompanies the bachelor thesis *Analyzing the transfer of aspect-based sentiment extraction to hotel reviews*.

# Table of Contents
- [General](#general)
  * [Important Information](#important-information)
- [Setup](#setup)
  * [Example Run: Data Generation](#example-run--data-generation)
  * [Example Run: Evaluation](#example-run--evaluation)
  * [How are the models evaluated? How do you get the performance values?](#how-are-the-models-evaluated--how-do-you-get-the-performance-values-)
    + [Research Question I](#research-question-i)
    + [Research Question II](#research-question-ii)
- [Structure](#structure)
  * [Folders](#folders)
  * [Class Descriptions](#class-descriptions)


## Important Information

All data has been already generated with the modules included in this project. Since this is a purely scientific study, there is no Graphical User Interface or visualisation. The data generated by the methods is meant to be the supplementary work which comprises the found results in this thesis. All data (raw annotation data, instance data, evaluation, etc.) is already included and calculated: it can be accessed through the resources folder, as described in [Structure](#folders).

# Setup
The program represents the framework we used for implementing annotation procession, model construction and evaluation. This is a Maven project, so downloading and importing the ``pom.xml`` is necessary for the installation of the dependecy modules. Make sure that all classes referenced in the ``pom.xml`` are properly imported and available after the IDE finished downloading the data. If ClassNotFound compiling errors occur, please check if Maven found the correct version of each dependency. Other external programs are not necessary to run this project. We recommend to use Notepad++ to inspect all ``.txt`` files, since the normal notepad editor has difficulties in correctly displaying linebreaks.


The main pipeline is the main execution point, and is executed by running the code within the class [MainPipeline.java](https://github.com/jonasmeise/AutomaticAspectExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/pipeline/MainPipeline.java).
In the current iteration, the code does not execute any of the tasks represented in the thesis. But, all methods that are needed for constructing our found results are implemented:

``buildFilesOldDomain(String inputFile, String outputFolder)``

``buildFilesNewDomain(String inputFile, String outputFolder)``

``executeAnnotationStudy()``

``executeRegressionTask(String inputFile, String outputFolder, boolean constrained)``

``foldLearning()``

To show how we generated our data and results, we will showcase some examples, so that the replication of our found data is possible.
If you chose to execute any method, make sure to delete the preexisting files in the output folders first. The methods append created data to already existing files, which renders them useless. To prevent that, move the already existing content to a cache folder or delete it.

## Example Run: Data Generation
If you want to build the constrained AUEB model for Slot 1 on the domain of _hotel_ reviews, enable the following method calls in MainPipeline:

```java
public static void main(String[] args) throws Exception {
		MainPipeline myPipeline = new MainPipeline();

		myPipeline.buildFilesNewDomain("src\\main\\resources", "src\\main\\resources\\learningtest_AUEB\\subtask1\\constrained");
	}
```
The first parameter refers to the folder containing the .xmi files with the annotated data set (sub-folders are ignored). This path should not be changed, since ``output_1.xmi`` to ``output_5.xmi`` contain the annotated data for the five hotels and are always located in the [\resources\\](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/tree/master/src/main/resources), regardless of the model.
The second parameter refers to the output folder, where the generated ``.arff`` files that contain the data for the model are saved in. This path should be changed to the designated folders, as shown in the example. Each model has a designated folder for it, with the following structure:

``learningtest_modelName \ subtask1 OR subtask3 \ constrained OR unconstrained \``

Additionally, the old data of the model can be found in the following folder:

``learningtest_modelName \ subtask1 OR subtask 3 \ old \ constrained OR unconstrained \``

Be wary of executing the method while there are already files in this folder.

To correctly choose the right model, you have to specify the model classifier ``AUEB_ClassifierGenerator`` within the ``writer`` object in ``buildFilesNewDomain``:
```java
AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(AUEB_ClassifierGenerator.class, 
	        		AUEB_ClassifierGenerator.PARAM_OUTPUT_PATH, outputFolder, 
	        		AUEB_ClassifierGenerator..PARAM_RELATION_NAME, "AUEB",
	        		AUEB_ClassifierGenerator..PARAM_CONSTRAINED, "true",
	        		AUEB_ClassifierGenerator..PARAM_USE_OLD_DATA, "false");
```
``AUEB_ClassifierGenerator.class`` can be interchanged with any of the generator models that are in [\classifiers\\](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/tree/master/src/main/java/de/unidue/langtech/bachelor/meise/type/classifiers). No suffix in the class name indicates Slot 1, the suffix indicates the Slot 3 model.

``PARAM_OUTPUT_PATH`` sets the output folder for the .arff files. This parameter is already set due to the method call.
``PARAM_RELATION_NAME`` sets the relation name for the .arff files. This is an optional parameter, it does not change the data itself.
``PARAM_CONSTRAINED`` sets the constrain type of the model. If the model only supports a single type, then setting this parameter has no impact.
``PARAM_USE_OLD_DATA`` sets the input type: ``true`` generates ``.arff`` files (separate training + test data) based on the data from SE-ABSA16, while ``false`` generates them for the new domain (combined training + test data). This value should not be changed: ifor constructing the data of the old SE-ABSA16 task, call ``buildFilesOldDomain("src\\main\\resources\\SEABSA16_data", "src\\main\\resources\\learningtest_AUEB\\subtask1\\constrained")`` instead. The correct generator model needs to be set in _both_ writer pipelines (one pipeline generates training, the other test data). The process is identical with the one mentioned above.

An exemplary call for each main method is included in the comment section of the ``main()`` method. 
To properly execute the method, make sure to correctly address the folder paths with the respective data - a short guideline for handling the data is presented in **Structure**.

## Example Run: Evaluation

If you want to run the evaluation process for a model, execute the method ``myPipeline.foldLearning()``.

```java
public void foldLearning() throws Exception {	 
		AUEB_Evaluator myEvaluator = new AUEB_Evaluator();
		myEvaluator.useOldData(false);
		//myEvaluator.setUpAblation("0,1,2", 23, 3);
		myEvaluator.execute(1);
	}
```
The evaluator class for each model is contained in the folder [\evaluation\\](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/tree/master/src/main/java/de/unidue/langtech/bachelor/meise/evaluation). Following commands can be executed on an ``\_Evaluator`` class:

``useOldData(boolean)`` enables the evaluation on either the new data (``false``) or the old data (``true``). Old data is evaluated on a fixed training/test data split, while the new domain is evaluated with 10-folded cross validation. 

``execute(slot)`` executes evaluation for a specific slot. If the slot does not exist for the model, an error prompt will be shown. The ``analysis.txt`` file is autmoatically generated in the corresponding folder as presented in **[Structure](#structure)**. The paths for the evaluation files is _fixed_: I.e. the ``AUEB_Evaluator`` will always access the folder ``src\main\resources\learningtest_AUEB\subtask1\constrained`` for evaluating the Slot 1 data. All ``.arff`` files that appear in that folder are included in the evaluation process. The path can be changed by altering the source code within the ``\_Evaluator`` class (``getSourcePath()`` and ``getSourcePath_Slot1()``).

``setUpAblation(alreadyRemoved, maxFeatures, slot)`` executes an iterative ablation analysis on a reduced set of features. The string ``alreadyRemoved`` indicates the features that are _already_ removed. "0" should be always included, since for every model, the first feature (ID) is always removed; during the normal evaluation process, the feature at position "0" is automatically ignored. Additional numbers are added by separating them with a comma. The integer ``maxFeatures`` refers to the maximum number of features in a model (counting from 1, including the final class label). For each feature that is not already contained in ``alreadyRemoved`` and its index is smaller than ``maxFeatures``, it will be removed once in the ablation test. The final integer ``slot`` refers to the used slot for the test - it equals the ``slot`` parameter of the ``execute(slot)`` method. The method produces an analysis file for each variation of the feature analysis (with the naming syntax ``_alreadyRemoved_currentlyRemoved.txt``) in the folder of the respective model. To run multiple iterative ablation tests, the ``alreadyRemoved`` has to manually updated by the user and the method needs to be called again.

## How are the models evaluated? How do you get the performance values?

### Research Question I

For each model, the same procedure is executed:

```java
	buildFilesOldDomain("src//main//resources//SEABSA16_data", "src//main//resources//learningtest_modelXXX//subtaskX//old//constrained//");
	foldLearning();
``` 
for generating the training files (``.arff``), test files (``.gold``) and the evaluation file ``analysis.txt`` regarding the **old** data.

_or_

```java
	buildFilesNewDomain("src//main//resources", "src//main//resources//learningtest_modelXXX//subtaskX//constrained//");
	foldLearning();
``` 
for generating the training files (``.arff``) and the evaluation file ``analysis.txt`` regarding the **new** data.

The objects, methods and parameters are manually set with the same procedures as described in **[Setup](#setup)**.

We manually combined the individual performance results of the binary classifiers with the labels, as described in the thesis, and calculated the final score of a model. The singular performances of each classifier can be found the ``analysis.txt`` file of each model folder. The weights for the hotel domain are presented in table 3.2 of the thesis (percentage share), the distribution of labels in the test data of the restaurant domain are included in the file [stats.txt](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/resources/SEABSA16_data/stats.txt). We used a small assistance table for calculating the respective values; this process was manually conducted.

### Research Question II

The data for the evaluation of Research Question II was created with the ``executeRegressionTask(String inputFile, String outputFolder, boolean constrained)`` method. An example call looks like this:

``//myPipeline.executeRegressionTask("src\\main\\resources", "src\\main\\resources\\RQ2_learningtest\\unconstrained", false);``

This creates the data, which is identical for each model and category presented in our thesis. To evaluate their performance and get stats about the regression formula, mean absolute error, mean squared root error, etc., myPipeline.foldLearning() is executed again:

```java
public void foldLearning() throws Exception {	 
	Regression_Evaluator myEvaluator = new Regression_Evaluator();
	myEvaluator.useOldData(false);
	myEvaluator.execute(3, "esvr");
}
```
The important part is ``myEvaluator.execute(3, "esvr");``: The integer "3" refers to the category of the model (1=constrained, 2=unconstrained, 3=unconstrained, no text). "esvr" is an indicator for the classifier algorithm type: We implemented four different types of classifiers, their indicative strings are:
``lr`` = Linear Regression
``slr`` = Simplified Linear Regression
``esvr`` = Epsilon Support Vector Machine
``knn`` = k-Nearest Neighbors

``foldLearning()`` generates analysis files in the respective [folders](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/tree/master/src/main/resources/RQ2_learningtest/unconstrained). The analysis files have the following name schemata: ``analysis_category_classifier.txt``. They contain the single evaluation summaries for every fold of the data, and an averaged root-mean squared error and averaged root-mean squared error (mapped) at the end of it.

# Structure

## Folders

The main data for this program is included in [resources](https://github.com/jonasmeise/AutomaticAspectExtraction/tree/master/src/main/resources). The sub-folders include...

* **folders with "_\_learningtest_"** --> The model-specific instances of each reconstructed and implemented model, as well as their analysis in .txt format. If possible, do not edit these folders, since they contain the evaluation data of this thesis.

  * **constrained** --> The constrained data for the reconstructed task.
  
  * **unconstrained** --> The unconstrained data for the reconstructed task.
  
Constrained and unconstrained folders are further split into the slots that the model was tested on. Each slot folder contains the learning instances (``.arff``), a raw performance of each classifier (``analysis.txt`` or ``analysis_old.txt``) and a calculated final performance score (``eval.txt``). The paths to these final sub-folders are used when generating the output data for the classifier models. We recommend to save the files in this path in a custom folder when executing learning methods, otherwise the files are overwritten and corrupted.
  
  * **old** --> The comparison data for the old SE-ABSA16 task.
  
  * save --> Outdated data. The files in it can be ignored and are not used.

* **folders with "datasetX"** --> The annotation data of each hotel X (1 to 5), in plain .txt form, parsed .xml form, converted .tsv form (for importing them into WebAnno) and the final .xmi JCas annotation file. The .xmi files are equal with the ones present in the root [resources](https://github.com/jonasmeise/AutomaticAspectExtraction/tree/master/src/main/resources) folder. For [dataset1](https://github.com/jonasmeise/AutomaticAspectExtraction/tree/master/src/main/resources/dataset1) and [dataset2](https://github.com/jonasmeise/AutomaticAspectExtraction/tree/master/src/main/resources/dataset2), the different annotations of both the curator (Annotator A) and a secondary annotator (either B or C) are included.
 
* **"sentimentlexicon"** --> The raw data files for the three sentiment lexicons that are used in the thesis.

**"SEABSA16_data"** --> The original training and test data that was available in the SemEval 2016 ABSA task. It also includes a short statistics file about the aspect label distribution of the test data.

**"stopwords.txt"** --> The file with NLKT stopword list.

All other files can be ignored.

## Class Descriptions

The final section includes a description of all classes. The code itself can be difficult to understand; to provide a general idea about what exactly the classes do, we compiled a short summary. All of our implemented classes can be found in the folder [/meise/](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/tree/master/src/main/java/de/unidue/langtech/bachelor/meise). The other classes in the other folders are either auto-generated (WebAnno-classes) or external classes, which are used as a surrogate for methods that are not yet implemented in Maven-libraries (Weka's ThresholdSelector).

[AspectClassifier](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/classifier/AspectClassifier.java): Handles the actual learning process, as well as regression analysis (+ allocation algorithm). Uses Weka and its supportive modules to generate data and evaluation scores, handles plugins such as e.g. String2WordVector.

[ClassifierHandler](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/classifier/ClassifierHandler.java): The interface between an external learning call and AspectClassifier. It handles ablation testing, parameter selection and I/O operations. All ``_Evaluator`` classes are extended ClassifierHandlers.

[ConsoleLog](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/extra/ConsoleLog.java): A custom console class, which automatically prints the calling method in addition to the console text.

[DataParser](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/files/DataParser.java): Parses raw ``.txt`` content into ``.xml``, is able to read ``.xml`` into abstract objects which can be accessed during runtime.

[FileUtils](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/files/FileUtils.java): General I/O operations, combined in a single class, such as file writing/reading, fetching files in folders and subfolders, etc..

[RawJsonReviewReader](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/files/RawJsonReviewReader.java): Class that works as a pipeline reader. It is able to fetch both old and new data and input it into the pipeline. It also is used as an external provider for the SE-ABSA16 review data; classifiers that operate on the old domain use it to fetch the annotation of the old restaurant reviews.

[AnnotationStudy](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/pipeline/AnnotationStudy.java): Performs the annotation study on a folder with two ``.xml`` files (by two annotators). It calculates Cohen's Kappa for the annotation labels of a single sentence (all annotation targets: aspect label, target word, polarity), which were found by both annotators.

[MainPipeline](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/pipeline/MainPipeline.java): Main pipeline. Program is executed from here, change up methods and method calls as necessary.

[TSVExporter](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/pipeline/TSVExporter.java): Class that works as a pipeline writer. Exports pre-annotated (tokens, POS, dependencies) textual data into the ``.tsv`` format. ``.tsv`` files are used for importing these files into WebAnno, so that the sentiments can be manually annotated.

[CustomLexicon](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/sentimentlexicon/CustomLexicon.java): A dummy class for a completely empty lexicon, which needs to be filled during runtime. For external lexicon loading, use SentimentLexicon.

[ArffGenerator](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/type/ArffGenerator.java): The abstract framework class for all ``_ClassifierGenerator`` classes. It sets up the feature, data input and output, sentence-wise execution and auxilary functions.

[ReviewData](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/type/ReviewData.java): The abstract representation of a review within the Java program. It is created by RawJsonReviewReader or DataParser.

[SentimentLexicon](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/type/SentimentLexicon.java): Implements the framework for a sentiment lexicon. AFINN, BingLiu and EmoLex inherit this type.

[StopwordHandler](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/type/StopwordHandler.java): Handles the stopwords and implements Weka's StopwordHandlers interface. Uses the existing NLTK word list for checking whether a single word is a stopword or not.

[Tree](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/blob/master/src/main/java/de/unidue/langtech/bachelor/meise/type/Tree.java). A custom-built class that represents a tree-shaped data structure. It is used to build sentence dependency trees, find the root of a sentence, calculate the dependency distance between two tokens etc..

All classes that are not named, are not categorized with one of the earlier structures like ``_ClassifierGenerator`` or ``_Evaluator`` and are contained in the [/meise/](https://github.com/jonasmeise/TransferAnalysisSentimentExtraction/tree/master/src/main/java/de/unidue/langtech/bachelor/meise) folder have been redacted and are not used in the final version.

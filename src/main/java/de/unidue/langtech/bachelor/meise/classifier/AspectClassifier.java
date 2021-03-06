package de.unidue.langtech.bachelor.meise.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.unidue.langtech.bachelor.meise.evaluation.Baseline2_Evaluator;
import de.unidue.langtech.bachelor.meise.evaluation.Regression_Evaluator;
import de.unidue.langtech.bachelor.meise.extra.ConsoleLog;
import de.unidue.langtech.bachelor.meise.files.FileUtils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class AspectClassifier {
	//a single Aspect Classifier for a set of Instances
	
	public String sourcePath;
	public FilteredClassifier classifier;
	public ClassifierHandler caller;
	public Instances instances;
	public Evaluation evaluation;
	private FileUtils fu;
	private ConsoleLog myLog;
	public boolean idfTransformEnabled;
	public boolean regression=true;
	public Classifier outerParameterClassifier; //if called from outside, this thing needs to be set
	public boolean mapToHistogram = true;
	public boolean enableCPS = false;
	public double parameterHistogram = 1;
	public double mappedSquaredMeanError=0;
	public ArrayList<String> classifierDescriptions;
	
	public int[] removeArray;
	
	public CVParameterSelection cps;
	
	int seed;
	int folds = 10;
	
	public AspectClassifier() {
		classifierDescriptions = new ArrayList<String>();
		fu = new FileUtils();
		sourcePath = "";
		instances = null;
		classifier = new FilteredClassifier();
		myLog = new ConsoleLog();
	}
	
	public AspectClassifier(Classifier outerParameterClassifier) {
		this();
		this.outerParameterClassifier = outerParameterClassifier;
	}
	
	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(FilteredClassifier classifier) {
		this.classifier = classifier;
	}

	public AspectClassifier(String sourcePath) {
		this();
		this.sourcePath = sourcePath;
	}
	
	//Trains Classifier for given training Data
	public Instances buildClassifier(Instances train) throws Exception{ 
			StringToWordVector s2wFilter = createS2WVector();
			//s2wFilter.setDoNotOperateOnPerClassBasis(true);

			Remove removeFilter = new Remove();
			//remove ID-Feature#
			
			if(removeArray!=null) {
				removeFilter.setAttributeIndicesArray(removeArray);
			} else {
				removeFilter.setAttributeIndices("1");
			}
			
			MultiFilter mf = new MultiFilter();
			mf.setInputFormat(train);
			mf.setFilters(new Filter[] {removeFilter, s2wFilter});
			
			myLog.log("Found classifier from outside...");
			classifier.setClassifier(outerParameterClassifier);
			
			if(outerParameterClassifier.getClass().equals(CVParameterSelection.class)) {
				cps = (CVParameterSelection) outerParameterClassifier;
				enableCPS = true;
			}

			classifier.setFilter(mf);
			classifier.buildClassifier(train);
			
			return train;
	}
	
	public ArrayList<Evaluation> learn(Instances train, Instances test) throws Exception {
		ArrayList<Evaluation> returnList = new ArrayList<Evaluation>();

		double avgMeanError=0, avgMeanErrorMapped=0, avgMeanErrorMappedCumulated=0;
			   
		   //test.deleteAttributeAt(0);
		   //build-Classifier loescht automatisch 0
		   buildClassifier(train);
	
		   saveModel("currentmodel.model", classifier);
		   
		   Evaluation currentEvaluation;
		   
		   if(folds>0) {
			   currentEvaluation = new Evaluation(test);
			   currentEvaluation.evaluateModel(classifier, test);
		   } else {
			   currentEvaluation = new Evaluation(test);
			   currentEvaluation.evaluateModel(classifier, test);
		   }
		   
		   if(regression) {
			   avgMeanErrorMapped=0;
			   
			   double minimalAcceptanceValue=0;
			   HashMap<Double, Integer> histogram = new HashMap<Double, Integer>();
			   double minimalValue=1000, maximalValue=0, spanValue=0;
			   if(mapToHistogram) {
				   //create histogram
				   for(Instance singleInstance : train) {   
					   if(singleInstance.classValue()>maximalValue) {
						   maximalValue = singleInstance.classValue();
					   }
					   if(singleInstance.classValue()<minimalValue) {
						   minimalValue = singleInstance.classValue();
					   }
					   
					   if(!histogram.containsKey(singleInstance.classValue())) {
						   histogram.put(singleInstance.classValue(),1);
					   } else {
						   histogram.put(singleInstance.classValue(), histogram.get(singleInstance.classValue())+1);
					   }
				   }
				   
				   //TODO: make the *10 dependant on the data set
				   spanValue = Math.abs(maximalValue - minimalValue)*10;
				   
				   myLog.log("span value: " + spanValue);
				   minimalAcceptanceValue = (train.size()/spanValue);
				   
				   for(Double key : histogram.keySet()) {
					   myLog.log(key + "-" + histogram.get(key) + " ? " + minimalAcceptanceValue);
					   if(histogram.get(key) < minimalAcceptanceValue) {
						   histogram.put(key, 0);
					   }
				   }
			   }
			   
			   for(Instance testInstance : test) {
				   double initialScore = classifier.classifyInstance(testInstance);
				   double dblMappedScore = initialScore;
	
				   if(mapToHistogram) {		   
					   double minimalDistance = 1000;
					   
					   for(Double key : histogram.keySet()) {
						   double currentDistance = Math.abs(initialScore - key);
						   
						   if(currentDistance < minimalDistance && histogram.get(key)>0) {
							   minimalDistance = currentDistance;
							   dblMappedScore = key;
						   }
					   }
				   }
				   
				   double difference = Math.abs(testInstance.classValue()-dblMappedScore);
				   difference = difference * difference;
				   
				   //System.out.println(testInstance.classValue() + "-(" + dblMappedScore + "<" + initialScore + ") --> " + difference);
				   avgMeanErrorMapped += difference;
				   
			   }
			   
			   classifierDescriptions.add(classifier.toString());
			   
			   avgMeanErrorMappedCumulated += (avgMeanErrorMapped/test.size());
			   
			   avgMeanError += currentEvaluation.rootMeanSquaredError();
			   myLog.log("absolute squared error: " + currentEvaluation.rootMeanSquaredError());
		   } else {
			   System.out.println("Matrix: " + currentEvaluation.toMatrixString());
			   System.out.println(currentEvaluation.toSummaryString());
		   }
		   
		   returnList.add(currentEvaluation);
		   
		   if(enableCPS && cps!=null && cps.getBestClassifierOptions()!=null) {
			   myLog.log("Best found parameters for this fold: ");
			   for(String parameter : cps.getBestClassifierOptions()) {
				   myLog.log(parameter);
			   }
		   }
		 
		 if(regression) {
			 myLog.log("AVG. SQUARED MEAN ERROR: " + (avgMeanError));
			 
			 avgMeanErrorMappedCumulated = Math.sqrt(avgMeanErrorMappedCumulated);
			 
			 myLog.log("AVG. SQUARED MAPPED ERROR: " + (avgMeanErrorMappedCumulated));
			 mappedSquaredMeanError+= avgMeanErrorMappedCumulated;
		 }
		 
		 return returnList;
	}
	
	public ArrayList<Evaluation> learn(Instances data) throws Exception {
		ArrayList<Evaluation> returnList = new ArrayList<Evaluation>();
		
		Random rand = new Random(seed);
		Instances scrambledData = new Instances(data);

		scrambledData.randomize(rand);
		
	    if (scrambledData.classAttribute().isNominal()) {
	    	myLog.log("Stratified data set.");
	    	scrambledData.stratify(folds);
	    }
	    
	    
		 for (int n = 0; n < folds; n++) {
			   Instances train = scrambledData.trainCV(folds, n, rand);
			   Instances test = scrambledData.testCV(folds, n);
			   myLog.log("Train size: " + train.size());
			   myLog.log("test size: " + test.size());
			   
			   returnList.addAll(learn(train, test));
		 }
				
		 return returnList;
	}
	
	//import Instances -> learn and export models
	public void learnAndExport(String inputPath, String outputPath) throws IOException, URISyntaxException, Exception {
		learnAndExport(getData(inputPath, 1), outputPath);
	}
	
	public void learnAndExport(Instances data, String outputPath) throws IOException, URISyntaxException, Exception {
		Instances newData = buildClassifier(data);
		saveModel(outputPath, classifier);
	}
	
	private StringToWordVector createS2WVector() {
		StringToWordVector s2wFilter;
		s2wFilter = new StringToWordVector(); 
		s2wFilter.setAttributeIndices("first-last");
		s2wFilter.setIDFTransform(idfTransformEnabled);
		s2wFilter.setLowerCaseTokens(true);	
		s2wFilter.setAttributeNamePrefix("s2w");
		
		if(caller!=null && caller.getClass().equals(Baseline2_Evaluator.class)) {
			s2wFilter.setWordsToKeep(1000);
			s2wFilter.setLowerCaseTokens(false);	
			s2wFilter.setDoNotOperateOnPerClassBasis(false);
		}
		
		if(caller!=null && caller.getClass().equals(Regression_Evaluator.class)) {
			s2wFilter.setMinTermFreq(5);
		}
		return s2wFilter;
		
	}
   
   public void loadModels(String filePath) throws Exception {
	   sourcePath = new File(filePath).getName();
	   classifier = (FilteredClassifier) weka.core.SerializationHelper.read(filePath);
   }
   
   public Instances getData( String folderName, String fileType, boolean includeSubfolders, Integer posClass) throws IOException, URISyntaxException {
	   Instances returnInstances = null;
	   
	   ArrayList<String> fileList  = fu.getFilesInFolder(folderName, fileType, includeSubfolders);
	   
	   for(String filePath : fileList) {
		   if(returnInstances==null) {
			   returnInstances = getData(filePath, posClass);
		   } else {
			   returnInstances.addAll(getData(filePath, posClass));
		   }
	   }
	   
	   return returnInstances;
   }
   
   public void saveModel(String fileName, FilteredClassifier classifier) {
		try {
           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
           out.writeObject(classifier);
           out.close();
			myLog.log("Exported Model to:" + fileName);
       } 
		catch (Exception e) {
			System.out.println("Problem found when writing: " + fileName);
		}
	}
   
   public static int[] convertIntegers(List<Integer> integers)
   {
       int[] returnArray = new int[integers.size()];
       for (int i=0; i < returnArray.length; i++)
       {
    	   returnArray[i] = integers.get(i).intValue();
       }
       return returnArray;
   }
   
   public Instances getData(String fileName, Integer posClass ) throws IOException, URISyntaxException {
       File file = new File(fileName);
       sourcePath = file.getName();
       myLog.log("Loading instances from " + fileName + "...");
       BufferedReader inputReader = new BufferedReader(new FileReader(file));
       Instances data = new Instances(inputReader);
       data.setClassIndex(data.numAttributes() - posClass);
       myLog.log("Found class attribute at '" + (data.numAttributes() - posClass) + "'. " + data.size() + " Instances loaded!");
       
       if(data.classAttribute().isNumeric()) {
    	   regression = true;
       } else {
    	   regression = false;
       }
       
       myLog.log("REGRESSION=" + regression);

       return data;
   }
}

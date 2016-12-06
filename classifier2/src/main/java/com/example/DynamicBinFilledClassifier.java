package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

//implements weka.classifiers.UpdateableClassifier

public class DynamicBinFilledClassifier {

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter out = null;
    //WLSVM svmCls;
    Classifier ibk;

//    DynamicBinFilledClassifier(){
//
//    }

	/*
		The function createNewModel creates a new file for the training model.
		It will require all the relevant attributes as arguments for the function
		to create the first results in the file

		@param The filename must include the root folder if the weka lib is in a different folder from
		this class, and it should not include .arff extension as the .arff extension is added within the file
		future improvements might include support for filenames with or without .arff extensions

		Returns "ERROR" if unsuccessful and "SUCCESS" if update completes
	*/

    public String createNewModel(String fileName,Double weight, Double sonar1,Double sonar2,Double sonar3,
                               Double sonar4,Double sonar5,Double sonar6, String classification){

        if(new File(fileName+".arff").isFile()){
            System.out.println("The file already exists! Consider updating the model instead. " +
                            "\nUnless you want to overwrite data, please delete or change location/filename of existing file");
            return "ERROR";
        }

        // Declare the numeric weight and sonar attributes
        Attribute Attribute1 = new Attribute("weight"); // weight is in grams
        Attribute Attribute2 = new Attribute("sonar1"); // sonar readings is in metres
        Attribute Attribute3 = new Attribute("sonar2");
        Attribute Attribute4 = new Attribute("sonar3");
        Attribute Attribute5 = new Attribute("sonar4");
        Attribute Attribute6 = new Attribute("sonar5");
        Attribute Attribute7 = new Attribute("sonar6");

        // Declare the class attribute along with its values
        //   @ATTRIBUTE class        {Overflowing,75pFilled,50pFilled,25pFilled,Empty}
        FastVector fvClassVal = new FastVector(5);
        fvClassVal.addElement("Overflowing");
        fvClassVal.addElement("75pFilled");
        fvClassVal.addElement("50pFilled");
        fvClassVal.addElement("25pFilled");
        fvClassVal.addElement("Empty");
        Attribute ClassAttribute = new Attribute("binStatus", fvClassVal);

        // Declare the feature vector template
        FastVector fvWekaAttributes = new FastVector(8);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(Attribute4);
        fvWekaAttributes.addElement(Attribute5);
        fvWekaAttributes.addElement(Attribute6);
        fvWekaAttributes.addElement(Attribute7);
        fvWekaAttributes.addElement(ClassAttribute);

        // Create an empty training set
        Instances trainingSet = new Instances("Rel",fvWekaAttributes, 1);
        // Set Class Index
        trainingSet.setClassIndex(trainingSet.numAttributes() - 1);

        // Create the instance
        Instance iClassify = new Instance(trainingSet.numAttributes());

        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(0),weight);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(1),sonar1);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(2),sonar2);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(3),sonar3);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(4),sonar4);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(5),sonar5);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(6),sonar6);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(7),classification);

        // Add the Instance
        trainingSet.add(iClassify);
//        System.out.println(trainingSet);

//        try{
//            PrintWriter writer = new PrintWriter(fileName+".arff", "UTF-8");
//            writer.print(trainingSet);
//            writer.close();
//        } catch (IOException e) {
//            // do something
//        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName+".arff"), "utf-8"))) {
            writer.write(String.valueOf(trainingSet));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
//            svmCls = new WLSVM();
//            svmCls.buildClassifier(trainingSet);
            ibk = new IBk();
            ibk.buildClassifier(trainingSet);
            weka.core.SerializationHelper.write(fileName, ibk);//svmCls); // Stores trained model as "svmBinTrainedModel"
            // To load, use   WLSVM svmCls = (WLSVM) weka.core.SerializationHelper.read("svmTrainedModel");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "SUCCESS";

    }

    /*

        Returns "ERROR" if unsuccessful and "SUCCESS" if update completes

     */

    public String updateModel(String fileName,Double weight, Double sonar1,Double sonar2,Double sonar3,
                            Double sonar4,Double sonar5,Double sonar6, String classification){
        if(!new File(fileName+".arff").isFile()){
            System.out.println("No such file found, check for location or misspelling of filename");
            return "ERROR";
        }
        String toAdd = String.format("%f,%f,%f,%f,%f,%f,%f,%s",weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,classification);
        System.out.println(toAdd);
        try{
            fw = new FileWriter(fileName+".arff",true);
            bw = new BufferedWriter(fw);
            fw.write("\n"+toAdd);
            fw.close();
            File f = new File(fileName+".arff");
            BufferedReader inputReader;
            inputReader = readFile(f);
            Instances data;

            try {
                data = new Instances(inputReader);
                data.setClassIndex(data.numAttributes()-1);
                ibk = new IBk();
                ibk.buildClassifier(data);
                weka.core.SerializationHelper.write(fileName, ibk);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            data= new Instances(inputReader);
//            int numAttributes = data.numAttributes();
//            data.setClassIndex(numAttributes-1);
////            svmCls = new WLSVM();
////            svmCls.buildClassifier(data);
//            ibk = new IBk();
//            ibk.buildClassifier(data);
//            weka.core.SerializationHelper.write("svmBinTrainedModel", ibk);//svmCls);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "SUCCESS";

//    @Override
//    public void updateClassifier(Instance instance) throws Exception {
//        // load data
//        ArffLoader loader = new ArffLoader();
//        loader.setFile(new File("/some/where/data.arff"));
//        Instances structure = loader.getStructure();
//        structure.setClassIndex(structure.numAttributes() - 1);
//
//        // train NaiveBayes
//        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
//        nb.buildClassifier(structure);
//        Instance current;
//        while ((current = loader.getNextInstance(structure)) != null)
//            nb.updateClassifier(current);
//    }
    }

    private BufferedReader readFile(File f) {
        try {
            return new BufferedReader(new FileReader(f.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

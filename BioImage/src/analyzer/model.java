package analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class model {
 

  
    public static void main(String args[]){
         try{
               Instances test = null; 
               DataSource source = new DataSource("src/analyzer/2013-03-24.csv.arff");
               test = source.getDataSet();
               test.setClassIndex(0); 
 
               SMO cl1 = (SMO) weka.core.SerializationHelper.read("src/analyzer/20130324.model"); 

               Evaluation evaluation = new Evaluation(test);
               evaluation.evaluateModel(cl1, test);
               System.out.println("Results:" + evaluation.toSummaryString()); 
 
         }catch(Exception e){
               System.out.println(e);
         }
    }
    
} 


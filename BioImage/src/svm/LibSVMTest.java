package svm;

import java.io.IOException;

import libsvm.*;

public class LibSVMTest {

	public static void main(String[] args) throws IOException {

		String[] trainArgs = {"test.txt"};
		String modelFile = svm_train.main(trainArgs);		
//		String[] testArgs = {"test", modelFile, "result"};//directory of test file, model file, result file
//		Double accuracy = svm_predict.main(testArgs);
//		System.out.println("SVM Classification is done!");
//		System.out.println("The modelFile is " + modelFile);
//		System.out.println("The accuracy is " + accuracy);

	}

}

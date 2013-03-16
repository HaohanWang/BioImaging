package svm;

import java.io.IOException;

import libsvm.*;


/* 这个是我从网上找到的jar包，里面包含一些svm的函数
 * 基本都是写好的，我就根据咱们的样本对于函数做了一些很小的修改，然后跑了跑核心函数。
 */
 


public class svm_test {

	public static void main(String[] args) throws IOException {

		//这个是train的函数，trainArgs 是咱们的测试文件，我做了一个train，里面是咱们第一次测试的一部分数据。
		//只选了concentration和meditation这两个属性。如果需要更多的参数，直接修改即可。
		//train里面是输入文件格式。第一列样本output，其他几列是value
		//会生成一个train.model的文件，是训练结果的一些数据。（向量参数等）
		
		String[] trainArgs = {"train"};
		String modelFile = svm_train.main(trainArgs);
		
		//这个函数是测试函数。test是测试文件，会生成一个result文件。
		//result就是通过svm算法得到的模型，对于test文件进行标定，得到的结果。
		//accuracy 是测试test文件的得到的准确度。把svm得到的结果和test数据本身的结果进行比较。
		//可以在test文件里面加项目value，不同结果的数据，（e.g. "1 1:10 2:10 & 0 1:10 2:10"）
		//通过这些数据，可以看看算法得到的结果。
		//由于咱们第一次测量的数据，结果分界很不清楚，所以这个的测试结果准确率只有60%左右。
		
		String[] testArgs = {"test", modelFile, "result"};
		Double accuracy = svm_predict.main(testArgs);
		
		//一些测试输出
		System.out.println("SVM Classification is done!");
		System.out.println("The modelFile is " + modelFile);
		System.out.println("The accuracy is " + accuracy);

	}

}

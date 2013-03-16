package svm;

import java.io.IOException;

import libsvm.*;


/* ������Ҵ������ҵ���jar�����������һЩsvm�ĺ���
 * ��������д�õģ��Ҿ͸������ǵ��������ں�������һЩ��С���޸ģ�Ȼ�������ܺ��ĺ�����
 */
 


public class svm_test {

	public static void main(String[] args) throws IOException {

		//�����train�ĺ�����trainArgs �����ǵĲ����ļ���������һ��train�����������ǵ�һ�β��Ե�һ�������ݡ�
		//ֻѡ��concentration��meditation���������ԡ������Ҫ����Ĳ�����ֱ���޸ļ��ɡ�
		//train�����������ļ���ʽ����һ������output������������value
		//������һ��train.model���ļ�����ѵ�������һЩ���ݡ������������ȣ�
		
		String[] trainArgs = {"train"};
		String modelFile = svm_train.main(trainArgs);
		
		//��������ǲ��Ժ�����test�ǲ����ļ���������һ��result�ļ���
		//result����ͨ��svm�㷨�õ���ģ�ͣ�����test�ļ����б궨���õ��Ľ����
		//accuracy �ǲ���test�ļ��ĵõ���׼ȷ�ȡ���svm�õ��Ľ����test���ݱ���Ľ�����бȽϡ�
		//������test�ļ��������Ŀvalue����ͬ��������ݣ���e.g. "1 1:10 2:10 & 0 1:10 2:10"��
		//ͨ����Щ���ݣ����Կ����㷨�õ��Ľ����
		//�������ǵ�һ�β��������ݣ�����ֽ�ܲ��������������Ĳ��Խ��׼ȷ��ֻ��60%���ҡ�
		
		String[] testArgs = {"test", modelFile, "result"};
		Double accuracy = svm_predict.main(testArgs);
		
		//һЩ�������
		System.out.println("SVM Classification is done!");
		System.out.println("The modelFile is " + modelFile);
		System.out.println("The accuracy is " + accuracy);

	}

}

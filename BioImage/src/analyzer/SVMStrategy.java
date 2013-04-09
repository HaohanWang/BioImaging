package analyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.DenseInstance;
import weka.classifiers.functions.SMO;
import weka.core.Instance;
import weka.core.Instances;

public class SVMStrategy implements Strategy {

	private String TrainingDateFile = "trainingdata/weather.nominal.arff";

	private SMO svm;

	private Instances inst = null;

	public void setSVM(SMO s) {
		this.svm = s;
	}

	public SVMStrategy() {
		SMO s = new SMO();
		s = (SMO) this.loadModel("model/svm.model");
		this.setSVM(s);
		String[] ops2 = { "-t", "trainingdata/weather.nominal.arff", "-L",
				"0.0010", "-N", "1", "-V",
				"10", // k-fold cross-validation
				"-K", "weka.classifiers.functions.supportVector.PolyKernel",
				"-W", "1" };
		this.setoption(ops2);

	}

	public SVMStrategy(String[] ops) {
		svm = new SMO();

		SVMStrategy svm = new SVMStrategy();
		this.setoption(ops);
	}

	public void evaluationClassifier(String[] option) {
		try {
			// System.out.println(weka.classifiers.Evaluation.evaluateModel(
			// this.svm, option));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Instances getInstances() {
		return this.inst;
	}

	public boolean setoption(String[] option) {

		if (option.length == 0) {
			return false;
		}
		try {
			svm.setOptions(option);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * @param AttributeIndex
	 *            -classIndex number
	 * @return true if sucesess,otherwise false
	 */
	public boolean training(int AttributeIndex) {

		System.out.println("Start training.....");
		try {
			inst = new Instances(new FileReader(this.TrainingDateFile));
			if (AttributeIndex < 0 || AttributeIndex > inst.numAttributes()) {
				return false;
			}
			inst.setClassIndex(AttributeIndex);
			svm.buildClassifier(inst);
			System.out.println("build complete");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * @param ins
	 *            -the instance to be classified
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @return- -1 if take error,otherwise the predicted value
	 */
	public double predicted(int[] predictedInstance)
			throws FileNotFoundException, IOException {
		double predicted = 0;
		inst = new Instances(new FileReader(this.TrainingDateFile));
		int cIdx = inst.numAttributes() - 1;
		inst.setClassIndex(cIdx);
		// add a new sentence

		int attributeNum = inst.numAttributes();
		// attributeNum++;
		// System.out.println("inst="+inst);
		if (predictedInstance.length > attributeNum) {
			return -1;
		}
		Instance instance = new DenseInstance(attributeNum);
		// instance.setClassIndex(10);

		for (int i = 0; i < predictedInstance.length; i++) {
			instance.setValue(inst.attribute(i), predictedInstance[i]);
		}

		instance.setDataset(inst);
		// System.out.println("测试样本为:"+instance);
		try {
			predicted = svm.classifyInstance(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return predicted;

	}

	/**
	 * save the trained classifier to Disk
	 * 
	 * @param classifier
	 *            -the classifier to be saved
	 * @param modelname
	 *            -file name
	 */
	public void saveModel(Object classifier, String modelname) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("model/" + modelname));
			oos.writeObject(classifier);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * load the model from disk
	 * 
	 * @param file
	 *            -the model filename
	 * @return-the trained classifier
	 */
	public static Object loadModel(String file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					file));
			Object classifier = ois.readObject();
			ois.close();
			return classifier;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] ops2 = { "-t", "trainingdata/weather.nominal.arff",

		"-L", "0.0010", "-N", "1", "-V",
				"10", // k-fold cross-validation
				"-K", "weka.classifiers.functions.supportVector.PolyKernel",
				"-W", "1" };
		SVMStrategy svm = new SVMStrategy();
		SMO s = new SMO();
		svm.setoption(ops2);
		String[] predictedData = { "59.99", "60.0", "418.0", "778674",
				"528735", "967181", "149394.0", "81975", "94950", "138113",
				"93129", "0" };
		int[] prediction = new int[predictedData.length];
		for (int i = 0; i < prediction.length; i++) {
			prediction[i] = (int) Double.parseDouble(predictedData[i]);
		}
		s = (SMO) svm.loadModel("model/svm.model");
		svm.setSVM(s);
		double result = svm.predicted(prediction);
		System.out.println("confusion level : " + result);
	}

	@Override
	public int analyze(int[] featureVector) {
		double predicted = 0;
		try {
			inst = new Instances(new FileReader(this.TrainingDateFile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int cIdx = inst.numAttributes() - 1;
		inst.setClassIndex(cIdx);
		// add a new sentence

		int attributeNum = inst.numAttributes();
		// attributeNum++;
		// System.out.println("inst="+inst);
		if (featureVector.length > attributeNum) {
			return -1;
		}
		Instance instance = new DenseInstance(attributeNum);
		// instance.setClassIndex(10);

		for (int i = 0; i < featureVector.length; i++) {
			instance.setValue(inst.attribute(i), featureVector[i]);
		}

		instance.setDataset(inst);
		try {
			predicted = svm.classifyInstance(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) predicted;
	}

}

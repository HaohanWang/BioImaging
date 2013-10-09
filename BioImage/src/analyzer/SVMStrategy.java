package analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import weka.core.DenseInstance;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class SVMStrategy implements Strategy {
	private SMO svm;

	private Instances inst = null;

	public void setSVM(SMO s) {
		this.svm = s;
	}

	public SVMStrategy() {
		// Manually construct an Instances for use
		setUpInstances();

		// Load the SVM classifier from a pre-trained model
		SMO s = new SMO();
		s = (SMO) loadModel("model/svm.model");
		this.setSVM(s);
		String[] ops2 = { "-L", "0.0010", "-N", "1", "-V",
				"10", // k-fold cross-validation
				"-K", "weka.classifiers.functions.supportVector.PolyKernel",
				"-W", "1" };
		try {
			s.setOptions(ops2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setUpInstances() {
		ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
		for (int i = 0; i < 11; i++) {
			Attribute att = new Attribute("att" + i);
			attributeList.add(att);
		}
		ArrayList<String> labelValue = new ArrayList<String>();
		labelValue.add("0");
		labelValue.add("1");
		Attribute label = new Attribute("label", labelValue);
		attributeList.add(label);
		inst = new Instances("testInstance", attributeList, 0);
		inst.setClassIndex(inst.numAttributes() - 1);
	}

	public SVMStrategy(String[] ops) {
		svm = new SMO();
		try {
			svm.setOptions(ops);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Instances getInstances() {
		return this.inst;
	}

	/**
	 * load the model from disk
	 * 
	 * @param file
	 *            -the model filename
	 * @return-the trained classifier
	 */
	private Object loadModel(String file) {
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

	public static void main(String[] args) throws Exception {
		SVMStrategy svm = new SVMStrategy();
		String[] predictedData = { "59.99", "60.0", "418.0", "778674",
				"528735", "967181", "149394.0", "81975", "94950", "138113",
				"93129", "0" };
		double[] prediction = new double[predictedData.length];
		for (int i = 0; i < prediction.length; i++) {
			prediction[i] = Double.parseDouble(predictedData[i]);
		}
		double result = svm.analyze(prediction);
		System.out.println("confusion level : " + result);
	}

	@Override
	public int analyze(double[] featureVector) {
		double predicted = 0;

		int attributeNum = inst.numAttributes();
		if (featureVector.length > attributeNum) {
			return -1;
		}
		Instance instance = new DenseInstance(attributeNum);

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

package analyzer;

import objectModel.SignalNode;
import synchronization.Consumer;
import synchronization.Producer;
import synchronization.SynchronizedBuffer;

public class Analyzer extends Thread implements Consumer, Producer {
	private Strategy strategy;
	private SynchronizedBuffer inputBuffer;
	private SynchronizedBuffer outputBuffer;

	public Analyzer(Strategy strategy, SynchronizedBuffer inputBuffer,
			SynchronizedBuffer outputBuffer) {
		this.strategy = strategy;
		this.inputBuffer = inputBuffer;
		this.outputBuffer = outputBuffer;
	}

	public int executeAnalyze(double[] featureVector) {
		return this.strategy.analyze(featureVector);
	}

	@Override
	public void consume(SynchronizedBuffer buffer) {
		SignalNode node = null;
		try {
			node = buffer.takeFirst();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (node != null) {
			double[] featureVector = new double[12];
			featureVector[0] = node.getConcentration();
			featureVector[1] = node.getMeditation();
			featureVector[2] = node.getRaw();
			featureVector[3] = node.getDelta();
			featureVector[4] = node.getTheta();
			featureVector[5] = node.getAlpha1();
			featureVector[6] = node.getAlpha2();
			featureVector[7] = node.getBeta1();
			featureVector[8] = node.getBeta2();
			featureVector[9] = node.getGamma1();
			featureVector[10] = node.getGamma2();
			featureVector[11] = node.getConfusion();

			node.setConfusion(this.executeAnalyze(featureVector));
			if (node.getConfusion() != -1) {
				produce(node, outputBuffer);
			}
		}
	}

	public void produce(SignalNode node, SynchronizedBuffer buffer) {
		try {
			buffer.putFirst(node);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			consume(inputBuffer);
		}
	}
}

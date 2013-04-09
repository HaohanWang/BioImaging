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

	public int executeAnalyze(int[] featureVector) {
		return this.strategy.analyze(featureVector);
	}

	@Override
	public void consume(SynchronizedBuffer buffer) {
		// TODO Auto-generated method stub
		SignalNode node = null;
		try {
			node = buffer.takeFirst();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (node != null) {
			int[] featureVector = new int[12];
			featureVector[0] = (int) node.getConcentration();
			featureVector[1] = (int) node.getMeditation();
			featureVector[2] = (int) node.getRaw();
			featureVector[3] = (int) node.getDelta();
			featureVector[4] = (int) node.getTheta();
			featureVector[5] = (int) node.getAlpha1();
			featureVector[6] = (int) node.getAlpha2();
			featureVector[7] = (int) node.getBeta1();
			featureVector[8] = (int) node.getBeta2();
			featureVector[9] = (int) node.getGamma1();
			featureVector[10] = (int) node.getGamma2();
			featureVector[11] = (int) node.getConfusion();

			node.setConfusion(this.executeAnalyze(featureVector));
			produce(node, outputBuffer);
		}
	}

	public void produce(SignalNode node, SynchronizedBuffer buffer) {
		try {
			buffer.putFirst(node);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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

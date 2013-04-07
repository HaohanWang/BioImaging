package analyzer;

import objectModel.SignalNode;
import synchronization.Consumer;
import synchronization.SynchronizedBuffer;

public class Analyzer extends Thread implements Consumer {
	private Strategy strategy;
	private SynchronizedBuffer buffer;

	public Analyzer(Strategy strategy, SynchronizedBuffer buffer) {
		this.strategy = strategy;
		this.buffer = buffer;
	}

	public int executeAnalyze(int[] featureVector) {
		return this.strategy.analyze(featureVector);
	}

	@Override
	public void consume() {
		// TODO Auto-generated method stub
		SignalNode node = null;
		try {
			node = buffer.take();
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

			this.executeAnalyze(featureVector);
		}
	}

	@Override
	public void run() {
		consume();
	}
}

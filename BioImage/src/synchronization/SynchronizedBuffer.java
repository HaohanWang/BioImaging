package synchronization;

import java.util.concurrent.ArrayBlockingQueue;

import objectModel.SignalNode;

public class SynchronizedBuffer extends ArrayBlockingQueue<SignalNode> {
	private static final int INITIAL_CAPACITY = 100;

	public SynchronizedBuffer() {
		super(INITIAL_CAPACITY);
	}

	// Return the first node whose time stamp is larger than the given time
	public SignalNode getNode(long timestamp) throws InterruptedException {
		SignalNode node = this.take();
		while(node.getTimestamp() < timestamp){
			node = this.take();
		}
		return node;
	}
}

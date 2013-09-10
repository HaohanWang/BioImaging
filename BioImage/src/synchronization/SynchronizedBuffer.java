package synchronization;

import java.util.concurrent.LinkedBlockingDeque;

import objectModel.SignalNode;

public class SynchronizedBuffer extends LinkedBlockingDeque<SignalNode> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int INITIAL_CAPACITY = 10000;

	public SynchronizedBuffer() {
		super(INITIAL_CAPACITY);
	}

	// Return the first node whose time stamp is larger than the given time
	public SignalNode getNode(long timestamp) throws InterruptedException {
		SignalNode node = this.takeFirst();
		while(node.getTimestamp() < timestamp){
			node = this.takeFirst();
		}
		return node;
	}
}

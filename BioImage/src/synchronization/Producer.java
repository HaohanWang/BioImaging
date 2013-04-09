package synchronization;

import objectModel.SignalNode;

public interface Producer {
  public void produce(SignalNode node, SynchronizedBuffer buffer);
}

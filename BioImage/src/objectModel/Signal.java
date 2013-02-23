package objectModel;

import java.util.ArrayList;
import java.util.List;

public class Signal {
  private int nodeCount;
  private ArrayList<SignalNode> signalData;
  
  public int getNodeCount(){
    return this.nodeCount;
  }
  public void addNode(SignalNode node){
    this.signalData.add(node);
  }
  public void deteteNode(SignalNode node){
    if (signalData.contains(node)){
      signalData.remove(node);
    }
  }
  public void deleteNode(int index){
    if (index<=signalData.size()-1){
      signalData.remove(index);
    }
  }
  public ArrayList<SignalNode> getAllNodes(){
    return signalData;
  }
}

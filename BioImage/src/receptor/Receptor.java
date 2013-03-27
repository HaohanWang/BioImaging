package receptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import objectModel.SignalNode;
import ui.BrainPanel;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class Receptor extends Thread {
	private Object tgInstance;
	private Method getNodeMethod;
	private List<SignalNode> nodeList;
	private int currentNode;
	private EmbeddedMediaPlayer mediaPlayer;

	public Receptor(List<SignalNode> nodeList) {
		this.nodeList = nodeList;
		try {
			Class test = Class.forName("ThinkGear");
			Class[] nullClass = new Class[] {};
			Object[] nullObject = new Object[] {};
			Constructor constructor = test.getConstructor(nullClass);
			tgInstance = constructor.newInstance(nullObject);
			getNodeMethod = test.getMethod("getSignalNode", nullClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setMediaPlayer(EmbeddedMediaPlayer mediaPlayer){
		this.mediaPlayer = mediaPlayer;
	}
	
	public SignalNode getNode() {
		if (currentNode < nodeList.size()) {
			return nodeList.get(currentNode++);
		}
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			SignalNode node;
			try {
				node = (SignalNode) getNodeMethod.invoke(tgInstance);
				node.setTimestamp(mediaPlayer.getTime());
				nodeList.add(node);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		List<SignalNode> nodeList = new ArrayList<SignalNode>();
		Receptor r = new Receptor(nodeList);
		r.start();
	}
}

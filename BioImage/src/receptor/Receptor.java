package receptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import objectModel.SignalNode;
import synchronization.Producer;
import synchronization.SynchronizedBuffer;
import thinkGearCommunications.ThinkGearAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class Receptor extends Thread implements Producer {
	private ThinkGearAdapter tgAdapter;
	private SynchronizedBuffer buffer;
	private EmbeddedMediaPlayer mediaPlayer;
	private boolean testMode;

	public Receptor(SynchronizedBuffer buffer, boolean testMode) {
		tgAdapter = ThinkGearAdapter.getInstance();
		this.testMode = testMode;
		this.buffer = buffer;
	}

	public void setMediaPlayer(EmbeddedMediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!testMode) {
			while (true) {
				SignalNode node;
				node = tgAdapter.getSignalNode();
				node.setTimestamp(mediaPlayer.getTime());
				this.produce(node, buffer);
			}
		} else {
			File demoFile = new File("labeledData.txt");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						demoFile));
				String line;
				while ((line = reader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line, ",");
					SignalNode node = new SignalNode();
					node.setTimestamp(Long.parseLong(st.nextToken()));
					st.nextToken();
					st.nextToken();
					node.setConcentration(Double.parseDouble(st.nextToken()));
					node.setMeditation(Double.parseDouble(st.nextToken()));
					node.setRaw(Double.parseDouble(st.nextToken()));
					node.setDelta(Double.parseDouble(st.nextToken()));
					node.setTheta(Double.parseDouble(st.nextToken()));
					node.setAlpha1(Double.parseDouble(st.nextToken()));
					node.setAlpha2(Double.parseDouble(st.nextToken()));
					node.setBeta1(Double.parseDouble(st.nextToken()));
					node.setBeta2(Double.parseDouble(st.nextToken()));
					node.setGamma1(Double.parseDouble(st.nextToken()));
					node.setGamma2(Double.parseDouble(st.nextToken()));
					node.setConfusion(Integer.parseInt(st.nextToken()));
					this.produce(node, buffer);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		SynchronizedBuffer buffer = new SynchronizedBuffer();
		Receptor r = new Receptor(buffer, true);
		r.start();
	}

	@Override
	public void produce(SignalNode node, SynchronizedBuffer buffer) {
		// TODO Auto-generated method stub
		try {
			buffer.putFirst(node);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

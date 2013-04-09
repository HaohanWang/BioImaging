package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import objectModel.SignalNode;
import receptor.Receptor;
import synchronization.Consumer;
import synchronization.Producer;
import synchronization.SynchronizedBuffer;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

import analyzer.Analyzer;
import analyzer.SVMStrategy;
import analyzer.Strategy;

import com.sun.jna.NativeLibrary;

public class VLCJFrame extends JFrame implements ActionListener, Consumer,
		Producer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final long DELAY = 500;

	private boolean testMode;

	private Strategy analyzerStrategy;
	private Analyzer analyzer;

	private EmbeddedMediaPlayer mediaPlayer;
	private JPanel buttonPanel;
	private JPanel progressPanel;
	private JPanel buttonAndProgressPanel;
	private BrainPanel brainWavePanel;
	private JPanel progressAndBrainPanel;
	private JProgressBar progressBar;
	private JFileChooser fileChooser;
	private String[] mediaOptions;
	private Receptor rec;
	private SynchronizedBuffer rawDataBuffer;
	private SynchronizedBuffer analyzedDataBuffer;
	private SynchronizedBuffer reportDataBuffer;

	public static void main(String[] args) {

		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VLCJFrame(true);
			}
		});
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
		rec.setTestMode(testMode);
	}

	private void initializeVideoCanvas() {
		// String[] libvlcArgs;
		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
		FullScreenStrategy fullScreenStrategy = new DefaultFullScreenStrategy(
				this);
		mediaPlayer = mediaPlayerFactory
				.newEmbeddedMediaPlayer(fullScreenStrategy);
		String[] standardMediaOptions = { "video-filter=logo",
				"logo-file=vlcj-logo.png", "logo-opacity=25" };
		mediaPlayer.setStandardMediaOptions(standardMediaOptions);

		Canvas canvas = new Canvas();
		canvas.setVisible(true);
		canvas.setSize(300, 300);
		canvas.setLocation(0, 0);

		add(canvas, BorderLayout.NORTH);

		CanvasVideoSurface videoSurface = mediaPlayerFactory
				.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

	}

	private void initializeButtonPanel() {
		JCheckBox resumeStopButton = new JCheckBox();
		// URL url = getClass().getResource("a.png");
		// if (url == null)
		// System.out.println("Error");
		resumeStopButton.setSize(30, 30);

		ImageIcon stopIcon = new ImageIcon(
				"icons/Actions-media-playback-pause-icon.png");
		Image stopImage = stopIcon.getImage();
		stopIcon.setImage(stopImage.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH));

		ImageIcon resumeIcon = new ImageIcon(
				"icons/Actions-media-playback-start-icon.png");
		Image resumeImage = resumeIcon.getImage();
		resumeIcon.setImage(resumeImage.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH));

		resumeStopButton.setIcon(resumeIcon);
		resumeStopButton.setSelectedIcon(stopIcon);

		resumeStopButton.setActionCommand("stopOrResume");
		resumeStopButton.addActionListener(this);

		JCheckBox forwardButton = new JCheckBox();
		JCheckBox backwardButton = new JCheckBox();

		ImageIcon forwardIcon = new ImageIcon(
				"icons/Actions-media-seek-forward-icon.png");
		Image forwardImage = forwardIcon.getImage();
		forwardIcon.setImage(forwardImage.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH));
		forwardButton.setIcon(forwardIcon);

		ImageIcon backwardIcon = new ImageIcon(
				"icons/Actions-media-seek-backward-icon.png");
		Image backwardImage = backwardIcon.getImage();
		backwardIcon.setImage(backwardImage.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH));
		backwardButton.setIcon(backwardIcon);

		forwardButton.setActionCommand("forward");
		forwardButton.addActionListener(this);
		backwardButton.setActionCommand("backward");
		backwardButton.addActionListener(this);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		buttonPanel.add(backwardButton);
		buttonPanel.add(resumeStopButton);
		buttonPanel.add(forwardButton);

	}

	private void initializeProgressPanel() {
		progressPanel = new JPanel();
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(350, 20));
		progressPanel.add(progressBar, BorderLayout.CENTER);
		progressPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		progressPanel.setPreferredSize(new Dimension(370, 40));
	}

	private void initializeBrainWavePanel() {
		brainWavePanel = new BrainPanel();

	}

	public VLCJFrame(boolean testMode) {
		this.testMode = testMode;
		rawDataBuffer = new SynchronizedBuffer();
		analyzedDataBuffer = new SynchronizedBuffer();
		reportDataBuffer = new SynchronizedBuffer();
		rec = new Receptor(rawDataBuffer, testMode);
		analyzerStrategy = new SVMStrategy();
		analyzer = new Analyzer(analyzerStrategy, rawDataBuffer,
				analyzedDataBuffer);
		this.setSize(400, 580);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		File test = new File("test.mp4");
		fileChooser = new JFileChooser(test.getAbsolutePath());

		mediaOptions = new String[] { "video-filter=logo",
				"logo-file=vlcj-logo.png", "logo-opacity=25" };

		buttonAndProgressPanel = new JPanel();
		buttonAndProgressPanel.setLayout(new BorderLayout());
		progressAndBrainPanel = new JPanel();
		progressAndBrainPanel.setLayout(new FlowLayout());
		initializeVideoCanvas();
		initializeButtonPanel();
		initializeProgressPanel();
		initializeBrainWavePanel();

		progressAndBrainPanel.add(progressPanel);
		progressAndBrainPanel.add(brainWavePanel);
		progressAndBrainPanel.setPreferredSize(new Dimension(350, 150));

		buttonAndProgressPanel.add(progressAndBrainPanel, BorderLayout.NORTH);
		buttonAndProgressPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonAndProgressPanel.setPreferredSize(new Dimension(350, 200));
		add(buttonAndProgressPanel, BorderLayout.CENTER);
	}

	private boolean isPaused() {
		if (mediaPlayer.isPlaying())
			return false;
		if (mediaPlayer.getPosition() == -1.0)
			return false;

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Stop or resume the video
		if (e.getActionCommand().equals("stopOrResume")) {
			JCheckBox cb = (JCheckBox) e.getSource();
			if (cb.isSelected()) {
				if (isPaused()) {
					mediaPlayer.play();
				} else if (!mediaPlayer.isPlaying()) {

					int returnVal = fileChooser.showOpenDialog(this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File videoFile = fileChooser.getSelectedFile();
						String mediaPath = videoFile.getAbsolutePath();
						mediaPlayer.playMedia(mediaPath, mediaOptions);
						Timer progressBarUpdateTimer = new Timer(1000, this);
						progressBarUpdateTimer
								.setActionCommand("updateProgressBar");
						progressBarUpdateTimer.start();

						Timer brainWaveTimer = new Timer(1000, this);
						brainWaveTimer.setActionCommand("updateBrainWave");
						brainWaveTimer.start();
						rec.setMediaPlayer(mediaPlayer);
						rec.start();
						analyzer.start();
					} else {

					}

				}
			} else {
				if (mediaPlayer.canPause()) {
					mediaPlayer.pause();
				}
			}

		}
		// Update the progress bar of the video
		if (e.getActionCommand().equals("updateProgressBar")) {
			progressBar.setValue((int) (mediaPlayer.getPosition() * 100) + 1);
		}
		// Update the brainwave panel
		if (e.getActionCommand().equals("updateBrainWave")) {
			consume(analyzedDataBuffer);
		}
		// Button that Move Forward the video
		if (e.getActionCommand().equals("forward")) {
			if (mediaPlayer.getPosition() != -1.0) {
				float currentPosition = mediaPlayer.getPosition() + 0.05F;
				if (currentPosition >= 1.0)
					currentPosition = 0.99F;
				mediaPlayer.setPosition(currentPosition);
				int x = (int) (mediaPlayer.getPosition() * brainWavePanel
						.getWidth());
				brainWavePanel.setPreviousX(x);
			}
		}
		// Button that Move Backward the video
		if (e.getActionCommand().equals("backward")) {
			if (mediaPlayer.getPosition() != -1.0) {
				float currentPosition = mediaPlayer.getPosition() - 0.05F;
				if (currentPosition <= 0.0)
					currentPosition = 0.01F;
				mediaPlayer.setPosition(currentPosition);
				int x = (int) (mediaPlayer.getPosition() * brainWavePanel
						.getWidth());
				brainWavePanel.setPreviousX(x);
			}
		}

	}

	@Override
	public void consume(SynchronizedBuffer buffer) {
		// TODO Auto-generated method stub
		SignalNode node = null;
		long timestamp = mediaPlayer.getTime() - DELAY;
		try {
			node = buffer.takeFirst();
			while(node.getTimestamp() < timestamp){
				node = buffer.takeFirst();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (node != null) {
			int concentration = (int) node.getConcentration();
			int meditation = (int) node.getMeditation();
			int confusion = node.getConfusion();
			System.out.println(node.getConcentration() + ","
					+ node.getMeditation());

			int concentrationY = (int) ((1.0 - concentration / 100.0) * brainWavePanel
					.getHeight());
			int meditationY = (int) ((1.0 - meditation / 100.0) * brainWavePanel
					.getHeight());
			int x = (int) (mediaPlayer.getPosition() * brainWavePanel
					.getWidth());
			brainWavePanel.paintLines(x, concentrationY, meditationY, confusion);
			produce(node, reportDataBuffer);
		}
		brainWavePanel.repaint();
	}

	@Override
	public void produce(SignalNode node, SynchronizedBuffer buffer) {
		// TODO Auto-generated method stub
		try {
			buffer.putLast(node);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

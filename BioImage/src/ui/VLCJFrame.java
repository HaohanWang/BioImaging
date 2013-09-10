package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

	private static final long CONFUSING_VIDEO_NUMBER = 9;
	private static final long EASY_VIDEO_NUMBER = 5;

	private Strategy analyzerStrategy;
	private Analyzer analyzer;

	private Timer progressBarUpdateTimer;
	private Timer brainWaveTimer;

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
	private List<String> watchedTrainVideos;
	private int watchedConfusedVideoCount;
	private int watchedUnconfusedVideoCount;
	private File trainingData;
	private BufferedWriter trainingDataWriter;

	private CardLayout cardLayout;
	private JPanel centralPanel;
	private JPanel instructionPanel;
	private JTextArea instructions;
	private int countDown;
	private String previousVideo;
	private HashMap<String, Integer> videoRateMap;
	private Timer countDownTimer;
	private List<String> videoSequenceList;

	private boolean train;
	private JCheckBox resumeStopButton;
	private int confused;

	private String userName;

	public static void main(String[] args) {

		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VLCJFrame(true, "");
			}
		});
	}

	public void setTestMode(boolean testMode) {
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
		canvas.setSize(400, 300);
		canvas.setLocation(0, 0);

		cardLayout = new CardLayout();
		centralPanel = new JPanel();
		centralPanel.setSize(400, 300);
		centralPanel.setLayout(cardLayout);
		centralPanel.setVisible(true);

		instructionPanel = new JPanel();
		instructionPanel.setSize(400, 300);
		instructions = new JTextArea();

		instructions.append("You are starting with the training option" + "\n");
		instructions
				.append("You will be watching 10 clips of videos each of length 2 minutes."
						+ "\n");
		instructions
				.append("You will have 30 seconds between the videos for resting your mind."
						+ "\n");
		instructions
				.append("Are you ready? Click the start button to start your test."
						+ "\n");
		instructions.setEditable(false);

		JPanel temporyButtonPanel = new JPanel(
				new FlowLayout(FlowLayout.CENTER));

		JButton startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setActionCommand("startTraining");
		temporyButtonPanel.add(startButton);

		instructionPanel.add(instructions, BorderLayout.CENTER);
		instructionPanel.add(temporyButtonPanel, BorderLayout.SOUTH);

		centralPanel.add(canvas, "videoPanel");
		centralPanel.add(instructionPanel, "instructionPanel");

		add(centralPanel, BorderLayout.NORTH);

		cardLayout.show(centralPanel, "videoPanel");

		CanvasVideoSurface videoSurface = mediaPlayerFactory
				.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

	}

	private void initializeButtonPanel() {
		resumeStopButton = new JCheckBox();
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

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private void countDown(int seconds) {
		cardLayout.show(centralPanel, "instructionPanel");
		countDown = seconds;
		instructionPanel.removeAll();
		instructions.setText("");
		instructions.append("You now have " + countDown + " seconds to rest");
		instructionPanel.add(instructions, BorderLayout.CENTER);
		JRadioButton rate1Button = new JRadioButton("1");
		JRadioButton rate2Button = new JRadioButton("2");
		JRadioButton rate3Button = new JRadioButton("3");
		JRadioButton rate4Button = new JRadioButton("4");
		JRadioButton rate5Button = new JRadioButton("5");
		JRadioButton rate6Button = new JRadioButton("6");
		JRadioButton rate7Button = new JRadioButton("7");
		rate1Button.addActionListener(this);
		rate2Button.addActionListener(this);
		rate3Button.addActionListener(this);
		rate4Button.addActionListener(this);
		rate5Button.addActionListener(this);
		rate6Button.addActionListener(this);
		rate7Button.addActionListener(this);
		rate1Button.setActionCommand("rate");
		rate2Button.setActionCommand("rate");
		rate3Button.setActionCommand("rate");
		rate4Button.setActionCommand("rate");
		rate5Button.setActionCommand("rate");
		rate6Button.setActionCommand("rate");
		rate7Button.setActionCommand("rate");
		ButtonGroup rateGroup = new ButtonGroup();
		JPanel temporyRatePanel = new JPanel();
		JPanel temporyButtonPanel = new JPanel();

		temporyRatePanel.setLayout(new BorderLayout());
		temporyButtonPanel.setLayout(new GridLayout(1, 7));

		temporyButtonPanel.add(rate1Button);
		temporyButtonPanel.add(rate2Button);
		temporyButtonPanel.add(rate3Button);
		temporyButtonPanel.add(rate4Button);
		temporyButtonPanel.add(rate5Button);
		temporyButtonPanel.add(rate6Button);
		temporyButtonPanel.add(rate7Button);

		rateGroup.add(rate1Button);
		rateGroup.add(rate2Button);
		rateGroup.add(rate3Button);
		rateGroup.add(rate4Button);
		rateGroup.add(rate5Button);
		rateGroup.add(rate6Button);
		rateGroup.add(rate7Button);

		temporyRatePanel.add(new JLabel("Please rate the previous video("
				+ previousVideo + ") :"), BorderLayout.NORTH);
		temporyButtonPanel.setPreferredSize(new Dimension(300, 100));

		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new FlowLayout());
		descriptionPanel.setPreferredSize(new Dimension(300, 100));

		descriptionPanel.add(new JLabel("Least Confused"), FlowLayout.LEFT);
		descriptionPanel.add(new JLabel("Neutual"), FlowLayout.CENTER);
		descriptionPanel.add(new JLabel("Most Confused"), FlowLayout.RIGHT);

		JPanel temporyPanel = new JPanel();
		temporyPanel.setLayout(new GridLayout(2, 1));
		temporyPanel.add(temporyButtonPanel);
		temporyPanel.add(descriptionPanel);

		temporyRatePanel.add(temporyPanel, BorderLayout.CENTER);

		instructionPanel.add(temporyRatePanel, BorderLayout.SOUTH);
		countDownTimer = new Timer(1000, this);
		countDownTimer.setActionCommand("countDown");
		countDownTimer.start();
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

	public VLCJFrame(boolean testMode, String userName) {
		this.userName = userName;
		videoSequenceList = new ArrayList<String>();
		videoRateMap = new HashMap<String, Integer>();
		rawDataBuffer = new SynchronizedBuffer();
		analyzedDataBuffer = new SynchronizedBuffer();
		reportDataBuffer = new SynchronizedBuffer();
		rec = new Receptor(rawDataBuffer, testMode);
		watchedTrainVideos = new ArrayList<String>();
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

		rec.setMediaPlayer(mediaPlayer);
		rec.start();
	}

	private boolean isPaused() {
		if (mediaPlayer.isPlaying())
			return false;
		if (mediaPlayer.getPosition() == -1.0)
			return false;

		return true;
	}

	private File getNextVideo() {
		String fileName = "";
		do {
			fileName = "trainingVideo/";
			double confuse = Math.random();

			if (confuse > 0.5D && watchedConfusedVideoCount < 5) {
				fileName = fileName + "confused";
				this.confused = 1;
			} else {
				fileName = fileName + "easy";
				this.confused = 0;
			}
			if (confused == 0) {
				int fileIndex = (int) (EASY_VIDEO_NUMBER * Math.random()) + 1;
				fileName = fileName + fileIndex + ".m4v";
			} else {
				int fileIndex = (int) (CONFUSING_VIDEO_NUMBER * Math.random()) + 1;
				fileName = fileName + fileIndex + ".m4v";
			}
		} while (watchedTrainVideos.contains(fileName));
		watchedTrainVideos.add(fileName);
		if (fileName.contains("confused"))
			watchedConfusedVideoCount++;
		else
			watchedUnconfusedVideoCount++;
		File videoFile = new File(fileName);
		previousVideo = fileName.substring(14, fileName.length() - 4);
		this.trainingData = new File("trainingData/" + userName + "_"
				+ fileName.substring(14, fileName.length() - 4) + "_"
				+ (watchedConfusedVideoCount + watchedUnconfusedVideoCount)
				+ ".txt");
		try {
			trainingDataWriter = new BufferedWriter(
					new FileWriter(trainingData));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(trainingData.getAbsolutePath() + "start training");
		return videoFile;
	}

	public void train() {
		train = true;
		// countDown(30);
		cardLayout.show(centralPanel, "instructionPanel");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// Stop or resume the video
		if (e.getActionCommand().equals("rate")) {
			JRadioButton tempButton = (JRadioButton) e.getSource();
			if (!videoSequenceList.contains(previousVideo))
				videoSequenceList.add(previousVideo);
			this.videoRateMap.put(previousVideo,
					Integer.parseInt(tempButton.getText()));
			System.out.println(previousVideo + ":"
					+ videoRateMap.get(previousVideo));
		}
		if (e.getActionCommand().equals("startTraining")) {
			cardLayout.show(centralPanel, "videoPanel");
			resumeStopButton.doClick();
		}
		if (e.getActionCommand().equals("countDown")) {
			countDown--;
			if (countDown >= 0) {
				instructions.setText("You now have " + countDown
						+ " seconds to rest");
				centralPanel.repaint();
			} else {
				if (watchedConfusedVideoCount < 5
						|| watchedUnconfusedVideoCount < 5) {
					brainWavePanel.restart();
					File videoFile = getNextVideo();
					String mediaPath = videoFile.getAbsolutePath();
					cardLayout.show(centralPanel, "videoPanel");
					mediaPlayer.playMedia(mediaPath, mediaOptions);
					this.rawDataBuffer.clear();
					this.analyzedDataBuffer.clear();
					this.reportDataBuffer.clear();
					this.progressBarUpdateTimer.restart();
					this.brainWaveTimer.restart();
					this.countDownTimer.stop();
				} else {
					progressBarUpdateTimer.stop();
					brainWaveTimer.stop();
					instructions.setText("You have finished the training"
							+ "\n" + "Please exit now");
					File rateFile = new File("trainingdata/" + userName
							+ "_rate.txt");
					try {
						BufferedWriter rateWriter = new BufferedWriter(
								new FileWriter(rateFile));
						for (int i = 0; i < videoSequenceList.size(); i++) {
							String videoName = (String) videoSequenceList
									.get(i);
							rateWriter.write(videoName + ":"
									+ videoRateMap.get(videoName));
							rateWriter.newLine();
						}
						rateWriter.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		if (e.getActionCommand().equals("stopOrResume")) {
			JCheckBox cb = (JCheckBox) e.getSource();
			if (cb.isSelected()) {
				if (isPaused()) {
					mediaPlayer.play();
				} else if (!mediaPlayer.isPlaying()) {
					if (!train) {
						int returnVal = fileChooser.showOpenDialog(this);

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File videoFile = fileChooser.getSelectedFile();
							String mediaPath = videoFile.getAbsolutePath();
							mediaPlayer.playMedia(mediaPath, mediaOptions);
							progressBarUpdateTimer = new Timer(1000, this);
							progressBarUpdateTimer
									.setActionCommand("updateProgressBar");
							progressBarUpdateTimer.start();

							brainWaveTimer = new Timer(1000, this);
							brainWaveTimer.setActionCommand("updateBrainWave");
							brainWaveTimer.start();
							rec.startRecord();
							analyzer.start();
						} else {

						}
					} else {
						File videoFile = getNextVideo();
						String mediaPath = videoFile.getAbsolutePath();
						mediaPlayer.playMedia(mediaPath, mediaOptions);
						progressBarUpdateTimer = new Timer(1000, this);
						progressBarUpdateTimer
								.setActionCommand("updateProgressBar");
						progressBarUpdateTimer.start();
						brainWaveTimer = new Timer(1000, this);
						brainWaveTimer.setActionCommand("updateBrainWave");
						brainWaveTimer.start();
						rec.startRecord();
						analyzer.start();
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
			System.out.println(((int) (mediaPlayer.getPosition() * 100)));
			if (train && ((int) (mediaPlayer.getPosition() * 100)) >= 99) {
				try {
					trainingDataWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.brainWaveTimer.stop();
				this.progressBarUpdateTimer.stop();
				countDown(30);
			}
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
			while (node.getTimestamp() < timestamp) {
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
			System.out.println(node.getTimestamp() + ",0.0,0.0,"
					+ node.getConcentration() + "," + node.getMeditation()
					+ "," + node.getRaw() + "," + node.getDelta() + ","
					+ node.getTheta() + "," + node.getAlpha1() + ","
					+ node.getAlpha2() + "," + node.getBeta1() + ","
					+ node.getBeta2() + "," + node.getGamma1() + ","
					+ node.getGamma2() + "," + confused);
			try {
				trainingDataWriter.write(node.getTimestamp() + ",0.0,0.0,"
						+ node.getConcentration() + "," + node.getMeditation()
						+ "," + node.getRaw() + "," + node.getDelta() + ","
						+ node.getTheta() + "," + node.getAlpha1() + ","
						+ node.getAlpha2() + "," + node.getBeta1() + ","
						+ node.getBeta2() + "," + node.getGamma1() + ","
						+ node.getGamma2() + "," + confused);
				trainingDataWriter.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int concentrationY = (int) ((1.0 - concentration / 100.0) * brainWavePanel
					.getHeight());
			int meditationY = (int) ((1.0 - meditation / 100.0) * brainWavePanel
					.getHeight());
			int x = (int) (mediaPlayer.getPosition() * brainWavePanel
					.getWidth());
			brainWavePanel
					.paintLines(x, concentrationY, meditationY, confusion);
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

package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;
import com.sun.jna.Native;

public class VLCJFrame extends JFrame implements ActionListener {
    private EmbeddedMediaPlayer mediaPlayer;
    private JPanel buttonPanel;
    private JPanel progressPanel;
    private JPanel buttonAndProgressPanel;
    private JPanel brainWavePanel;
    private JPanel progressAndBrainPanel;
    private JProgressBar progressBar;
    private JFileChooser fileChooser;
    private String[] mediaOptions;

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new VLCJFrame();
	    }
	});
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
	brainWavePanel = new JPanel() {
	    private HashMap<Integer, Shape> shapes = new HashMap<Integer, Shape>();
	    private Shape currentShape = null;
	    private int currentX = 0;
	    private int currentY = 0;

	    private int previousX = 0;
	    private int previousY = 0;

	    public void paintLine() {
		currentShape = new Line2D.Double(previousX * 1.0D,
			previousY * 1.0D, currentX * 1.0D, currentY * 1.0D);
		if (!shapes.containsKey(currentX)) {
		    shapes.put(currentX, currentShape);
		    previousX = currentX;
		    previousY = currentY;
		}
	    }

	    protected void paintComponent(Graphics g) {
		int width = brainWavePanel.getWidth();
		int height = brainWavePanel.getHeight();
		if (mediaPlayer.getLength() != 0) {
		    currentX = (int) ((mediaPlayer.getTime() * 1.0)
			    / mediaPlayer.getLength() * width);
		    currentY = (++currentY) % height;
		    paintLine();
		}

		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i <= 4; i++) {
		    g2d.draw(new Line2D.Double(0.0D, 0.25D * i * height,
			    1.0D * (width - 1), 0.25D * i * height));
		}
		g2d.draw(new Line2D.Double(0.0D, 1.0D * (height - 1),
			1.0D * (width - 1), 1.0D * (height - 1)));
		for (int i = 0; i <= 10; i++) {
		    g2d.draw(new Line2D.Double(0.1D * i * width, 0.0D, 0.1D * i
			    * width, 1.0D * (height - 1)));
		}
		g2d.draw(new Line2D.Double(1.0D * (width - 1), 0.0D,
			1.0D * (width - 1), 1.0D * (height - 1)));
		g2d.setPaint(Color.BLACK);
		for (Shape shape : shapes.values()) {
		    g2d.draw(shape);
		}
	    }
	};
	brainWavePanel.setPreferredSize(new Dimension(350, 100));

    }

    public VLCJFrame() {

	this.setSize(400, 550);
	this.setVisible(true);
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
			Timer progressBarUpdateTimer = new Timer(50, this);
			progressBarUpdateTimer
				.setActionCommand("updateProgressBar");
			progressBarUpdateTimer.start();

			Timer brainWaveTimer = new Timer(50, this);
			brainWaveTimer.setActionCommand("updateBrainWave");
			brainWaveTimer.start();
		    } else {

		    }

		}
	    } else {
		if (mediaPlayer.canPause()) {
		    mediaPlayer.pause();
		}
	    }

	}
	if (e.getActionCommand().equals("updateProgressBar")) {
	    progressBar.setValue((int) (mediaPlayer.getPosition() * 100) + 1);
	}
	if (e.getActionCommand().equals("updateBrainWave")) {

	    brainWavePanel.repaint();
	}
	if (e.getActionCommand().equals("forward")){
	    if (mediaPlayer.getPosition() != -1.0){
		float currentPosition = mediaPlayer.getPosition() + 0.05F;
		if (currentPosition >= 1.0)
		    currentPosition = 0.99F;
		mediaPlayer.setPosition(currentPosition);
	    }
	}
	if (e.getActionCommand().equals("backward")){
	    if (mediaPlayer.getPosition() != -1.0){
		float currentPosition = mediaPlayer.getPosition() - 0.05F;
		if (currentPosition <= 0.0)
		    currentPosition = 0.01F;
		mediaPlayer.setPosition(currentPosition);
	    }
	}

    }
}

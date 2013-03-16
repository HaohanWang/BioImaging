package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

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

public class TestVlcj extends JFrame implements ActionListener {
    private EmbeddedMediaPlayer mediaPlayer;
    private JPanel buttonPanel;
    private JPanel progressPanel;
    private JPanel buttonAndProgressPanel;
    private JProgressBar progressBar;
    private JFileChooser fileChooser;
    private String[] mediaOptions;

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new TestVlcj();
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

	buttonPanel = new JPanel();
	buttonPanel.setLayout(new FlowLayout());

	buttonPanel.add(resumeStopButton);

    }
    
    private void initializeProgressPanel(){
	progressPanel = new JPanel();
	progressBar = new JProgressBar();
	progressBar.setPreferredSize(new Dimension(350, 20));
	progressPanel.add(progressBar);
    }

    public TestVlcj() {

	this.setSize(400, 450);
	this.setVisible(true);
	this.setLayout(new BorderLayout());
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	File test = new File("test.mp4");
	fileChooser = new JFileChooser(test.getAbsolutePath());
	
	mediaOptions = new String[] { "video-filter=logo",
		"logo-file=vlcj-logo.png", "logo-opacity=25" };

	buttonAndProgressPanel = new JPanel();
	buttonAndProgressPanel.setLayout(new BorderLayout());
	initializeVideoCanvas();
	initializeButtonPanel();
	initializeProgressPanel();
	
	buttonAndProgressPanel.add(progressPanel, BorderLayout.CENTER);
	buttonAndProgressPanel.add(buttonPanel, BorderLayout.SOUTH);
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
			Timer progressBarUpdateTimer = new Timer(1000, this);
			progressBarUpdateTimer.setActionCommand("updateProgressBar");
			progressBarUpdateTimer.start();
		    } else {

		    }

		}
	    } else {
		if (mediaPlayer.canPause()) {
		    mediaPlayer.pause();
		}
	    }

	}
	if (e.getActionCommand().equals("updateProgressBar")){
	    progressBar.setValue((int)(mediaPlayer.getPosition()*100) + 1);
	    this.repaint();
	}

    }
}

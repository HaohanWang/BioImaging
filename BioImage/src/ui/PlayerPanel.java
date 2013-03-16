package ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;


public class PlayerPanel extends JPanel{
    /*This is a class that creates a JPanel to hold a video player.
     * The video player used here adopt vlcj framework that uses
     * vlc opensource video player.
     * */
    
    private JPanel buttonPanel;
    private JPanel videoPanel;
    private boolean stopped;
    private JButton startStopButton;
    String[] libvlcArgs;
    MediaPlayerFactory mediaPlayerFactory;
    EmbeddedMediaPlayer embeddedMediaPlayer;
    
    public PlayerPanel(){
	mediaPlayerFactory = new MediaPlayerFactory();
	embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
	
    }
    
}

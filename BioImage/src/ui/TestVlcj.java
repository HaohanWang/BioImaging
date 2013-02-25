package ui;

import javax.swing.*;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;
import com.sun.jna.Native;

public class TestVlcj {
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          new TestVlcj();
        }
      });
    }
    
    private TestVlcj() {
      JFrame frame = new JFrame("vlcj Tutorial");
      
      mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

      frame.setContentPane(mediaPlayerComponent);
      
      frame.setLocation(100, 100);
      frame.setSize(1050, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      mediaPlayerComponent.getMediaPlayer().playMedia("/home/kane/workspace/BioImaging/test.mp4");
    }
}

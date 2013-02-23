package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.*;

import javax.swing.*;

public class VideoFrame extends JFrame implements ActionListener {
    private JPanel videoPanel;
    private JPanel buttonPanel;
    private JButton selectButton;
    private JButton cancelButton;
    private BorderLayout frameLayout;
    private FlowLayout buttonLayout;
    private JFileChooser fileChooser;
    private File videoFile;
    private Player player;

    public VideoFrame() {
	initComponents();
	this.setSize(650, 500);
    }

    private void initComponents() {
	videoPanel = new JPanel();
	buttonPanel = new JPanel();
	selectButton = new JButton("Select");
	cancelButton = new JButton("Cancel");
	frameLayout = new BorderLayout();
	buttonLayout = new FlowLayout();
	fileChooser = new JFileChooser();

	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLayout(frameLayout);

	selectButton.setActionCommand("select");
	cancelButton.setActionCommand("cancel");
	selectButton.addActionListener(this);
	cancelButton.addActionListener(this);

	buttonPanel.setLayout(buttonLayout);
	buttonPanel.add(selectButton);
	buttonPanel.add(cancelButton);

	this.add(videoPanel, BorderLayout.CENTER);
	this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
	VideoFrame vf = new VideoFrame();
	vf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

	String command = e.getActionCommand();
	if (command == "select") {
	    int retrvl = fileChooser.showDialog(this, null);
	    if (retrvl == JFileChooser.APPROVE_OPTION) {
		videoFile = fileChooser.getSelectedFile();
		if (videoFile.isDirectory()) {
		    JOptionPane.showMessageDialog(this,
			    "You chose this directory: "
				    + fileChooser.getSelectedFile()
					    .getAbsolutePath());
		} else {
		    JOptionPane.showMessageDialog(this, "You chose this file: "
			    + fileChooser.getSelectedFile().getAbsolutePath());
		}
		try {
		    player = Manager.createRealizedPlayer(videoFile.toURI()
			    .toURL());
		    Component c = player.getVisualComponent();
		    videoPanel.add(c);
		    player.start();
		} catch (NoPlayerException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (CannotRealizeException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (MalformedURLException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    } else
		System.out.println("Cancelled");

	}

    }
}

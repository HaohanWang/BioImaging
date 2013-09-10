package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.jna.NativeLibrary;

public class MainFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel loginInfoPanel;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPanel buttonPanel;
	private VLCJFrame vlcjFrame;

	public void addLoginPanel() {
		loginInfoPanel = new JPanel();
		loginInfoPanel.setLayout(new GridLayout(2, 2));

		userNameField = new JTextField();
		passwordField = new JPasswordField();

		loginInfoPanel.add(new JLabel("Username: "));
		loginInfoPanel.add(userNameField);
		loginInfoPanel.add(new JLabel("Password: "));
		loginInfoPanel.add(passwordField);

		this.add(loginInfoPanel, BorderLayout.CENTER);
	}

	public void addButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton loginButton = new JButton("Login");
		JButton testButton = new JButton("Test");
		JButton trainButton = new JButton("Train");

		loginButton.addActionListener(this);
		loginButton.setActionCommand("login");

		testButton.addActionListener(this);
		testButton.setActionCommand("test");

		trainButton.addActionListener(this);
		trainButton.setActionCommand("train");

		buttonPanel.add(loginButton);
		buttonPanel.add(testButton);
		buttonPanel.add(trainButton);

		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public MainFrame() {
		super();
		this.setSize(250, 110);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		this.setLayout(new BorderLayout());

		addLoginPanel();
		addButtonPanel();

		vlcjFrame = new VLCJFrame(false, "");

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("login")) {
			vlcjFrame.setUserName(userNameField.getText());
			this.setVisible(false);
			vlcjFrame.setVisible(true);
		}
		if (e.getActionCommand().equals("test")) {
			vlcjFrame.setUserName(userNameField.getText());
			this.setVisible(false);
			vlcjFrame.setTestMode(true);
			vlcjFrame.setVisible(true);
		}
		if (e.getActionCommand().equals("train")) {
			vlcjFrame.setUserName(userNameField.getText());
			this.setVisible(false);
			vlcjFrame.setVisible(true);
			vlcjFrame.train();
		}

	}

	public static void main(String[] args) {
		NativeLibrary.addSearchPath("libvlc", "C:/Program Files/VideoLAN/VLC");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});

	}
}

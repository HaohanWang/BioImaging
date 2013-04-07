package objectModel;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tutorial {

	String name;
	String fileName;
	int length;
	User unploader;
	ArrayList<String> tag;

	public static void main(String[] args) {
		JFrame jf = new JFrame("Interests");
		jf.setLocation(400, 300);
		// jf.setSize(800, 600);
		JPanel jp = new JPanel();
		jp.add(new TagLabel("Computer Science",
				"https://www.coursera.org/courses"));
		jp.add(new TagLabel("Electrical Engineer",
				"https://www.coursera.org/courses"));
		jp.add(new TagLabel("Biomedical Engineer",
				"https://www.coursera.org/courses"));
		jf.setContentPane(jp);
		jf.pack();
		jf.setVisible(true);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setUnploader(User unploader) {
		this.unploader = unploader;
	}

	public void setTag(ArrayList<String> tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return fileName;
	}

	public int getLength() {
		return length;
	}

	public User getUnploader() {
		return unploader;
	}

	public ArrayList<String> getTag() {
		return tag;
	}

	public void addTage(String tag) {

	}

	public static class TagLabel extends JLabel {
		private String text, url;
		private boolean isSupported;

		public TagLabel(String text, String url) {
			this.text = text;
			this.url = url;
			try {
				this.isSupported = Desktop.isDesktopSupported()
						&& Desktop.getDesktop().isSupported(
								Desktop.Action.BROWSE);
			} catch (Exception e) {
				this.isSupported = false;
			}

			setText(false);

			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					setText(isSupported);
					if (isSupported)
						setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				public void mouseExited(MouseEvent e) {
					setText(false);
				}

				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(
								new java.net.URI(TagLabel.this.url));
					} catch (Exception ex) {
					}
				}
			});
		}

		private void setText(boolean b) {
			if (!b)
				setText("<html><font color=blue><u>" + text);
			else
				setText("<html><font color=red><u>" + text);
		}
	}

}

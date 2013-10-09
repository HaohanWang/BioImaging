package objectModel;

import java.util.ArrayList;

public class Tutorial extends AuditableEntity{

	String name;
	String fileName;
	int length;
	User unploader;
	ArrayList<String> tag;

	public Tutorial(){
		super();
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

}

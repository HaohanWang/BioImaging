package objectModel;

import java.util.ArrayList;
import java.util.Date;

public class InstructorReport extends Report {
	private int stuCount = 0;
	private Date time = null;

	public int getStuCount(long courseID, ArrayList<Tutorial> tutorialList) {
		/*
		 * for(int i = 0; i < tutorialList.length; i++) { if(
		 * tutorialList.get(i).id == courseID) { stuCount++; } }
		 */

		return stuCount;
	}

}

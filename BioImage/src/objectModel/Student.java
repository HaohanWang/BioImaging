package objectModel;

import java.util.ArrayList;
//import java.util.Date;

public class Student extends User{
	
	public static void main(String[] args){
		Date date = new Date(1990, 4, 1);
		Date.displayDate(date); 
	}
	
	
	String school;
	String gradeLevel;
	Date graduateDate;
	ArrayList<String> interestArea;
		
	public String getSchool() {
		return school;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public Date getGraduateDate() {
		return graduateDate;
	}
	public ArrayList<String> getInterestArea() {
		return interestArea;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	public void setInterestArea(ArrayList<String> interestArea) {
		this.interestArea = interestArea;
	}
	
	public void addInterest(String interest){
		
	}

	public static class Date{
		int year;
		int month;
		int day;
		
		public Date(int year, int month, int day){
			this.year = year;
			this.month = month;
			this.day = day;			
		}
		
		public static void displayDate(Date date){
			System.out.print(date.month + "/");
			System.out.print(date.day + "/");
			System.out.print(date.year);
		}
	}
}



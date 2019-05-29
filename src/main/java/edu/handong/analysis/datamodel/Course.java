package edu.handong.analysis.datamodel;

import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
 
public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(CSVRecord csvRecord) { 
		
		// Split the line from constructor to initialize the field.
		
		this.studentId = ((CSVRecord) csvRecord).get(0);		
		this.yearMonthGraduated = csvRecord.get(1);
		this.firstMajor = csvRecord.get(2);
		this.secondMajor = csvRecord.get(3);
		this.courseCode = csvRecord.get(4);
		this.courseName = csvRecord.get(5);
		this.courseCredit = csvRecord.get(6);
		this.yearTaken = Integer.parseInt(csvRecord.get(7));
		this.semesterCourseTaken = Integer.parseInt(csvRecord.get(8));
	}
	
	//getter and setter
	public String getStudentId() {
		return this.studentId;
	}
	
	public void setStudentId(String student_Id) {
		this.studentId = student_Id;
	}
	
	public String getYearMonthGraduated() {
		return this.yearMonthGraduated;
	}
	
	public void setYearMonthGraduated(String Year_Month_Graduated) {
		this.yearMonthGraduated = Year_Month_Graduated;
	}
	
	public String getFirstMajor() {
		return this.firstMajor;
	}
	
	public void setFirstMajor(String First_Major) {
		this.firstMajor = First_Major;
	}
	
	public String getSecondMajor() {
		return this.secondMajor;
	}
	
	public void setSecondMajor(String Second_Major) {
		this.secondMajor = Second_Major;
	}
	
	public String getCourseCode() {
		return this.courseCode;
	}
	
	public void setCourseCode(String Course_Code) {
		this.courseCode = Course_Code;
	}
	
	public String getCourseName() {
		return this.courseName;
	}
	
	public void setCourseName(String Course_Name) {
		this.courseName = Course_Name;
	}
	
	public String getCourseCredit() {
		return this.courseCredit;
	}
	
	public void setCourseCredit(String Course_Credit) {
		this.courseCredit = Course_Credit;
	}
	
	public int getYearTaken() {
		return this.yearTaken;
	}
	
	public void setYearTaken(int Year_Taken) {
		this.yearTaken = Year_Taken;
	}
	
	public int getSemesterCourseTaken() {
		return this.semesterCourseTaken;
	}
	
	public void setSemesterCourseTaken(int Semester_Course_Taken) {
		this.semesterCourseTaken = Semester_Course_Taken;
	}

}

package edu.handong.analysis.datamodel;

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
	
	public Course(String line) { // Split the line from constructor to initialize the field.
	
	}
	/* Self-define getter and setter if needed*/
	
	public String getStudentId() {
		return this.studentId;
	}
	
	public void getStudentId(String student_Id) {
		this.studentId = student_Id;
	}
	
	public String getYearMonthGraduated() {
		return this.yearMonthGraduated;
	}
	
	public String getFirstMajor() {
		return this.firstMajor;
	}
	
	public String getSecondMajor() {
		return this.secondMajor;
	}
	
	public String getCourseCode() {
		return this.courseCode;
	}
	
	public String getCourseName() {
		return this.courseName;
	}
	
	public String getCourseCredit() {
		return this.courseCredit;
	}
	
	public int getYearTaken() {
		return this.yearTaken;
	}
	
	public int getSemesterCourseTaken() {
		return this.semesterCourseTaken;
	}

}

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
	
	public Course(String line) { 
		// Split the line from constructor to initialize the field.
		this.studentId = line.split(",")[0];
		this.yearMonthGraduated = line.split(",")[1];
		this.secondMajor = line.split(",")[2];
		this.courseCode = line.split(",")[3];
		this.courseName = line.split(",")[4];
		this.courseCredit = line.split(",")[5];
		this.yearTaken = Integer.parseInt(line.split(",")[6]);
		this.semesterCourseTaken = Integer.parseInt(line.split(",")[7]);
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

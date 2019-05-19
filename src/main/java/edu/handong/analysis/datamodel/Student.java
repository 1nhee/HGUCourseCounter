package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentId;
	private ArrayList<Course> coursesTaken; // List of courses student has taken
	private HashMap<String,Integer> semestersByYearAndSemester; 
	                                                         // key: Year-Semester
	                                                         // e.g., 2003-1, 
	public Student(String studentId) { // constructor
	}

	public void addCourse(Course newRecord) {
		///add a Course instance created while reading line to the CourseTaken ArrayList in the Student instance.
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		return semestersByYearAndSemester;
		//creates a hashmap to store the student’s sequential semester information by using strong year and semester information.
	}
	public int getNumCourseInNthSementer(int semester) {
		return semester;
		//returns the number of subjects in the semester if you enter the sequential semester number.
		//HashMap에 key로 학기 수를 입력하면 그 학기 때 들었던  과목의 수를 return
	}

	//getter and setter
	public String getStudentId() {
		return this.studentId;
	}
	
	public void setStudentId(String student_Id) {
		this.studentId = student_Id;
	}
	
	public ArrayList<Course> getCoursesTaken() {
		return this.coursesTaken;
	}
	
	public void setCoursesTaken(ArrayList<Course> Courses_Taken) {
		this.coursesTaken = Courses_Taken;
	}
	
	/*
	 * public HashMap<String,Integer> getSemestersByYearAndSemester(){ return
	 * this.semestersByYearAndSemester; }
	 */
	
	public void setSemestersByYearAndSemester(HashMap<String,Integer> Semesters_By_Year_And_Semester) {
		this.semestersByYearAndSemester = Semesters_By_Year_And_Semester;
	}
	
	
}

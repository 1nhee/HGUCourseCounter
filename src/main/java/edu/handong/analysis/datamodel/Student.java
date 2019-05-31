package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.utils.*;

public class Student {
	
	private String studentId;
	private ArrayList<Course> coursesTaken; // List of courses student has taken
	private ArrayList<String> yearsAndSemesters;
	private HashMap<String,Integer> semestersByYearAndSemester; 
	                                                         // key: Year-Semester
	                                                         // e.g., 2003-1, 
	
	
	public Student(String studentId) { // constructor
		this.studentId = studentId;
		coursesTaken = new ArrayList<Course>();
		semestersByYearAndSemester = new HashMap<String,Integer>();
	}

	public void addCourse(Course newRecord) {
		/*add a Course instance created 
		while reading line to the CourseTaken ArrayList in the Student instance.*/
		this.coursesTaken.add(newRecord);
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		/*creates a hashmap to store the student’s sequential semester information 
		by using string year and semester information.*/
		
		int yearToCheck = coursesTaken.get(0).getYearTaken();
		int semesterToCheck = coursesTaken.get(0).getSemesterCourseTaken();
		int whatSemester = 1;
		
		String startSemesterStrig  = yearToCheck + "-" + semesterToCheck; 
		
		semestersByYearAndSemester.put(startSemesterStrig, whatSemester);
		whatSemester++;
		
		for(Course courseToCheck:this.coursesTaken) {
				if(courseToCheck.getYearTaken() != yearToCheck || courseToCheck.getSemesterCourseTaken() != semesterToCheck) {
					yearToCheck = courseToCheck.getYearTaken();
					semesterToCheck = courseToCheck.getSemesterCourseTaken();
					
					if(!yearsAndSemesters.contains(yearToCheck + "-" + semesterToCheck)) {
						yearsAndSemesters.add(yearToCheck + "-" + semesterToCheck);
					}
					
					semestersByYearAndSemester.put(yearToCheck + "-" + semesterToCheck, whatSemester);
					whatSemester++;
				}
		}
		return this.semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		/*returns the number of subjects in the semester 
		 * if you enter the sequential semester number.
		HashMap에 key로 학기 수를 입력하면 그 학기 때 들었던  과목의 수를 return*/
		
		int numOfCoursesInNthSemester = 0;
		int yearToCheck;
		int semesterToCheck;
		int inputSemesterToCheck;
	
		for(Course courseToCheck:this.coursesTaken) {
			yearToCheck = courseToCheck.getYearTaken();
			semesterToCheck = courseToCheck.getSemesterCourseTaken();
			inputSemesterToCheck = this.semestersByYearAndSemester.get(yearToCheck + "-" + semesterToCheck);
			
			if(inputSemesterToCheck == semester) {
				numOfCoursesInNthSemester++;
			}
		} 
		return numOfCoursesInNthSemester;
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
	
	
	public ArrayList<String> getYearsAndSemesters() {
		return this.yearsAndSemesters;
	}
	
	public void setYearsAndSemesters(ArrayList<String> years_and_semesters) {
		this.yearsAndSemesters = years_and_semesters;
	}
	
	
	/*
	 * public HashMap<String,Integer> getSemestersByYearAndSemester(){ return
	 * this.semestersByYearAndSemester; }
	 */
	
	public void setSemestersByYearAndSemester(HashMap<String,Integer> Semesters_By_Year_And_Semester) {
		this.semestersByYearAndSemester = Semesters_By_Year_And_Semester;
	}
	
	
}

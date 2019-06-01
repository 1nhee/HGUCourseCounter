package edu.handong.analysis;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String, Student> students;

	// CLI Variables
	String Input_path;
	String Output_path;
	int Analysis_option;
	String course_code;
	int Start_year_for_analysis;
	int End_year_for_analysis;
	boolean Help;
	String courseNameByCourseCode;
	
	Options options = createOptions();

	/**
	 * This method runs our analysis logic to save the number courses taken by each
	 * student per semester in a result file. Run method must not be changed!!
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public void run(String[] args) throws IOException {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		if (parseOptions(options, args)) {
			if (Help) {
				printHelp(options);
				return;
			}
		}
		
		/*
		 * if(Input_path.isEmpty()) { System.out.
		 * println("The file path does not exist. Please check your CLI argument!");
		 * System.exit(0); }
		 */
		
		Reader reader = Files.newBufferedReader(Paths.get(Input_path));
		CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

		students = new HashMap<String, Student>();
		students = loadStudentCourseRecords(csvParser);
		
		// To sort HashMap entries by key values so that we can save the results by
		// student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String, Student>(students);
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				
		HashMap<String, Integer> totalStudents = new HashMap<String, Integer>();
		totalStudents = getTotalStudentsNumberByYearAndSemseter(students);
		
		HashMap<String, Integer> courseStudents = new HashMap<String, Integer>();
		courseStudents = getNumberOfStudentsByCourseCode(students);
		
		ArrayList<String> rateAdded = new ArrayList<String>();
		rateAdded = rateOfStudentsTakingTheGivenCourse(totalStudents, courseStudents);

		if (Analysis_option == 1) {
			Utils.writeAFile(linesToBeSaved, Output_path);
			System.out.println("Your result is made! Check this file: " + Output_path);
		} else if (Analysis_option == 2) {
			// Write a file (named like the value of resultPath) with linesTobeSaved.
			Utils.writeAFile(rateAdded, Output_path);
			System.out.println("Your result is made! Check this file: " + Output_path);
		}
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a
	 * student id and the corresponding object is an instance of Student. The
	 * Student instance have all the Course instances taken by the student.
	 * 
	 * @param lines
	 * @return
	 */
	private HashMap<String, Student> loadStudentCourseRecords(CSVParser csvParser) {

		// TODO: Implement this method 


		for (CSVRecord csvRecord : csvParser) {
			
			// Accessing values by Header names
			Course CourseToAdd = new Course(csvRecord);
			String studentIdToCheck = CourseToAdd.getStudentId();

			// if the student Id already exists in HashMap
			if (!students.containsKey(studentIdToCheck)) {
				Student studentToAdd = new Student(studentIdToCheck);
				studentToAdd.addCourse(CourseToAdd);
				students.put(studentIdToCheck, studentToAdd);
			}
			// if there is no same thing in HashMap
			else {
				Student studentInstanceToAddCourse = students.get(studentIdToCheck);
				studentInstanceToAddCourse.addCourse(CourseToAdd);
				students.put(studentIdToCheck, studentInstanceToAddCourse);
			}
		}

		return students; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each
	 * semester. The result file look like this: StudentID,
	 * TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9 0001,14,2,8 ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In
	 * the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
		ArrayList<String> numberOfCoursesTakenInEachSemester = new ArrayList<String>();

		for (int i = 0; i < sortedStudents.size(); i++) {
			
			String studentIdToCheck = String.format("%04d", i + 1);
			Student studentToCheck = sortedStudents.get(studentIdToCheck);
			String studentId = studentToCheck.getStudentId();
			int totalSemester = 0;
			
			studentToCheck.getYearsAndSemestersMethod();
			
			ArrayList<String> toCheckStartYearAndSemester = new ArrayList<String>();
			toCheckStartYearAndSemester = studentToCheck.getYearsAndSemesters();
			
			ArrayList<Integer> forNumOfInNthSemester = new ArrayList<Integer>();
			
			for(String toCheckYear : toCheckStartYearAndSemester) {
				int year = Integer.parseInt(toCheckYear.substring(0, 4));
				
				if((this.Start_year_for_analysis <= year) && (year <= this.End_year_for_analysis ) ){
					totalSemester++;
					forNumOfInNthSemester.add(studentToCheck.getNumCourseInNthSemester(toCheckYear));
				}
			}
		
			for(int j = 0; j < forNumOfInNthSemester.size(); j++){
				String studentIdToAdd = studentId;
				String toAdd = studentIdToAdd + "," + totalSemester + "," + (j+1) + "," + forNumOfInNthSemester.get(j);
				numberOfCoursesTakenInEachSemester.add(toAdd);
			}	
		}
			
		return numberOfCoursesTakenInEachSemester; // do not forget to return a proper variable
	}// end of method

	private HashMap<String, Integer> getNumberOfStudentsByCourseCode(HashMap<String, Student> students) {

		HashMap<String, Integer> courseStudents = new HashMap<String, Integer>();

		for (int i = 0; i < students.size(); i++) {
			String studentIdToCheck = String.format("%04d", i + 1);
			Student studentToCheck = students.get(studentIdToCheck);

			ArrayList<Course> arrayListToCheck = studentToCheck.getCoursesTaken();

			for (Course toCheckCourse : arrayListToCheck) {
				int yearTaken = toCheckCourse.getYearTaken();
				int semesterTaken = toCheckCourse.getSemesterCourseTaken();
				String courseCodeToCheck = toCheckCourse.getCourseCode();
				String toCheckYearAndSemester = yearTaken + "-" + semesterTaken;

				if (Start_year_for_analysis <= yearTaken && yearTaken <= End_year_for_analysis) {
					// there is no year
					if (courseCodeToCheck.equals(this.course_code)) {
						this.courseNameByCourseCode = toCheckCourse.getCourseName();
						//there is no year 
						if (!(courseStudents.containsKey(toCheckYearAndSemester))) {
							courseStudents.put(toCheckYearAndSemester, 1);
							// update the number of students 
							} else { 
								int totalNum = courseStudents.get(toCheckYearAndSemester);
								totalNum++; 
								courseStudents.put(toCheckYearAndSemester, totalNum);
							}
					}
				}
			}
		}
		
		return courseStudents;
	}

	private HashMap<String, Integer> getTotalStudentsNumberByYearAndSemseter(HashMap<String, Student> students) {

		HashMap<String, Integer> totalStudents = new HashMap<String, Integer>();
		 
		for (int i = 0; i < students.size(); i++) { 
			String studentIdToCheck = String.format("%04d", i + 1);
			Student studentToCheck = students.get(studentIdToCheck);
			studentToCheck.getYearsAndSemestersMethod();
		  
			ArrayList<String> arrayListToCheck = studentToCheck.getYearsAndSemesters();
			
			for (String toCheckYearAndSemester : arrayListToCheck) { 
			
				int mid = toCheckYearAndSemester.indexOf("-"); 
				int year = Integer.parseInt(toCheckYearAndSemester.substring(0, mid)); 
				int semester = Integer.parseInt(toCheckYearAndSemester.substring(mid + 1));
				
				if (Start_year_for_analysis <= year && year <= End_year_for_analysis) {
					//there is no year 
					if (!(totalStudents.containsKey(toCheckYearAndSemester))) {
						totalStudents.put(toCheckYearAndSemester, 1);
						// update the number of students 
						} else { 
							int totalNum = totalStudents.get(toCheckYearAndSemester);
							totalNum++; 
							totalStudents.put(toCheckYearAndSemester, totalNum); 
						} 
					} 
				} 
		}
		 return totalStudents;
	}

private ArrayList<String> rateOfStudentsTakingTheGivenCourse(HashMap<String, Integer> totalByYear, HashMap<String, Integer> totalByCoursecode) {
		
		ArrayList<String> addRate = new ArrayList<String>(); 
	
		for(int i = 0; i < totalByYear.size(); i++) {
			int year = Start_year_for_analysis + i;
			
			for(int j = 1; j < 5; j++) {
				String toCheck = year + "-" + j;
				
				if(totalByYear.containsKey(toCheck) && totalByCoursecode.containsKey(toCheck)) {
					int intTotalByYear = totalByYear.get(toCheck);
					int intTotalByCoursecode = totalByCoursecode.get(toCheck);
					float rateToPut = ((float)intTotalByCoursecode / (float)intTotalByYear)*100;
					String rateString = year + "," + j + "," + this.course_code + "," + this.courseNameByCourseCode + "," + intTotalByYear + "," + intTotalByCoursecode + "," + rateToPut + "%";
					addRate.add(rateString);
				}else if(totalByYear.containsKey(toCheck) && (!totalByCoursecode.containsKey(toCheck))) {
					int intTotalByYear = totalByYear.get(toCheck);
					int intTotalByCoursecode = 0;
					float rateToPut = ((float)intTotalByCoursecode / (float)intTotalByYear)*100;
					String rateString = year + "," + j + "," + this.course_code + "," + this.courseNameByCourseCode + "," + intTotalByYear + "," + intTotalByCoursecode + "," + rateToPut + "%";
					addRate.add(rateString);
				}else if((!totalByYear.containsKey(toCheck)) && totalByCoursecode.containsKey(toCheck)) {
					int intTotalByYear = 0;
					int intTotalByCoursecode = totalByCoursecode.get(toCheck);
					float rateToPut = ((float)intTotalByCoursecode / (float)intTotalByYear)*100;
					String rateString = year + "," + j + "," + this.course_code + "," + this.courseNameByCourseCode + "," + intTotalByYear + "," + intTotalByCoursecode + "," + rateToPut + "%";
					addRate.add(rateString);
					}
				}
			}
		
		return addRate;
	}

	// CLI
	public boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			Input_path = cmd.getOptionValue("i");
			//System.out.println("args is "+args);
			//System.out.println("Input is "+Input_path);
			Output_path = cmd.getOptionValue("o");
			Analysis_option = Integer.parseInt(cmd.getOptionValue("a"));
			course_code = cmd.getOptionValue("c");
			Start_year_for_analysis = Integer.parseInt(cmd.getOptionValue("s"));
			End_year_for_analysis = Integer.parseInt(cmd.getOptionValue("e"));
			Help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	public Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input").desc("Set an input file path").hasArg()
				.argName("Output_path").required().build());

		options.addOption(Option.builder("o").longOpt("output").desc("Set an output file path").hasArg()
				.argName("Output_path").required().build());

		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year").hasArg()
				.argName("Analysis_option").required().build());

		options.addOption(Option.builder("c").longOpt("coursecode").desc("Course code for '-a 2' option").hasArg()
				.argName("course code").build());

		options.addOption(Option.builder("s").longOpt("startyear").desc("Set the start year for analysis e.g., -s 2002")
				.hasArg().argName("Start_year_for_analysis").required().build());

		options.addOption(Option.builder("e").longOpt("endyear").desc("Set the end year for analysis e.g., -e 2005")
				.hasArg().argName("End_year_for_analysis").required().build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help").desc("Show a Help page").argName("Help").build());

		return options;
	}

	private void printHelp(Options HGUCourseCounter) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = ""; // Leave this empty.
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}// end of class

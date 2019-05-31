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

	private HashMap<String,Student> students;
	
	//CLI Variables
	String Input_path;
	String Output_path;
	int Analysis_option;
	String course_code;
	int Start_year_for_analysis;
	int End_year_for_analysis;
	boolean Help;		
	
	Options options = createOptions();
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Input_path = args[0]; // csv file to be analyzed
		Output_path = args[1]; // the file path where the results are saved.
		
		//ArrayList<String> lines = Utils.getLines(Input_path, true);
		Reader reader = Files.newBufferedReader(Paths.get(Input_path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
		
		if(parseOptions(options, args)){
			if (Help){
				printHelp(options);
				return;
			}
		}

		
		students = new HashMap<String,Student>();
		students = loadStudentCourseRecords(csvParser);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);
		HashMap<String, Integer> totalStudents = getTotalStudentsNumberByYearAndSemseter(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		if(Analysis_option == 1) {
			Utils.writeAFile(linesToBeSaved, Output_path);
		}else if(Analysis_option == 2) {
			// Write a file (named like the value of resultPath) with linesTobeSaved.
			writeAFile(linesToBeSaved, Output_path);
		}
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(CSVParser csvParser) {
		
		// TODO: Implement this method
		
		for (CSVRecord csvRecord : csvParser) {
            // Accessing values by Header names
			Course CourseToAdd = new Course(csvRecord);
	        String studentIdToCheck = CourseToAdd.getStudentId();
	         
	         //if the student Id already exists in HashMap
	         if(!students.containsKey(studentIdToCheck)) {
	        	 Student studentToAdd = new Student(studentIdToCheck);
	        	 studentToAdd.addCourse(CourseToAdd);
	        	 students.put(studentIdToCheck, studentToAdd);
	         }
	         //if there is no same thing in HashMap 
	         else {
	        	 Student studentInstanceToAddCourse = students.get(studentIdToCheck);
	        	 studentInstanceToAddCourse.addCourse(CourseToAdd);
	        	 students.put(studentIdToCheck, studentInstanceToAddCourse);
	         }
	      }
		
		return students; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method 
		ArrayList<String> numberOfCoursesTakenInEachSemester = new ArrayList<String>();
		int totalSemester;
		
		for(int  i = 0; i < sortedStudents.size(); i++) {
			String studentIdToCheck = String.format("%04d", i+1);
			Student studentToCheck = sortedStudents.get(studentIdToCheck);
			String studentId = studentToCheck.getStudentId();
			totalSemester = studentToCheck.getSemestersByYearAndSemester().size();
			
			for(int j = 0; j < studentToCheck.getSemestersByYearAndSemester().size(); j++) {
				int numOfCoursesInNthSemester = studentToCheck.getNumCourseInNthSemester(j+1);
				String studentIdToAdd = studentId;
				String toAdd = studentIdToAdd + "," + totalSemester + "," + j + "," + numOfCoursesInNthSemester;
				numberOfCoursesTakenInEachSemester.add(toAdd);
			}
		}
		return numberOfCoursesTakenInEachSemester; // do not forget to return a proper variable
	}//end of method
	
	/*
	 * private rateOfStudentsTakingTheGivenCourse () {
	 * 
	 * }
	 */
	
	private HashMap<String, Integer> getTotalStudentsNumberByYearAndSemseter(HashMap<String,Student> students) {
		
		HashMap<String, Integer> totalStudents;
		
		for(int  i = 0; i < students.size(); i++) {
			String studentIdToCheck = String.format("%04d", i+1);
			Student studentToCheck = students.get(studentIdToCheck);
			
			ArrayList<String> arrayListToCheck = studentToCheck.getYearsAndSemesters();
			
			for(String toCheckYearAndSemester: arrayListToCheck) {
				int mid = toCheckYearAndSemester.indexOf("-");
				int year = Integer.parseInt(toCheckYearAndSemester.substring(0,mid-1));
				int semester = Integer.parseInt(toCheckYearAndSemester.substring(mid+1));
				
				if(Start_year_for_analysis < year && year < End_year_for_analysis) {
					
				}
			}
		} 
		return totalStudents;
	}
	
	//CLI
	public boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			Input_path = cmd.getOptionValue("i");
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
				options.addOption(Option.builder("i").longOpt("input")
						.desc("Set an input file path")
						.hasArg()
						.argName("Output_path")
						.required()
						.build());
		
				options.addOption(Option.builder("o").longOpt("output")
						.desc("Set an output file path")
						.hasArg()
						.argName("Output_path")
						.required()
						.build());
				
				options.addOption(Option.builder("a").longOpt("analysis")
						.desc("1: Count courses per semester, 2: Count per course name and year")
						.hasArg()
						.argName("Analysis_option")
						.required()
						.build());
				
				options.addOption(Option.builder("c").longOpt("coursecode")
						.desc("Course code for '-a 2' option")
						.hasArg()
						.argName("course code")
						.required()
						.build());
				
				options.addOption(Option.builder("s").longOpt("startyear")
						.desc("Set the start year for analysis e.g., -s 2002")
						.hasArg()
						.argName("Start_year_for_analysis")
						.required()
						.build());
				
				
				options.addOption(Option.builder("e").longOpt("endyear")
						.desc("Set the end year for analysis e.g., -e 2005")
						.hasArg()
						.argName("End_year_for_analysis")
						.required()
						.build());
				
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				        .desc("Show a Help page")
				        .argName("Help")
				        .build());
				
		return options;
	}
	
	private void printHelp(Options HGUCourseCounter) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer =""; // Leave this empty.
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

	public void writeAFile(ArrayList<String> linesToBeSaved, String Output_path) {
		try {
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(Output_path));

		            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
		                    .withHeader("ID", "Name", "Designation", "Company"));
		            
		            for(String toSave: linesToBeSaved) {
		            	csvPrinter.printRecord(toSave);
		            }

		            csvPrinter.flush();            
		    }
	}



	
}//end of class

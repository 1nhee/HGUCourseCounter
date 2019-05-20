package edu.handong.analysis.utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import edu.handong.analysis.datamodel.*;

public class Utils {
	public static ArrayList<String> getLines(String file, boolean removeHeader){
		
		ArrayList<Course> courseArray = new ArrayList<Course>();
		
		String fileName = file;
		
		try{
			// Skip the header line by reading and ignoring it
			Scanner inputStream = new Scanner(new File(fileName)); 
			String line;
			
			// Read the rest of the file line by line
			while (inputStream.hasNextLine()){
				
				line = inputStream.nextLine();

				// Turn the string into an array of strings
				courseArray.studentId.add(line.split(",")[0]);
				String yearMonthGraduated = line.split(",")[1];
				String SecondMajor = line.split(",")[2];
				String CourseCode = line.split(",")[3];
				String CourseName = line.split(",")[4];
				String CourseCredit = line.split(",")[5];
				String YearTaken = line.split(",")[6];
				String SemesterTaken = line.split(",")[7];
				
				// Extract each item into an appropriate variable
				
				int quantity = Integer.parseInt(ary[1]);
				double price = Double.parseDouble(ary[2]);
				String description = ary[3];
				
				// Output item
				
				// Compute total
				total += quantity * price;
			}
			System.out.println("Total sales: " + total);
			inputStream.close( );
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find file " + fileName);
		}
		return null;
	}

}

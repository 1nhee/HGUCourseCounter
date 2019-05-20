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
		
		ArrayList<Student> studentArray = new ArrayList<Student>();
		
		String fileName = file;
		try{
			// Skip the header line by reading and ignoring it
			Scanner inputStream = new Scanner(new File(fileName)); 
			String line = inputStream.nextLine();
			
			double total = 0;
			// Read the rest of the file line by line
			while (inputStream.hasNextLine())
			{
				
				line = inputStream.nextLine();

				// Turn the string into an array of strings
				String studentNumber = line.split(",")[0];
				String studentNumber = line.split(",")[1];
				
				
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

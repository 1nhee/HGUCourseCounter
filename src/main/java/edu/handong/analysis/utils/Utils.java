package edu.handong.analysis.utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	public static ArrayList<String> getLines(String file, boolean removeHeader){
		
		ArrayList<String> stringArray = new ArrayList<String>();
		
		String fileName = file;
		
		try{
			// Skip the header line by reading and ignoring it
			Scanner inputStream = new Scanner(new File(fileName)); 
			String line;
			
			// Read the rest of the file line by line
			while (inputStream.hasNextLine()){
				
				line = inputStream.nextLine();
				stringArray.add(line);
			}
			inputStream.close( );
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find file " + fileName);
		}
		return stringArray;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName){
		
	}

}

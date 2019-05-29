package edu.handong.analysis.utils;

public class NotEnoughArgumentException extends Exception{
	/*With Customized Exception, you only need to extend the Exception 
	and implement two constructors as you learned in the class. 
	Refer to Exception message below.*/
	public NotEnoughArgumentException(){
		String exceptionString ="The file path does not exist. Please check your CLI argument!";		
		System.out.println(exceptionString);
	}
	public NotEnoughArgumentException(String message){
		String CLIString = "No CLI argument Exception! Please put a file path.";
		System.out.println(CLIString);
	}
	
	public NotEnoughArgumentException(String message1, String message2){
		String CLIString = "The file path does not exist. Please check your CLI argument!";
		System.out.println(CLIString);
		System.exit(0);
	}
}

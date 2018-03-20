package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
         System.out.println("GLHF");
         boolean quit = false;
         while(!quit) {
        try {
        	String cmd = kb.next();
        	switch(cmd) {
        	case "quit":
        		quit = true;
        		System.out.println("You have quit.");
        		break;
        		
        	case "show":
        		Critter.displayWorld();
        		break;	
        		
        	case "step":
        		int steps = 1;
        		if(kb.hasNextInt()) {
        			steps = kb.nextInt();
        		}
        		for(int i = 0; i < steps; i++) {
        			Critter.worldTimeStep();
        		}
        		break;
        		
        	case "seed":
        		int seed = kb.nextInt();
        		Critter.setSeed(seed);
        		break;
        		
        	case "make": //STAGE 1/2 NEED TO UPGRADE FOR STAGE 3
//        		for(int i = 0; i < 100 ; i ++) {
//        			Critter.makeCritter("assignment4.Algae");
//        		}
//        		for(int i = 0; i < 25 ; i ++) {
//        			Critter.makeCritter("assignment4.Craig");
//        		}
        		String class_name = kb.next();
        		int amount = 1;
        		if(kb.hasNextInt()	) {
        			amount = kb.nextInt();
        		}
        		class_name = "assignment4."+ class_name;
        		for(int i = 0; i < amount; i++) {
        			Critter.makeCritter(class_name);
        		}
        		break;
        		
        	case "stats":
        		break;
        	
        	case "clear":
        		Critter.clearWorld();
        		System.out.println("World Cleared.");
        	}
        	
        } 
        catch(InvalidCritterException e) {
        	
        }
    }
        /* Write your code above */
        System.out.flush();

    }
    
}

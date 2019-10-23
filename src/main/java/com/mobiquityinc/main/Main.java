package com.mobiquityinc.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;

/**
 * Main thread
 * @author davidesteban.gomez
 *
 */
public class Main {
	
	private static final Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		try {
			String filePath = "C:\\Users\\davidesteban.gomez\\Documents\\Personal\\Workspace\\package-challenge\\src\\main\\resources\\firstTest.txt";
			String packages = Packer.pack(filePath);
			
			System.out.println(packages);
		} catch (APIException e) {
			log.log(Level.SEVERE, e.getMessage());
			System.out.println("There was an error while executing the process, cause: " + e.getMessage());
		}
	}
}

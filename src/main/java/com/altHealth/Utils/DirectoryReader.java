package com.altHealth.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

@ManagedBean
public class DirectoryReader {
	
	final static File folder = new File("C:\\Users\\Ninja\\Documents\\AltHealthWebAppBackup");
	
	/*
	 * public static void main(String[] args) { System.out.println("TEST"); //
	 * listFilesForFolder(); }
	 */
	
	public List<String> listFilesForFolder() {
		List<String> fileNames = new ArrayList<String>();
		if(folder.exists()) {
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
	//	            listFilesForFolder();
		        	System.out.println("Directory:");
		        	System.out.println(fileEntry.getName());
		        } else {
		        	fileNames.add(fileEntry.getName());
		            System.out.println(fileEntry.getName());
		        }
		    }
		}
		return fileNames;
	}
}

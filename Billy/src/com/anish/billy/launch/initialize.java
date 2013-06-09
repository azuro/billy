package com.anish.billy.launch;

import java.io.File;

import javax.swing.JFileChooser;

public class initialize {
	public static String DataBase;
	
	public static void main(String[] args){
		ServGUI.start();
	}

	public static void startServer(){
		
	}
	
	public static void stopServer(){
		
		
	}
	
	public static void setDatabase(File dbFile){
		System.out.println("Database Set: "+dbFile.getName());
	}
	
	public static void setSubscription(){
		System.out.println("@TODO: Create a set Monthly Rate Method.");
		
	}
}

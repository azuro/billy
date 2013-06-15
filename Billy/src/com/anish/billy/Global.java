package com.anish.billy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.anish.billy.launch.SplashScreen;

public class Global {
	private static double rate;
	private static String db;
	private static double ConnRate;
	
	public static DateFormat  DF = new SimpleDateFormat("dd/MM/yyyy");

	public static synchronized String getDb() {
		return db;
	}

	public static synchronized void setDb(String db, boolean change) {
		Global.db = db;
		if(change)
		updateValues();
	}

	public static synchronized double get() {
		return rate;
	}

	public static synchronized void set(double rate, boolean change) {
		Global.rate = rate;
		if(change)
		updateValues();
	}
	
	
	
	public static synchronized double getConnRate() {
		return ConnRate;
	}

	public static synchronized void setConnRate(double connRate, boolean change) {
		ConnRate = connRate;
		if(change)
			updateValues();
	}

	private static void updateValues(){
		Properties prop = new Properties();
		
		try{
			
			prop.setProperty("DbPath", getDb());
			prop.setProperty("MonthlyRate", String.valueOf(get()));
			prop.setProperty("ConnCharge", String.valueOf(getConnRate()));
			
			prop.store(new FileOutputStream("Library"+File.separator+"Billy.config"), null);
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Whoops! I can seem to save the file!");
		}
		
	}
	//If Successful: true, else: false
	public static boolean readValues(){
		Properties prop = new Properties();
		try{
			
			prop.load(new FileInputStream("Library"+File.separator+"Billy.config"));
			
			setDb(prop.getProperty("DbPath"),false);
			set(Double.parseDouble(prop.getProperty("MonthlyRate")), false);
			setConnRate(Double.parseDouble(prop.getProperty("ConnCharge")),false);
			
			
			return true;
		}catch (Exception e) {
			SplashScreen.terminate();
			return false;
		}
	}
}

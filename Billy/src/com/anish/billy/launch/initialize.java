package com.anish.billy.launch;

import javax.swing.JOptionPane;

import com.anish.billy.Global;
import com.anish.billy.DataBase.Db;


public class initialize {
	private static Thread server;
	public static String DataBase;
	private static boolean running = false;
	
	public static void main(String[] args){
		//Initialization Section
		
		//Splash Screen:
		SplashScreen.Show();
		//Do some Housekeeping here:
		
		
		
		//Read Configuration Files	
		if(Global.readValues()){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SplashScreen.terminate();
		//Start Console
		ServGUI.start();
		}
		//First Run
		else{
			FirstRunGUI.start();
		}
	}

	public static void startServer(){
		running = true;
		server = new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("Hello World");
				//Initialize Database
				Db.initialize();
				while(running);//Run the server
				System.out.println("Server Stopped");
			}
						
		});
		server.start();
	}
	
	public static void stopServer(){
		running = false;
	}
	
}

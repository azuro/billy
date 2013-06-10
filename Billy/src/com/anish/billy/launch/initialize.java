package com.anish.billy.launch;


public class initialize {
	private static Thread server;
	public static String DataBase;
	private static boolean running = false;
	
	public static void main(String[] args){
		ServGUI.start();
	}

	public static void startServer(){
		running = true;
		server = new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("Hello World");
				while(running){};//Run the server
				System.out.println("Server Stopped");
			}
						
		});
		server.start();
	}
	
	public static void stopServer(){
		running = false;
	}
	
}

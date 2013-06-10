package com.anish.billy;

public class Global {
	private static double rate;
	private static String db;

	public static synchronized String getDb() {
		return db;
	}

	public static synchronized void setDb(String db) {
		Global.db = db;
	}

	public static synchronized double get() {
		return rate;
	}

	public static synchronized void set(double rate) {
		Global.rate = rate;
	}
}

package com.anish.billy.FIOS;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class monthlyRate {
private static double rate;

public static synchronized double get() {
	return rate;
}

public static synchronized void set(double rate) {
	monthlyRate.rate = rate;
}
	
public static void setMonthlyRateGUI(){
	EventQueue.invokeLater(new Runnable(){

		@Override
		public void run() {
			String getRate = JOptionPane.showInputDialog("Set Monthly Rate to:");
			try{
			double dMonthlyRate = Double.parseDouble(getRate);
			monthlyRate.set(dMonthlyRate);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error:"+e.getMessage());
				
			}
		} 
		
	});
	
	
}
}

package com.anish.billy.FIOS;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import com.anish.billy.Global;

public class monthlyRate {

	
public static void setMonthlyRateGUI(){
	EventQueue.invokeLater(new Runnable(){

		@Override
		public void run() {
			String getRate = JOptionPane.showInputDialog("Set Monthly Rate to:");
			try{
			double dMonthlyRate = Double.parseDouble(getRate);
			Global.set(dMonthlyRate);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error:"+e.getMessage());
				
			}
		} 
		
	});
	
	
}
}

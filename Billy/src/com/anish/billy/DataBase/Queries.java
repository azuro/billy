package com.anish.billy.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.anish.billy.Global;

public class Queries {
	//New Customer:
	public static void addCustomer(String name, String address, String area, String date,String phone,boolean connpaid){
		try {
			String CustID = generateCustID(area);
			                                              //ID         Name    Address     Area     Date     Phone       Connection Paid?    Balance   
			Db.query.execute("insert into accounts values('"+CustID+"','"+name+"','"+address+"','"+area+"','"+date+"','"+phone+"',"+((connpaid)?1:0)+","+((connpaid)?0:Global.getConnRate())+")");
			Db.query.execute("insert into transactions values("+CustID+",'Connection Charge',"+date+","+(-1*Global.getConnRate())+")");
			Db.query.execute("insert into transactions values("+CustID+",'Connection Charge Paid',"+date+","+Global.getConnRate()+")");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String generateCustID(String area)throws SQLException{
		ResultSet rs = Db.query.executeQuery("select abbr,last from area where name='"+area+"'");
		rs.next();
		String abbr = rs.getString("abbr");
		int lastCount = rs.getInt("last")+1;
		Db.query.execute("update area set last="+lastCount+" where abbr='"+abbr+"'");
		return abbr.toUpperCase()+"-"+lastCount;
	}
	
	//Add Payment:
	public static void payment(String CustID, double amount, String CollID){
		try {
			Db.query.execute("insert into transactions values("+CustID+",'Customer Payment','"+Global.DF.format(Calendar.getInstance().getTime())+"',"+amount+")");
			ResultSet balanceResult = Db.query.executeQuery("select balance from accounts where custid='"+CustID+"'");
			balanceResult.next();
			double balance = balanceResult.getFloat("balance");
			balance = balance-amount;
			Db.query.execute("update accounts set balance="+balance+" where custid='"+CustID+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//
	
}

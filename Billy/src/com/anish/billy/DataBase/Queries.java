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
			//Create Customer Entry
			Table Accounts = new Table("Accounts");
			Accounts.INSERT(new Value[]{                                            //Column
										new Value(CustID),                          //Customer ID
									    new Value(name),                            //Name
									    new Value(address),                         //Address
									    new Value(area),                            //Area
									    new Value(date),                            //Date of Connection
									    new Value(phone),                           //Phone Number
									    new Value((connpaid)?1:0),                  //Connection Charge Paid?
										new Value((connpaid)?0:Global.getConnRate())//Current Balance
							}).execute();
			//Create Connection Charge Entr(ies)
			Table Transactions = new Table("Transactions");
			Transactions.INSERT(new Value[]{                      //Columns
								new Value(CustID),                //Customer ID
								new Value("Connection Charge"),   //Transaction Name
								new Value(date),                  //Transaction Date
								new Value(-1*Global.getConnRate())//Amount
			}).execute();
			if(connpaid){
				Transactions.INSERT(new Value[]{                      		//Columns
									new Value(CustID),                		//Customer ID
									new Value("Connection Charge Paid"),    //Transaction Name
									new Value(date),                  		//Transaction Date
									new Value(Global.getConnRate())			//Amount
				}).execute();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String generateCustID(String area)throws SQLException{
		Table Area = new Table("Area");
		ResultSet rs = Area.SELECT()
						.addParameter("abbr")
						.addParameter("last")
					   .fromTable().WHERE()
					    .addParameter("Name", area)
					   .executeQuery();
		rs.next();
		String abbr = rs.getString("abbr");
		int lastCount = rs.getInt("last")+1;
		Area.UPDATE()
			.SET()
			 .addParameter("Last", lastCount)
			.WHERE()
			 .addParameter("abbr", abbr)
			.execute();
		return abbr.toUpperCase()+"-"+lastCount;
	}
	
	//Add Payment:
	public static void payment(String CustID, double amount, String CollID){
		try {
			//Add Transaction to History
			Table Transactions = new Table("Transactions");
			Transactions.INSERT(new Value[]{                      //Columns
								new Value(CustID),                //Customer ID
								new Value("Customer Payment"),    //Transaction Name
								new Value(Global.DF.format(Calendar.getInstance().getTime())),//Transaction Date
								new Value(amount)//Amount
			}).execute();
			//Modify Customer Balance
			Table Accounts = new Table("Accounts");
			ResultSet balanceResult = Accounts.SELECT().addParameter("Balance").fromTable().WHERE().addParameter("CustID", CustID).executeQuery();
			balanceResult.next();
			double balance = balanceResult.getFloat("balance");
			balance = balance-amount;
			Accounts.UPDATE().SET().addParameter("Balance",balance).WHERE().addParameter("CustID", CustID).execute();
			//Give Commission to Collector:
			Table Collectors = new Table("Collectors");
				//Collector ID[CollID](String) Commission[Commission](Float)  Balance[Balance](Float)
			ResultSet collectorData = Collectors.SELECT().addParameter("Commission").addParameter("Balance").fromTable().WHERE().addParameter("CollID", CollID).executeQuery();
			collectorData.next();
			float commission = collectorData.getFloat("Commission");
			float currBalance = collectorData.getFloat("Balance");
			Collectors.UPDATE().SET().addParameter("Balance", currBalance+(commission*currBalance)).WHERE().addParameter("CollID",CollID).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//
	
}

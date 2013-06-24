package com.anish.billy.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.anish.billy.Global;

public class Queries {
	/*
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                           To - Do
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Add: [Done] Customer - [Done]Collector - Area - Manager - Investor 
	 * Modify: [Done]Customer - [Done]Collector
	 * Payment: - [Done]Customer - Performance - Turnover
	 * Expenses: - [Done]Misc - [Done]Collector - Investor - Manager
	 */

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                           Customer Queries
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	// New Customer:
	public static void addCustomer(String name, String address, String area,
			String date, String phone, boolean connpaid) {
		try {
			String CustID = generateCustID(area);
			// Create Customer Entry
			Table Accounts = new Table("Accounts");
			Accounts.INSERT(new Value[] { // Column
					new Value(CustID), // Customer ID
							new Value(name), // Name
							new Value(address), // Address
							new Value(area), // Area
							new Value(date), // Date of Connection
							new Value(phone), // Phone Number
							new Value((connpaid) ? 1 : 0), // Connection Charge
															// Paid?
							new Value((connpaid) ? 0 : Global.getConnRate()) // Current
																				// Balance
					}).execute();
			// Create Connection Charge Entr(ies)
			Table Transactions = new Table("Transactions");
			Transactions.INSERT(new Value[] { // Columns
					new Value(CustID), // Customer ID
							new Value("Connection Charge"), // Transaction Name
							new Value(date), // Transaction Date
							new Value(Global.getConnRate()) // Amount
					}).execute();
			if (connpaid) {
				Transactions.INSERT(new Value[] { // Columns
						new Value(CustID), // Customer ID
								new Value("Connection Charge Paid"), // Transaction
																		// Name
								new Value(date), // Transaction Date
								new Value(-1 * Global.getConnRate()) // Amount
						}).execute();
			} else
				updateBalance(-1 * Global.getConnRate());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Modify
	 * ````````````````````````````````````````````````````````````````````````````````````````````````````````
	 */
	
	//Modify Name
	public static void changeName(String CustID, String name){
		Table Accounts = new Table("Accounts");
		try {
			Accounts.UPDATE().SET().addParameter("Name", name).WHERE().addParameter("CustID", CustID).execute();
		} catch (SQLException e) {
			//TODO Add Error Logger
			e.printStackTrace();
		}
		
	}
	
	//Modify Phone Number
	public static void changePhone(String CustID, String phno){
			Table Accounts = new Table("Accounts");
			try {
				Accounts.UPDATE().SET().addParameter("Phone", phno).WHERE().addParameter("CustID", CustID).execute();
			} catch (SQLException e) {
				//TODO Add Error Logger
				e.printStackTrace();
			}
			
		}
	
	//Modify Connection Paid
		public static void changeConnPaid(String CustID, boolean connpaid){
				Table Accounts = new Table("Accounts");
				try {
					Accounts.UPDATE().SET().addParameter("ConnPaid", ((connpaid)?1:0)).WHERE().addParameter("CustID", CustID).execute();
				} catch (SQLException e) {
					//TODO Add Error Logger
					e.printStackTrace();
				}
				
			}

	//Freeze Account
	public static void Freeze(String CustID){
			Table Accounts = new Table("Accounts");
				try {
					ResultSet rs = Accounts.SELECT().addParameter("ConnPaid").fromTable().WHERE().addParameter("CustID", CustID).executeQuery();
					rs.next();
					int ConnPaid = rs.getInt("ConnPaid");
					if(ConnPaid==1||ConnPaid==0)
					Accounts.UPDATE().SET().addParameter("ConnPaid", ConnPaid+42).WHERE().addParameter("CustID", CustID).execute();
					else
						Accounts.UPDATE().SET().addParameter("ConnPaid", ConnPaid-42).WHERE().addParameter("CustID", CustID).execute();
				} catch (SQLException e) {
					//TODO Add Error Logger
					e.printStackTrace();
				}
				
	}

	
	//Modify Address
	public static void changeAddress(String CustID, String address){
		Table Accounts = new Table("Accounts");
		try {
			Accounts.UPDATE().SET().addParameter("Address", address).WHERE().addParameter("CustID", CustID).execute();
		} catch (SQLException e) {
			//TODO Add Error Logger
			e.printStackTrace();
		}
		
	}
	
	
	// Generate Customer ID
	private static String generateCustID(String area) throws SQLException {
		Table Area = new Table("Area");
		ResultSet rs = Area.SELECT().addParameter("abbr").addParameter("last")
				.fromTable().WHERE().addParameter("Name", area).executeQuery();
		rs.next();
		String abbr = rs.getString("abbr");
		int lastCount = rs.getInt("last") + 1;
		Area.UPDATE().SET().addParameter("Last", lastCount).WHERE()
				.addParameter("abbr", abbr).execute();
		return abbr.toUpperCase() + "-" + lastCount;
	}
	/*
	 * Payment:
	 * ``````````````````````````````````````````````````````````````````````````
	 */

	// Add Payment:
	public static void payment(String CustID, double amount, String CollID) {
		try {
			// Add Transaction to History
			Table Transactions = new Table("Transactions");
			Transactions.INSERT(new Value[] { // Columns
							new Value(CustID), // Customer ID
							new Value("Customer Payment"), // Transaction Name
							new Value(Global.DF.format(Calendar.getInstance()
									.getTime())),// Transaction Date
							new Value(amount) // Amount
					}).execute();
			// Modify Customer Balance
			Table Accounts = new Table("Accounts");
			ResultSet balanceResult = Accounts.SELECT().addParameter("Balance")
					.fromTable().WHERE().addParameter("CustID", CustID)
					.executeQuery();

			balanceResult.next();
			double balance = balanceResult.getFloat("balance");
			balance = balance - amount;
			Accounts.UPDATE().SET().addParameter("Balance", balance).WHERE()
					.addParameter("CustID", CustID).execute();
			// Give Commission Credit to Collector:
			Table Collectors = new Table("Collectors");
			// Collector ID[CollID](String) Commission[Commission](Float)
			// Balance[Balance](Float)
			ResultSet collectorData = Collectors.SELECT()
					.addParameter("Commission").addParameter("Balance")
					.fromTable().WHERE().addParameter("CollID", CollID)
					.executeQuery();
			collectorData.next();
			float commission = collectorData.getFloat("Commission");
			float currBalance = collectorData.getFloat("Balance");
			Collectors
					.UPDATE()
					.SET()
					.addParameter("Balance",
							currBalance + (commission * currBalance)).WHERE()
					.addParameter("CollID", CollID).execute();
			// Update Balance after payment:
			updateBalance(amount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Pay Connection Fee
	public void payConnectionFee(String CustID) {
		Table Transactions = new Table("Transactions");
		try {
			Transactions.INSERT(new Value[] { // Columns
							new Value(CustID), // Customer ID
							new Value("Connection Charge Paid"), // Transaction
																	// Name
							new Value(Global.DF.format(Calendar.getInstance()
									.getTime())),// Transaction Date
							new Value(-1 * Global.getConnRate()) // Amount
					}).execute();

			Table Accounts = new Table("Accounts");
			ResultSet rs = Accounts.SELECT().addParameter("Balance")
					.fromTable().WHERE().addParameter("CustID", CustID)
					.executeQuery();
			rs.next();
			double cbalance = rs.getFloat("Balance");
			cbalance = cbalance - Global.getConnRate();
			Accounts.UPDATE().SET().addParameter("Balance", cbalance).WHERE()
					.addParameter("CustID", CustID);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	//Increment Balance After a Month
	static void incrementAfterAMonth(String CustID) {
		Table Account = new Table("Account");
		try {
			ResultSet rs = Account.SELECT().addParameter("Balance").WHERE()
					.addParameter("CustID", CustID).executeQuery();
			rs.next();
			float currBalance = rs.getFloat("Balance");
			Account.UPDATE().SET()
					.addParameter("Balance", currBalance + Global.get())
					.WHERE().addParameter("CustID", CustID).execute();
			Table Transactions = new Table("Transactions");
			Transactions.INSERT(new Value[] { // Columns
							new Value(CustID), // Customer ID
							new Value("Monthly Subscription Charge"),// Transaction
																		// Name
							new Value(Global.DF.format(Calendar.getInstance()
									.getTime())),// Transaction Date
							new Value(Global.get()) // Amount
					}).execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                           Collector Queries
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	//Add Collector
	static void addCollector(String name, float commission){
		Table Collector = new Table("Collector");
		try {
			String CollID = generateCollID();
			Collector.INSERT(new Value[]{
				new Value(CollID),
				new Value(name),
				new Value(commission),
				new Value(0)
			}).execute();
		} catch (SQLException e) {
			// TODO Add Error Logging
			e.printStackTrace();
		}
		
		
	}
	//Generate Collector ID
	static String generateCollID() throws SQLException{
		String CollID = "COLL-";
		Table Misc = new Table("Misc");
		ResultSet cb = Misc.SELECT().addParameter("NumeralValue").fromTable().WHERE().addParameter("Name", "Collector").executeQuery();
		cb.next();
		int count = (int)cb.getFloat(1)+1;
		CollID = CollID + count;
		Misc.UPDATE().SET().addParameter("Collector", (double)count).WHERE().addParameter("Name", "Collector").execute();
		
		return CollID;
	}
	
	// Pay Collectors:
		static void payCollector(String CollID, double amount) {
			Table Collectors = new Table("Collector");
			try {
				ResultSet CollBall = Collectors.SELECT().addParameter("Balance")
						.fromTable().WHERE().addParameter("CollID", CollID)
						.executeQuery();
				CollBall.next();
				double currBalance = CollBall.getFloat("Balance");
				if (currBalance < amount)
					throw new SQLException(
							"Withdrawal amount greater than available balance");
				else {
					currBalance = currBalance - amount;
					Collectors.UPDATE().SET().addParameter("Balance", currBalance)
							.WHERE().addParameter("CollID", CollID).execute();
					updateBalance(amount);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * Modify
		 * ````````````````````````````````````````````````````````````````````````````````````````````````````````
		 */
		
		//Modify Name
		public static void changeNameColl(String CollID, String Name){
			Table Collectors = new Table("Collector");
			try {
				Collectors.UPDATE().SET().addParameter("Name", Name).WHERE().addParameter("CollID", CollID).execute();
			} catch (SQLException e) {
				//TODO Add Error Logger
				e.printStackTrace();
			}
			
		}
		
		//Modify Commission
		public static void changeCommission(String CollID, float Commission){
			Table Collectors = new Table("Collector");
			try {
				Collectors.UPDATE().SET().addParameter("Commission", Commission).WHERE().addParameter("CollID", CollID).execute();
			} catch (SQLException e) {
				//TODO Add Error Logger
				e.printStackTrace();
			}
			
		}
		
		//Modify Balance
				public static void changeBalance(String CollID, float Balance){
					Table Collectors = new Table("Collector");
					try {
						Collectors.UPDATE().SET().addParameter("Balance", Balance).WHERE().addParameter("CollID", CollID).execute();
					} catch (SQLException e) {
						//TODO Add Error Logger
						e.printStackTrace();
					}
					
				}
	
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *                           Miscellaneous
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	// Update Company Balance +ve Debt Clearance, -ve for Debt Accumulation
		static void updateBalance(double amount) throws SQLException {
			Table Misc = new Table("Misc");
			Misc.UPDATE().SET().addParameter("NumeralValue", amount).WHERE()
					.addParameter("Name", "Current Balance").execute();

		}
		
		static void MiscPurchase(double amount){
			Table Misc = new Table("Misc");
			try{
				ResultSet rs = Misc.SELECT().addParameter("NumeralValue").fromTable().WHERE().addParameter("Name", "Maintainance").executeQuery();
				rs.next();
				float balance = rs.getFloat("NumeralValue");
				if(amount<0)
					throw new SQLException("Negative Numbers not Accepted");
				else
					Misc.UPDATE().SET().addParameter("NumeralValue", balance+amount).WHERE().addParameter("Name", "Maintainance").execute();
			}catch(SQLException e){
				//TODO Add Logger
				e.printStackTrace();
				
			}
		}
		
		static void MiscClear(){
			Table Misc = new Table("Misc");
			try{
					Misc.UPDATE().SET().addParameter("NumeralValue", 0).WHERE().addParameter("Name", "Maintainance").execute();
			}catch(SQLException e){
				//TODO Add Logger
				e.printStackTrace();
				
			}
		}

}

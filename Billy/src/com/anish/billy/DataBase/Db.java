package com.anish.billy.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


import com.anish.billy.Global;

public class Db{
	
	static Connection connection = null;
	static Statement query = null;
	
	public static void initialize(){
		try {
			Class.forName("org.sqlite.JDBC");
			//Construct a Connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+Global.getDb());
			query = connection.createStatement();
			doFirstRunTest();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void doFirstRunTest() throws SQLException{
		try{
		Db.query.execute("select * from admins");
		}catch(SQLException e){
			//Admin:  Username            Password
			Db.query.execute("create table admins(username string, password string)");
			Db.query.execute("insert into admins values('admin','admin')");
			//Accounts:      ID      Name     Address    Area   ConnDate Phone  Connection Charge Paid?   Balance
			Db.query.execute("create table accounts(custid string,name string,address string,area string,conndate date,phone string,connpaid int,balance float)");
			//Transactions: ID  Type   Date   Amount
			Db.query.execute("create table transactions(id string,type string,dateTrans date,amount float)");
			
			Db.query.execute("create table collector(collid string,name string,commission float,balance float)");
			
			Db.query.execute("create table misc(name string,numeralvalue float)");
			
			Db.query.execute("create table area(name string,abbr string, last int)");
			
			Db.query.execute("insert into misc values('Current Balance',0)");
			
			Db.query.execute("insert to misc values('Collector',0)");
			
			Db.query.execute("insert into misc values('Maintainance', 0)");
		}finally{
			if(Db.connection!=null)
				connection.close();
			if(query!=null)
				query.close();
			
		}
	}
	
	static void close(){
		
			try {
				
				if(Db.connection!=null)
				connection.close();
				
				if(query!=null)
					query.close();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
	}
}

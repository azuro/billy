package com.anish.billy.DataBase;

import java.sql.ResultSet;

public class Table {
	String query;
	int lastCall;
	String Table;
	Table(String Table){
		query="";
		this.Table = Table;
	}
	public synchronized Table UPDATE(){
		this.query = "UPDATE "+this.Table+" ";
		lastCall = 0;
		return this;
	}
	public synchronized Table INSERT(Value[] values){
		this.query = "INSERT into "+this.Table+" values(";
		
		for(int iter = 0;iter<values.length;iter++){
			this.query = this.query+values[iter].get();
			if(iter==(values.length-1))
				this.query = this.query+")";
			else
				this.query = this.query+",";
		}
		
		lastCall = 1;
		return this;
	}
	public synchronized Table and(){
		this.query =this.query + ",";
		lastCall = 2;
		return this;
	}
	public synchronized Table WHERE(){
		this.query = this.query + " WHERE ";
		lastCall = 3;
		return this;
	}
	public synchronized Table addParameter(String Column){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" ";
		lastCall = 4;
		return this;
	}
	public synchronized Table addParameter(String Column, String Value){
		if(lastCall==4){
			this.and();
		}
		com.anish.billy.DataBase.Value param = new com.anish.billy.DataBase.Value(Value);
		this.query = this.query + " "+Column+" = "+param.get()+" ";
		lastCall = 4;
		return this;
	}
	public synchronized Table addParameter(String Column, int Value){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" = "+Value+" ";
		lastCall = 4;
		return this;
	}
	public synchronized Table addParameter(String Column, double Value){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" = "+Value+" ";
		lastCall = 4;
		return this;
	}
	public synchronized Table SET(){
		this.query = this.query + " SET ";
		lastCall = 5;
		return this;
	}
	public synchronized Table SELECT(){
		this.query = "SELECT ";
		lastCall = 6;
		return this;
	}
	public synchronized Table all(){
		this.query =this.query + " * ";
		lastCall = 7;
		return this;
	}
	public synchronized Table fromTable(){
		this.query =this.query + " FROM "+this.Table;
		lastCall = 8;
		return this;
	}
	
	public String toString(){
		System.out.println(query);
		return null;
	}
	
	public ResultSet executeQuery() throws java.sql.SQLException{
		String exeQue = this.query;
		clear();
		return Db.query.executeQuery(exeQue);
	}
	
	public void execute() throws java.sql.SQLException{
		String exeQue = this.query;
		clear();
		Db.query.execute(exeQue);
	}
	
	public void clear(){
		this.query = "";
	}
}
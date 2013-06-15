package com.anish.billy.DataBase;

public class SQLQuery {
	String query;
	int lastCall;
	SQLQuery(){
		query="";
	}
	public synchronized SQLQuery UPDATE(){
		this.query = "UPDATE ";
		lastCall = 0;
		return this;
	}
	public synchronized SQLQuery INSERT(String Table, Value[] values){
		this.query = "INSERT into "+Table+" values(";
		
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
	public synchronized SQLQuery and(){
		this.query =this.query + ",";
		lastCall = 2;
		return this;
	}
	public synchronized SQLQuery WHERE(){
		this.query = this.query + " WHERE ";
		lastCall = 3;
		return this;
	}
	public synchronized SQLQuery addParameter(String Column){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" ";
		lastCall = 4;
		return this;
	}
	public synchronized SQLQuery addParameter(String Column, String Value){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" = '"+Value+"' ";
		lastCall = 4;
		return this;
	}
	public synchronized SQLQuery addParameter(String Column, int Value){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" = "+Value+" ";
		lastCall = 4;
		return this;
	}
	public synchronized SQLQuery addParameter(String Column, double Value){
		if(lastCall==4){
			this.and();
		}
		this.query = this.query + " "+Column+" = "+Value+" ";
		lastCall = 4;
		return this;
	}
	public synchronized SQLQuery SET(){
		this.query = this.query + " SET ";
		lastCall = 5;
		return this;
	}
	public synchronized SQLQuery SELECT(){
		this.query = "SELECT ";
		lastCall = 6;
		return this;
	}
	public synchronized SQLQuery all(){
		this.query =this.query + " * ";
		lastCall = 7;
		return this;
	}
	public synchronized SQLQuery from(){
		this.query =this.query + " FROM ";
		lastCall = 8;
		return this;
	}
	
	public synchronized SQLQuery table(String Table){
		this.query = this.query+" "+Table+" ";
		lastCall = 9;
		return this;
	}
	public String toString(){
		System.out.println(query);
		return null;
	}
	
	public static void main(String[] args){
		SQLQuery collect = new SQLQuery();	
		collect.INSERT("Accounts",new Value[]{new Value("My CustID"), new Value("Anish Basu"), new Value("8961051211"), new Value(42.0)});
		collect.toString();
	}
}

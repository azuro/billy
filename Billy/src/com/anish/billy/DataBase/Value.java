package com.anish.billy.DataBase;

public class Value {
	final static int INTEGER = 0;
	final static int FLOAT = 1;
	final static int TEXT = 2;
	int type;
	double ValueD;
	int ValueI;
	String ValueS;
	Value(double arg){
		ValueD = arg;
		type = FLOAT;
	}
	Value(int arg){
		ValueI = arg;
		type = INTEGER;
	}
	Value(String arg){
		ValueS = arg;
		type = TEXT;
	}
	public Object get(){
		switch(type){
		case INTEGER:
			return ValueI;
		case FLOAT:
			return ValueD;
		case TEXT:
			return "'"+ValueS+"'";
		}
		return null;
	}
}

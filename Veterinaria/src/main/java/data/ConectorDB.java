package data;

import java.sql.*;

public class ConectorDB {

	private static ConectorDB instancia;
	/*
	private String driver="com.mysql.cj.jdbc.Driver";
	private String host="us-cdbr-east-06.cleardb.net";
	private String port="";
	private String user="b8d2bf396af28d";
	private String password="fdeb722e";
	private String db="heroku_124fca0ef8ff963";*/
	private String driver="com.mysql.cj.jdbc.Driver";
	private String host="localhost";
	private String port="";
	private String user="root";
	private String password="root";
	private String db="veterinaria";
	
	private int conectados=0;
	private Connection conn=null;
	
	private ConectorDB() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ConectorDB getInstancia() {
		if (instancia == null) {
			instancia = new ConectorDB();
		}
		return instancia;
	}
	
	public Connection getConn() {
		try {
			if(conn==null || conn.isClosed()) {
				conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db, user, password);
				conectados=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conectados++;
		return conn;
	}
	
	public void releaseConn() {
		conectados--;
		try {
			if (conectados<=0) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
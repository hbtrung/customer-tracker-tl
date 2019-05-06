package com.trungho.crm.demo.testJdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {

		String jdbcUrl1 = "jdbc:mysql://customer-tracker-db.c0xepbffki6o.us-east-2.rds.amazonaws.com:3306/customer_tracker?useSSL=false&serverTimezone=UTC";
		String jdbcUrl2 = "jdbc:mysql://customer-tracker-db.c0xepbffki6o.us-east-2.rds.amazonaws.com:3306/customer_tracker_login?useSSL=false&serverTimezone=UTC";
		String user = "trungho";
		String pass = "trungho1";
		
		try {
			System.out.println("Connecting to database: " + jdbcUrl1);
			
			Connection conn1 = DriverManager.getConnection(jdbcUrl1, user, pass);
			
			System.out.println("Connection to db 1 successful");
			
			conn1.close();

			System.out.println("Connecting to database: " + jdbcUrl2);
			
			Connection conn2 = DriverManager.getConnection(jdbcUrl2, user, pass);
			
			System.out.println("Connection to db 2 successful");
			
			conn2.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.lan.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
	protected Connection connection;
	private static DBContext instance;

	public DBContext() throws Exception {
		try {
			String driverName = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/training";
			String user = "root";
			String password = "Lan123456@";
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static DBContext getInstance() throws Exception {
		if (instance == null) {
			try {
				instance = new DBContext();
			} catch (Exception ex) {
				throw ex;
			}
		}

		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}

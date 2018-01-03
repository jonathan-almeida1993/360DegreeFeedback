package com.mahindra.database.connection;

import java.io.FileNotFoundException; 
import java.io.IOException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionFactory {

	public static Connection getConnection() throws SQLException, FileNotFoundException, IOException, NamingException {
		Connection conn = null;

		// Get DataSource
		Context initContext = new InitialContext();

		DataSource dataSource = (DataSource) initContext.lookup("java:jboss/datasources/dbConFor360DegreeFeedback");
		if ((conn == null) || conn.isClosed()) {
			conn = dataSource.getConnection();
		}

		return conn;
	}


	public static void close(ResultSet resultSet, Statement statement, Connection connect) {
		try {

			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			System.out.println("ERROR in DBCONNECTION FACTORY-->"+e.getMessage());
		}
	}

}

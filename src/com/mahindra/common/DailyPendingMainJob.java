package com.mahindra.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.naming.NamingException;

import com.mahindra.common.constants.SqlConstants;
import com.mahindra.common.utils.MailShooter;
import com.mahindra.database.connection.DBConnectionFactory;

public class DailyPendingMainJob extends TimerTask {

	Connection connection = null;
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> map1 = new HashMap<String, String>();
	Map<String, String> map2 = new HashMap<String, String>();

	public Connection getConnection() throws SQLException,
			FileNotFoundException, ClassNotFoundException, IOException,
			NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}

	public void run() {

		Connection connection = null;
		Connection connection1 = null;
		Connection connection2 = null;

		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;

		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;
		try {
			connection = getConnection();
			connection1 = getConnection();
			connection2 = getConnection();

			preparedStatement = connection
					.prepareStatement(SqlConstants.GET_DEFAULTER_EMPLOYEE);
			preparedStatement1 = connection1
					.prepareStatement(SqlConstants.GET_DEFAULTER_EMPLOYEESELF);
			preparedStatement2 = connection2
					.prepareStatement(SqlConstants.GET_DEFAULTER_EMPLOYEE_NOTSELF);

			resultSet = preparedStatement.executeQuery();
			resultSet1 = preparedStatement1.executeQuery();
			resultSet2 = preparedStatement2.executeQuery();

			while (resultSet.next()) {
				map.put("username", resultSet.getString(1));
				map.put("password", resultSet.getString(2));
				map.put("email", resultSet.getString(3));
				map.put("firstName",
						resultSet.getString(4) + " " + resultSet.getString(5));
				map.put("status", "E");
				MailShooter mailShooter = new MailShooter(map);
				Thread t = new Thread(mailShooter);
				t.start();
			}
			while (resultSet1.next()) {
				map1.put("username", resultSet1.getString(1));
				map1.put("password", resultSet1.getString(2));
				map1.put("email", resultSet1.getString(3));
				map1.put("firstName", resultSet1.getString(4) + " "
						+ resultSet1.getString(5));
				map1.put("status", "ES");
				MailShooter mailShooter = new MailShooter(map1);
				Thread t = new Thread(mailShooter);
				t.start();
			}

			while (resultSet2.next()) {

				map2.put("username", resultSet2.getString(1));
				map2.put("password", resultSet2.getString(2));
				map2.put("email", resultSet2.getString(1));
				map2.put("firstName", resultSet2.getString(4) + " "
						+ resultSet2.getString(5));
				map2.put("firstName1", resultSet2.getString(6) + " "
						+ resultSet2.getString(7));
				map2.put("status", "ENS");
				MailShooter mailShooter = new MailShooter(map2);
				Thread t = new Thread(mailShooter);
				t.start();
			}

		} catch (Exception e) {

		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
			DBConnectionFactory.close(resultSet1, preparedStatement1,
					connection1);
			DBConnectionFactory.close(resultSet2, preparedStatement2,
					connection2);
		}

	}

}

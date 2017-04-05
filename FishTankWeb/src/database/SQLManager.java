/**
 * 
 */
package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gustaf Peter Hultgren 
 * @version 1.0 **/
public class SQLManager {
	/** The connection to the SQL database. **/
	private Connection connection = null;
	
	/** A private default constructor. **/
	public SQLManager() {
		
	}
	
	/** Initialize to local host database with default values. 
	 * @throws SQLException **/
	public void initialize() throws SQLException {
		initialize("jdbc:mysql://localhost:3306/aquarium?useSSL=false", "root", "root");
	}
	
	/** Initialize the connection to the database.
	 * @param uri -The URI to the database (jdbc:mysql://localhost:3306/codealong?useSSL=false).
	 * @param user -The user name to log in as.
	 * @param password -The password of the database. 
	 * @throws SQLException **/
	public void initialize(String uri, String user, String password) throws SQLException {
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection = DriverManager.getConnection(uri, user, password);
	}
	
	/** Call the specified procedure. 
	 * @param sql -The SQL script to call the stored procedure "procedure_name(input)".
	 * @return An array list of maps, where each map represents a row of data with the columns name as key.
	 * @throws SQLException **/
	public ArrayList<Map<String, Object>> callStoredProcedure(String sql) throws SQLException {
		String script = "CALL " + sql;
		
		CallableStatement statement = connection.prepareCall(script);
		ResultSet result = null;
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if(statement.execute()) {
			result = statement.getResultSet();
		
			while(result.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
				ResultSetMetaData data = result.getMetaData();
				
				int count = data.getColumnCount();
				for(int i = 1; i <= count; i++) {
					row.put(data.getColumnName(i), result.getObject(i));
				}
				
				list.add(row);
			}
		}
		
		result.close();
		statement.close();
		
		return list;
	}
	
	/** Update the database.
	 * @param sql -The script for the update. 
	 * @throws SQLException **/
	public void updateDatabase(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);	
		statement.executeUpdate();
		
		statement.close();
	}
	
	
	/** Send a query to the database.
	 * @param sql -The query to send.
	 * @return An array list of maps, where each map represents a row of data with the columns name as key.
	 * @throws SQLException **/
	public ArrayList<Map<String, Object>> sendQuery(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = null;
		
		result = statement.executeQuery();
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while(result.next()) {
			Map<String, Object> row = new HashMap<String, Object>();
			ResultSetMetaData data = result.getMetaData();
			
			int count = data.getColumnCount();

			for(int i = 1; i <= count; i++) {
				row.put(data.getColumnName(i), result.getObject(i));
			}
			
			list.add(row);
		}
		
		result.close();
		statement.close();
		
		return list;
	}
	
	/** Close all used resources.
	 * @throws SQLException **/
	public void close() throws SQLException {
		if(connection != null) {
			connection.close();
			connection = null;
		}
	}
}

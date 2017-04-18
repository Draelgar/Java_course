/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Gustaf Peter Hultgren **/
public class DatabaseHandler {
	public String user = "root";
	public String password = "root";
	public String dbUri = "jdbc:mysql://localhost:3306/servlet_codealong?useSSL=false";
	
	/** Establish a connection to the database. 
	 * @throws ClassNotFoundException 
	 * @throws SQLException **/
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		
		return DriverManager.getConnection(dbUri, user, password);
	}
	
	/** Add a book to the database. 
	 * @throws ClassNotFoundException **/
	public void addBook(String title, String author) throws SQLException, ClassNotFoundException {	
		Connection con = null;
		PreparedStatement statement = null;
		
		try {
			con = getConnection();
			statement = con.prepareStatement("INSERT INTO book VALUES(NULL, ?, ?);");
			statement.setString(1, title);
			statement.setString(2, author);
			statement.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			statement.close();
			con.close();
		}
	}
	
	/** Get the information from the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public String get() throws SQLException, ClassNotFoundException {
		String data = "";
		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			con = getConnection();
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM book;");
			
			data = "\nCurrently available books:\n\n";
			// Unpack the result set into a string array.
			while(resultSet.next()) {
				data += resultSet.getString(1);
				data += " - ";
				data += resultSet.getString(2);
				data += ", ";
				data += resultSet.getString(3);
				data += "\n";
			}
			
			resultSet.close();
			statement.close();
			
		} catch(SQLException e) {		
			throw e;		
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			resultSet.close();
			statement.close();
			con.close();
		}
		
		return data;
	}
	
	/** Main function for testing. **/
	public static void main(String args[]) {
		
	}
}

package servletPackage;

// import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
// import java.sql.Statement;

public class TestConnectToDb {
	
	private static final String user = "root";
	private static final String password = "root";
	private static final String dbUri = "jdbc:mysql://localhost:3306/codealong?useSSL=false";
	private static final String fetchWhere = "SELECT * FROM employee"
					+ "	WHERE id > ?;";
	@SuppressWarnings("unused")
	private static final String fetchMixed = "SELECT * FROM employee"
			 		+ "	INNER JOIN borrowed ON employee.id = borrowed.employeeid"
			 		+ " ORDER BY firstname ASC;";
	
	@SuppressWarnings("unused")
	private static final String fetch = "SELECT * FROM employee;";

	private static final String addNew = "INSERT INTO employee VALUES (?, ?, ?, ?);";
	@SuppressWarnings("unused")
	private static final String remove = "DELETE FROM employee WHERE lastname = ?;";
	
	public static void insert(String name) {
		Connection connection = null;
		PreparedStatement preparedStatement = null; // Use this for occasional query to the database.
		
		try {			
			connection = DriverManager.getConnection(dbUri, user, password);
			System.out.println("Connection Established.");
			preparedStatement = connection.prepareStatement(addNew);
			preparedStatement.setByte(1, (byte)20);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, name);
			preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

			System.out.println(preparedStatement.executeUpdate());
			  
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 } finally {
			 try {
				 if(preparedStatement != null)
					 preparedStatement.close();
				 if(connection != null)
					 connection.close();

				 System.out.println("Connection Terminated.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		 }
	}
	
	public static void main(String[] args) {
		 Connection connection = null;
		 // Statement statement = null; // Use this for occasional query to the database.
		 // CallableStatement callableStatement = null; // Use this for calling stored procedures in the database.
		 PreparedStatement preparedStatement = null; // Use this for multiple queries to the database.
		 ResultSet resultSet = null;
		 
		 try {
			 connection = DriverManager.getConnection(dbUri, user, password);
			 System.out.println("Connection Established.");
			 preparedStatement = connection.prepareStatement(fetchWhere);
			 preparedStatement.setByte(1, (byte) 3);
			 resultSet = preparedStatement.executeQuery();
			 
			 while(resultSet.next()) {
				 System.out.println(resultSet.getString("firstname") + ", " + resultSet.getString("lastname"));
			 }
			 
			 
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		 } finally {
			 try {
				 if(resultSet != null)
					 resultSet.close();
				 if(preparedStatement != null)
					 preparedStatement.close();
				 if(connection != null)
					 connection.close();

				 System.out.println("Connection Terminated.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
	}
}

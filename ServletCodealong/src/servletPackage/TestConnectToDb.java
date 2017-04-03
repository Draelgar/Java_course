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
	
	public static void insert(String id, String name, String surname, String date) {
		Connection connection = null;
		PreparedStatement preparedStatement = null; // Use this for occasional query to the database.
		
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			 
			connection = DriverManager.getConnection(dbUri, user, password);
			System.out.println("Connection Established.");
			preparedStatement = connection.prepareStatement(addNew);
			preparedStatement.setByte(1, Byte.parseByte(id));
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, surname);
			preparedStatement.setDate(4, Date.valueOf(date));

			System.out.println(preparedStatement.executeUpdate());
			  
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 } catch (ClassNotFoundException e) {
			e.printStackTrace();
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
}

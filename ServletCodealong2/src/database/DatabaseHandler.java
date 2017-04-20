/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import book.Book;

/**
 * @author Gustaf Peter Hultgren **/
public class DatabaseHandler {
	public String user = "root";
	public String password = "root";
	public String dbUri = "jdbc:mysql://localhost:3306/servlet_codealong?useSSL=false&useUnicode=true&amp;amp;characterEncoding=UTF-8";
	
	/** Establish a connection to the database. 
	 * @throws ClassNotFoundException 
	 * @throws SQLException **/
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		
		return DriverManager.getConnection(dbUri, user, password);
	}
	
	public int getAuthorId(String firstName, String lastName) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int id = -1;
		
		try {
			con = getConnection();
			statement = con.prepareStatement("SELECT id_ FROM authors WHERE first_name=? AND last_name=?;");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			resultSet = statement.executeQuery();	
			
			if(resultSet.next()) {
				id = resultSet.getInt("id_");
			}
		} finally {
			resultSet.close();
			statement.close();
			con.close();
		}
		
		return id;
	}
	
	public void addAuthor(String firstName, String lastName) throws SQLException, ClassNotFoundException {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = getConnection();
			statement = con.prepareStatement("INSERT INTO authors VALUES(NULL, ?, ?);");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.executeUpdate();
		} finally {
			statement.close();
			con.close();
		}
		}
	
	/** Add a book to the database. 
	 * @throws ClassNotFoundException **/
	public void addBook(String title, String firstName, String lastName) throws SQLException, ClassNotFoundException {	
		Connection con = null;
		PreparedStatement statement = null;
		
		try {
			int id = getAuthorId(firstName, lastName);
			
			if(id == -1) {
				addAuthor(firstName, lastName);
				id = getAuthorId(firstName, lastName);
			}
			
			con = getConnection();
			statement = con.prepareStatement("INSERT INTO books VALUES(NULL, ?, ?);");
			statement.setString(1, title);
			statement.setInt(2, id);
			
			statement.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			statement.close();
			con.close();
		}
	}
	
	/** Alter an existing book.
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public void alterBook(int id, String title, String firstName, String lastName) throws ClassNotFoundException, SQLException {
		String query = "UPDATE books INNER JOIN authors "
				+ " ON books.author_id = authors.id_ "
				+ " SET ";
		
		if(title.length() > 0 && firstName.length() > 0 && lastName.length() > 0) {
			query += "title=?, first_name=?, last_name=? Where id=?;";
			
			try (Connection con = getConnection();
					PreparedStatement statement = con.prepareStatement(query);){
				
				statement.setString(1, title);
				statement.setString(2, firstName);
				statement.setString(3, lastName);
				statement.setInt(4, id);
				
				statement.executeUpdate();
				
			}
		}
		else {
			boolean first = true;
			String data[] = new String[3];
			int index = 0;
			
			if(title.length() > 0) {
				if(!first)
					query += ", ";
				
				query += "title=? ";
				data[index] = title;
				index++;
			}
			if(firstName.length() > 0) {
				if(!first)
					query += ", ";
				
				query += "first_name=? ";
				data[index] = firstName;
				index++;
			}
			if(lastName.length() > 0) {
				if(!first)
					query += ", ";
				
				query += "last_name=? ";
				data[index] = lastName;
				index++;
			}
			
			query += "WHERE id=?;";
			
			try (Connection con = getConnection();
					PreparedStatement statement = con.prepareStatement(query);){
				
				statement.setInt(index + 1, id);
				
				while (index > 0) {
					index--;
					statement.setString(index + 1, data[index]);
				}
				
				statement.executeUpdate();
				
			}
			
		}
	}
	
	/** Get the books as a list. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public List<Book> getBooks() throws ClassNotFoundException, SQLException {
		List<Book> books = new ArrayList<Book>();
		
		try (Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT id, title, first_name, last_name FROM books "
															+ "INNER JOIN authors "
															+ "ON books.author_id = authors.id_ "
															+ "ORDER BY title ASC;");
				ResultSet resultSet = statement.executeQuery()){
			
			while(resultSet.next()) {
				books.add(new Book(resultSet.getInt("id"), 
						resultSet.getString("title"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name")));
			}
			
		}
		
		return books;
	}
	
	/** Get one specific book from the database.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * **/
	public Book getBook(int id) throws ClassNotFoundException, SQLException {
		Book book = null;
		ResultSet resultSet = null;
		
		try (Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT id, title, first_name, last_name "
													+ "FROM books "
													+ "INNER JOIN authors "
													+ "ON books.author_id = authors.id_ "
													+ "WHERE id=?;")){
			
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
					
			if(!resultSet.wasNull()) {
				resultSet.next();
				book = new Book(resultSet.getInt("id"), 
							resultSet.getString("title"),
							resultSet.getString("first_name"),
							resultSet.getString("last_name"));
			}
		} finally {
			if(resultSet != null) {
				resultSet.close();
			}
		}
		
		return book;
	}
	
	/** Delete a book from the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public void deleteBook(int id) throws ClassNotFoundException, SQLException {
		try (Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement("DELETE FROM books WHERE id=?;")){
			
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}
	
	public List<Book> getBooksOfAuthor(String firstName, String lastName) throws ClassNotFoundException, SQLException {
		ResultSet resultSet = null;
		List<Book> books = new ArrayList<Book>();
		
		try (Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT id, title, first_name, last_name FROM books "
						+ "INNER JOIN authors ON books.author_id = authors.id_ "
						+ "Where first_name=? AND last_name=? "
						+ "ORDER BY title ASC;")) {
			
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				
				books.add(new Book(id, title, fName, lName));
			}
		} finally {
			if(resultSet != null)
				resultSet.close();
		}
		
		return books;
	}
	
	/** Get books matching the tile or author. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public List<Book> getBooks(String title, String author) throws ClassNotFoundException, SQLException {
		ResultSet resultSet = null;
		String query = "SELECT id, title, first_name, last_name FROM books "
				+ "INNER JOIN authors "
				+ "ON books.author_id = authors.id_";
		String data = "";
		boolean both = false;
		List<Book> books = new ArrayList<Book>();
				
		if(title.length() > 0 && author.length() > 0) {
			query += " WHERE title LIKE ? AND (last_name LIKE ? OR first_name LIKE ?) COLLATE utf8_swedish_ci ORDER BY title ASC;";
			both = true;
		} 
		else if(title.length() > 0) {
			query += " WHERE title LIKE ? COLLATE utf8_swedish_ci ORDER BY title ASC;";
			data = "%" + title + "%";
		} 
		else if(author.length() > 0){
			query += " WHERE last_name LIKE ? OR first_name LIKE ? COLLATE utf8_swedish_ci ORDER BY title ASC;";
			data = "%" + author + "%";
		} 
		else {
			return getBooks();
		}
		
		if(!query.equals("")) {
			try (Connection con = getConnection();
					PreparedStatement statement = con.prepareStatement(query)) {
				if(both) {
					statement.setString(1, "%" + title + "%");
					statement.setString(2, "%" + author + "%");
					statement.setString(3, "%" + author + "%");
				}
				else if(title.length() > 0){
					statement.setString(1, data);
				}
				else {
					statement.setString(1, data);
					statement.setString(2, data);
				}
				
				resultSet = statement.executeQuery();
				
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String bookTitle = resultSet.getString("title");
					String authorFirstName = resultSet.getString("first_name");
					String authorLastName = resultSet.getString("last_name");
					
					books.add(new Book(id, bookTitle, authorFirstName, authorLastName));
				}
				
			} finally {
				if(resultSet != null) {
					resultSet.close();
				}
			}
		}
		
		return books;
	}
	
	/** Main function for testing. **/
	public static void main(String args[]) {
		
	}
}

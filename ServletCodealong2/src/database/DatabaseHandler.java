/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import book.Book;
import book.Film;
import book.Game;

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
	
	/** Add a new film to the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public boolean addFilm(String title, String genre, LocalTime duration, int pgi, String bookTitle) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		boolean success = false;
		
		try {
			con = getConnection();
			statement = con.prepareStatement("SELECT id FROM books WHERE title LIKE ? COLLATE utf8_swedish_ci;");
			statement.setString(1, bookTitle);
			
			results = statement.executeQuery();
			
			// Get the book ID.
			int bookId = -1;
			if(results.next())
				bookId = results.getInt("id");
			
			// Close the used resources in preparation for the next step.
			results.close();
			statement.close();
			
			// Did the book exist?
			if(bookId != -1) {
				
				// Add the film.
				statement = con.prepareStatement("INSERT INTO films VALUES(NULL,?,?,?,?,?);");
				statement.setString(1, title);
				Time time = Time.valueOf(duration);
				statement.setTime(2, time);
				statement.setString(3, genre);
				statement.setInt(4, pgi);
				statement.setInt(5, bookId);
				
				statement.executeUpdate();
				success = true;
			}
				
			
		} finally {
			if(results != null)
				results.close();
			if(statement != null)
				statement.close();
			if(con != null)
				con.close();
		}
		
		return success;
	}
	
	/** Add a new game to the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public boolean addGame(String title, String genre, int pgi, String bookTitle) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		boolean success = false;
		
		try {
			con = getConnection();
			statement = con.prepareStatement("SELECT id FROM books WHERE title LIKE ? COLLATE utf8_swedish_ci;");
			statement.setString(1, bookTitle);
			
			results = statement.executeQuery();
			
			// Get the book ID.
			int bookId = -1;
			if(results.next())
				bookId = results.getInt("id");
			
			// Close the used resources in preparation for the next step.
			results.close();
			statement.close();
			
			// Did the book exist?
			if(bookId != -1) {
				
				// Add the film.
				statement = con.prepareStatement("INSERT INTO games VALUES(NULL,?,?,?,?);");
				statement.setString(1, title);
				statement.setString(2, genre);
				statement.setInt(3, pgi);
				statement.setInt(4, bookId);
				
				statement.executeUpdate();
				success = true;
			}
				
			
		} finally {
			if(results != null)
				results.close();
			if(statement != null)
				statement.close();
			if(con != null)
				con.close();
		}
		
		return success;
	}
	
	/** Get all films from the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public List<Film> getFilms() throws ClassNotFoundException, SQLException {
		List<Film> films = new ArrayList<Film>();
		
		Connection con = null;
		PreparedStatement filmStatement = null;
		PreparedStatement bookStatement = null;
		ResultSet filmResults = null;
		ResultSet bookResults = null;
		
		try {
			con = getConnection();
			filmStatement = con.prepareStatement("SELECT * FROM films ORDER BY title ASC;");
			filmResults = filmStatement.executeQuery();
			
			while(filmResults.next()) {
				int bookId = filmResults.getInt("book_id");
				bookStatement = con.prepareStatement("SELECT title FROM books WHERE id=?;");
				bookStatement.setInt(1, bookId);
				bookResults = bookStatement.executeQuery();
				
				if(bookResults.next()) {
					String bookTitle = bookResults.getString("title");
					String title = filmResults.getString("title");
					int id = filmResults.getInt("id");
					String genre = filmResults.getString("genre");
					int pgi = filmResults.getInt("pgi");
					Time time = filmResults.getTime("duration");
					LocalTime duration = time.toLocalTime();
					
					films.add(new Film(id, title, duration, genre, pgi, bookTitle, bookId));
				}
				
				bookResults.close();
				bookStatement.close();
			}
			
		} finally {
			if(filmResults != null)
				filmResults.close();
			if(filmStatement != null)
				filmStatement.close();
			if(con != null)
				con.close();
		}
		
		return films;
	}
	
	/** Get all games from the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public List<Game> getGame(int id) throws ClassNotFoundException, SQLException {
		List<Game> games = new ArrayList<Game>();
		
		Connection con = null;
		PreparedStatement gameStatement = null;
		PreparedStatement bookStatement = null;
		ResultSet gameResults = null;
		ResultSet bookResults = null;
		
		try {
			con = getConnection();
			gameStatement = con.prepareStatement("SELECT * FROM games WHERE id=? ORDER BY title ASC;");
			gameStatement.setInt(1, id);
			gameResults = gameStatement.executeQuery();

			while(gameResults.next()) {
				int bookId = gameResults.getInt("book_id");
				bookStatement = con.prepareStatement("SELECT title FROM books WHERE id=?;");
				bookStatement.setInt(1, bookId);
				bookResults = bookStatement.executeQuery();
				
				if(bookResults.next()) {
					String bookTitle = bookResults.getString("title");
					String title = gameResults.getString("title");
					int gid = gameResults.getInt("id");
					String genre = gameResults.getString("genre");
					int pgi = gameResults.getInt("pgi");
					
					games.add(new Game(gid, title, genre, pgi, bookTitle, bookId));
				}
				
				bookResults.close();
				bookStatement.close();
			}
		} finally {
			if(gameResults != null)
				gameResults.close();
			if(gameStatement != null)
				gameStatement.close();
			if(con != null)
				con.close();
		}
		
		return games;
	}
	
	/** Get all films from the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public List<Film> getFilm(int id) throws ClassNotFoundException, SQLException {
		List<Film> films = new ArrayList<Film>();
		
		Connection con = null;
		PreparedStatement filmStatement = null;
		PreparedStatement bookStatement = null;
		ResultSet filmResults = null;
		ResultSet bookResults = null;
		
		try {
			con = getConnection();
			filmStatement = con.prepareStatement("SELECT * FROM films Where id=? ORDER BY title ASC;");
			filmStatement.setInt(1, id);
			filmResults = filmStatement.executeQuery();
			
			while(filmResults.next()) {
				int bookId = filmResults.getInt("book_id");
				bookStatement = con.prepareStatement("SELECT title FROM books WHERE id=?;");
				bookStatement.setInt(1, bookId);
				bookResults = bookStatement.executeQuery();
				
				if(bookResults.next()) {
					String bookTitle = bookResults.getString("title");
					String title = filmResults.getString("title");
					int fid = filmResults.getInt("id");
					String genre = filmResults.getString("genre");
					int pgi = filmResults.getInt("pgi");
					Time time = filmResults.getTime("duration");
					LocalTime duration = time.toLocalTime();
					
					films.add(new Film(fid, title, duration, genre, pgi, bookTitle, bookId));
				}
				
				bookResults.close();
				bookStatement.close();
			}
			
		} finally {
			if(filmResults != null)
				filmResults.close();
			if(filmStatement != null)
				filmStatement.close();
			if(con != null)
				con.close();
		}
		
		return films;
	}
	
	/** Get all games from the database. 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public List<Game> getGames() throws ClassNotFoundException, SQLException {
		List<Game> games = new ArrayList<Game>();
		
		Connection con = null;
		PreparedStatement gameStatement = null;
		PreparedStatement bookStatement = null;
		ResultSet gameResults = null;
		ResultSet bookResults = null;
		
		try {
			con = getConnection();
			gameStatement = con.prepareStatement("SELECT * FROM games ORDER BY title ASC;");
			gameResults = gameStatement.executeQuery();

			while(gameResults.next()) {
				int bookId = gameResults.getInt("book_id");
				bookStatement = con.prepareStatement("SELECT title FROM books WHERE id=?;");
				bookStatement.setInt(1, bookId);
				bookResults = bookStatement.executeQuery();
				
				if(bookResults.next()) {
					String bookTitle = bookResults.getString("title");
					String title = gameResults.getString("title");
					int id = gameResults.getInt("id");
					String genre = gameResults.getString("genre");
					int pgi = gameResults.getInt("pgi");
					
					games.add(new Game(id, title, genre, pgi, bookTitle, bookId));
				}
				
				bookResults.close();
				bookStatement.close();
			}
		} finally {
			if(gameResults != null)
				gameResults.close();
			if(gameStatement != null)
				gameStatement.close();
			if(con != null)
				con.close();
		}
		
		return games;
	}
	
	/** Alter an existing book.
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public void alterFilm(int id, String title, String genre, int pgi, LocalTime duration) throws ClassNotFoundException, SQLException {
		String query = "UPDATE films INNER JOIN SET ";
		int indices[] = {-1, -1, -1, -1, -1};
		int index = 0;
		
		if(title.length() > 0) {
			query += "title=? ";
			indices[0] = index;
			index++;
		}
		if(genre.length() > 0) {
			query += "genre=? ";
			
			if(indices[index - 1] > -1)
				query += ",";
			
			indices[1] = index;
			index++;
		}
		if(pgi > 0) {
			query += "pgi=? ";
			
			if(indices[index - 1] > -1)
				query += ",";
			
			indices[2] = index;
			index++;
		}
		if(duration.getHour() != 0 && duration.getMinute() != 0) {
			query += "duration=? ";
			
			indices[3] = index;
			index++;
		}
		
		indices[4] = index;
		
		query += "WHERE id=?;";
		
			
		try (Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement(query);){
			if(indices[0] > -1)
				statement.setString(indices[0], title);
			if(indices[1] > -1)
				statement.setString(indices[1], genre);
			if(indices[2] > -1)
				statement.setInt(indices[2], pgi);
			if(indices[3] > -1)
				statement.setTime(indices[3], Time.valueOf(duration));

			statement.setInt(indices[4], id);
			
			statement.executeUpdate();
			
		}
	}
	
	/** Main function for testing. **/
	public static void main(String args[]) {
		
	}
}

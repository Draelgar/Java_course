package web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseHandler;

/**
 * Servlet implementation class AddBookServlet
 */
@WebServlet("/AddBook")
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		
		byte[] bytes = title.getBytes(StandardCharsets.ISO_8859_1);
		title = new String(bytes, StandardCharsets.UTF_8);
		
		bytes = author.getBytes(StandardCharsets.ISO_8859_1);
		author = new String(bytes, StandardCharsets.UTF_8);
		
		DatabaseHandler db = new DatabaseHandler();
		try {
			db.addBook(title, author);
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
		
		response.sendRedirect("Books");
	}

}

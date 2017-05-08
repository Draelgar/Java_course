package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import book.Book;
import database.DatabaseHandler;

/**
 * Servlet implementation class BooksServlet
 */
@WebServlet("/Books")
public class BooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseHandler db = new DatabaseHandler();
		try {
			request.setCharacterEncoding("UTF-8");
			
			String author = request.getParameter("author");
			
			List<Book> books = null;
			if(author == null) {	
				books = db.getBooks();
			}
			else {
				books = db.getBooks("", author);
			}
			
			response.setContentType("application/json;characterset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(books));
			out.flush();
			
		} catch (ClassNotFoundException | SQLException e) {
			response.getWriter().append("Error: " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

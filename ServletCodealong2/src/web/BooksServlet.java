package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			
			List<Book> books = db.getBooks();
			request.setAttribute("books", books.toArray());
			
			// Send to JSP.
			RequestDispatcher dispatcher = request
                     .getRequestDispatcher("WEB-INF/books.jsp");  
			if (dispatcher != null){  
			   dispatcher.forward(request, response);  
			}
			
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

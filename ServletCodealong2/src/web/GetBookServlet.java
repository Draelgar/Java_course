package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.Book;
import database.DatabaseHandler;

/**
 * Servlet implementation class GetBookServlet
 */
@WebServlet("/GetBook")
public class GetBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseHandler db = new DatabaseHandler();
		try {
			Book book = db.getBook(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("book", book);
			
			// Send to JSP.
			RequestDispatcher dispatcher = request
                     .getRequestDispatcher("WEB-INF/book.jsp");  
			if (dispatcher != null){  
			   dispatcher.forward(request, response);  
			}
			
		} catch(ClassNotFoundException | SQLException e) {
			response.getWriter().append("Error: " + e.getMessage());
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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
 * Servlet implementation class BookServlet
 */
@WebServlet(name = "Book", urlPatterns = { "/Book" })
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseHandler db = new DatabaseHandler();
		request.setCharacterEncoding("UTF-8");
		
		try {
			int id = Integer.parseInt(request.getParameter("id").substring(1)); // Remove that damned colon character...
			
			Book book = db.getBook(id);
			
			response.setContentType("application/json;characterset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(book));
			out.flush();
			
		} catch (SQLException | NumberFormatException | ClassNotFoundException e) {
			response.getWriter().append("Error: " + e.getMessage());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import book.Book;
import database.DatabaseHandler;
import database.Parameters;

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
		request.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		
		if(title.equalsIgnoreCase("undefined")) {
			title="";
		}
		if(firstName.equalsIgnoreCase("undefined")) {
			firstName="";
		}
		if(lastName.equalsIgnoreCase("undefined")) {
			lastName="";
		}
		
		DatabaseHandler db = new DatabaseHandler();
		
		if(title.length() > 0 && firstName.length() > 0 && lastName.length() > 0) {	
			try {
				db.addBook(title, firstName, lastName);
			} catch (ClassNotFoundException e) {
			} catch (SQLException e) {
			}
		}
		
		List<Book> books;
		try {
			books = db.getBooks();
		
			response.setContentType("application/json;characterset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(books));
			out.flush();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String parameters = request.getReader().lines().collect(Collectors.joining());

		Gson gson = new Gson();
		
		Parameters info = gson.fromJson(parameters, Parameters.class);
		
		
		if(info.title.equalsIgnoreCase("undefined")) {
			info.title="";
		}
		if(info.first_name.equalsIgnoreCase("undefined")) {
			info.first_name="";
		}
		if(info.last_name.equalsIgnoreCase("undefined")) {
			info.last_name="";
		}
		
		DatabaseHandler db = new DatabaseHandler();
		
		if(info.title.length() > 0 && info.first_name.length() > 0 && info.last_name.length() > 0) {	
			try {
				db.addBook(info.title, info.first_name, info.last_name);
			} catch (ClassNotFoundException e) {
			} catch (SQLException e) {
			}
		}
		
		List<Book> books;
		try {
			books = db.getBooks();
		
			response.setContentType("application/json;characterset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(books));
			out.flush();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}

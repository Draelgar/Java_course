package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DatabaseHandler;
import database.IntParam;
import database.Parameters;

/**
 * Servlet implementation class RemoveBookServlet
 */
@WebServlet(name = "RemoveBook", urlPatterns = { "/RemoveBook" })
public class RemoveBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseHandler db = new DatabaseHandler();
		request.setCharacterEncoding("UTF-8");
		
		try {
			String parameters = request.getReader().lines().collect(Collectors.joining());

			Gson gson = new Gson();
			
			IntParam param = gson.fromJson(parameters, IntParam.class);
			
			db.deleteBook(param.id);
			response.getWriter().append("Success!");
			
		} catch (ClassNotFoundException e) {
			response.getWriter().append("Class Not Found Error: " + e.getMessage());
		} catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
		}
	}

}

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
 * Servlet implementation class AlterBookServlet
 */
@WebServlet("/AlterBook")
public class AlterBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterBookServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseHandler db = new DatabaseHandler();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		
		byte[] bytes = title.getBytes(StandardCharsets.ISO_8859_1);
		title = new String(bytes, StandardCharsets.UTF_8);
		
		bytes = firstName.getBytes(StandardCharsets.ISO_8859_1);
		firstName = new String(bytes, StandardCharsets.UTF_8);
		
		bytes = lastName.getBytes(StandardCharsets.ISO_8859_1);
		lastName = new String(bytes, StandardCharsets.UTF_8);
		
		try {
			db.alterBook(id, title, firstName, lastName);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("Books");
	}

}

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

import book.Film;
import database.DatabaseHandler;

/**
 * Servlet implementation class GetFilmServlet
 */
@WebServlet("/GetFilm")
public class GetFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFilmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseHandler db = new DatabaseHandler();
		try {
			request.setCharacterEncoding("UTF-8");
			
			int id =Integer.parseInt(request.getParameter("id"));
					
			List<Film> films = db.getFilm(id);
			request.setAttribute("film", films.get(0));
			
			// Send to JSP.
			RequestDispatcher dispatcher = request
                     .getRequestDispatcher("WEB-INF/Film.jsp");  
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

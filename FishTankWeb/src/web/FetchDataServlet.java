package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aquarium.Aquarium;
import aquarium.Fish;

/**
 * Servlet implementation class FetchDataServlet
 */
@WebServlet("/FetchDataServlet")
public class FetchDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Aquarium mAquarium;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchDataServlet() {
        super();
        mAquarium = new Aquarium();
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
		// TODO Auto-generated method stub
		
		ArrayList<Fish> fishes = mAquarium.fetch();

		String data = "";
		
		if(fishes != null) {
			for(Fish fish : fishes) {
				data += fish.name() + "," + fish.fishType().toString().toLowerCase() + ","
						+ fish.age() + "," + fish.weight() + "," + fish.length() + ":";
			}
			
			data = data.substring(0, data.length() - 1);
		
			response.getWriter().append(data);
		}
		else
			response.getWriter().append("Error, Something went wrong!");
	}

}

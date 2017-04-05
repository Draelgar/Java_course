package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aquarium.*;

/**
 * Servlet implementation class AquariumServlet
 */
@WebServlet("/AquariumServlet")
public class AquariumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Aquarium mAquarium;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AquariumServlet() {
        super();
        mAquarium = new Aquarium();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		double weight = Double.parseDouble(request.getParameter("weight"));
		double length = Double.parseDouble(request.getParameter("length"));
		int age = Integer.parseInt(request.getParameter("age"));
		String genderString = request.getParameter("gender");
		
		boolean gender = false;
		if(genderString.equalsIgnoreCase("male"))
			gender = true;
		
		if(type.equalsIgnoreCase("Shark"))
			mAquarium.addFish(new Shark(name, weight, length, age, gender));
		else if(type.equalsIgnoreCase("Eel"))
			mAquarium.addFish(new Eel(name, weight, length, age, gender));
		
		response.sendRedirect("index.html");
	}

}

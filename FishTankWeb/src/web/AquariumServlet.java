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
		
		FishType fishType = FishType.valueOf(type.toUpperCase());
		
		switch(fishType) {
			case SHARK: {
				mAquarium.addFish(new Shark(name, weight, length, age, gender));
				break;
			}
			case EEL: {
				mAquarium.addFish(new Eel(name, weight, length, age, gender));
				break;
			}
			case PIKE: {
				mAquarium.addFish(new Pike(name, weight, length, age, gender));
				break;
			}
			case CLOWNFISH: {
				mAquarium.addFish(new Clownfish(name, weight, length, age, gender));
				break;
			}
			case BASS: {
				mAquarium.addFish(new Bass(name, weight, length, age, gender));
				break;
			}
			default: {
				
			}
		}
		
		response.sendRedirect("index.html");
	}

}

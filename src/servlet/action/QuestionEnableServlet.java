package servlet.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.form.creation.QuestionActivation;

/**
 * Servlet implementation class QuestionEnableServlet
 */
@WebServlet("/admin/enable/question")
public class QuestionEnableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionEnableServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("id") != null) {
			try {
				Integer id = Integer.valueOf(request.getParameter("id"));
				if(QuestionActivation.enable(request, id)) {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/sondage/question");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/question");
					dispatcher.forward(request, response);
				}
			} catch(NumberFormatException e) {
				getServletContext().log(e.getMessage());
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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

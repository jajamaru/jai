package servlet.sondage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import context.QuestionActivation;

/**
 * Servlet implementation class VoteServlet
 */
@WebServlet("/student/action/vote")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//private static final String IS_POLLED = "isPolled";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("id") != null) {
			try {
				Integer id = Integer.valueOf(request.getParameter("id"));
				if(QuestionActivation.addVote(id)) {
					//request.getSession(true).setAttribute(IS_POLLED, true);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/student/display/question");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/student/display/question");
					dispatcher.forward(request, response);
				}
			} catch(NumberFormatException e) {
				getServletContext().log(e.getMessage());
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} else {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}

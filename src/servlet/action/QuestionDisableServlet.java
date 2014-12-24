package servlet.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import context.QuestionActivation;

/**
 * Servlet implementation class QuestionDisableServlet
 */
@WebServlet("/admin/disable/question")
public class QuestionDisableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionDisableServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("id") != null) {
			try {
				Integer id = Integer.valueOf(request.getParameter("id"));
				if(QuestionActivation.disable(request, id)) {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/sondage/result");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/question");
					dispatcher.forward(request, response);
				}
			} catch(NumberFormatException e) {
				getServletContext().log(e.getMessage());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/question");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

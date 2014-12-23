package servlet.form.verification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import context.FormTools;
import entity.Answer;

/**
 * Servlet implementation class AnswerFormValidationServlet
 */
@WebServlet("/admin/validation/answer")
public class AnswerFormValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerFormValidationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		errors.clear();
		if(request.getParameter("desc") != null && !"".equals(request.getParameter("desc"))) {
			Answer answer = createAnswer(request);
			FormTools.addAnswer(request, answer);
		} else {
			errors.put("answer_err_desc", true);
			request.setAttribute("error", errors);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/answer");
		dispatcher.forward(request, response);
	}
	
	private Answer createAnswer(HttpServletRequest request) {
		Answer answer = new Answer();
		answer.setDesc(request.getParameter("desc"));
		return answer;
	}

}

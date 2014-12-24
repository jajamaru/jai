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

import context.CreateQuestion;
import entity.Question;

/**
 * Servlet implementation class QuestionFormValidationServlet
 */
@WebServlet("/admin/validation/question")
public class QuestionFormValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionFormValidationServlet() {
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
			Question question = CreateQuestion.getQuestion(request);
			question.setDesc(request.getParameter("desc"));
			CreateQuestion.setQuestion(request, question);
		} else {
			errors.put("question_err_desc", true);
			request.setAttribute("error", errors);
		}
		if(errors.isEmpty()) {
			//On autorise la validation de la question
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/answer");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/question");
			dispatcher.forward(request, response);
		}
	}

}

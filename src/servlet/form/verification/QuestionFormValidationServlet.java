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

import servlet.form.creation.QuestionFormTools;
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
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display-list/question");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String> errors = new HashMap<String, String>();
		Question question = QuestionFormTools.getAncCreateBeginQuestion(request);
		if(request.getParameter("desc") != null && !"".equals(request.getParameter("desc"))) {
			question.setDesc(request.getParameter("desc"));
			QuestionFormTools.setBeginQuestion(question, request);
		} else {
			errors.put("err_desc", "Le champs 'desc' est vide");
			request.setAttribute("error", errors);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display-list/question");
		dispatcher.forward(request, response);
	}

}

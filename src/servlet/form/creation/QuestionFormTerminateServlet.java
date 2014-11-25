package servlet.form.creation;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Answer;
import entity.Question;

/**
 * Servlet implementation class QuestionFormBuildServlet
 */
@WebServlet("/admin/terminate/question")
public class QuestionFormTerminateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionFormTerminateServlet() {
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
		Question question = FormTools.getQuestion(request);
		Map<Integer, Answer> answersId = FormTools.getAnswers(request);
		if(answersId.size() > 0 && question.getDesc() != null) {
			for(Entry<Integer, Answer> entry : answersId.entrySet()) {
				question.addAnswer(entry.getValue());
			}
			answersId.clear();
			FormTools.cleanAnswer(request);
			FormTools.cleanQuestion(request);
			FormTools.setReadyQuestion(request, question);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/answer");
			dispatcher.forward(request, response);
		} else {
			// Un problème est survenue
			System.out.println("Un problème est survenue lors de la création d'une question !");
		}
	}

}

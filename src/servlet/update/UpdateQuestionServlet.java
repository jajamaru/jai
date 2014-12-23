package servlet.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listener.InitDataBase;
import context.UpdateQuestion;
import entity.Answer;
import entity.Question;

/**
 * Servlet implementation class UpdateQuestionServlet
 */
@WebServlet("/admin/update/question")
public class UpdateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String KEY_ANSWER_ID = "answerId";
	private static final String KEY_ANSWER_DESC = "answerDesc";
	private static final String KEY_ANSWER_TRUTH = "answerIsTrue";
	private static final String KEY_ANSWER_DELETE = "answerDelete";
	
	private static final String NB_ANSWERS = "nbAnswers";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuestionServlet() {
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
				Question question = null;
				if((question = retrieveQuestion(request, id)) != null) {
					UpdateQuestion.startUpdate(request, question);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/update");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/question");
					dispatcher.forward(request, response);
				}
			} catch(NumberFormatException e) {
				request.getServletContext().log(e.getMessage());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/question");
				dispatcher.forward(request, response);
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
		Question question = null;
		if((question = UpdateQuestion.getQuestion(request)) != null) {
			Map<String, Boolean> errors = new HashMap<String, Boolean>();
			errors.clear();
			if(request.getParameter("desc") != null && !"".equals(request.getParameter("desc"))) {
				question.setDesc(request.getParameter("desc"));
				
				try {
					List<Answer> answersList = checkAndGetAnswers(request, question.getId());
					question.setAnswers(answersList);
				} catch(IllegalArgumentException e) {
					errors.put(e.getMessage(), true);
				}
				
				UpdateQuestion.setQuestion(request, question);
				UpdateQuestion.checkUpdate(request);
			} else {
				errors.put("question_err_desc", true);
			}
			request.setAttribute("error", errors);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/update");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/create/question");
			dispatcher.forward(request, response);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Question retrieveQuestion(HttpServletRequest request, int id) {
		Question question = null;
		List<Question> questionList = (List<Question>)request.getServletContext().getAttribute(InitDataBase.QUESTION_LIST);
		for(Question q : questionList) {
			if(q.getId() == id) {
				question = q;
			}
		}
		return question;
	}
	
	private List<Answer> checkAndGetAnswers(HttpServletRequest request, Integer idQuestion) throws IllegalArgumentException {
		List<Answer> answersList = new ArrayList<Answer>();
		int size = Integer.valueOf(request.getParameter(NB_ANSWERS));
		for(int i=0; i<size; i++) {
			Answer answer = new Answer();
			if(request.getParameter(KEY_ANSWER_DELETE+i) != null) {
				System.out.println("answer"+i);
				if(request.getParameter(KEY_ANSWER_ID+i) != null) {
					Integer id = Integer.valueOf(request.getParameter(KEY_ANSWER_ID+i));
					UpdateQuestion.addDeletedAnswer(request, id);
				}
				continue;
			}
			if(request.getParameter(KEY_ANSWER_ID+i) != null) {
				Integer id = Integer.valueOf(request.getParameter(KEY_ANSWER_ID+i));
				answer.setId(id);
			}
			if(request.getParameter(KEY_ANSWER_TRUTH+i) != null) {
				String truth = request.getParameter(KEY_ANSWER_TRUTH+i);
				answer.setTrue(truth.equalsIgnoreCase("vraie"));
			}
			if(request.getParameter(KEY_ANSWER_DESC+i) != null) {
				String desc = request.getParameter(KEY_ANSWER_DESC+i);
				if("".equals(desc)) {
					throw new IllegalArgumentException("answer_err_desc");
				}
				answer.setDesc(desc);
			}
			answer.setIdQuestion(idQuestion);
			answersList.add(answer);
		}
		return answersList;
	}

}

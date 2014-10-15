package servlet.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.QuestionRdg;
import tools.DBUtils;
import entity.Answer;
import entity.Question;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/action/question")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer id = null;
		if(request.getParameter("id") != null) {
			try {
				Question question = null;
				QuestionRdg rdg = new QuestionRdg(DBUtils.getConnection());
				id = Integer.valueOf(request.getParameter("id"));
				question = rdg.retrieve(id);
				request.setAttribute("question", question);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
			
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Question question = null;
		if((question = checkAndGetQuestionWithId(request)) != null) {
			try {
				Integer id = null;
				QuestionRdg rdg = new QuestionRdg(DBUtils.getConnection());
				rdg.update(question);
				id = question.getId();
				question = rdg.retrieve(id);
				request.setAttribute("question", question);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Question question = null;
		if((question = checkAndGetQuestion(request)) != null) {
			try {
				QuestionRdg rdg = new QuestionRdg(DBUtils.getConnection());
				rdg.persist(question);
				request.setAttribute("question", question);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Question question = null;
		if((question = checkAndGetQuestionWithId(request)) != null) {
			try {
				QuestionRdg rdg = new QuestionRdg(DBUtils.getConnection());
				rdg.delete(question);
				request.setAttribute("question", question);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");
		dispatcher.forward(request, response);
	}
	
	private Question checkAndGetQuestionWithId(HttpServletRequest request) {
		Question question = null;
		if((question = checkAndGetQuestion(request)) != null && request.getParameter("id") != null) {
			question.setId(Integer.valueOf(request.getParameter("id")));
			return question;
		}
		return null;
	}
	
	private Question checkAndGetQuestion(HttpServletRequest request) {
		Question question = null;
		List<Answer> answers = null;
		if(request.getParameter("desc") != null && request.getParameter("idQcm") != null &&
				(answers = checkAndGetAnswers(request)) != null) {
			question = new Question();
			question.setDesc(request.getParameter("desc"));
			question.setIdQcm(Integer.valueOf(request.getParameter("idQcm")));
			question.setAnswers(answers);
		}
		return question;
	}
	
	private List<Answer> checkAndGetAnswers(HttpServletRequest request) {
		Boolean good = true;
		List<Answer> answers = new ArrayList<Answer>();
		if(request.getAttribute("answers") != null) {
			answers = (List<Answer>)request.getAttribute("answers");
			for(Answer a : answers) {
				if(!checkAnswer(a)) good = false;
			}
		}
		return (answers.isEmpty() || good)?(null):(answers);
	}
	
	public boolean checkAnswer(Answer answer) {
		return answer.getDesc() != null && answer.getIdQuestion() != null &&
				answer.getCpt() > -1;
	}

}

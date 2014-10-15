package servlet.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdg.AnswerRdg;
import tools.DBUtils;
import entity.Answer;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/action/answer")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerServlet() {
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
				Answer answer = null;
				AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
				id = Integer.valueOf(request.getParameter("id"));
				answer = rdg.retrieve(id);
				request.setAttribute("answer", answer);
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
		Answer answer = null;
		if((answer = checkAndGetAnswerWithId(request)) != null) {
			try {
				Integer id = null;
				AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
				id = answer.getId();
				rdg.update(answer);
				answer = rdg.retrieve(id);
				request.setAttribute("answer", answer);
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
		Answer answer = null;
		if((answer = checkAndGetAnswer(request)) != null) {
			try {
				AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
				rdg.persist(answer);
				request.setAttribute("answer", answer);
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
		Answer answer = null;
		if((answer = checkAndGetAnswerWithId(request)) != null) {
			try {
				AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
				rdg.delete(answer);
				request.setAttribute("answer", answer);
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
	
	private Answer checkAndGetAnswer(HttpServletRequest request) {
		Answer answer = null;
		if(request.getParameter("desc") != null && request.getParameter("isTrue") != null && 
				request.getParameter("cpt") != null && request.getParameter("idQuestion") != null) {
			answer = new Answer();
			answer.setDesc(request.getParameter("desc"));
			answer.setTrue(Boolean.valueOf(request.getParameter("isTrue")));
			answer.setCpt(Integer.valueOf(request.getParameter("cpt")));
			answer.setIdQuestion(Integer.valueOf(request.getParameter("idQuestion")));
		}
		return answer;
	}
	
	private Answer checkAndGetAnswerWithId(HttpServletRequest request) {
		Answer answer = checkAndGetAnswer(request);
		if(answer != null && request.getParameter("id") != null) {
			answer.setId(Integer.valueOf(request.getParameter("id")));
			return answer;
		}
		else return null;
	}

}

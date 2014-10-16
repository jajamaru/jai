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

import org.json.JSONException;
import org.json.JSONObject;

import rdg.QCMRdg;
import tools.Checking;
import tools.DBUtils;
import entity.QCM;
import entity.Question;

/**
 * Servlet implementation class SelectServlet
 */
@WebServlet("/action/qcm")
public class QcmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public QcmServlet() {
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
				QCM qcm = null;
				id = Integer.valueOf(request.getParameter("id"));
				QCMRdg rdg = new QCMRdg(DBUtils.getConnection());
				qcm = rdg.retrieve(id);
				request.setAttribute("qcm", qcm);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		QCM qcm = null;
		if(request.getParameter("qcm") != null) {
			try {
				qcm = QCM.retrieveObject(new JSONObject(request.getParameter("qcm")));
				QCMRdg rdg = new QCMRdg(DBUtils.getConnection());
				rdg.update(qcm);
				Integer id = qcm.getId();
				qcm = rdg.retrieve(id);
				request.setAttribute("qcm", qcm);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		QCM qcm = null;
		if(request.getParameter("qcm") != null) {
			try {
				qcm = QCM.retrieveObject(new JSONObject(request.getParameter("qcm")));
				QCMRdg rdg = new QCMRdg(DBUtils.getConnection());
				rdg.persist(qcm);
				request.setAttribute("qcm", qcm);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		QCM qcm = null;
		if(request.getParameter("qcm") != null) {
			try {
				qcm = QCM.retrieveObject(new JSONObject(request.getParameter("qcm")));
				QCMRdg rdg = new QCMRdg(DBUtils.getConnection());
				rdg.delete(qcm);
				request.setAttribute("qcm", qcm);
				request.setAttribute("statut", "ok");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("statut", "nok");
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	private QCM checkAndGetQcmWithId(HttpServletRequest request) {
		QCM qcm = null;
		if((qcm = checkAndGetQcm(request)) != null && request.getParameter("id") != null) {
			qcm.setId(Integer.valueOf(request.getParameter("id")));
		}
		return qcm;
	}
	
	private QCM checkAndGetQcm(HttpServletRequest request) {
		QCM qcm = null;
		List<Question> questions = new ArrayList<Question>();
		if(request.getParameter("title") != null && (questions = checkAndGetQuestion(request)) != null) {
			qcm = new QCM();
			qcm.setTitle(request.getParameter("title"));
			qcm.setQuestions(questions);
		}
		return qcm;
	}
	
	private List<Question> checkAndGetQuestion(HttpServletRequest request) {
		Boolean good = true;
		List<Question> questions = new ArrayList<Question>();
		if(request.getAttribute("question") != null) {
			questions = (List<Question>)request.getAttribute("question");
			for(Question q : questions) {
				if(!Checking.checkQuestion(q)) good = false;
			}
		}
		return (questions != null || questions.isEmpty() || good)?(null):(questions);
	}
	

}

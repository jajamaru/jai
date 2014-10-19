package servlet.action;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import rdg.QuestionRdg;
import tools.DBUtils;
import entity.Question;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/admin/action/question")
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
				if(question != null) {
					request.setAttribute("question", question);
					request.setAttribute("statut", "ok");
				} else {
					((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La question demandée n'existe pas",e);
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Question question = null;
		if(request.getParameter("question") != null) {
			try {
				Integer id = null;
				question = Question.retrieveObject(new JSONObject(request.getParameter("question")));
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
		Question question = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = request.getReader();
			writer = response.getWriter();
			String line = "";
			String json = "";
			while((line = reader.readLine()) != null) {
				json += line;
			}
			
			question = Question.retrieveObject(new JSONObject(json));
			QuestionRdg rdg = new QuestionRdg(DBUtils.getConnection());
			rdg.persist(question);
			Integer id = question.getId();
			question = rdg.retrieve(id);
			
			writer.write(question.stringify());
			writer.flush();
			
		} catch(JSONException e) {
			request.getServletContext().log("Problème de parsing au niveau du json",e);
		} catch(SQLException e) {
			request.getServletContext().log("Problème au niveau de la base de donnée",e);
		} catch(IOException e) {
			request.getServletContext().log("Problème d'écriture/lecture des flux lors du doPut QCM",e);
		} finally {
			close(writer, request);
			close(reader, request);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void close(Closeable inOut, HttpServletRequest request) {
		if(inOut != null) {
			try {
				inOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Problème lors de la fermeture I/O",e);
			}
		}
	}

}

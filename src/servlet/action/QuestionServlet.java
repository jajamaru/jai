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
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/question");
					dispatcher.forward(request, response);
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (NumberFormatException e) {
				request.getServletContext().log("L'id passer en param�tre n'est pas un entier",e);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La question demand�e n'existe pas",e);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/question");
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
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La question ne respecte pas le format json d�fini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un probl�me est survenu lors de la mise � jour de la question",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} 
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
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
			request.getServletContext().log("Le param�tre donn� n'est pas sous format json",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(SQLException e) {
			request.getServletContext().log("Le json fourni ne correspond pas au format attendu",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(IOException e) {
			request.getServletContext().log("Probl�me d'�criture/lecture des flux lors du doPut Question",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			close(writer, request);
			close(reader, request);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Question question = null;
		if(request.getParameter("question") != null) {
			try {
				question = Question.retrieveObject(new JSONObject(request.getParameter("question")));
				QuestionRdg rdg = new QuestionRdg(DBUtils.getConnection());
				rdg.delete(question);
				request.setAttribute("question", question);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La question ne respecte pas le format json d�fini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un probl�me est survenu lors de la suppression de la question",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} 
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	private void close(Closeable inOut, HttpServletRequest request) {
		if(inOut != null) {
			try {
				inOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Probl�me lors de la fermeture I/O",e);
			}
		}
	}

}

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

import rdg.AnswerRdg;
import tools.DBUtils;
import entity.Answer;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/admin/action/answer")
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
				if(answer != null) {
					request.setAttribute("answer", answer);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (NumberFormatException e) {
				request.getServletContext().log("L'id passer en paramètre n'est pas un entier",e);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La réponse demandée n'existe pas",e);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
		Answer answer = null;
		if(request.getParameter("answer") != null) {
			try {
				Integer id = null;
				answer = Answer.retrieveObject(new JSONObject(request.getParameter("answer")));
				AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
				id = answer.getId();
				rdg.update(answer);
				answer = rdg.retrieve(id);
				request.setAttribute("answer", answer);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La réponse ne respecte pas le format json défini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un problème est survenu lors de la mise à jour de la réponse",e);
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
		Answer answer = null;
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
			
			answer = Answer.retrieveObject(new JSONObject(json));
			AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
			rdg.persist(answer);
			Integer id = answer.getId();
			answer = rdg.retrieve(id);
			
			writer.write(answer.stringify());
			writer.flush();
			
		} catch(JSONException e) {
			request.getServletContext().log("Le paramètre donné n'est pas sous format json",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(SQLException e) {
			request.getServletContext().log("Le json fourni ne correspond pas au format attendu",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(IOException e) {
			request.getServletContext().log("Problème d'écriture/lecture des flux lors du doPut Answer",e);
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
		Answer answer = null;
		if(request.getParameter("answer") != null) {
			try {
				answer = Answer.retrieveObject(new JSONObject(request.getParameter("answer")));
				AnswerRdg rdg = new AnswerRdg(DBUtils.getConnection());
				rdg.delete(answer);
				request.setAttribute("answer", answer);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("La question ne respecte pas le format json défini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un problème est survenu lors de la suppression de la question",e);
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
				request.getServletContext().log("Problème lors de la fermeture I/O",e);
			}
		}
	}

}

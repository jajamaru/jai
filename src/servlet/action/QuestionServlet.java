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

import listener.InitDataBase;

import org.json.JSONException;
import org.json.JSONObject;

import context.QuestionActivation;
import rdg.QuestionRdg;
import entity.MissingJsonArgumentException;
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
				QuestionRdg rdg = (QuestionRdg)getServletContext().getAttribute(InitDataBase.RDG_QUESTION);
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
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log(e.getMessage(),e);
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
		PrintWriter writer = null;
		System.out.println("Question " + request.getParameter("question"));
		if(request.getParameter("question") != null) {
			try {
				Integer id = null;
				question = Question.retrieveObject(new JSONObject(request.getParameter("question")));
				QuestionRdg rdg = (QuestionRdg)getServletContext().getAttribute(InitDataBase.RDG_QUESTION);
				rdg.update(question);
				id = question.getId();
				question = rdg.retrieve(id);
				
				writer = response.getWriter();
				writer.write(question.stringify());
				writer.flush();
				
				System.out.println("Mise à jour effectuée !");
				
				QuestionActivation.updateQuestionToContext(request, question);
			} catch(MissingJsonArgumentException e) {
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} finally {
				close(writer, request);
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
			QuestionRdg rdg = (QuestionRdg)getServletContext().getAttribute(InitDataBase.RDG_QUESTION);
			rdg.persist(question);
			Integer id = question.getId();
			question = rdg.retrieve(id);
			
			writer.write(question.stringify());
			writer.flush();
			
			//On ajoute la question au contexte
			QuestionActivation.addQuestionToContext(request, question);
		} catch(MissingJsonArgumentException e) {
			request.getServletContext().log(e.getMessage(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(JSONException e) {
			request.getServletContext().log(e.getMessage(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(SQLException e) {
			request.getServletContext().log(e.getMessage(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(IOException e) {
			request.getServletContext().log(e.getMessage(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			close(reader, request);
			close(writer, request);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = null;
		Integer id = null;
		if(request.getParameter("id") != null) {
			try {
				Question question = null;
				id = Integer.valueOf(request.getParameter("id"));
				QuestionRdg rdg = (QuestionRdg)getServletContext().getAttribute(InitDataBase.RDG_QUESTION);
				question = rdg.retrieve(id);
				if(question == null) {
					request.getServletContext().log("La suppresion ne peut pas se faire car aucune question ne correspond à l'id donné !");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} else {
					rdg.delete(id);
					writer = response.getWriter();
					writer.write(question.stringify());
					writer.flush();
					QuestionActivation.removeQuestionFromContext(request, id);
				}
			} catch (JSONException e) {
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch(NumberFormatException e) {
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} finally {
				close(writer, request);
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
				request.getServletContext().log(e.getMessage(),e);
			}
		}
	}

}

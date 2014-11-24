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

import rdg.ResultRdg;
import entity.MissingJsonArgumentException;
import entity.Result;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/admin/action/result")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultServlet() {
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
				Result result = null;
				ResultRdg rdg = (ResultRdg)getServletContext().getAttribute(InitDataBase.RDG_RESULT);
				id = Integer.valueOf(request.getParameter("id"));
				result = rdg.retrieve(id);
				if(result != null) {
					request.setAttribute("result", result);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/result");
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
				request.getServletContext().log("Le résultat du questionnaire demandé n'existe pas",e);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display/result");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Result result = null;
		if(request.getParameter("result") != null) {
			try {
				Integer id = null;
				result = Result.retrieveObject(new JSONObject(request.getParameter("result")));
				ResultRdg rdg = (ResultRdg)getServletContext().getAttribute(InitDataBase.RDG_RESULT);
				id = result.getId();
				rdg.update(result);
				result = rdg.retrieve(id);
				request.setAttribute("result", result);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display-list/result");
				dispatcher.forward(request, response);
			} catch (MissingJsonArgumentException e) {
				request.getServletContext().log(e.getMessage(),e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (JSONException e) {
				request.getServletContext().log("Le résultat du questionnaire ne respecte pas le format json défini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				request.getServletContext().log("Un problème est survenu lors de la mise à jour du résultat",e);
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
		Result result = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = request.getReader();
			//writer = response.getWriter();
			String line = "";
			String json = "";
			while((line = reader.readLine()) != null) {
				json += line;
			}
			
			result = Result.retrieveObject(new JSONObject(json));
			ResultRdg rdg = (ResultRdg)getServletContext().getAttribute(InitDataBase.RDG_RESULT);
			rdg.persist(result);
			Integer id = result.getId();
			result = rdg.retrieve(id);
			
			request.setAttribute("result", result);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display-list/result");
			dispatcher.forward(request, response);
			
			/*writer.write(result.stringify());
			writer.flush();*/
		} catch (MissingJsonArgumentException e) {
			request.getServletContext().log(e.getMessage(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(JSONException e) {
			request.getServletContext().log("Le paramètre donné n'est pas sous format json",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(SQLException e) {
			request.getServletContext().log("Le json fourni ne correspond pas au format attendu",e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(IOException e) {
			request.getServletContext().log("Problème d'écriture/lecture des flux lors du doPut QCMResult",e);
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
		Result result = null;
		if(request.getParameter("id") != null) {
			try {
				Integer id = Integer.valueOf(request.getParameter("id"));
				ResultRdg rdg = (ResultRdg)getServletContext().getAttribute(InitDataBase.RDG_RESULT);
				rdg.delete(id);
				request.setAttribute("result", result);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display-list/result");
				dispatcher.forward(request, response);
			} catch(NumberFormatException e) {
				request.getServletContext().log("Le paramètre passé n'est pas integer",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un problème est survenu lors de la suppression du résultat",e);
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

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

import rdg.QCMRdg;
import tools.DBUtils;
import entity.QCM;

/**
 * Servlet implementation class SelectServlet
 */
@WebServlet("/admin/action/qcm")
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
				if(qcm != null) {
					request.setAttribute("qcm", qcm);
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
				request.getServletContext().log("Le qcm demandé n'existe pas",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Le qcm ne respecte pas le format json défini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un problème est survenu lors de la mise à jour du qcm",e);
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
		QCM qcm = null;
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
			
			qcm = QCM.retrieveObject(new JSONObject(json));
			QCMRdg rdg = new QCMRdg(DBUtils.getConnection());
			rdg.persist(qcm);
			Integer id = qcm.getId();
			qcm = rdg.retrieve(id);
			
			writer.write(qcm.stringify());
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
			request.getServletContext().log("Problème d'écriture/lecture des flux lors du doPut QCM",e);
		} finally {
			close(writer, request);
			close(reader, request);
		}
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
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Le qcm ne respecte pas le format json défini",e);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un problème est survenu lors de la suppression du qcm",e);
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

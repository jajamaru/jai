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
					((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Le qcm demand� n'existe pas",e);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Le qcm ne respecte pas le format json d�fini",e);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un probl�me est survenu lors de la mise � jour du qcm",e);
			} 
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
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
			request.getServletContext().log("Probl�me de parsing au niveau du json",e);
		} catch(SQLException e) {
			request.getServletContext().log("Probl�me au niveau de la base de donn�e",e);
		} catch(IOException e) {
			request.getServletContext().log("Probl�me d'�criture/lecture des flux lors du doPut QCM",e);
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Le qcm ne respecte pas le format json d�fini",e);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.getServletContext().log("Un probl�me est survenu lors de la suppression du qcm",e);
			} 
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
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

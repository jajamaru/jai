package servlet.display;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QcmDisplayingServlet
 */
@WebServlet("/admin/display/qcm")
public class QcmDisplayingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String KEY_OBJECT = "qcm";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QcmDisplayingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getAttribute(KEY_OBJECT) != null) {
			
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/display-list/qcm");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

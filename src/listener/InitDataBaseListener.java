package listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import rdg.AnswerRdg;
import rdg.QCMRdg;
import rdg.QCMResultRdg;
import rdg.QuestionRdg;
import tools.DBUtils;

@WebListener
public class InitDataBaseListener implements ServletContextListener {

	public static final String DB_CONNECTION = "dbConnection";
	public static final String RDG_ANSWER = "rdgAnswer";
	public static final String RDG_QUESTION = "rdgQuestion";
	public static final String RDG_QCM = "rdgQcm";
	public static final String RDG_RESULT = "rdgResult";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			Connection connection = DBUtils.getConnection();
			arg0.getServletContext().setAttribute(DB_CONNECTION, connection);
			arg0.getServletContext().setAttribute(RDG_ANSWER, new AnswerRdg(connection));
			arg0.getServletContext().setAttribute(RDG_QUESTION, new QuestionRdg(connection));
			arg0.getServletContext().setAttribute(RDG_QCM, new QCMRdg(connection));
			arg0.getServletContext().setAttribute(RDG_RESULT, new QCMResultRdg(connection));
			arg0.getServletContext().log("Démarrage de l'application !");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arg0.getServletContext().log("Impossible d'initialiser la base de données !");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		arg0.getServletContext().log("Arrêt de l'application !");
	}

}

package listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import rdg.AnswerRdg;
import rdg.QuestionRdg;
import rdg.ResultRdg;
import tools.DBUtils;

@WebListener
public class InitDataBase implements ServletContextListener {

	public static final String DB_CONNECTION = "dbConnection";
	public static final String RDG_ANSWER = "rdgAnswer";
	public static final String RDG_QUESTION = "rdgQuestion";
	public static final String RDG_RESULT = "rdgResult";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		arg0.getServletContext().log("Arrêt de l'application !");
		try {
			DBUtils.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			Connection connection = DBUtils.getConnection();
			AnswerRdg answerRdg = new AnswerRdg(connection);
			arg0.getServletContext().setAttribute(DB_CONNECTION, connection);
			arg0.getServletContext().setAttribute(RDG_ANSWER, answerRdg);
			arg0.getServletContext().setAttribute(RDG_QUESTION, new QuestionRdg(connection, answerRdg));
			arg0.getServletContext().setAttribute(RDG_RESULT, new ResultRdg(connection));
			arg0.getServletContext().log("Démarrage de l'application !");
		} catch (SQLException e) {
			e.printStackTrace();
			arg0.getServletContext().log("Problème lors de l'initialisation de la base de données");
		}
	}

}

package listener;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.json.JSONException;
import org.json.JSONObject;

import rdg.AnswerRdg;
import rdg.QuestionRdg;
import rdg.ResultRdg;
import tools.DBUtils;
import entity.MissingJsonArgumentException;
import entity.Question;

@WebListener
public class InitDataBase implements ServletContextListener {

	public static final String DB_CONNECTION = "dbConnection";
	public static final String RDG_ANSWER = "rdgAnswer";
	public static final String RDG_QUESTION = "rdgQuestion";
	public static final String RDG_RESULT = "rdgResult";
	
	public static final String QUESTION_LIST = "questionList";
	
	private Driver driver;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Connection connection = (Connection)arg0.getServletContext().getAttribute(DB_CONNECTION);
		if(connection != null) {
			try {
				if(!connection.isClosed()) {
					DBUtils.closeConnection();
					connection.close();
					DriverManager.deregisterDriver(driver);
					arg0.getServletContext().log("Arr�t DB ok !");
				}
			} catch (SQLException e) {
				arg0.getServletContext().log(e.getMessage());
			}
		}
		arg0.getServletContext().log("Arr�t de l'application");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			arg0.getServletContext().log("D�marrage de l'application");
			arg0.getServletContext().log("Connection � la base de donn�es ...");
			
			driver = new EmbeddedDriver();
			
			Connection connection = DBUtils.getConnection();
			AnswerRdg answerRdg = new AnswerRdg(connection);
			QuestionRdg questionRdg = new QuestionRdg(connection, answerRdg);
			
			List<Question> questions = new ArrayList<Question>();//questionRdg.retrieveAll();
			String json = "{'question':{'id':1,'desc':'Ceci est une question','answers':[{'answer':{'id':1,'desc':'r�ponse','isTrue':true,'idQuestion':1}},{'answer':{'id':2,'desc':'r�ponse 2','isTrue':false,'idQuestion':1}}]}}";
			questions.add(Question.retrieveObject(new JSONObject(json)));
			
			arg0.getServletContext().setAttribute(DB_CONNECTION, connection);
			arg0.getServletContext().setAttribute(RDG_ANSWER, answerRdg);
			arg0.getServletContext().setAttribute(RDG_QUESTION, questionRdg);
			arg0.getServletContext().setAttribute(RDG_RESULT, new ResultRdg(connection));
			arg0.getServletContext().setAttribute(QUESTION_LIST, questions);
			arg0.getServletContext().log("Connection DB ok !");
		} catch (SQLException e) {
			arg0.getServletContext().log(e.getMessage());
		} catch (MissingJsonArgumentException e) {
			arg0.getServletContext().log(e.getMessage());
		} catch (JSONException e) {
			arg0.getServletContext().log(e.getMessage());
		}
	}

}

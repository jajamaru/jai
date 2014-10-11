package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Entity.Answer;
import Entity.QCM;
import Entity.Question;
import RDG.AnswerRdg;
import RDG.QCMRdg;
import RDG.QuestionRdg;
import Tools.DBUtils;

public class AnswerTest {
	
	private static Connection connection;
	private static AnswerRdg answerRdg;
	private static QuestionRdg questionRdg;
	private static QCMRdg qcmRdg;
	
	private static QCM qcm;
	private static Question question;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		answerRdg = new AnswerRdg(connection);
		questionRdg = new QuestionRdg(connection);
		qcmRdg = new QCMRdg(connection);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		qcm = new QCM();
		qcm.setTitle("Ceci est un qcm");
		qcmRdg.persist(qcm);
		
		question = new Question();
		question.setDesc("Ceci est une question");
		question.setIdQcm(qcm.getId());
		questionRdg.persist(question);
	}
	
	@After
	public void tearDown() throws SQLException {
		DBUtils.resetDatabase(connection);
	}
	
	@AfterClass
	public static void tearDownOnce() throws SQLException {
		connection.close();
	}
	
	@Test
	public void testRetrieve() throws SQLException {
		Answer answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setCpt(0);
		answer.setTrue(true);
		answer.setIdQuestion(question.getId());
		answerRdg.persist(answer);
		Integer id = answer.getId();
		answer = answerRdg.retrieve(id);
		assertNotNull(answer);
		assertEquals(true, answer.isTrue());
		assertEquals("La réponse A !", answer.getDesc());
		assertEquals(0, answer.getCpt());
	}
	
	@Test
	public void testPersist() throws SQLException {
		Answer answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setCpt(0);
		answer.setTrue(true);
		answer.setIdQuestion(question.getId());
		answerRdg.persist(answer);
		assertNotNull(answer.getId());
	}
	
	@Test
	public void testUpdate() throws SQLException {
		Answer answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setCpt(0);
		answer.setTrue(true);
		answer.setIdQuestion(question.getId());
		answerRdg.persist(answer);
		answer.setTrue(false);
		answerRdg.update(answer);
		assertEquals(false, answerRdg.retrieve(answer.getId()).isTrue());
	}
	
	@Test
	public void testDelete() throws SQLException {
		Answer answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setCpt(0);
		answer.setTrue(true);
		answer.setIdQuestion(question.getId());
		answerRdg.persist(answer);
		Integer id = answer.getId();
		answerRdg.delete(answer);
		assertNull(answerRdg.retrieve(id));
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(answerRdg.retrieve(0));
	}

}

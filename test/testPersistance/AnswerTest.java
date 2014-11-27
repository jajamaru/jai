package testPersistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rdg.AnswerRdg;
import rdg.QuestionRdg;
import tools.DBUtils;
import entity.Answer;
import entity.MissingJsonArgumentException;
import entity.Question;

public class AnswerTest {
	
	private static Connection connection;
	private static AnswerRdg answerRdg;
	private static QuestionRdg questionRdg;
	
	private static Question question;
	private static Answer answer;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		answerRdg = new AnswerRdg(connection);
		questionRdg = new QuestionRdg(connection, answerRdg);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		
		question = new Question();
		question.setDesc("Ceci est une question");
		questionRdg.persist(question);
		
		answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setTrue(true);
		answer.setIdQuestion(question.getId());
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
		answerRdg.persist(answer);
		Integer id = answer.getId();
		answer = answerRdg.retrieve(id);
		assertNotNull(answer);
		assertEquals(true, answer.isCorrectAnswer());
		assertEquals("La réponse A !", answer.getDesc());
	}
	
	@Test
	public void testPersist() throws SQLException {
		answerRdg.persist(answer);
		assertNotNull(answer.getId());
	}
	
	@Test
	public void testUpdate() throws SQLException {
		answerRdg.persist(answer);
		answer.setTrue(false);
		answerRdg.update(answer);
		assertEquals(false, answerRdg.retrieve(answer.getId()).isCorrectAnswer());
	}
	
	@Test
	public void testDelete() throws SQLException {
		answerRdg.persist(answer);
		Integer id = answer.getId();
		answerRdg.delete(answer.getId());
		assertNull(answerRdg.retrieve(id));
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(answerRdg.retrieve(0));
	}
	
	@Test
	public void testJson() throws JSONException, MissingJsonArgumentException {
		JSONObject json = answer.getJson();
		assertTrue(answer.equals(Answer.retrieveObject(json)));
	}
	
	@Test
	public void testJson2() throws JSONException, MissingJsonArgumentException {
		Answer.retrieveObject(new JSONObject("{'answer':{'desc':'une réponse','isTrue':false}}"));
		Answer.retrieveObject(new JSONObject("{'answer':{'id':1,'desc':'une réponse','isTrue':false}}"));
		Answer.retrieveObject(new JSONObject("{'answer':{'idQuestion':1,'desc':'une réponse','isTrue':false}}"));
		Answer.retrieveObject(new JSONObject("{'answer':{'id':1,'idQuestion':1,'desc':'une réponse','isTrue':false}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter1() throws JSONException, MissingJsonArgumentException {
		JSONObject json = new JSONObject("{}");
		Answer.retrieveObject(json);
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter2() throws JSONException, MissingJsonArgumentException {
		Answer.retrieveObject(new JSONObject("{'answer':{}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter3() throws JSONException, MissingJsonArgumentException {
		Answer.retrieveObject(new JSONObject("{'answer':{'desc':'une réponse'}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter4() throws JSONException, MissingJsonArgumentException {
		Answer.retrieveObject(new JSONObject("{'answer':{'isTrue':true}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter5() throws JSONException, MissingJsonArgumentException {
		Answer.retrieveObject(new JSONObject("{'question':{'desc':'une réponse','isTrue':false}}"));
	}

}

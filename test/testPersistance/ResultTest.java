package testPersistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
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
import rdg.ResultRdg;
import tools.DBUtils;
import entity.MissingJsonArgumentException;
import entity.Question;
import entity.Result;

public class ResultTest {
	
	private static Connection connection;
	private static QuestionRdg questionRdg;
	private static ResultRdg resultRdg;
	
	private Question question;
	private Result result;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		questionRdg = new QuestionRdg(connection, new AnswerRdg(connection));
		resultRdg = new ResultRdg(connection);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		question = new Question();
		question.setDesc("Ceci est une question");
		questionRdg.persist(question);
		
		result = new Result();
		result.setNbParticipants(20);
		result.setSuccessRate(80.0);
		result.setQuestionId(question.getId());
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
		resultRdg.persist(result);
		Integer id = result.getId();
		result = resultRdg.retrieve(id);
		assertNotNull(result);
		assertEquals(Double.doubleToLongBits(80.0), Double.doubleToLongBits(result.getSuccessRate()));
		assertEquals(20, result.getNbParticipants());
	}
	
	@Test
	public void testPersist() throws SQLException {
		resultRdg.persist(result);
		assertNotNull(result.getId());
	}
	
	@Test
	public void testDelete() throws SQLException {
		resultRdg.persist(result);
		Integer id = result.getId();
		resultRdg.delete(result.getId());
		assertNull(resultRdg.retrieve(id));
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(resultRdg.retrieve(0));
	}
	
	@Test
	public void testJson() throws JSONException, MissingJsonArgumentException {
		final long time = System.currentTimeMillis();
		Date date = new Date(time);
		assertEquals(date.getTime(), time);
		
		assertTrue(result.equals(Result.retrieveObject(result.getJson())));
	}
	
	@Test
	public void testJson2() throws JSONException, MissingJsonArgumentException {
		Result.retrieveObject(new JSONObject("{'result':{'successRate':80,'nbParticipants':23}}"));
		Result.retrieveObject(new JSONObject("{'result':{'questionId':1,'successRate':80,'nbParticipants':23}}"));
		Result.retrieveObject(new JSONObject("{'result':{'questionId':1,'successRate':80,'nbParticipants':23,'date':1416677298605}}"));
		Result.retrieveObject(new JSONObject("{'result':{'id':1,'questionId':1,'successRate':80,'nbParticipants':23,'date':1416677298605}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter1() throws JSONException, MissingJsonArgumentException {
		Result.retrieveObject(new JSONObject("{}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter2() throws JSONException, MissingJsonArgumentException {
		Result.retrieveObject(new JSONObject("{'result':{}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter3() throws JSONException, MissingJsonArgumentException {
		Result.retrieveObject(new JSONObject("{'result':{'succesRate':80}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter4() throws JSONException, MissingJsonArgumentException {
		Result.retrieveObject(new JSONObject("{'result':{'nbParticipants':23}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter5() throws JSONException, MissingJsonArgumentException {
		Result.retrieveObject(new JSONObject("{'answer':{'questionId':1,'succesRate':80,'nbParticipants':23}}"));
	}

}

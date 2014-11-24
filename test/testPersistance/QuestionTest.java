package testPersistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

public class QuestionTest {
	
	private static Connection connection;
	private static QuestionRdg questionRdg;
	private static AnswerRdg answerRdg;
	
	private static List<Answer> answers = new ArrayList<Answer>();
	private Question question;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		answerRdg = new AnswerRdg(connection);
		questionRdg = new QuestionRdg(connection, answerRdg);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		Answer answer1, answer2;
		answer1 = new Answer();
		answer1.setDesc("A");
		answer1.setTrue(false);
		answer2 = new Answer();
		answer2.setDesc("B");
		answer2.setTrue(true);
		answers.add(answer1);
		answers.add(answer2);
		
		question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
	}
	
	@After
	public void tearDown() throws SQLException {
		DBUtils.resetDatabase(connection);
		answers.clear();
	}
	
	@AfterClass
	public static void tearDownOnce() throws SQLException {
		connection.close();
	}
	
	@Test
	public void testRetrieve() throws SQLException {
		questionRdg.persist(question);
		assertEquals(2, question.getAnswers().size());
		Integer id = question.getId();
		question = questionRdg.retrieve(id);
		assertNotNull(question);
		assertEquals(2, question.getAnswers().size());
	}
	
	@Test
	public void testPersist() throws SQLException {
		questionRdg.persist(question);
		assertNotNull(question.getId());
		Iterator<Answer> it = question.getAnswers().iterator();
		while(it.hasNext()) {
			Answer a = it.next();
			assertNotNull(a.getId());
		}
	}
	
	@Test
	public void testDelete() throws SQLException {
		Integer id = null;
		Collection<Answer> answersQuestion = new ArrayList<Answer>();
		questionRdg.persist(question);
		id = question.getId();
		answersQuestion = question.getAnswers();
		questionRdg.delete(question.getId());
		assertNull(questionRdg.retrieve(id));
		//On vérifie que les réponses sont aussi supprimées
		Iterator<Answer> it = answersQuestion.iterator();
		while(it.hasNext()) {
			Answer a = it.next();
			assertNull(answerRdg.retrieve(a.getId()));
		}
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(questionRdg.retrieve(0));
	}
	
	@Test
	public void testUpdate() throws SQLException {
		questionRdg.persist(question);
		question.setDesc("T'es qui ?");
		questionRdg.update(question);
		assertEquals("T'es qui ?", questionRdg.retrieve(question.getId()).getDesc());
	}
	
	@Test
	public void testAddAnswer() {
		Question question = new Question();
		Answer answer = new Answer();
		Answer answer2 = new Answer();
		assertEquals(0, question.getAnswers().size());
		question.addAnswer(answer);
		assertEquals(1, question.getAnswers().size());
		question.addAnswer(answer);
		assertEquals(1, question.getAnswers().size());
		question.addAnswer(answer2);
		assertEquals(2, question.getAnswers().size());
		question.addAnswer(answer);
		assertEquals(2, question.getAnswers().size());
	}
	
	@Test
	public void testRemoveAnswer() {
		Question question = new Question();
		Answer answer = new Answer();
		assertEquals(0, question.getAnswers().size());
		question.addAnswer(answer);
		assertEquals(1, question.getAnswers().size());
		question.removeAnswer(answer);
		assertEquals(0, question.getAnswers().size());
	}
	
	@Test
	public void testJson() throws JSONException, MissingJsonArgumentException {		
		JSONObject json = question.getJson();
		assertTrue(question.equals(Question.retrieveObject(json)));
	}
	
	@Test
	public void testJson2() throws JSONException, MissingJsonArgumentException {
		Question.retrieveObject(new JSONObject("{'question':{'desc':'Ceci est une question','answers':[{'answer':{'desc':'réponse','isTrue':true}}]}}"));
		Question.retrieveObject(new JSONObject("{'question':{'id':1,'desc':'Ceci est une question','answers':[{'answer':{'desc':'réponse','isTrue':true}}]}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter1() throws JSONException, MissingJsonArgumentException {
		Question.retrieveObject(new JSONObject("{}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter2() throws JSONException, MissingJsonArgumentException {
		Question.retrieveObject(new JSONObject("{'question':{}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter3() throws JSONException, MissingJsonArgumentException {
		Question.retrieveObject(new JSONObject("{'question':{'desc':'Ceci est une question'}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter4() throws JSONException, MissingJsonArgumentException {
		Question.retrieveObject(new JSONObject("{'question':{'answers':[{'answer':{'desc':'réponse','isTrue':true}}]}}"));
	}
	
	@Test(expected=MissingJsonArgumentException.class)
	public void testJsonWithMissingParameter5() throws JSONException, MissingJsonArgumentException {
		Question.retrieveObject(new JSONObject("{'answer':{'desc':'Ceci est une question','answers':[{'answer':{'desc':'réponse','isTrue':true}}]}}"));
	}

}

package testPersistance;

import static org.junit.Assert.*;

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

import rdg.QCMRdg;
import rdg.QuestionRdg;
import tools.DBUtils;
import entity.Answer;
import entity.QCM;
import entity.Question;

public class QCMTest {
	
	private static Connection connection;
	private static QCMRdg QCMRdg;
	private static QuestionRdg questionRdg;
	
	private static List<Question> questions;
	
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		QCMRdg = new QCMRdg(connection);
		questionRdg = new QuestionRdg(connection);
		questions = new ArrayList<Question>();
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = new Answer();
		answer.setCpt(0);
		answer.setDesc("A");
		answer.setTrue(true);
		answers.add(answer);
		answer = new Answer();
		answer.setCpt(0);
		answer.setDesc("B");
		answer.setTrue(false);
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
		questions.add(question);
	}
	
	@After
	public void tearDown() throws SQLException {
		DBUtils.resetDatabase(connection);
		questions.clear();
	}
	@AfterClass
	public static void tearDownOnce() throws SQLException {
		connection.close();
	}
	
	@Test
	public void testRetrieve() throws SQLException {
		QCM qcm = new QCM();
		qcm.setQuestions(questions);
		qcm.setTitle("Mon qcm");
		QCMRdg.persist(qcm);
		Integer id = qcm.getId();
		qcm = QCMRdg.retrieve(id);
		assertNotNull(qcm);
		assertEquals(1, qcm.getQuestions().size());
	}
	
	@Test
	public void testPersist() throws SQLException {
		QCM qcm = new QCM();
		qcm.setQuestions(questions);
		qcm.setTitle("Mon qcm");
		QCMRdg.persist(qcm);
		assertNotNull(qcm.getId());
		Iterator<Question> it = qcm.getQuestions().iterator();
		while(it.hasNext()) {
			Question q = it.next();
			assertNotNull(q.getId());
		}
	}
	
	@Test
	public void testUpdate() throws SQLException {
		QCM qcm = new QCM();
		qcm.setQuestions(questions);
		qcm.setTitle("Mon qcm");
		QCMRdg.persist(qcm);
		qcm.setTitle("qcm");
		QCMRdg.update(qcm);
		assertEquals("qcm", QCMRdg.retrieve(qcm.getId()).getTitle());
		assertEquals(1, qcm.getQuestions().size());
	}
	
	@Test
	public void testDelete() throws SQLException {
		QCM qcm = new QCM();
		qcm.setQuestions(questions);
		qcm.setTitle("Mon qcm");
		QCMRdg.persist(qcm);
		Integer id = qcm.getId();
		Collection<Question> questions = qcm.getQuestions();
		QCMRdg.delete(qcm);
		assertNull(QCMRdg.retrieve(id));
		Iterator<Question> it = questions.iterator();
		while(it.hasNext()) {
			Question q = it.next();
			assertNull(questionRdg.retrieve(q.getId()));
		}
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(QCMRdg.retrieve(0));
	}
	
	@Test
	public void testAddQuestion() {
		QCM qcm = new QCM();
		qcm.setTitle("Ceci est un qcm");
		Question question = new Question();
		assertEquals(0, qcm.getQuestions().size());
		qcm.addQuestion(question);
		assertEquals(1, qcm.getQuestions().size());
	}
	
	@Test
	public void testRemoveQuestion() {
		QCM qcm = new QCM();
		qcm.setTitle("Ceci est un qcm");
		Question question = new Question();
		assertEquals(0, qcm.getQuestions().size());
		qcm.addQuestion(question);
		assertEquals(1, qcm.getQuestions().size());
		qcm.removeQuestion(question);
		assertEquals(0, qcm.getQuestions().size());
	}
	
	@Test
	public void testUpdateQuestion() {
		QCM qcm = new QCM();
		qcm.setTitle("Ceci est un qcm");
		Question question = new Question();
		assertEquals(0, qcm.getQuestions().size());
		qcm.addQuestion(question);
		assertEquals(1, qcm.getQuestions().size());
		question.setDesc("Ceci est une question");
		qcm.addQuestion(question);
		assertEquals(1, qcm.getQuestions().size());
	}
	
	@Test
	public void testJson() throws JSONException {
		QCM qcm = new QCM();
		qcm.setQuestions(questions);
		qcm.setTitle("Mon qcm");
		
		JSONObject json = qcm.getJson();
		System.out.println(json.toString());
		assertTrue(qcm.equals(QCM.retrieveObject(json)));
	}

}

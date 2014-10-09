package Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Entity.Answer;
import Entity.Question;
import RDG.AnswerRdg;
import RDG.QuestionRdg;
import Tools.DBUtils;

public class QuestionTest {
	
	private static Connection connection;
	private static QuestionRdg questionRdg;
	private static AnswerRdg answerRdg;
	
	private static List<Answer> answers = new ArrayList<Answer>();
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		questionRdg = new QuestionRdg(connection);
		answerRdg = new AnswerRdg(connection);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		Answer answer1, answer2;
		answer1 = new Answer();
		answer1.setCpt(0);
		answer1.setDesc("A");
		answer1.setTrue(false);
		answer2 = new Answer();
		answer2.setCpt(0);
		answer2.setDesc("B");
		answer2.setTrue(true);
		answers.add(answer1);
		answers.add(answer2);
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
	public void testPersist() throws SQLException {
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
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
		List<Answer> answersQuestion = new ArrayList<Answer>();
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
		questionRdg.persist(question);
		id = question.getId();
		answersQuestion = question.getAnswers();
		questionRdg.delete(question);
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
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
		questionRdg.persist(question);
		question.setDesc("T'es qui ?");
		questionRdg.update(question);
		assertEquals("T'es qui ?", questionRdg.retrieve(question.getId()).getDesc());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAtLeastOneAnswer() throws SQLException {
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		questionRdg.persist(question);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAtLeastOneTrueAnswer() throws SQLException {
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = new Answer();
		answer.setCpt(0);
		answer.setDesc("A");
		answer.setTrue(false);
		
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
		questionRdg.persist(question);
	}
	
	@Test
	public void testIncreaseAnswer() throws SQLException {
		Question question = new Question();
		question.setDesc("Qui es-tu ?");
		question.setAnswers(answers);
		questionRdg.persist(question);
		
		Answer answer = question.getAnswers().get(0);
		questionRdg.increaseAnswer(answer);
		
		assertEquals(1, answerRdg.retrieve(answer.getId()).getCpt());
	}

}

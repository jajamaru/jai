package Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Entity.Answer;
import RDG.AnswerRdg;
import Tools.DBUtils;

public class AnswerTest {
	
	private static Connection connection;
	private static AnswerRdg answerRdg;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		answerRdg = new AnswerRdg(connection);
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
	public void testPersist() throws SQLException {
		Answer answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setCpt(0);
		answer.setTrue(true);
		answerRdg.persist(answer);
		assertNotNull(answer.getId());
	}
	
	@Test
	public void testUpdate() throws SQLException {
		Answer answer = new Answer();
		answer.setDesc("La réponse A !");
		answer.setCpt(0);
		answer.setTrue(true);
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

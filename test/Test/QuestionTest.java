package Test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import RDG.QuestionRdg;
import Tools.DBUtils;

public class QuestionTest {
	
	private static Connection connection;
	private static QuestionRdg questionRdg;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		questionRdg = new QuestionRdg(connection);
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
		
	}
	
	@Test
	public void testDelete() throws SQLException {
		
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		
	}
	
	@Test
	public void testUpdate() throws SQLException {
		
	}
	
	@Test
	public void testAtLeastOneAnswer() throws SQLException {
		
	}
	
	@Test
	public void testAtLeastOneTrueAnswer() throws SQLException {
		
	}

}

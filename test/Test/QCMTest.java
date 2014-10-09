package Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Entity.QCM;
import RDG.QCMRdg;
import RDG.QuestionRdg;
import Tools.DBUtils;

public class QCMTest {
	
	private static Connection connection;
	private static QCMRdg QCMRdg;
	private static QuestionRdg questionRdg;
	
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		QCMRdg = new QCMRdg(connection);
		questionRdg = new QuestionRdg(connection);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
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
		
	}
	
	@Test
	public void testPersist() throws SQLException {
		
	}
	
	@Test
	public void testUpdate() throws SQLException {
		
	}
	
	@Test
	public void testDelete() throws SQLException {
		
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(QCMRdg.retrieve(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAtLeastOneQuestion() throws SQLException {
		
	}

}

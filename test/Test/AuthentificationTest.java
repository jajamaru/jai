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

import DBUtils.DBUtils;
import Entity.Authentification;
import RDG.AuthentificationRDG;

public class AuthentificationTest {
	
	private static Connection connection;
	private static AuthentificationRDG authRdg;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		authRdg = new AuthentificationRDG(connection);
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
	public void testPersist() throws SQLException {
		Authentification auth = new Authentification("toto","****");
		authRdg.persist(auth);
		Authentification auth2 = authRdg.retrieve(auth.getLogin());
		assertNotNull(auth2);
		assertEquals("****", auth2.getPwd());
	}
	
	@Test
	public void testDoesNotExists() throws SQLException {
		assertNull(authRdg.retrieve("tata"));
	}
	
	@Test
	public void testExists() throws SQLException {
		Authentification auth = new Authentification("toto", "****");
		authRdg.persist(auth);
		assertNotNull(authRdg.retrieve(auth.getLogin()));
	}
	
	@Test
	public void testDelete() throws SQLException {
		Authentification auth = new Authentification("toto","****");
		authRdg.persist(auth);
		authRdg.delete(auth);
		assertNull(authRdg.retrieve(auth.getLogin()));
	}
	
	
	@Test
	public void testUpdate() throws SQLException {
		Authentification auth = new Authentification("toto", "****");
		authRdg.persist(auth);
		auth.setPwd("----");
		authRdg.update(auth);
		assertEquals("----", authRdg.retrieve(auth.getLogin()).getPwd());
	}

}

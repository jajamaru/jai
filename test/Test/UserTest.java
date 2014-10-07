package Test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import RDG.AuthentificationRDG;
import RDG.UserRDG;
import Tools.DBUtils;

public class UserTest {
	
	private static Connection connection;
	private static UserRDG userRdg;
	private static AuthentificationRDG authRdg;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		authRdg = new AuthentificationRDG(connection);
		userRdg = new UserRDG(connection, authRdg);
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

}

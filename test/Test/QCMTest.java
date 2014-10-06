package Test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import DBUtils.DBUtils;
import RDG.QCMRdg;

public class QCMTest {
	
	private static Connection connection;
	private static QCMRdg QCMRdg;
	
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		QCMRdg = new QCMRdg(connection);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		this.QCMRdg = new QCMRdg(connection);
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

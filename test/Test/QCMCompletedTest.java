package Test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import DBUtils.DBUtils;
import RDG.QCMRdg;

public class QCMCompletedTest {
	
	private static Connection connection;
	private static QCMRdg qcmRdg;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		this.qcmRdg = new QCMRdg(connection);
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

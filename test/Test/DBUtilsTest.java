package Test;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import Tools.DBUtils;

public class DBUtilsTest {
	
	@Test
	public void testConnection() throws SQLException {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			connection.close();
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateDb() throws SQLException {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			DBUtils.destroyDatabase(connection, false);
			DBUtils.createDatabase(connection);
			connection.close();
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDestroyDb() throws SQLException {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection();
			DBUtils.createDatabase(connection, false);
			DBUtils.destroyDatabase(connection);
			connection.close();
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

}

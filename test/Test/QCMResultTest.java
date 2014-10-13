package test;

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

import entity.QCM;
import entity.QCMResult;
import rdg.QCMRdg;
import rdg.QCMResultRdg;
import tools.DBUtils;

public class QCMResultTest {
	
	private static Connection connection;
	private static QCMRdg qcmRdg;
	private static QCMResultRdg qcmResultRdg;
	
	private QCM qcm;
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
		qcmRdg = new QCMRdg(connection);
		qcmResultRdg = new QCMResultRdg(connection);
	}
	
	@Before
	public void setUp() throws SQLException {
		DBUtils.resetDatabase(connection);
		qcm = new QCM();
		qcm.setTitle("Ceci est un qcm");
		qcmRdg.persist(qcm);
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
		QCMResult result = new QCMResult();
		result.setNbParticipants(20);
		result.setDuration(60*5);
		result.setSuccessRate(80.0);
		result.setIdQcm(qcm.getId());
		qcmResultRdg.persist(result);
		Integer id = result.getId();
		result = qcmResultRdg.retrieve(id);
		assertNotNull(result);
		assertEquals(60*5, result.getDuration());
		assertEquals(Double.doubleToLongBits(80.0), Double.doubleToLongBits(result.getSuccessRate()));
		assertEquals(20, result.getNbParticipants());
	}
	
	@Test
	public void testPersist() throws SQLException {
		QCMResult result = new QCMResult();
		result.setNbParticipants(20);
		result.setDuration(60*5);
		result.setSuccessRate(80.0f);
		result.setIdQcm(qcm.getId());
		qcmResultRdg.persist(result);
		assertNotNull(result.getId());
	}
	
	@Test
	public void testDelete() throws SQLException {
		QCMResult result = new QCMResult();
		result.setNbParticipants(20);
		result.setDuration(60*5);
		result.setSuccessRate(80.0f);
		result.setIdQcm(qcm.getId());
		qcmResultRdg.persist(result);
		Integer id = result.getId();
		qcmResultRdg.delete(result);
		assertNull(qcmResultRdg.retrieve(id));
	}
	
	@Test
	public void testDoesNotExist() throws SQLException {
		assertNull(qcmResultRdg.retrieve(0));
	}

}

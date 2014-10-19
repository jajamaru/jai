package testRequest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.DBUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import entity.Answer;
import entity.QCM;
import entity.Question;

public class QcmServletTest {
	
	private WebClient webClient;
	private static Connection connection;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/qcm";
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException {
		webClient = new WebClient();
		DBUtils.resetDatabase(connection);
	}
	
	@After
	public void tearDown() throws SQLException {
		webClient.closeAllWindows();
		DBUtils.resetDatabase(connection);
	}
	
	@AfterClass
	public static void tearDownOnce() throws SQLException {
		connection.close();
	}
	
	@Test
	public void testPersistQcm() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException {
		final QCM qcm = new QCM();
		qcm.setTitle("qcm");
		final Question question = new Question();
		question.setDesc("Qui es-tu ?");
		final Answer answer = new Answer();
		answer.setCpt(0);
		answer.setDesc("A");
		answer.setTrue(true);
		
		question.addAnswer(answer);
		qcm.addQuestion(question);
		
		final URL url = new URL(URL);
		final WebRequest request = new WebRequest(url, HttpMethod.PUT);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "{\"qcm\":{\"title\":\"qcm\",\"questions\":[{\"question\":{\"desc\":\"Qui es-tu ?\",\"answers\":[{\"answer\":{\"desc\":\"A\",\"cpt\":0,\"isTrue\":true}}]}}]}}"));
		
		TextPage page = webClient.getPage(request);
		assertEquals(200, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveQcm() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException {
		final QCM qcm = new QCM();
		qcm.setTitle("qcm");
		final Question question = new Question();
		question.setDesc("Qui es-tu ?");
		final Answer answer = new Answer();
		answer.setCpt(0);
		answer.setDesc("A");
		answer.setTrue(true);
		
		question.addAnswer(answer);
		qcm.addQuestion(question);
		
		System.out.println(qcm.stringify());
		
		/*final URL url = new URL(URL);
		WebRequest request = new WebRequest(url, HttpMethod.PUT);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", qcm.stringify()));
		webClient.getPage(request);
		
		/*request = new WebRequest(url, HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "1"));
		
		HtmlPage page = webClient.getPage(request);
		System.out.println(page.getWebResponse().getContentAsString());
		assertEquals(200, page.getWebResponse().getStatusCode());*/
	}
	
	@Test
	public void testDeleteQcm() throws SQLException {
		
	}
	
	@Test
	public void testUpdateQcm() throws SQLException {
		
	}

}

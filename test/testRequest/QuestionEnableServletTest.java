package testRequest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import tools.DBUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import entity.QCM;
import entity.Question;

@Ignore
public class QuestionEnableServletTest {
	
	private static WebClient webClient;
	private static Connection connection;
	
	private Question question;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/question/enable";
	private final static String URL_QCM = "http://localhost:8081/romain_huret_jai/admin/action/qcm";
	private final static String JSON_QCM = "{\"qcm\":{\"title\":\"qcm\",\"questions\":[{\"question\":{\"desc\":\"Qui es-tu ?\",\"answers\":[{\"answer\":{\"desc\":\"A\",\"cpt\":0,\"isTrue\":true}}]}}]}}";
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		webClient = new WebClient();
		DBUtils.resetDatabase(connection);
		
		final WebRequest request = new WebRequest(new URL(URL_QCM), HttpMethod.PUT);
		request.setRequestBody(JSON_QCM);
		
		TextPage page = webClient.getPage(request);
		QCM qcm = QCM.retrieveObject(new JSONObject(page.getContent()));
		
		question = qcm.getQuestions().get(0);
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
	public void testEnableQuestion() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", question.getId() + ""));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testEnableQuestionAgain() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", question.getId() + ""));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testEnableQuestionDoesNotExist() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", Integer.MAX_VALUE + ""));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testWrongArgument() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "edede"));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", ""));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "-1"));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "{\"question\":{\"id\":1}}"));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testWrongParameterKey() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON_QCM));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("", JSON_QCM));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("-1", JSON_QCM));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("8528282", JSON_QCM));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}

}

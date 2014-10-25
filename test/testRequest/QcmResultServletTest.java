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
import entity.QCMResult;

@Ignore
public class QcmResultServletTest {
	
	private static WebClient webClient;
	private static Connection connection;
	
	private QCM qcm;
	private QCMResult result;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/result";
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
		
		WebRequest request = new WebRequest(new URL(URL_QCM), HttpMethod.PUT);
		request.setRequestBody(JSON_QCM);
		
		TextPage page = webClient.getPage(request);
		qcm = QCM.retrieveObject(new JSONObject(page.getContent()));
		
		final String json_result = "{\"result\":{\"nbParticipants\":20,\"successRate\":\"85\",\"duration\":120}}";
		result = QCMResult.retrieveObject(new JSONObject(json_result));
		result.setIdQcm(qcm.getId());
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
	public void testPersistQcmResult() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException {	
		QCMResult resultPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(result.stringify());
		
		TextPage page = webClient.getPage(request);
		resultPut = QCMResult.retrieveObject(new JSONObject(page.getContent()));
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", resultPut.getId() + ""));
		webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	
	@Test
	public void testPersistJsonMalformedQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody("dedefefef");
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistJsonMalformedQcmResult2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody("{}");
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistCorrectJsonAnotherObject() throws FailingHttpStatusCodeException, IOException, JSONException  {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(JSON_QCM);
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		TextPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveMissingArgumentQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveParameterMalformedQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "yguyguguyg"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteQcmResult() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		QCMResult resultPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(result.stringify());
		
		TextPage page = webClient.getPage(request);
		resultPut = QCMResult.retrieveObject(new JSONObject(page.getContent()));
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("result", resultPut.stringify()));
		page = webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteDoesNotExistQcmResult() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("result", result.stringify()));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("result", "rrefzefzef"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedQcmResult2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("result", "{\"result\":{\"id\":\"fef\"}}"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteMissingArgumentQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRequestWithWrongArgumentKey() throws FailingHttpStatusCodeException, IOException {
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		//Requête get
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON_QCM));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		//Requête delete
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON_QCM));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
	}

}

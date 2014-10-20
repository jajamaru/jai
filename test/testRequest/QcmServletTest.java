package testRequest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

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

public class QcmServletTest {
	
	private WebClient webClient;
	private static Connection connection;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/qcm";
	private final static String JSON = "{\"qcm\":{\"title\":\"qcm\",\"questions\":[{\"question\":{\"desc\":\"Qui es-tu ?\",\"answers\":[{\"answer\":{\"desc\":\"A\",\"cpt\":0,\"isTrue\":true}}]}}]}}";
	
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
		final WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON));
		
		TextPage page = webClient.getPage(request);
		assertEquals(200, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistJsonMalformedQcm() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "dededede"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistJsonMalformedQcm2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "{}"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveQcm() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException {
		final URL url = new URL(URL);
		WebRequest request = new WebRequest(url, HttpMethod.PUT);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON));
		webClient.getPage(request);
		
		request = new WebRequest(url, HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "1"));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveMissingArgumentQcm() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveParameterMalformedQcm() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "yguyguguyg"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteQcm() throws SQLException {
		
	}
	
	@Test
	public void testDeleteDoesNotExistQcm() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedQcm() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "rrefzefzef"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedQcm2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "{\"qcm\":{\"id\":\"fef\"}}"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteMissingArgumentQcm() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateQcm() throws SQLException {
		
	}
	
	@Test
	public void testUpdateDoesNotExist() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateJsonMalformedQcm() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "rrefzefzef"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateJsonMalformedQcm2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", "{\"qcm\":{\"id\":\"fef\"}}"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateMissingArgumentQcm() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}

}

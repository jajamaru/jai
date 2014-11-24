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
import org.junit.Test;

import tools.DBUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import entity.Answer;
import entity.MissingJsonArgumentException;
import entity.Question;
import entity.Result;

public class ResultServletTest {
	
	private static WebClient webClient;
	private static Connection connection;
	
	private Result result;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/result";
	private final static String WRONG_JSON = "{'answer':{'desc':'B','isTrue':false}}";
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException, MissingJsonArgumentException {
		webClient = new WebClient();
		DBUtils.resetDatabase(connection);
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		final String URL_PERSIST = "http://localhost:8081/romain_huret_jai/admin/action/question";
		
		Answer answer = new Answer();
		answer.setDesc("Réponse");
		answer.setTrue(true);

		Question question = new Question();
		question.setDesc("Ceci est une question !");
		question.setAnswers(new ArrayList<Answer>());
		question.getAnswers().add(answer);
		
		final WebRequest request = new WebRequest(new URL(URL_PERSIST), HttpMethod.PUT);
		request.setRequestBody(question.stringify());
		
		Page page = webClient.getPage(request);
		question = Question.retrieveObject(new JSONObject(page.getWebResponse().getContentAsString()));
		
		final String json_result = "{'result':{'nbParticipants':20,'successRate':85}}";
		result = Result.retrieveObject(new JSONObject(json_result));
		result.setQuestionId(question.getId());
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
	public void testPersistResult() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException, MissingJsonArgumentException {	
		Result resultPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(result.stringify());
		
		Page page = webClient.getPage(request);
		resultPut = Result.retrieveObject(new JSONObject(page.getWebResponse().getContentAsString()));
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", resultPut.getId() + ""));
		webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	
	@Test
	public void testPersistJsonMalformedResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody("dedefefef");
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestBody("{}");
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistCorrectJsonAnotherObject() throws FailingHttpStatusCodeException, IOException, JSONException  {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(WRONG_JSON);
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveMissingArgumentQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveParameterMalformedResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "yguyguguyg"));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteResult() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException, MissingJsonArgumentException {
		Result resultPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(result.stringify());
		
		Page page = webClient.getPage(request);
		resultPut = Result.retrieveObject(new JSONObject(page.getWebResponse().getContentAsString()));
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "" + resultPut.getId()));
		page = webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteDoesNotExistResult() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "" + Integer.MAX_VALUE));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "rrefzefzef"));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "{'result':{'id':'fef'}}"));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", ""));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteMissingArgumentQcmResult() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRequestWithWrongArgumentKey() throws FailingHttpStatusCodeException, IOException {
		//Requête get
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", WRONG_JSON));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		//Requête delete
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", WRONG_JSON));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
	}

}

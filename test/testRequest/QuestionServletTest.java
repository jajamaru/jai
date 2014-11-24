package testRequest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import entity.Answer;
import entity.MissingJsonArgumentException;
import entity.Question;

public class QuestionServletTest {
	
	private static WebClient webClient;
	private static Connection connection;
	
	private Question question;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/question";
	private final static String WRONG_JSON = "{'answer':{'desc':'B','isTrue':false}}";
	
	@BeforeClass
	public static void setUpOnce() throws SQLException {
		connection = DBUtils.getConnection();
	}
	
	@Before
	public void setUp() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		DBUtils.resetDatabase(connection);
		
		Answer answer = new Answer();
		answer.setDesc("Réponse");
		answer.setTrue(true);
		
		question = new Question();
		question.setDesc("Ceci est une question !");
		
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(answer);
		
		question.setAnswers(answers);
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
	public void testPersistQuestion() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException {	
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(question.stringify());
		
		Page page = webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistJsonMalformedQuestion() throws FailingHttpStatusCodeException, IOException {
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
		request.setRequestBody("{'answer':{'desc':'B','isTrue':false}}");
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveQuestion() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException, MissingJsonArgumentException {
		Question questionPut = null;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(question.stringify());
		
		Page page = webClient.getPage(request);
		questionPut = Question.retrieveObject(new JSONObject(page.getWebResponse().getContentAsString()));
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", ""+questionPut.getId()));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveMissingArgumentQuestion() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveParameterMalformedQuestion() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "yguyguguyg"));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteQuestion() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException, MissingJsonArgumentException {
		Question questionPut = null;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(question.stringify());
		
		Page page = webClient.getPage(request);
		questionPut = Question.retrieveObject(new JSONObject(page.getWebResponse().getContentAsString()));
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "" + questionPut.getId()));
		page = webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteDoesNotExistQuestion() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "" + Integer.MAX_VALUE));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedQuestion() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("question", "uiheiduhezih"));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "{}"));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", ""));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateQuestion() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException, MissingJsonArgumentException {
		Question questionPut = null;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(question.stringify());
		
		Page page = webClient.getPage(request);
		questionPut = Question.retrieveObject(new JSONObject(page.getWebResponse().getContentAsString()));
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		questionPut.setDesc("Bidule");
		request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("question", questionPut.stringify()));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateJsonMalformedQuestion() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("question", "rrefzefzef"));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("question", "{'question':{'id':'fef'}}"));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
		
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("question", "{}"));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateMissingArgumentQuestion() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRequestWithWrongArgumentKey() throws FailingHttpStatusCodeException, IOException {
		//Requête post
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", WRONG_JSON));
		
		Page page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		//Requête get
		request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", WRONG_JSON));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
	}

}

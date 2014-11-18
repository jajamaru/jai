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

import entity.Answer;
import entity.QCM;
import entity.Question;

@Ignore
public class AnswerServletTest {
	
	private static WebClient webClient;
	private static Connection connection;
	
	private Answer answer;
	private Question question;
	private QCM qcm;
	
	private final static String URL = "http://localhost:8081/romain_huret_jai/admin/action/answer";
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
		question = qcm.getQuestions().get(0);
		
		final String json_answer = "{'answer':{'desc':'B','cpt':0,'isTrue':false}}";
		answer = Answer.retrieveObject(new JSONObject(json_answer));
		answer.setIdQuestion(question.getId());
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
	public void testPersistAnswer() throws SQLException, JSONException, FailingHttpStatusCodeException, IOException {	
		Answer answerPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(answer.stringify());
		
		TextPage page = webClient.getPage(request);
		answerPut = Answer.retrieveObject(new JSONObject(page.getContent()));
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", answerPut.getId() + ""));
		webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistJsonMalformedAnswer() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody("dedefefef");
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testPersistJsonMalformedAnswer2() throws FailingHttpStatusCodeException, IOException {
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
	public void testRetrieveMissingArgumentQuestion() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRetrieveParameterMalformedAnswer() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("id", "yguyguguyg"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_NOT_FOUND, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteAnswer() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		Answer answerPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(answer.stringify());
		
		TextPage page = webClient.getPage(request);
		answerPut = Answer.retrieveObject(new JSONObject(page.getContent()));
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", answerPut.stringify()));
		page = webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteDoesNotExistAnswer() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", answer.stringify()));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedAnswer() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", "rrefzefzef"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteJsonMalformedAnswer2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", "{\"answer\":{\"id\":\"fef\"}}"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testDeleteMissingArgumentAnswer() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateAnswer() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		Answer answerPut;
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.PUT);
		request.setRequestBody(answer.stringify());
		
		TextPage page = webClient.getPage(request);
		answerPut = Answer.retrieveObject(new JSONObject(page.getContent()));
		answerPut.setDesc("truc");
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
		
		request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", answerPut.stringify()));
		page = webClient.getPage(request);
		
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateDoesNotExist() throws SQLException, FailingHttpStatusCodeException, IOException, JSONException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", answer.stringify()));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_OK, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateJsonMalformedAnswer() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", "rrefzefzef"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateJsonMalformedAnswer2() throws FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("answer", "{\"answer\":{\"id\":\"fef\"}}"));
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testUpdateMissingArgumentAnswer() throws SQLException, FailingHttpStatusCodeException, IOException {
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
	}
	
	@Test
	public void testRequestWithWrongArgumentKey() throws FailingHttpStatusCodeException, IOException {
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		//Requête post
		WebRequest request = new WebRequest(new URL(URL), HttpMethod.POST);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON_QCM));
		
		HtmlPage page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		//Requête get
		request = new WebRequest(new URL(URL), HttpMethod.GET);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON_QCM));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
		//Requête delete
		request = new WebRequest(new URL(URL), HttpMethod.DELETE);
		request.setRequestParameters(new ArrayList<NameValuePair>());
		request.getRequestParameters().add(new NameValuePair("qcm", JSON_QCM));
		
		page = webClient.getPage(request);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, page.getWebResponse().getStatusCode());
		
	}

}

package servlet.form.creation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.Answer;
import entity.Question;

public class FormTools {
	
	private static final String CREATED_QUESTION = "createdQuestion";
	private static final String ANSWERS_ID = "answersId";
	private static int GEN_ANSWER_ID = 1;
	
	/**
	 * Identifiant utilis� pour la question a persister.
	 */
	private static final String READY_QUESTION = "readyQuestion";
	
	/**
	 * Cette m�thode supprime la question temporaire de la session.
	 * @param request
	 */
	public static void removeQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute(CREATED_QUESTION) != null) {
			session.removeAttribute(CREATED_QUESTION);
		}
	}
	
	/**
	 * Cette m�thode change la question temporaire de la session.
	 * @param request
	 * @param question
	 */
	public static void setQuestion(HttpServletRequest request, Question question) {
		HttpSession session = request.getSession(true);
		session.setAttribute(CREATED_QUESTION, question);
	}
	
	/**
	 * Cette m�thode r�cup�re la question temporaire visible dans la session.
	 * Si la question n'existe pas, elle est cr�e.
	 * Dans tous les cas une question est retourn�e.
	 * @param request
	 * @return
	 */
	public static Question getQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute(CREATED_QUESTION) != null) {
			return (Question)session.getAttribute(CREATED_QUESTION);
		} else {
			Question q = new Question();
			setQuestion(request, q);
			return q;
		}
	}
	
	/**
	 * Cette m�thode supprime la question temporaire mise dans la session
	 * @param request
	 */
	public static void cleanQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(CREATED_QUESTION);
	}
	
	/**
	 * Cette m�thode pr�pare une question en vue d'�tre persist�e dans la base de donn�es
	 * @param request
	 * @param question
	 */
	public static void setReadyQuestion(HttpServletRequest request, Question question) {
		HttpSession session = request.getSession(true);
		session.setAttribute(READY_QUESTION, question);
	}
	
	
	
	
	
	/**
	 * Cette m�thode r�cup�re l'ensemble des r�ponses temporaires de la session
	 * sous forme d'une map<Integer, Answer> o� la cl� un identifiant g�n�r� 
	 * par l'application et la valeur un objet Answer.
	 * Si cet ensemble n'existe pas, il est cr�e.
	 * Dans tous les cas un ensemble est retourn�.
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Integer, Answer> getAnswers(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute(ANSWERS_ID) != null) {
			return (Map<Integer, Answer>)session.getAttribute(ANSWERS_ID);
		} else {
			return new HashMap<Integer,Answer>();
		}
	}
	
	public static void addAnswer(HttpServletRequest request, Answer answer) {
		HttpSession session = request.getSession(true);
		Map<Integer, Answer> answersId = getAnswers(request);
		answersId.put(GEN_ANSWER_ID++, answer);
		session.setAttribute(ANSWERS_ID, answersId);
	}
	
	public static void removeAnswer(HttpServletRequest request, int id) {
		HttpSession session = request.getSession(true);
		Map<Integer, Answer> answersId = getAnswers(request);
		answersId.remove(id);
		session.setAttribute(ANSWERS_ID, answersId);
	}
	
	public static void cleanAnswer(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(ANSWERS_ID);
	}

}

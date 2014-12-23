package context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.Answer;
import entity.Question;

/**
 * CreateQuestion g�re tout le processus de cr�ation d'une question.
 * Une question passe par plusieurs �tats :
 * <ul>
 * 	<li><strong>createdQuestion</strong> La question est en cours de cr�ation</li>
 * 	<li><strong>closeQuestion</strong> La question est v�rifi�e et est autoris�e � poursuivre</li>
 * 	<li><strong>readyQuestion</strong> La question est v�rifi�e et peut �tre cr�e</li>
 * </ul>
 * @author romain
 *
 */
public class CreateQuestion {
	
	private static final String CREATED_QUESTION = "createdQuestion";
	private static final String ANSWERS_ID = "answersId";
	private static int GEN_ANSWER_ID = 1;
	
	/**
	 * Identifiant utilis� pour la question a persister.
	 */
	private static final String READY_QUESTION = "readyQuestion";
	
	/**
	 * Limite de r�ponse associ�es � une question
	 * pour laquelle il est possible de terminer une question.
	 */
	private static final int LIMIT_TO_TERMINATE_QUESTION = 2;
	
	/**
	 * Identifiant utilis� pour terminer une question.
	 * Lorsque la limite 'LIMIT_TO_TERMINATE_QUESTION' est atteinte,
	 * cette constante apparait dans la session.
	 */
	private static final String CLOSE_QUESTION = "closeQuestion";
	
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
		if(question.getId() != null) {
			for(Answer a : question.getAnswers()) {
				CreateQuestion.addAnswer(request, a);
			}
			question.cleanAnswers();
		}
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
	 * Cette m�thode indique si une question est en cours de cr�ation.
	 * @param request
	 * @return True si une question est en cours, False sinon.
	 */
	public static boolean isThereAQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		return session.getAttribute(CREATED_QUESTION) != null;
	}
	
	/**
	 * Cette m�thode supprime la question temporaire mise dans la session
	 * @param request
	 */
	private static void cleanQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(CREATED_QUESTION);
	}
	
	/**
	 * Cette m�thode pr�pare une question en vue d'�tre persist�e dans la base de donn�es
	 * @param request
	 * @param question
	 */
	private static void setReadyQuestion(HttpServletRequest request, Question question) {
		HttpSession session = request.getSession(true);
		session.setAttribute(READY_QUESTION, question);
	}
	
	/**
	 * Cette m�thode termine la question de la session.
	 * C'est-�-dire qu'elle ajoute toutes les r�ponses � cette question
	 * et la met dans la session sous la valeur 'readyQuestion'.
	 * @param request
	 * @return True si la question est termin�e, False sinon.
	 */
	public static boolean terminateQuestion(HttpServletRequest request) {
		Collection<Answer> answers = getAnswers(request).values();
		Question question = getQuestion(request);
		if(!answers.isEmpty() && question != null) {
			for(Iterator<Answer> it = answers.iterator(); it.hasNext();) {
				question.addAnswer(it.next());
			}
			CreateQuestion.cleanAnswer(request);
			CreateQuestion.cleanQuestion(request);
			CreateQuestion.setReadyQuestion(request, question);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette m�thode valide la question de la session. Elle enl�ve la question de la session.
	 * @param request
	 * @return True si la question est enlev�e, False sinon.
	 */
	public static boolean deleteReadyQuestion(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Question question = (Question)session.getAttribute(READY_QUESTION);
		if(question != null) {
			session.removeAttribute(READY_QUESTION);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette m�thode d�termine si ma question de la session peut �tre termin�e.
	 * C'est-�-dire si la question peut �tre persist�e dans la base de donn�es.
	 * @param request
	 */
	private static void questionIsTerminted(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(CreateQuestion.getAnswers(request).size() >= LIMIT_TO_TERMINATE_QUESTION)
			session.setAttribute(CLOSE_QUESTION, true);
		else
			session.removeAttribute(CLOSE_QUESTION);
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
	private static Map<Integer, Answer> getAnswers(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute(ANSWERS_ID) != null) {
			return (Map<Integer, Answer>)session.getAttribute(ANSWERS_ID);
		} else {
			return new HashMap<Integer,Answer>();
		}
	}
	
	/**
	 * Cette m�thode ajout une r�ponse � la question temporaire de la session.
	 * @param request
	 * @param answer
	 */
	public static void addAnswer(HttpServletRequest request, Answer answer) {
		HttpSession session = request.getSession(true);
		Map<Integer, Answer> answersId = getAnswers(request);
		answersId.put(GEN_ANSWER_ID++, answer);
		session.setAttribute(ANSWERS_ID, answersId);
		questionIsTerminted(request);
	}
	
	/**
	 * Cette m�thode supprime une r�ponse de la question temporaire de la session.
	 * @param request
	 * @param id
	 */
	public static void removeAnswer(HttpServletRequest request, int id) {
		HttpSession session = request.getSession(true);
		Map<Integer, Answer> answersId = getAnswers(request);
		answersId.remove(id);
		session.setAttribute(ANSWERS_ID, answersId);
		questionIsTerminted(request);
	}
	
	/**
	 * Cette m�thode permet la suppresion de toutes les r�ponses de la session.
	 * @param request
	 */
	private static void cleanAnswer(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(ANSWERS_ID);
		questionIsTerminted(request);
	}
	
}

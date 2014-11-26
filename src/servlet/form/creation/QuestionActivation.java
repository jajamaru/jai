package servlet.form.creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import listener.InitDataBase;
import entity.Question;

public class QuestionActivation {
	
	/**
	 * Identifiant de la liste de toutes les questions mise dans le context de l'application.
	 */
	private static final String QUESTION_LIST = InitDataBase.QUESTION_LIST;
	
	/**
	 * Identifiant de la question activée.
	 */
	private static final String QUESTION_ACTIVATED = "questionActivated";
	
	private static Map<Integer, Question> questionsActivated = new HashMap<Integer, Question>();
	
	public static boolean enable(HttpServletRequest request, int id) {
		List<Question> questions = getQuestionsCanBeActivated(request);
		for(Question q : questions) {
			if(q.getId() == id) {
				questionsActivated.put(q.getId(), q);
				activate(request, q);
				return true;
			}
		}
		return false;
	}
	
	public static boolean disable(HttpServletRequest request, int id) {
		Question question = questionsActivated.get(id);
		if(question != null) {
			//On stocke le résultat
			//Et on la supprime de la map
			disactivate(request);
			return true;
		}
		return false;
	}
	public static boolean isEnable(HttpServletRequest request) {
		return request.getSession(true).getAttribute(QUESTION_ACTIVATED) != null;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Question> getQuestionsCanBeActivated(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute(QUESTION_LIST) != null) {
			return (List<Question>)session.getAttribute(QUESTION_LIST);
		} else {
			List<Question> questions = new ArrayList<Question>();
			session.setAttribute(QUESTION_LIST, questions);
			return questions;
		}
	}
	
	private static void activate(HttpServletRequest request, Question question) {
		request.getServletContext().setAttribute(QUESTION_ACTIVATED, question);
	}
	
	private static void disactivate(HttpServletRequest request) {
		request.getServletContext().removeAttribute(QUESTION_ACTIVATED);
	}

}

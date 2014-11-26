package servlet.form.creation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import listener.InitDataBase;
import entity.Question;

public class QuestionActivation {
	
	/**
	 * Identifiant de la liste de toutes les questions mise dans le context de l'application.
	 */
	private static final String QUESTION_LIST = InitDataBase.QUESTION_LIST;
	
	/**
	 * Identifiant de la question activ�e.
	 */
	private static final String QUESTION_ACTIVATED = "questionActivated";
	
	/**
	 * Map contenant toutes les questions en vues d'�tre activ�e.
	 * Elles sont identifi�es par leur id (id de persistance).
	 */
	private static Map<Integer, Question> questionsActivated = new HashMap<Integer, Question>();
	
	/**
	 * Cette m�thode active une question. Cette question est alors visible par les utilisateurs tiers.
	 * @param request
	 * @param id Identifiant de la question
	 * @return True si la question est trouv�e, False sinon
	 */
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
	
	/**
	 * Cette m�thode d�sactive une question. La question n'est plus visible par les utilisateurs tiers.
	 * @param request
	 * @param id Identifiant de la question
	 * @return True si la question est trouv�e, False sinon
	 */
	public static boolean disable(HttpServletRequest request, int id) {
		Question question = questionsActivated.get(id);
		if(question != null) {
			//On stocke le r�sultat
			//Et on la supprime de la map
			disactivate(request);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette m�thode est un pr�dicat sur l'existence d'une question activ�e.
	 * @param request
	 * @return True si une question est activ�e, False sinon.
	 */
	public static boolean isEnable(HttpServletRequest request) {
		return request.getServletContext().getAttribute(QUESTION_ACTIVATED) != null;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Question> getQuestionsCanBeActivated(HttpServletRequest request) {
		return (List<Question>)request.getServletContext().getAttribute(QUESTION_LIST);
	}
	
	private static void activate(HttpServletRequest request, Question question) {
		request.getServletContext().setAttribute(QUESTION_ACTIVATED, question);
	}
	
	private static void disactivate(HttpServletRequest request) {
		request.getServletContext().removeAttribute(QUESTION_ACTIVATED);
	}

}

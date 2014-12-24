package context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.Answer;
import entity.Question;

/**
 * UpdateQuestion permet de mettre à jour une question contenue dans le contexte de l'application.
 * La question que l'on souhaite mettre à jour n'est pas modifiée directement. C'est un objet Question intermédiare
 * qui prend en compte tous ces changements. Une fois les modifications terminées, la question que l'on souhaitée mettre
 * à jour est modifiée par rapport à l'objet intermédiare.
 * @author romain
 *
 */
public class UpdateQuestion {
	
	/**
	 * Identifiant utilisé pour la mise à jour d'une question.
	 * La valeur associée est un nouvel objet Question.
	 */
	private static final String UPDATE_HANDLER = "updateHandler";
	
	/**
	 * Identifiant utilisé pour la clé référente. C'est la clé que l'on souhaite
	 * mettre à jour.
	 */
	private static final String REFERENCE_QUESTION = "referenceQuestion";
	
	/**
	 * Identifiant utilisé pour autoriser la fin de modification d'une question.
	 */
	private static final String ALLOW_END_OF_UPDATE = "endUpdate";
	
	/**
	 * Session courante de l'utilisateur.
	 */
	private static HttpSession session = null;
	
	
	private static UpdateHandler getUpdateHandler(HttpServletRequest request) {
		session = request.getSession(true);
		if(session.getAttribute(UPDATE_HANDLER) != null) {
			return (UpdateHandler)session.getAttribute(UPDATE_HANDLER);
		}
		UpdateHandler handler = new UpdateHandler();
		session.setAttribute(UPDATE_HANDLER, handler);
		return handler;
	}
	
	private static boolean setUpdateHandler(UpdateHandler handler) {
		if(session != null) {
			session.setAttribute(UPDATE_HANDLER, handler);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette méthode retourne la question temporaire mise en session./
	 * Si elle n'existe pas la question est crée, mise en session puis retournée.
	 * @param request HttpServletRequest de la requête. C'est sur cet objet
	 * que la session est initialisée. Toutes les autres méthodes admettent 
	 * que la session est crée.
	 * @return Question de la session ou la crée.
	 */
	public static Question getQuestion(HttpServletRequest request) {
		UpdateHandler handler = getUpdateHandler(request);
		return handler.getQuestion();
	}
	
	/**
	 * Cette méthode met la question temporaire dans la session.
	 * @param question Question temporaire.
	 * @return True si la question est mise dans la session, False sinon.
	 */
	public static void setQuestion(HttpServletRequest request, Question question) {
		UpdateHandler handler = getUpdateHandler(request);
		handler.setQuestion(question);
		setUpdateHandler(handler);
	}
	
	/**
	 * Ajoute un id dans la liste de suppression des réponses.
	 * @param id Id de la réponse a supprimer.
	 */
	public static void addDeletedAnswer(HttpServletRequest request, Integer id) {
		UpdateHandler handler = getUpdateHandler(request);
		handler.addDeleteAnswer(id);
		setUpdateHandler(handler);
	}
	
	/**
	 * Cette méthode permet de changer la question de référence.
	 * @param refQuestion Nouvelle question de référence.
	 * @return True si la nouvelle question est mise en session, False sinon.
	 */
	private static boolean setReferenceQuestion(Question refQuestion) {
		if(session != null) {
			session.setAttribute(REFERENCE_QUESTION, refQuestion);
			return true;
		}
		return false;
	}
	
	/**
	 * Cette méthode démarre une mise à jour. A partir de maintenant, une copie 
	 * de la question est faites. tous les modifications portée sur la question
	 * ne seront pas répercutées sur elle sauf cas où la validation réussie.
	 * @param request HttpServletRequest courant
	 * @param refQuestion Question de référence (question orginale).
	 */
	public static void startUpdate(HttpServletRequest request, Question refQuestion) {
		System.out.println("Début de la mise à jour de la question... !");
		Question question = getQuestion(request);
		question.setId(refQuestion.getId());
		question.setDesc(refQuestion.getDesc());
		question.setAnswers(refQuestion.getAnswers());
		setQuestion(request, question);
		setReferenceQuestion(refQuestion);
	}
	
	/**
	 * Vérifie si les modifications apportées à la question peuvent être validées.
	 * @param request HttpServletRequest courant
	 * @return True si la validation est possible, False sinon.
	 */
	public static boolean checkUpdate(HttpServletRequest request) {
		Question question = getQuestion(request);
		int bAllow = 0;
		for(Answer a : question.getAnswers()) {
			if(a.getDesc()!=null && !"".equals(a.getDesc()))
					bAllow++;
		}
		if(bAllow > 1) {
			session.setAttribute(ALLOW_END_OF_UPDATE, true);
			return true;
		} else {
			session.removeAttribute(ALLOW_END_OF_UPDATE);
			return false;
		}
	}
	
	/**
	 * Stop la mise à jour. Toutes les variables de contexte sont effacées.
	 * La sauvegarde en base de donénes doit être faite avant d'appeler cette méthode.
	 * @param request HttpServletRequest courant
	 */
	public static void endUpdate(HttpServletRequest request) {
		session = request.getSession(true);
		session.removeAttribute(UPDATE_HANDLER);
		session.removeAttribute(REFERENCE_QUESTION);
		session.removeAttribute(ALLOW_END_OF_UPDATE);
		session = null;
	}
	

}

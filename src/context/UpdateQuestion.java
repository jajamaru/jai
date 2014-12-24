package context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.Answer;
import entity.Question;

/**
 * UpdateQuestion permet de mettre � jour une question contenue dans le contexte de l'application.
 * La question que l'on souhaite mettre � jour n'est pas modifi�e directement. C'est un objet Question interm�diare
 * qui prend en compte tous ces changements. Une fois les modifications termin�es, la question que l'on souhait�e mettre
 * � jour est modifi�e par rapport � l'objet interm�diare.
 * @author romain
 *
 */
public class UpdateQuestion {
	
	/**
	 * Identifiant utilis� pour la mise � jour d'une question.
	 * La valeur associ�e est un nouvel objet Question.
	 */
	private static final String UPDATE_HANDLER = "updateHandler";
	
	/**
	 * Identifiant utilis� pour la cl� r�f�rente. C'est la cl� que l'on souhaite
	 * mettre � jour.
	 */
	private static final String REFERENCE_QUESTION = "referenceQuestion";
	
	/**
	 * Identifiant utilis� pour autoriser la fin de modification d'une question.
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
	 * Cette m�thode retourne la question temporaire mise en session./
	 * Si elle n'existe pas la question est cr�e, mise en session puis retourn�e.
	 * @param request HttpServletRequest de la requ�te. C'est sur cet objet
	 * que la session est initialis�e. Toutes les autres m�thodes admettent 
	 * que la session est cr�e.
	 * @return Question de la session ou la cr�e.
	 */
	public static Question getQuestion(HttpServletRequest request) {
		UpdateHandler handler = getUpdateHandler(request);
		return handler.getQuestion();
	}
	
	/**
	 * Cette m�thode met la question temporaire dans la session.
	 * @param question Question temporaire.
	 * @return True si la question est mise dans la session, False sinon.
	 */
	public static void setQuestion(HttpServletRequest request, Question question) {
		UpdateHandler handler = getUpdateHandler(request);
		handler.setQuestion(question);
		setUpdateHandler(handler);
	}
	
	/**
	 * Ajoute un id dans la liste de suppression des r�ponses.
	 * @param id Id de la r�ponse a supprimer.
	 */
	public static void addDeletedAnswer(HttpServletRequest request, Integer id) {
		UpdateHandler handler = getUpdateHandler(request);
		handler.addDeleteAnswer(id);
		setUpdateHandler(handler);
	}
	
	/**
	 * Cette m�thode permet de changer la question de r�f�rence.
	 * @param refQuestion Nouvelle question de r�f�rence.
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
	 * Cette m�thode d�marre une mise � jour. A partir de maintenant, une copie 
	 * de la question est faites. tous les modifications port�e sur la question
	 * ne seront pas r�percut�es sur elle sauf cas o� la validation r�ussie.
	 * @param request HttpServletRequest courant
	 * @param refQuestion Question de r�f�rence (question orginale).
	 */
	public static void startUpdate(HttpServletRequest request, Question refQuestion) {
		System.out.println("D�but de la mise � jour de la question... !");
		Question question = getQuestion(request);
		question.setId(refQuestion.getId());
		question.setDesc(refQuestion.getDesc());
		question.setAnswers(refQuestion.getAnswers());
		setQuestion(request, question);
		setReferenceQuestion(refQuestion);
	}
	
	/**
	 * V�rifie si les modifications apport�es � la question peuvent �tre valid�es.
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
	 * Stop la mise � jour. Toutes les variables de contexte sont effac�es.
	 * La sauvegarde en base de don�nes doit �tre faite avant d'appeler cette m�thode.
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

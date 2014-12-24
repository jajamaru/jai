package context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import listener.InitDataBase;
import entity.Question;

/**
 * QuestionActivation permet l'activation/désactivation de question.
 * Activer une question permet aux autres utilisateurs de pouvoir y voter.
 * Le résultat d'un vote est mis un objet ResultApplication.
 * @author romain
 *
 */
public class QuestionActivation {
	
	/**
	 * Identifiant de la liste de toutes les questions mise dans le context de l'application.
	 */
	private static final String QUESTION_LIST = InitDataBase.QUESTION_LIST;
	
	/**
	 * Identifiant de la question activée.
	 */
	private static final String QUESTION_ACTIVATED = "questionActivated";
	
	/**
	 * Identifiant des votes de la question précédemment activée.
	 */
	private static final String VOTES = "vote";
	
	/**
	 * Identifiant bloquant l'utilisateur de voter à nouveau.
	 */
	private static final String IS_POLLED = "isPolled";
	
	/**
	 * Map contenant toutes les questions en vues d'être activée.
	 * Elles sont identifiées par leur id (id de persistance).
	 */
	private static Map<Integer, Question> questionsActivated = new HashMap<Integer, Question>();
	
	/**
	 * Contient toutes les sessions ayant voté à la question en cours.
	 */
	private static List<HttpSession> polledSessions = new ArrayList<HttpSession>();
	
	/**
	 * Objet contenant les résultats des votes.
	 */
	private static ResultApplication votes = null;
	
	/**
	 * Cette méthode active une question. Cette question est alors visible par les utilisateurs tiers.
	 * @param request
	 * @param id Identifiant de la question
	 * @return True si la question est trouvée, False sinon
	 */
	public static boolean enable(HttpServletRequest request, int id) {
		System.out.println("Récupération de la question en vue de son activation en cours ...");
		List<Question> questions = getQuestionsCanBeActivated(request);
		questionsActivated.clear();
		libSession();
		for(Question q : questions) {
			if(q.getId() == id) {
				System.out.println("Récupération de la question réussie !");
				questionsActivated.put(q.getId(), q);
				activate(request, q);
				return true;
			}
		}
		System.out.println("Récupération de la question en vue de son activation échec !");
		return false;
	}
	
	/**
	 * Cette méthode désactive une question. La question n'est plus visible par les utilisateurs tiers.
	 * @param request
	 * @param id Identifiant de la question
	 * @return True si la question est trouvée, False sinon
	 */
	public static boolean disable(HttpServletRequest request, int id) {
		System.out.println("Récupération de la question en vue d'une désactivation en cours ...");
		Question question = questionsActivated.get(id);
		if(question != null) {
			System.out.println("Récupération de la question réussie !");
			questionsActivated.remove(id);
			disactivate(request);
			setResultVote(request, question);
			libSession();
			return true;
		}
		System.out.println("Récupération de la question en vue d'une désactivation échec !");
		return false;
	}
	
	/**
	 * Cette méthode est un prédicat sur l'existence d'une question activée.
	 * @param request
	 * @return True si une question est activée, False sinon.
	 */
	public static boolean isEnable(HttpServletRequest request) {
		return request.getServletContext().getAttribute(QUESTION_ACTIVATED) != null;
	}
	
	/**
	 * Cette méthode récupère la liste des questions dans le contexte de l'application.
	 * @param request
	 * @return Liste des questions ou null.
	 */
	@SuppressWarnings("unchecked")
	private static List<Question> getQuestionsCanBeActivated(HttpServletRequest request) {
		return (List<Question>)request.getServletContext().getAttribute(QUESTION_LIST);
	}
	
	private static void setQuestionCanBeActivated(HttpServletRequest request, List<Question> questions) {
		request.getServletContext().setAttribute(QUESTION_LIST, questions);
	}
	
	/**
	 * Cette méthode active une question. Cela consiste en la mise en place
	 * de cette question dans le contexte de l'application.
	 * @param request
	 * @param question La question a contextualiser.
	 */
	private static void activate(HttpServletRequest request, Question question) {
		System.out.println("Activation de la question en cours ...");
		request.getServletContext().setAttribute(QUESTION_ACTIVATED, question);
		votes = new ResultApplication(question, question.getAnswers());
		System.out.println("Activation réussie !");
	}
	
	/**
	 * Cette méthode désactive la question présente dans le contexte de l'application.
	 * Cela consiste en la supression de la question du contexte.
	 * @param request
	 */
	private static void disactivate(HttpServletRequest request) {
		System.out.println("Désactivation de la questionn en cours ...");
		request.getServletContext().removeAttribute(QUESTION_ACTIVATED);
		System.out.println("Désactivation réussie !");
	}
	
	/**
	 * Cette méthode stocke les votes de la question précédemment activée
	 * dans le contexte de l'application.
	 * @param request
	 * @param question Question concernée par les votes.
	 */
	private static void setResultVote(HttpServletRequest request, Question question) {
		System.out.println("Comptage des votes en cours ...");
		if(votes != null) {
			request.getServletContext().setAttribute(VOTES, 
					new PollHandler(question, votes.getVotes()));
			votes = null;
			System.out.println("Comptage réussie !");
		} else {
			System.out.println("Comptage des votes échec !");
		}
		
	}
	
	/**
	 * Indique si un vote est en cours ou non
	 * @param request HttpServletRequest courant
	 * @return True si un vote est en cours, False sinon.
	 */
	public static boolean isVote(HttpServletRequest request) {
		return request.getServletContext().getAttribute(VOTES) != null;
	}
	
	
	/**
	 * Cette méthode ajoute un vote à l'une des réponses de la question
	 * présente dans le contexte de l'application. Le vote incrémente la
	 * réponse correspondante.
	 * @param id Id de la réponse.
	 * @return True si l'id est valide et si une question est activée, False sinon.
	 */
	public static boolean addVote(int id, HttpSession session) {
		System.out.println("Vote en cours ...");
		if(votes != null) {
			System.out.println("Liste des votes trouvés !");
			System.out.println("Vote terminé !");
			if(addSession(session)) {
				return votes.addVote(id);
			}
			return false;
		}
		System.out.println("Vote échoué !");
		return false;
	}
	
	/**
	 * Cette méthode ajoute une session à la liste des sessions ayant votées.
	 * @param session Session de ayant votée.
	 */
	private static boolean addSession(HttpSession session) {
		boolean isIn = false;
		for(HttpSession s : polledSessions) {
			if(s.getId() == session.getId()) {
				isIn = true;
			}
		}
		if(!isIn) {
			session.setAttribute(IS_POLLED, true);
			System.out.println("Utilisateur n°"+session.getId()+" a voté !");
			polledSessions.add(session);
			System.out.println("Nb polled users " + polledSessions.size());
			return true;
		}
		return false;
	}
	
	/**
	 * Cette méthode débloque toutes les sessions ayant votées.
	 */
	private static void libSession() {
		for(HttpSession session: polledSessions) {
			session.removeAttribute(IS_POLLED);
		}
		polledSessions.clear();
	}
	
	/**
	 * Cette méthode ajoute une question au contexte de l'application.
	 * @param request HttpServletRequest courant
	 * @param question Question ajoutée.
	 */
	public static boolean addQuestionToContext(HttpServletRequest request, Question question) {
		System.out.println("Ajout d'une question en contexte en cours ...");
		List<Question> questions = getQuestionsCanBeActivated(request);
		if(questions != null) {
			int pos = questions.size();
			if(question.getId() != null) {
				for(int i=0; pos == questions.size() && i<questions.size(); ++i) {
					Question q = questions.get(i);
					if(q.getId() != null && q.getId() == question.getId()) {
						pos = i;
					}
				}
				if(pos!=questions.size()) {
					questions.remove(pos);
				}
			}
			questions.add(pos, question);
			setQuestionCanBeActivated(request, questions);
			System.out.println("Ajout de la question en contexte réussie !");
			return true;
		}
		System.out.println("Ajout de la question en contexte échec !");
		return false;
	}
	
	/**
	 * Cette méthode supprime du contexte une question.
	 * @param request HttpServletRequest courant
	 * @param id Identifiant de la question.
	 * @return True si la question est supprimée, False sinon.
	 */
	public static boolean removeQuestionFromContext(HttpServletRequest request, int id) {
		System.out.println("Suppression d'une question en contexte en cours ...");
		List<Question> questions = getQuestionsCanBeActivated(request);
		int i_remove = -1;
		for(int i=0; i<questions.size(); ++i) {
			if(questions.get(i).getId() == id) {
				i_remove = i;
			}
		}
		if(i_remove > -1) {
			questions.remove(i_remove);
			setQuestionCanBeActivated(request, questions);
			System.out.println("Suppression d'une question en contexte en réussie !");
			return true;
		}
		System.out.println("Suppression d'une question en contexte échec !");
		return false;
	}
	
	/**
	 * Cette méthode met à jour une question par rapport à une autre ayant le même id.
	 * Elle agît sur le context de l'application (mise à ajour de la question et de l'interface).
	 * @param request HttpServletRequest courant
	 * @param question Question servant de référant
	 * @return True si la question voulant être mise à jour est trouvée et mise à jour, False sinon.
	 */
	public static boolean updateQuestionToContext(HttpServletRequest request, Question question) {
		System.out.println("Mise à jour d'une question dans le contexte...");
		List<Question> questions = getQuestionsCanBeActivated(request);
		for(int i=0; i<questions.size(); ++i) { 
			Question q = questions.get(i);
			if(q.getId() != null && q.getId() == question.getId()) {
				q.setDesc(question.getDesc());
				q.setAnswers(question.getAnswers());
				System.out.println("Mise à jour d'une question dans le contexte réussie !");
				return true;
			}
		}
		System.out.println("Mise à jour d'une question dans le contexte échec !");
		return false;
	}

}

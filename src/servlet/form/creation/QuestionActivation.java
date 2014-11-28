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
	 * Identifiant des votes de la question pr�c�demment activ�e.
	 */
	private static final String VOTES = "vote";
	
	/**
	 * Map contenant toutes les questions en vues d'�tre activ�e.
	 * Elles sont identifi�es par leur id (id de persistance).
	 */
	private static Map<Integer, Question> questionsActivated = new HashMap<Integer, Question>();
	
	/**
	 * Objet contenant les r�sultats des votes.
	 */
	private static ResultApplication votes = null;
	
	/**
	 * Cette m�thode active une question. Cette question est alors visible par les utilisateurs tiers.
	 * @param request
	 * @param id Identifiant de la question
	 * @return True si la question est trouv�e, False sinon
	 */
	public static boolean enable(HttpServletRequest request, int id) {
		System.out.println("R�cup�ration de la question en vue de son activation en cours ...");
		List<Question> questions = getQuestionsCanBeActivated(request);
		for(Question q : questions) {
			if(q.getId() == id) {
				System.out.println("R�cup�ration de la question r�ussie !");
				questionsActivated.put(q.getId(), q);
				activate(request, q);
				return true;
			}
		}
		System.out.println("R�cup�ration de la question en vue de son activation �chec !");
		return false;
	}
	
	/**
	 * Cette m�thode d�sactive une question. La question n'est plus visible par les utilisateurs tiers.
	 * @param request
	 * @param id Identifiant de la question
	 * @return True si la question est trouv�e, False sinon
	 */
	public static boolean disable(HttpServletRequest request, int id) {
		System.out.println("R�cup�ration de la question en vue d'une d�sactivation en cours ...");
		Question question = questionsActivated.get(id);
		if(question != null) {
			System.out.println("R�cup�ration de la question r�ussie !");
			questionsActivated.remove(id);
			disactivate(request);
			setResultVote(request, question);
			return true;
		}
		System.out.println("R�cup�ration de la question en vue d'une d�sactivation �chec !");
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
	
	/**
	 * Cette m�thode r�cup�re la liste des questions dans le contexte de l'application.
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
	 * Cette m�thode active une question. Cela consiste en la mise en place
	 * de cette question dans le contexte de l'application.
	 * @param request
	 * @param question La question a contextualiser.
	 */
	private static void activate(HttpServletRequest request, Question question) {
		System.out.println("Activation de la question en cours ...");
		request.getServletContext().setAttribute(QUESTION_ACTIVATED, question);
		votes = new ResultApplication(question.getAnswers());
		System.out.println("Activation r�ussie !");
	}
	
	/**
	 * Cette m�thode d�sactive la question pr�sente dans le contexte de l'application.
	 * Cela consiste en la supression de la question du contexte.
	 * @param request
	 */
	private static void disactivate(HttpServletRequest request) {
		System.out.println("D�sactivation de la questionn en cours ...");
		request.getServletContext().removeAttribute(QUESTION_ACTIVATED);
		System.out.println("D�sactivation r�ussie !");
	}
	
	/**
	 * Cette m�thode stocke les votes de la question pr�c�demment activ�e
	 * dans le contexte de l'application.
	 * @param request
	 * @param question Question concern�e par les votes.
	 */
	private static void setResultVote(HttpServletRequest request, Question question) {
		System.out.println("Comptage des votes en cours ...");
		if(votes != null) {
			request.getServletContext().setAttribute(VOTES, 
					new PollHandler(question, votes.getVotes()));
			votes = null;
			System.out.println("Comptage r�ussie !");
		} else {
			System.out.println("Comptage des votes �chec !");
		}
		
	}
	
	public static boolean isVote(HttpServletRequest request) {
		return request.getServletContext().getAttribute(VOTES) != null;
	}
	
	/**
	 * Cette m�thode ajoute un vote � l'une des r�ponses de la question
	 * pr�sente dans le contexte de l'application. Le vote incr�mente la
	 * r�ponse correspondante.
	 * @param id Id de la r�ponse.
	 * @return True si l'id est valide et si une question est activ�e, False sinon.
	 */
	public static boolean addVote(int id) {
		System.out.println("Vote en cours ...");
		if(votes != null) {
			System.out.println("Liste des votes trouv�e !");
			System.out.println("Vote termin� !");
			return votes.addVote(id);
		}
		System.out.println("Vote �chou� !");
		return false;
	}
	
	/**
	 * Cette m�thode ajoute une question au contexte de l'application.
	 * @param request
	 * @param question Question ajout�e.
	 */
	public static boolean addQuestionToContext(HttpServletRequest request, Question question) {
		System.out.println("Ajout d'une question en contexte en cours ...");
		List<Question> questions = getQuestionsCanBeActivated(request);
		if(questions != null) {
			questions.add(question);
			setQuestionCanBeActivated(request, questions);
			System.out.println("Ajout de la question en contexte r�ussie !");
			return true;
		}
		System.out.println("Ajout de la question en contexte �chec !");
		return false;
	}
	
	/**
	 * Cette m�thode supprime du contexte une question.
	 * @param request
	 * @param id Identifiant de la question.
	 * @return True si la question est supprim�e, False sinon.
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
			System.out.println("Suppression d'une question en contexte en r�ussie !");
			return true;
		}
		System.out.println("Suppression d'une question en contexte �chec !");
		return false;
	}

}

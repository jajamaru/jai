package context;

import java.util.HashSet;
import java.util.Set;

import entity.Question;

/**
 * Conteneur d'informations pour la mise à jour d'une question.
 * UpdateHandler contient la question mise à jour et l'ensemble des 
 * réponses devant être supprimées pour la question.
 * @author romain
 *
 */
public class UpdateHandler {
	
	private Question question = null;
	private Set<Integer> deletedAnswers = null;
	
	public UpdateHandler(Question question, Set<Integer> ids) {
		this.question = question;
		this.deletedAnswers = ids;
	}
	
	public UpdateHandler() {
		this(new Question(), new HashSet<Integer>());
	}

	/**
	 * Donne la question mise à jour.
	 * @return Question mise à jour.
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Met une nouvelle question. La mise à jour se porte alors sur celle-ci.
	 * @param question Nouvelle question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * Donne l'ensemble des réponses à supprimer.
	 * @return Ensemble des réponses à supprimer.
	 */
	public Set<Integer> getDeletedAnswers() {
		return deletedAnswers;
	}

	/**
	 * Met un nouvelle ensemble de réponses à supprimer.
	 * @param deletedAnswers Nouvelle ensemble.
	 */
	public void setDeletedAnswers(Set<Integer> deletedAnswers) {
		this.deletedAnswers = deletedAnswers;
	}
	
	/**
	 * Ajoute une réponse à supprimer pour la question.
	 * @param nAnswerId Id de la réponse.
	 */
	public void addDeleteAnswer(Integer nAnswerId) {
		this.deletedAnswers.add(nAnswerId);
	}

}

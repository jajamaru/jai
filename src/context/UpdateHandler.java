package context;

import java.util.HashSet;
import java.util.Set;

import entity.Question;

/**
 * Conteneur d'informations pour la mise � jour d'une question.
 * UpdateHandler contient la question mise � jour et l'ensemble des 
 * r�ponses devant �tre supprim�es pour la question.
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
	 * Donne la question mise � jour.
	 * @return Question mise � jour.
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Met une nouvelle question. La mise � jour se porte alors sur celle-ci.
	 * @param question Nouvelle question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * Donne l'ensemble des r�ponses � supprimer.
	 * @return Ensemble des r�ponses � supprimer.
	 */
	public Set<Integer> getDeletedAnswers() {
		return deletedAnswers;
	}

	/**
	 * Met un nouvelle ensemble de r�ponses � supprimer.
	 * @param deletedAnswers Nouvelle ensemble.
	 */
	public void setDeletedAnswers(Set<Integer> deletedAnswers) {
		this.deletedAnswers = deletedAnswers;
	}
	
	/**
	 * Ajoute une r�ponse � supprimer pour la question.
	 * @param nAnswerId Id de la r�ponse.
	 */
	public void addDeleteAnswer(Integer nAnswerId) {
		this.deletedAnswers.add(nAnswerId);
	}

}

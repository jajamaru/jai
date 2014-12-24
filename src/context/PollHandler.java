package context;

import java.util.Collection;

import entity.Question;

/**
 * Cette classe permet d'afficher toutes les informations concernant le résultat
 * d'une question clôturée. Elle contient la liste des compteurs ainsi que la question posée.
 * Une fois un vote effectué, la classe ResultApplication calcul le nombre de vote par réponse
 * et met ces votes dans un conteneur en vue de l'affichage : PollHandler.
 * @author romain
 *
 */
public class PollHandler {
	
	public PollHandler(Question question, Collection<Integer> votes) {
		this.votes = votes;
		this.question = question;
	}
	
	private Collection<Integer> votes;
	private Question question;
	
	/**
	 * Donne les votes correspondant aux réponses dans l'ordre
	 * Attention ici l'ordre est très important.
	 * @return La liste des votes par réponse.
	 */
	public Collection<Integer> getVotes() {return this.votes;}
	
	/**
	 * Donne la question liée aux votes.
	 * @return Question lié aux votes.
	 */
	public Question getQuestion() {return this.question;}
	
}
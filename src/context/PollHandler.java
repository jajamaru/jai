package context;

import java.util.Collection;

import entity.Question;

/**
 * Cette classe permet d'afficher toutes les informations concernant le r�sultat
 * d'une question cl�tur�e. Elle contient la liste des compteurs ainsi que la question pos�e.
 * Une fois un vote effectu�, la classe ResultApplication calcul le nombre de vote par r�ponse
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
	 * Donne les votes correspondant aux r�ponses dans l'ordre
	 * Attention ici l'ordre est tr�s important.
	 * @return La liste des votes par r�ponse.
	 */
	public Collection<Integer> getVotes() {return this.votes;}
	
	/**
	 * Donne la question li�e aux votes.
	 * @return Question li� aux votes.
	 */
	public Question getQuestion() {return this.question;}
	
}
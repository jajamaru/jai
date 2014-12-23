package context;

import java.util.Collection;

import entity.Question;

/**
 * Cette classe permet d'afficher toutes les informations concernant le r�sultat
 * d'une question cl�tur�e. Elle contient la liste des compteurs ainsi que la question pos�e.
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
	
	public Collection<Integer> getVotes() {return this.votes;}
	public Question getQuestion() {return this.question;}
	
}
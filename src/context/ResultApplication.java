package context;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Answer;
import entity.Question;
import entity.Result;

/**
 * ResultApplication permet le calcul des votes pour une question pos�e.
 * Une fois le calcul fait, l'affichage se fait par l'interm�diaire de l'objet PollHandler.
 * @author romain
 *
 */
public class ResultApplication {
	
	private Question question;
	
	/**
	 * Map contenant en cl� les ids des objets 'Answer' et en valeur un compteur de vote.
	 */
	private Map<Integer, Integer> votes = new HashMap<Integer, Integer>();
	
	/**
	 * Entit� contenant le r�sultat d'un vote.
	 */
	private Result result;
	
	public ResultApplication(Question question, List<Answer> answers) {
		result = new Result();
		for(Answer a : answers) {
			votes.put(a.getId(), 0);
		}
	}
	
	/**
	 * Cette m�thode ajoute un vote � une r�ponse.
	 * @param id Identifiant du compteur � incr�menter.
	 * @return True si le compteur est trouv�, False sinon.
	 */
	public boolean addVote(int id) {
		if(votes.get(id) != null) {
			int v = votes.get(id);
			System.out.println("Vote " + id + " -- " + v);
			votes.put(id, ++v);
			System.out.println("Vote " + id + " -- " + votes.get(id));
			return true;
		}
		return false;
	}
	
	/**
	 * Cette m�thode donne tous les votes.
	 * @return Collection de vote ou null.
	 */
	public Collection<Integer> getVotes() {
		return votes.values();
	}
	
	/**
	 * Cette m�thode donne l'objet result.
	 * C'est cet objet qui sera persist� dans la base de donn�es
	 * si l'utilisateur le souhaite.
	 * @return L'objet Result associ�.
	 */
	public Result getResult() {
		return this.result;
	}
	
	/**
	 * Cette m�thode remplie et donne l'objet result.
	 * @param date
	 * @param idQuestion
	 * @param nb
	 * @param success
	 * @return
	 */
	public Result makeResult(Date date, Integer nb) {
		this.result.setQuestionId(this.question.getId());
		this.result.setDate(date);
		this.result.setNbParticipants(nb);
		return this.result;
	}
}

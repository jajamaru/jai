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
 * ResultApplication permet le calcul des votes pour une question posée.
 * Une fois le calcul fait, l'affichage se fait par l'intermédiaire de l'objet PollHandler.
 * @author romain
 *
 */
public class ResultApplication {
	
	private Question question;
	
	/**
	 * Map contenant en clé les ids des objets 'Answer' et en valeur un compteur de vote.
	 */
	private Map<Integer, Integer> votes = new HashMap<Integer, Integer>();
	
	/**
	 * Entité contenant le résultat d'un vote.
	 */
	private Result result;
	
	public ResultApplication(Question question, List<Answer> answers) {
		result = new Result();
		for(Answer a : answers) {
			votes.put(a.getId(), 0);
		}
	}
	
	/**
	 * Cette méthode ajoute un vote à une réponse.
	 * @param id Identifiant du compteur à incrémenter.
	 * @return True si le compteur est trouvé, False sinon.
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
	 * Cette méthode donne tous les votes.
	 * @return Collection de vote ou null.
	 */
	public Collection<Integer> getVotes() {
		return votes.values();
	}
	
	/**
	 * Cette méthode donne l'objet result.
	 * C'est cet objet qui sera persisté dans la base de données
	 * si l'utilisateur le souhaite.
	 * @return L'objet Result associé.
	 */
	public Result getResult() {
		return this.result;
	}
	
	/**
	 * Cette méthode remplie et donne l'objet result.
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

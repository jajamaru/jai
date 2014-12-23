package context;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Answer;

public class ResultApplication {
	
	/**
	 * Map contenant en clé les ids des objets 'Answer' et en valeur un compteur de vote.
	 */
	private Map<Integer, Integer> votes = new HashMap<Integer, Integer>();
	
	public ResultApplication(List<Answer> answers) {
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
	
}

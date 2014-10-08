package Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Question {
	
	private Integer id;
	private String desc;
	
	private Map<Boolean, Answer> answers;

	public Question(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
		this.answers = new HashMap<Boolean, Answer>();
	}
	
	public Question() {
		this(null, null);
	}
	
	public void addAnswer(Answer answer) {
		answers.put(answer.isTrue(), answer);
	}
	
	public void removeAnswer(Answer answer) {
		answers.remove(answer);
	}
	
	public Collection<Answer> getAnswers() {
		return answers.values();
	}
	
	public void setAnswers(Collection<Answer> answers) {
		answers.clear();
		for(Answer a : answers) {
			addAnswer(a);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}

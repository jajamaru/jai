package Entity;

import java.util.HashMap;
import java.util.Map;

public class Question {
	
	private Integer id;
	private String desc;
	
	private Map<Integer, Answer> answers;

	public Question(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
		this.answers = new HashMap<Integer, Answer>();
	}
	
	public Question() {
		this(null, null);
	}
	
	public void addAnswer(Answer answer) {
		answers.put(answer.getId(), answer);
	}
	
	public void removeAnswer(Answer answer) {
		answers.remove(answer);
	}
	
	public Answer getAnswer(Integer id) {
		return answers.get(id);
	}
	
	public Map<Integer, Answer> getAnswers() {
		return answers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}

package Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Question {
	
	private Integer id;
	private String desc;
	
	private List<Answer> answers;

	public Question(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
		this.answers = new ArrayList<Answer>();
	}
	
	public Question() {
		this.id = null;
		this.desc = null;
		this.answers = new ArrayList<Answer>();
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	public void removeAnswer(Answer answer) {
		answers.remove(answer);
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(List<Answer> answers) {
		this.answers.clear();
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

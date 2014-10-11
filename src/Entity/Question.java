package Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Question {
	
	private Integer id;
	private String desc;
	private Integer idQcm;
	
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
	
	public void getAnswer(int position) {
		this.answers.get(position);
	}
	
	public void addAnswer(Answer answer) {
		if(this.answers.contains(answer))
			updateAnswer(answer);
		else
			this.answers.add(answer);
	}
	
	public void removeAnswer(Answer answer) {
		this.answers.remove(answer);
	}
	
	private void updateAnswer(Answer answer) {
		this.answers.remove(answer);
		this.answers.add(answer);
	}
	
	public Collection<Answer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(Collection<Answer> answers) {
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

	public Integer getIdQcm() {
		return idQcm;
	}

	public void setIdQcm(Integer idQcm) {
		this.idQcm = idQcm;
	}
	

}

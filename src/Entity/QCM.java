package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QCM {
	
	private Integer id;
	private String title;
	
	private List<Question> questions;
	
	public QCM(String title) {
		this.id = null;
		this.title = title;
		this.questions = new ArrayList<Question>();
	}
	
	public QCM() {
		this.questions = new ArrayList<Question>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(Collection<Question> questions) {
		this.questions.clear();
		for(Question q : questions) {
			addQuestion(q);
		}
	}
	
	public Question getQuestion(Integer id) {
		return questions.get(id);
	}
	
	public void addQuestion(Question question) {
		if(questions.contains(question)) 
			updateQuestion(question);
		else
			questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		questions.remove(question);
	}
	
	private void updateQuestion(Question question) {
		this.questions.remove(question);
		this.questions.add(question);
	}
}

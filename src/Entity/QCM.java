package Entity;

import java.util.HashMap;
import java.util.Map;

public class QCM {
	
	private Integer id;
	private String title;
	
	private Map<Integer, Question> questions;
	
	public QCM(String title, int duration) {
		this.id = null;
		this.title = title;
		this.questions = new HashMap<Integer, Question>();
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

	public Map<Integer, Question> getQuestions() {
		return questions;
	}
	
	public Question getQuestion(Integer id) {
		return questions.get(id);
	}
	
	public void addQuestion(Question question) {
		questions.put(question.getId(), question);
	}
	
	public void removeQuestion(Question question) {
		questions.remove(question);
	}
}

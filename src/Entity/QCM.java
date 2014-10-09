package Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class QCM {
	
	private Integer id;
	private String title;
	
	private Map<Integer, Question> questions;
	
	public QCM(String title) {
		this.id = null;
		this.title = title;
		this.questions = new HashMap<Integer, Question>();
	}
	
	public QCM() {
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

	public Collection<Question> getQuestions() {
		return questions.values();
	}
	
	public void setQuestions(Collection<Question> questions) {
		this.questions.clear();
		for(Question q : questions) {
			this.questions.put(q.getId(), q);
		}
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

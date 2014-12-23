package context;

import java.util.HashSet;
import java.util.Set;

import entity.Question;

public class UpdateHandler {
	
	private Question question = null;
	private Set<Integer> deletedAnswers = null;
	
	public UpdateHandler(Question question, Set<Integer> ids) {
		this.question = question;
		this.deletedAnswers = ids;
	}
	
	public UpdateHandler() {
		this(new Question(), new HashSet<Integer>());
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Set<Integer> getDeletedAnswers() {
		return deletedAnswers;
	}

	public void setDeletedAnswers(Set<Integer> deletedAnswers) {
		this.deletedAnswers = deletedAnswers;
	}

}

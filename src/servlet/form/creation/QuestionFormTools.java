package servlet.form.creation;

import javax.servlet.http.HttpServletRequest;

import entity.Question;

public class QuestionFormTools {
	
	private static final String CREATED_QUESTION = "createdQuestion";
	
	public static Question getAncCreateBeginQuestion(HttpServletRequest request) {
		if(request.getAttribute(CREATED_QUESTION) != null) {
			return (Question)request.getAttribute(CREATED_QUESTION);
		} else {
			Question question = new Question();
			request.setAttribute(CREATED_QUESTION, question);
			return question;
		}
	}
	
	public static void destroyBeginQuestion(HttpServletRequest request) {
		if(request.getAttribute(CREATED_QUESTION) != null) {
			request.removeAttribute(CREATED_QUESTION);
		}
	}
	
	public static void setBeginQuestion(Question question, HttpServletRequest request) {
		request.setAttribute(CREATED_QUESTION, question);
	}

}

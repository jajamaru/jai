package tools;

import java.util.Collection;
import java.util.Iterator;

import entity.Answer;
import entity.QCM;
import entity.Question;

public class Checking {
	
	public static boolean checkAnswer(Answer answer) {
		return answer.getDesc() != null && answer.getIdQuestion() != null &&
				answer.getCpt() > -1;
	}
	
	public static boolean checkQuestion(Question question) {
		Collection<Answer> answers = question.getAnswers();
		if(answers == null || answers.isEmpty()) return false;
		for(Iterator<Answer> it = answers.iterator(); it.hasNext();) {
			Answer a = it.next();
			if(!checkAnswer(a)) return false;
		}
		return question.getDesc() != null && question.getIdQcm() != null;
	}
	
	public static boolean checkQcm(QCM qcm) {
		Collection<Question> questions = qcm.getQuestions();
		if(questions == null || questions.isEmpty()) return false;
		for(Iterator<Question> it = questions.iterator(); it.hasNext();) {
			Question q = it.next();
			if(!checkQuestion(q)) return false;
		}
		return qcm.getTitle() != null;
	}

}

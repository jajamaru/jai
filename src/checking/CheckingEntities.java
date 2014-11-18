package checking;

import java.util.Iterator;
import java.util.List;

import entity.Answer;
import entity.QCM;
import entity.QCMResult;
import entity.Question;

class CheckingEntities {
	
	public static boolean checkingAnswer(Answer answer) {
		return answer.getDesc() != null && answer.getDesc().length() > 0 &&
				answer.getCpt() > 0 && answer.getIdQuestion() != null;
	}
	
	public static boolean checkingQuestion(Question question) {
		List<Answer> answers = question.getAnswers();
		if(answers == null || answers.size() < 1) return false;
		for(Iterator<Answer> it = answers.iterator(); it.hasNext();) {
			Answer a = it.next();
			if(!checkingAnswer(a)) return false;
		}
		return question.getDesc() != null && question.getDesc().length() > 0 &&
				question.getIdQcm() != null;
	}
	
	public static boolean checkingQcm(QCM qcm) {
		List<Question> questions = qcm.getQuestions();
		if(questions == null || questions.size() < 1) return false;
		for(Iterator<Question> it = questions.iterator(); it.hasNext();) {
			Question q = it.next();
			if(!checkingQuestion(q)) return false;
		}
		return qcm.getTitle() != null && qcm.getTitle().length() > 0;
	}
	
	public static boolean checkingResult(QCMResult result) {
		return result.getIdQcm() != null && checkingSuccessRateCompateToNbParticipants(result.getSuccessRate(), 
				result.getNbParticipants());
	}
	
	private static boolean checkingSuccessRateCompateToNbParticipants(double successRate, int nb) {
		double ratio = successRate / 100;
		return (ratio*nb) > 0;
	}
}

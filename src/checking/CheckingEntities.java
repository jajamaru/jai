package checking;

import java.util.Iterator;
import java.util.List;

import entity.Answer;
import entity.Question;
import entity.Result;

class CheckingEntities {
	
	public static boolean checkingAnswer(Answer answer) {
		return answer.getDesc() != null && answer.getDesc().length() > 0 &&
				answer.getIdQuestion() != null;
	}
	
	public static boolean checkingQuestion(Question question) {
		List<Answer> answers = question.getAnswers();
		if(answers == null || answers.size() < 1) return false;
		for(Iterator<Answer> it = answers.iterator(); it.hasNext();) {
			Answer a = it.next();
			if(!checkingAnswer(a)) return false;
		}
		return question.getDesc() != null && question.getDesc().length() > 0;
	}
	
	public static boolean checkingResult(Result result) {
		return result.getQuestionId() != null && checkingSuccessRateCompateToNbParticipants(result.getSuccessRate(), 
				result.getNbParticipants());
	}
	
	private static boolean checkingSuccessRateCompateToNbParticipants(double successRate, int nb) {
		double ratio = successRate / 100;
		return (ratio*nb) > 0;
	}
}

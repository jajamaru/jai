package classDiagram;

/**
 * 
 * @startuml
class Answer {
	-Integer id
	-String desc
	-boolean isTrue
	-int cpt
	-Integer idQuestion
	+Answer()
	+Answer(Integer id, String desc, boolean isTrue, int cpt)
	+Integer getId()
	+void setId(Integer id)
	+String getDesc()
	+void setDesc(String desc)
	+boolean isTrue()
	+void setTrue(boolean isTrue)
	+int getCpt()
	+void setCpt(int cpt)
	+Integer getIdQuestion()
	+void setIdQuestion(Integer idQuestion)
}
class QCM {
	-Integer id
	-String title
	+QCM(String title)
	+QCM()
	+Integer getId()
	+void setId(Integer id)
	+String getTitle()
	+void setTitle(String title)
	+Collection<Question> getQuestions()
	+void setQuestions(Collection<Question> questions)
	+Question getQuestion(Integer id)
	+void addQuestion(Question question)
	+void removeQuestion(Question question)
	-void updateQuestion(Question question)
}

class QCMResult {
	-Integer id
	-Integer idQcm
	-Date date
	-int nbParticipants
	-double successRate
	-int duration
	+QCMResult()
	+QCMResult(Integer id, Date date, int nbParticipants, float successRate, int duration)
	+Integer getId()
	+void setId(Integer id)
	+Date getDate()
	+void setDate(Date date)
	+int getNbParticipants()
	+void setNbParticipants(int nbParticipants)
	+double getSuccessRate()
	+void setSuccessRate(double successRate)
	+int getDuration()
	+void setDuration(int duration)
	+Integer getIdQcm()
	+void setIdQcm(Integer idqcm)
}

class Question {
	-Integer id
	-String desc
	-Integer idQcm
	+Question(Integer id, String desc)
	+Question()
	+void getAnswer(int position)
	+void addAnswer(Answer answer)
	+void removeAnswer(Answer answer)
	-void updateAnswer(Answer answer)
	+Collection<Answer> getAnswers()
	+void setAnswers(Collection<Answer> answers)
	+Integer getId()
	+void setId(Integer id)
	+String getDesc()
	+void setDesc(String desc)
	+Integer getIdQcm()
	+void setIdQcm(Integer idQcm)
}

Question  o-down- "1..n" Answer
QCM  o-left- "1..n" Question


 * @enduml
 *
 */
public class CDEntity {

}

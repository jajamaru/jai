package classDiagram;

/**
 * 
 * @startuml
interface IPersistable {
	void persist(OBJET obj)
	void update(OBJET obj)
	void delete(OBJET obj)
	OBJET retrieve(T_KEY id)
}

interface IPersistableWithId {
}

IPersistable <|.. IPersistableWithId : T_KEY = Integer

class AnswerRdg {
	+{static}String REQUEST_PERSIST
	+{static}String REQUEST_RETRIEVE
	+{static}String REQUEST_UPDATE
	+{static}String REQUEST_DELETE
	-Connection connection
	+AnswerRdg(Connection connection)
	+void persist(Answer obj)
	+void update(Answer obj)
	+void delete(Answer obj)
	+Answer retrieve(Integer id)
}

IPersistableWithId <|.. AnswerRdg

class QCMRdg {
	+{static}String REQUEST_PERSIST
	+{static}String REQUEST_RETRIEVE
	+{static}String REQUEST_RETRIEVE_QUESTIONS
	+{static}String REQUEST_UPDATE
	+{static}String REQUEST_DELETE
	-Connection connection
	+QCMRdg(Connection connection)
	+void persist(QCM obj)
	+void update(QCM obj)
	+void delete(QCM obj)
	+QCM retrieve(Integer id)
	-void persistQuestions(QCM obj)
	-void updateQuestions(Collection<Question> questions)
	-void deleteQuestions(Collection<Question> questions)
	-Collection<Question> retrieveQuestions(Integer id)
	-void checkGeneratedKey(PreparedStatement statement, QCM qcm)
}

IPersistableWithId <|.. QCMRdg

class QCMResultRdg {
	+{static}String REQUEST_PERSIST
	+{static}String REQUEST_RETRIEVE
	+{static}String REQUEST_UPDATE
	+{static}String REQUEST_DELETE
	-Connection connection
	+QCMResultRdg(Connection connection)
	+void persist(QCMResult obj)
	+void update(QCMResult obj)
	+void delete(QCMResult obj)
	+QCMResult retrieve(Integer id)
	-void checkGeneratedKey(PreparedStatement statement, QCMResult qcmResult)
}

IPersistableWithId <|.. QCMResultRdg

class QuestionRdg {
	+{static}String REQUEST_PERSIST
	+{static}String REQUEST_RETRIEVE
	+{static}String REQUEST_RETRIEVE_ANSWERS
	+{static}String REQUEST_UPDATE
	+{static}String REQUEST_DELETE
	-Connection connection
	+QuestionRdg(Connection connection)
	+void persist(Question obj)
	+void update(Question obj)
	+void delete(Question obj)
	+Question retrieve(Integer id)
	+void increaseAnswer(Answer answer)
	-void checkGeneratedKey(PreparedStatement statement, Question obj)
	-void persistAnswers(Question obj)
	-void updateAnswers(Collection<Answer> answers)
	-void deleteAnswers(Collection<Answer> answers)
	-List<Answer> retrieveAnswers(Integer id)
}

IPersistableWithId <|.left. QuestionRdg

AnswerRdg --o QuestionRdg
QuestionRdg -right-o QCMRdg
 * @enduml
 */
public class CDRdg {

}

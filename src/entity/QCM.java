package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Jsonable;

public class QCM implements Jsonable<QCM>{
	
	public final static String KEY_OBJECT = "qcm";
	public final static String KEY_ID = "id";
	public final static String KEY_TITLE = "title";
	public final static String KEY_QUESTIONS = "questions";
	
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

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		JSONObject qcm = new JSONObject();
		JSONArray questions = new JSONArray();
		for(Question q : getQuestions()) {
			questions.put(q.getJson());
		}
		qcm.put(KEY_ID, getId());
		qcm.put(KEY_TITLE, getTitle());
		qcm.put(KEY_QUESTIONS, questions);
		json.put(KEY_OBJECT, qcm);
		return json;
	}

	@Override
	public String stringify() throws JSONException{
		// TODO Auto-generated method stub
		return this.getJson().toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof QCM)) return false;
		QCM a = (QCM)obj;
		if(obj == this) return true;
		Boolean good = getQuestions().size() == a.getQuestions().size();
		for(int i=0; good && i<getQuestions().size(); ++i) {
			good = getQuestion(i).equals(a.getQuestion(i));
		}
		return good && getTitle() == a.getTitle() && getId() == a.getId();
	}
	
	public boolean equalsBeforePersist(Object obj) {
		if(!(obj instanceof QCM)) return false;
		QCM a = (QCM)obj;
		if(obj == this) return true;
		Boolean good = getQuestions().size() == a.getQuestions().size();
		for(int i=0; good && i<getQuestions().size(); ++i) {
			good = getQuestion(i).equalsBeforePersist(a.getQuestion(i));
		}
		return good && getTitle() == a.getTitle();
	}

	public static QCM retrieveObject(JSONObject json) {
		// TODO Auto-generated method stub
		QCM qcm = null;
		try {
			qcm = new QCM();
			JSONObject jsonQcm = json.getJSONObject(QCM.KEY_OBJECT);
			qcm.setTitle(jsonQcm.getString(QCM.KEY_TITLE));
			
			JSONArray jsonQuestions = jsonQcm.getJSONArray(QCM.KEY_QUESTIONS);
			List<Question> questions = new ArrayList<Question>();
			for(int i=0; i<jsonQuestions.length(); ++i) {
				questions.add(Question.retrieveObject(jsonQuestions.getJSONObject(i)));
			}
			
			qcm.setQuestions(questions);
			if(!jsonQcm.isNull(QCM.KEY_ID))
				qcm.setId(jsonQcm.getInt(QCM.KEY_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qcm;
	}
}

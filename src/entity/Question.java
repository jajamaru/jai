package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Jsonable;

public class Question implements Jsonable {
	
	public final static String KEY_OBJECT = "question";
	public final static String KEY_ID = "id";
	public final static String KEY_DESCRIPTION = "desc";
	public final static String KEY_ANSWERS = "answers";
	
	private Integer id;
	private String desc;
	
	private List<Answer> answers;

	public Question(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
		this.answers = new ArrayList<Answer>(); 
	}
	
	public Question() {
		this.id = null;
		this.desc = null;
		this.answers = new ArrayList<Answer>();
	}
	
	public Answer getAnswer(int position) {
		return this.answers.get(position);
	}
	
	public void addAnswer(Answer answer) {
		boolean isIn = false;
		for(Answer a : answers) {
			if(a.hashCode() == answer.hashCode()) isIn = true;
		}
		if(isIn)
			updateAnswer(answer);
		else
			this.answers.add(answer);
	}
	
	public void removeAnswer(Answer answer) {
		this.answers.remove(answer);
	}
	
	private void updateAnswer(Answer answer) {
		this.answers.remove(answer);
		this.answers.add(answer);
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(List<Answer> answers) {
		this.answers.clear();
		for(Answer a : answers) {
			addAnswer(a);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		JSONObject question = new JSONObject();
		JSONArray answers = new JSONArray();
		for(Answer a : getAnswers()) {
			answers.put(a.getJson());
		}
		question.put(KEY_ID, getId());
		question.put(KEY_DESCRIPTION, getDesc());
		question.put(KEY_ANSWERS, answers);
		json.put(KEY_OBJECT, question);
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
		if(!(obj instanceof Question)) return false;
		Question a = (Question)obj;
		if(obj == this) return true;
		Boolean good = getAnswers().size() == a.getAnswers().size();
		for(int i=0; good && i<getAnswers().size(); ++i) {
			good = getAnswer(i).equals(a.getAnswer(i));
		}
		return good && getDesc() == a.getDesc() && getId() == a.getId();
	}
	
	public boolean equalsBeforePersist(Object obj) {
		if(!(obj instanceof Question)) return false;
		Question a = (Question)obj;
		if(obj == this) return true;
		Boolean good = getAnswers().size() == a.getAnswers().size();
		for(int i=0; good && i<getAnswers().size(); ++i) {
			good = getAnswer(i).equalsBeforePersist(a.getAnswer(i));
		}
		return good && getDesc() == a.getDesc();
	}

	public static Question retrieveObject(JSONObject json) throws MissingJsonArgumentException {
		// TODO Auto-generated method stub
		Question question = null;
		try {
			question = new Question();
			if(json.isNull(Question.KEY_OBJECT))
				throw new MissingJsonArgumentException("Le json donné ne correspond pas à une question !");
			JSONObject jsonQuestion = json.getJSONObject(Question.KEY_OBJECT);
			
			if(jsonQuestion.isNull(Question.KEY_DESCRIPTION))
				throw new MissingJsonArgumentException("La question ne possède pas de description !");
			question.setDesc(jsonQuestion.getString(Question.KEY_DESCRIPTION));
			
			if(jsonQuestion.isNull(Question.KEY_ANSWERS))
				throw new MissingJsonArgumentException("La question ne possède pas de réponses !");
			JSONArray jsonAnswers = jsonQuestion.getJSONArray(Question.KEY_ANSWERS);
			List<Answer> answers = new ArrayList<Answer>();
			for(int i=0; i<jsonAnswers.length(); ++i) {
				answers.add(Answer.retrieveObject(jsonAnswers.getJSONObject(i)));
			}
			question.setAnswers(answers);
			
			if(!jsonQuestion.isNull(Question.KEY_ID))
				question.setId(jsonQuestion.getInt(Question.KEY_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return question;
	}
	

}

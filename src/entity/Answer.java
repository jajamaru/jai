package entity;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Jsonable;


public class Answer implements Jsonable<Answer>{
	
	public final static String KEY_OBJECT = "answer";
	public final static String KEY_ID = "id";
	public final static String KEY_DESCRIPTION = "desc";
	public final static String KEY_IS_TRUE = "isTrue";
	public final static String KEY_CPT = "cpt";
	public final static String KEY_ID_QUESTION = "idQuestion";
	
	private Integer id;
	private String desc;
	private boolean isTrue;
	private int cpt;
	private Integer idQuestion;
	
	public Answer() {}
	
	public Answer(Integer id, String desc, boolean isTrue, int cpt) {
		this.id = id;
		this.desc = desc;
		this.isTrue = isTrue;
		this.cpt = cpt;
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

	public boolean isTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public int getCpt() {
		return cpt;
	}

	public void setCpt(int cpt) {
		this.cpt = cpt;
	}

	public Integer getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		JSONObject answer = new JSONObject();
		answer.put(KEY_ID, getId());
		answer.put(KEY_DESCRIPTION, getDesc());
		answer.put(KEY_IS_TRUE, isTrue());
		answer.put(KEY_CPT, getCpt());
		answer.put(KEY_ID_QUESTION, getIdQuestion());
		json.put(KEY_OBJECT, answer);
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
		if(!(obj instanceof Answer)) return false;
		Answer a = (Answer)obj;
		if(obj == this) return true;
		return isTrue() == a.isTrue() && getCpt() == a.getCpt() && getDesc() == a.getDesc() &&
				getId() == a.getId() && getIdQuestion() == a.getIdQuestion();
	}
	
	public boolean equalsBeforePersist(Object obj) {
		if(!(obj instanceof Answer)) return false;
		Answer a = (Answer)obj;
		if(obj == this) return true;
		return isTrue() == a.isTrue() && getCpt() == a.getCpt() && getDesc() == a.getDesc();
	}

	public static Answer retrieveObject(JSONObject json) {
		// TODO Auto-generated method stub
		Answer answer = null;
		try {
			answer = new Answer();
			JSONObject jsonAnswer = json.getJSONObject(Answer.KEY_OBJECT);
			answer.setCpt(jsonAnswer.getInt(Answer.KEY_CPT));
			answer.setDesc(jsonAnswer.getString(Answer.KEY_DESCRIPTION));
			answer.setTrue(jsonAnswer.getBoolean(Answer.KEY_IS_TRUE));
			if(!jsonAnswer.isNull(Answer.KEY_ID_QUESTION))
				answer.setIdQuestion(jsonAnswer.getInt(Answer.KEY_ID_QUESTION));
			if(!jsonAnswer.isNull(Answer.KEY_ID))
				answer.setId(jsonAnswer.getInt(Answer.KEY_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}

}

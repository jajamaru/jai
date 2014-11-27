package entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Jsonable;


public class Answer implements Jsonable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String KEY_OBJECT = "answer";
	public final static String KEY_ID = "id";
	public final static String KEY_DESCRIPTION = "desc";
	public final static String KEY_IS_TRUE = "isTrue";
	public final static String KEY_ID_QUESTION = "idQuestion";
	
	private Integer id;
	private String desc;
	private boolean isTrue = false;
	private Integer idQuestion;
	
	public Answer() {}
	
	public Answer(Integer id, String desc, boolean isTrue) {
		this.id = id;
		this.desc = desc;
		this.isTrue = isTrue;
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

	public boolean isCorrectAnswer() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
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
		answer.put(KEY_IS_TRUE, isCorrectAnswer());
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
		return isCorrectAnswer() == a.isCorrectAnswer() && getDesc() == a.getDesc() &&
				getId() == a.getId() && getIdQuestion() == a.getIdQuestion();
	}
	
	public boolean equalsBeforePersist(Object obj) {
		if(!(obj instanceof Answer)) return false;
		Answer a = (Answer)obj;
		if(obj == this) return true;
		return isCorrectAnswer() == a.isCorrectAnswer() && getDesc() == a.getDesc();
	}

	public static Answer retrieveObject(JSONObject json) throws MissingJsonArgumentException {
		// TODO Auto-generated method stub
		Answer answer = null;
		try {
			answer = new Answer();
			if(json.isNull(Answer.KEY_OBJECT))
				throw new MissingJsonArgumentException("Le json donn� ne correspond pas � une r�ponse !");
			JSONObject jsonAnswer = json.getJSONObject(Answer.KEY_OBJECT);
			
			if(jsonAnswer.isNull(Answer.KEY_DESCRIPTION))
				throw new MissingJsonArgumentException("La r�ponse ne poss�de pas de description !");
			answer.setDesc(jsonAnswer.getString(Answer.KEY_DESCRIPTION));
			
			if(jsonAnswer.isNull(Answer.KEY_IS_TRUE))
				throw new MissingJsonArgumentException("La question ne poss�de pas de valeur de v�rit� !");
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

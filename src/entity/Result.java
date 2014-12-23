package entity;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Jsonable;

public class Result implements Jsonable {
	
	public final static String KEY_OBJECT = "result";
	public final static String KEY_ID = "id";
	public final static String KEY_ID_QUESTION = "questionId";
	public final static String KEY_DATE = "date";
	public final static String KEY_NB_PARTICIPANTS = "nbParticipants";
	
	private Integer id;
	private Integer questionId;
	private Date date;
	private int nbParticipants;
	
	public Result() {}
	
	public Result(Integer id, Date date, int nbParticipants) {
		this.id = id;
		this.date = date;
		this.nbParticipants = nbParticipants;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNbParticipants() {
		return nbParticipants;
	}

	public void setNbParticipants(int nbParticipants) {
		this.nbParticipants = nbParticipants;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		JSONObject result = new JSONObject();
		result.put(KEY_ID, getId());
		result.put(KEY_ID_QUESTION, getQuestionId());
		if(getDate() != null)
			result.put(KEY_DATE, getDate().getTime());
		result.put(KEY_NB_PARTICIPANTS, getNbParticipants());
		json.put(KEY_OBJECT, result);
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
		Boolean isDate = false;
		if(!(obj instanceof Result)) return false;
		Result a = (Result)obj;
		if(obj == this) return true;
		if((getDate() !=null && a.getDate() == null) || (getDate() == null && a.getDate() != null)) return false;
		if(getDate() == null && a.getDate() == null) isDate=true;
		else isDate = getDate().equals(a.getDate());
		return isDate && getId() == a.getId() && getQuestionId() == a.getQuestionId() &&
				getNbParticipants() == a.getNbParticipants();
	}
	
	public boolean equalsBeforePersist(Object obj) {
		if(!(obj instanceof Result)) return false;
		Result a = (Result)obj;
		if(obj == this) return true;
		return getNbParticipants() == a.getNbParticipants();
	}

	public static Result retrieveObject(JSONObject json) throws MissingJsonArgumentException {
		// TODO Auto-generated method stub
		Result result = null;
		try {
			result = new Result();
			if(json.isNull(Result.KEY_OBJECT))
				throw new MissingJsonArgumentException("Le json donnée ne correspond pas");
			JSONObject jsonResult = json.getJSONObject(Result.KEY_OBJECT);
			
			if(jsonResult.isNull(Result.KEY_NB_PARTICIPANTS))
				throw new MissingJsonArgumentException("Le nombre de participant est inexistant");
			result.setNbParticipants(jsonResult.getInt(Result.KEY_NB_PARTICIPANTS));
			
			if(!jsonResult.isNull(Result.KEY_ID_QUESTION))
				result.setQuestionId(jsonResult.getInt(Result.KEY_ID_QUESTION));
			if(!jsonResult.isNull(Result.KEY_DATE))
				result.setDate(new Date(jsonResult.getLong(Result.KEY_DATE)));
			if(!jsonResult.isNull(Result.KEY_ID))
				result.setId(jsonResult.getInt(Result.KEY_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}

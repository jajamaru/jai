package entity;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Jsonable;

public class QCMResult implements Jsonable<QCMResult>{
	
	public final static String KEY_OBJECT = "qcmResult";
	public final static String KEY_ID = "id";
	public final static String KEY_ID_QCM = "idQcm";
	public final static String KEY_DATE = "date";
	public final static String KEY_NB_PARTICIPANTS = "nbParticipants";
	public final static String KEY_SUCCESS_RATE = "successRate";
	public final static String KEY_DURATION = "duration";
	
	private Integer id;
	private Integer idQcm;
	private Date date;
	private int nbParticipants;
	private double successRate;
	private int duration; //en second
	
	public QCMResult() {}
	
	public QCMResult(Integer id, Date date, int nbParticipants,
			float successRate, int duration) {
		this.id = id;
		this.date = date;
		this.nbParticipants = nbParticipants;
		this.successRate = successRate;
		this.duration = duration;
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

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Integer getIdQcm() {
		return idQcm;
	}

	public void setIdQcm(Integer idqcm) {
		idQcm = idqcm;
	}

	@Override
	public JSONObject getJson() throws JSONException{
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		JSONObject result = new JSONObject();
		result.put(KEY_ID, getId());
		result.put(KEY_ID_QCM, getIdQcm());
		if(getDate() != null)
			result.put(KEY_DATE, getDate().getTime());
		result.put(KEY_NB_PARTICIPANTS, getNbParticipants());
		result.put(KEY_DURATION, getDuration());
		result.put(KEY_SUCCESS_RATE, getSuccessRate());
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
		if(!(obj instanceof QCMResult)) return false;
		QCMResult a = (QCMResult)obj;
		if(obj == this) return true;
		if((getDate() !=null && a.getDate() == null) || (getDate() == null && a.getDate() != null)) return false;
		if(getDate() == null && a.getDate() == null) isDate=true;
		else isDate = getDate().equals(a.getDate());
		return isDate && getId() == a.getId() && getIdQcm() == a.getIdQcm() &&
				getSuccessRate() == a.getSuccessRate() && getDuration() == a.getDuration() && 
				getNbParticipants() == a.getNbParticipants();
	}
	
	public boolean equalsBeforePersist(Object obj) {
		if(!(obj instanceof QCMResult)) return false;
		QCMResult a = (QCMResult)obj;
		if(obj == this) return true;
		return getSuccessRate() == a.getSuccessRate() && getDuration() == a.getDuration() && 
				getNbParticipants() == a.getNbParticipants();
	}

	public static QCMResult retrieveObject(JSONObject json) {
		// TODO Auto-generated method stub
		QCMResult result = null;
		try {
			result = new QCMResult();
			JSONObject jsonResult = json.getJSONObject(QCMResult.KEY_OBJECT);
			result.setIdQcm(jsonResult.getInt(QCMResult.KEY_ID_QCM));
			result.setNbParticipants(jsonResult.getInt(QCMResult.KEY_NB_PARTICIPANTS));
			result.setSuccessRate(jsonResult.getDouble(QCMResult.KEY_SUCCESS_RATE));
			result.setDuration(jsonResult.getInt(QCMResult.KEY_DURATION));
			if(!jsonResult.isNull(QCMResult.KEY_DATE))
				result.setDate(new Date(jsonResult.getLong(QCMResult.KEY_DATE)));
			if(!jsonResult.isNull(QCMResult.KEY_ID))
				result.setId(jsonResult.getInt(QCMResult.KEY_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}

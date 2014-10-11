package Entity;

import java.sql.Date;

public class QCMResult {
	
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
	
	
	
}

package Entity;

import java.sql.Date;

public class QCMResult {
	
	private Integer id;
	private User user;
	private QCM qcm;
	private Date date;
	private int nbParticipants;
	private float successRate;
	private int duration; //en second
	
	public QCMResult(Integer id, User user, QCM qcm, Date date, int nbParticipants,
			float successRate, int duration) {
		this.id = id;
		this.user = user;
		this.qcm = qcm;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public QCM getQcm() {
		return qcm;
	}

	public void setQcm(QCM qcm) {
		this.qcm = qcm;
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

	public float getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(float successRate) {
		this.successRate = successRate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
}
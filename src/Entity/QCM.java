package Entity;

public class QCM {
	
	private Integer id;
	private String title;
	private int duration; //En second
	
	public QCM(String title, int duration) {
		this.id = null;
		this.title = title;
		this.duration = duration;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}	

}

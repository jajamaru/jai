package Entity;


public class Answer {
	
	private Integer id;
	private String desc;
	private boolean isTrue;
	private int cpt;
	
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
	


}

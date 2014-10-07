package Entity;


public class Answer {
	
	private int id;
	private String desc;
	private String answer;
	private boolean isTrue;
	private int cpt;
	
	public Answer() {}
	
	public Answer(int id, String desc, String answer, boolean isTrue, int cpt) {
		this.id = id;
		this.desc = desc;
		this.answer = answer;
		this.isTrue = isTrue;
		this.cpt = cpt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

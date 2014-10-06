package Entity;

import java.sql.Date;

public class User {
	
	private Integer id;
	private String name;
	private String firstName;
	private Date birthday;
	private String profession;
	
	private Authentification auth;
	
	public User(Integer id, String name, String firstName, Date birthday,
			String profession, Authentification auth) {
		this.id = id;
		this.name = name;
		this.firstName = firstName;
		this.birthday = birthday;
		this.profession = profession;
		this.auth = auth;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date bornday) {
		this.birthday = bornday;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Authentification getAuth() {
		return auth;
	}

	public void setAuth(Authentification auth) {
		this.auth = auth;
	}
	
	
	
}

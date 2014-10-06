package RDG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBUtils.IPersistableWithId;
import Entity.Authentification;
import Entity.User;

public class UserRDG implements IPersistableWithId<User>{
	
	public static final String REQUEST_PERSIST = "insert into User(name, firstname, birthday, profession)"
			+ "values(?,?,?,?)";
	public static final String REQUEST_RETRIEVE = "select * from User where User.id = ?";
	public static final String REQUEST_UPDATE = "update User set name = ?, firstname = ?, birthday = ?,"
			+ "profession = ? where User.id = ?";
	public static final String REQUEST_DELETE = "delete from User where User.id = ?";
	
	private Connection connection;
	private AuthentificationRDG authRDG;
	
	public UserRDG(Connection connection, AuthentificationRDG authRDG) {
		this.connection = connection;
		this.authRDG = authRDG;
	}

	@Override
	public void persist(User obj) throws SQLException {
		// TODO Auto-generated method stub
		if(obj.getId() != null) {
			update(obj);
			return;
		}
		this.connection.setAutoCommit(false);
		try {
			this.authRDG.persist(obj.getAuth());
			this.persistUser(obj);
			this.connection.setAutoCommit(true);
		} catch(Exception e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public void update(User obj) throws SQLException {
		// TODO Auto-generated method stub
		this.connection.setAutoCommit(false);
		try {
			authRDG.update(obj.getAuth());
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
			statement.setString(1, obj.getName());
			statement.setString(2, obj.getFirstName());
			statement.setDate(3, obj.getBirthday());
			statement.setString(4, obj.getProfession());
			statement.executeUpdate();
		} catch(Exception e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
		
	}

	@Override
	public void delete(User obj) throws SQLException {
		// TODO Auto-generated method stub
		this.connection.setAutoCommit(false);
		try {
			authRDG.delete(obj.getAuth());
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_DELETE);
			statement.setInt(1, obj.getId());
			statement.executeUpdate();
		} catch(Exception e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public User retrieve(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		Authentification auth = null;
		User user = null;
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
		statement.setInt(1, id);
		ResultSet set = statement.executeQuery();
		if(!set.next()) return null;
		//On recherche le login et mot de passe associée
		auth = this.authRDG.retrieve(set.getString(6));
		//On créer l'utilisateur
		user = new User(id, set.getString(2), set.getString(3), set.getDate(4), set.getString(5), auth);
		return user;
	}

	private void persistUser(User obj) throws SQLException{
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST);
		statement.setString(1, obj.getName());
		statement.setString(2, obj.getFirstName());
		statement.setDate(3, obj.getBirthday());
		statement.setString(4, obj.getProfession());
		statement.executeUpdate();
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if(!generatedKeys.next()) throw new IllegalStateException("no generated keys");
		obj.setId(generatedKeys.getInt(1));
	}

}

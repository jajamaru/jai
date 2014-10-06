package RDG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBUtils.IPersistable;
import Entity.Authentification;

public class AuthentificationRDG implements IPersistable<Authentification, String>{
	
	public static final String REQUEST_PERSIST = "insert into authentification(login, password) values(?,?)";
	public static final String REQUEST_RETRIEVE = "select * from authentification where login = ?";
	public static final String REQUEST_UPDATE = "update authentification set password = ? where login = ?";
	public static final String REQUEST_DELETE = "delete from authentification where login = ?";
	
	private Connection connection;
	
	public AuthentificationRDG(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void persist(Authentification obj) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, obj.getLogin());
		statement.setString(2, obj.getPwd());
		statement.executeUpdate();
	}

	@Override
	public Authentification retrieve(String id) throws SQLException {
		// TODO Auto-generated method stub
		Authentification res = null;
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
		statement.setString(1, id);
		ResultSet set = statement.executeQuery();
		if(!set.next()) return null;
		res = new Authentification(set.getString(1), set.getString(2));
		return res;
	}

	@Override
	public void update(Authentification obj) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
		statement.setString(1, obj.getPwd());
		statement.setString(2, obj.getLogin());
		statement.executeUpdate();
	}

	@Override
	public void delete(Authentification obj) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_DELETE);
		statement.setString(1, obj.getLogin());
		statement.executeUpdate();
	}

}

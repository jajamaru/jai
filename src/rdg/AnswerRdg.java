package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tools.IPersistableWithId;
import entity.Answer;

public class AnswerRdg implements IPersistableWithId<Answer>{

	public static final String REQUEST_PERSIST = "insert into Answer(description, isTrue, idQuestion) values(?,?,?)";
	public static final String REQUEST_RETRIEVE = "select * from Answer where Answer.id = ?";
	public static final String REQUEST_UPDATE = "update Answer set description = ?, isTrue = ? where Answer.id = ?";
	public static final String REQUEST_DELETE = "delete from Answer where Answer.id = ?";
	
	private Connection connection;
	
	public AnswerRdg(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void persist(Answer obj) throws SQLException {
		// TODO Auto-generated method stub
		if(obj.getId() != null) {
			update(obj);
			return;
		}
		this.connection.setAutoCommit(false);	
		try {
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, obj.getDesc());
			statement.setBoolean(2, obj.isTrue());
			statement.setInt(3, obj.getIdQuestion());
			statement.executeUpdate();
			checkGeneratedKey(statement, obj);
			this.connection.setAutoCommit(true);
		} catch(SQLException e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public void update(Answer obj) throws SQLException {
		// TODO Auto-generated method stub
		if(obj.getId() == null) {
			persist(obj);
			return;
		}
		this.connection.setAutoCommit(false);	
		try {
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
			statement.setString(1, obj.getDesc());
			statement.setBoolean(2, obj.isTrue());
			statement.setInt(3, obj.getId());
			statement.executeUpdate();
			this.connection.setAutoCommit(true);
		} catch(SQLException e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		this.connection.setAutoCommit(false);	
		try {
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_DELETE);
			statement.setInt(1, id);
			statement.executeUpdate();
			this.connection.setAutoCommit(true);
		} catch(SQLException e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public Answer retrieve(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		this.connection.setAutoCommit(false);	
		try {
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			if(! set.next()) return null;
			Answer answer = new Answer();
			answer.setId(id);
			answer.setDesc(set.getString(2));
			answer.setTrue(set.getBoolean(3));
			this.connection.setAutoCommit(true);
			return answer;	
		} catch(SQLException e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}
	
	/**
	 * Check the generated key of question object
	 * @param statement
	 * @param obj
	 * @throws SQLException
	 * @throws IllegalStateException
	 */
	private void checkGeneratedKey(PreparedStatement statement, Answer obj) throws SQLException, IllegalStateException {
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if(!generatedKeys.next()) throw new IllegalStateException("no generated keys");
		System.out.println("Answer id generated -- " + generatedKeys.getInt(1));
		obj.setId(generatedKeys.getInt(1));
	}
	
}

package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tools.IPersistableWithId;
import entity.Answer;

public class AnswerRdg implements IPersistableWithId<Answer>{

	public static final String REQUEST_PERSIST = "insert into Answer(description, isTrue, cpt, idQuestion) values(?,?,?,?)";
	public static final String REQUEST_RETRIEVE = "select * from Answer where Answer.id = ?";
	public static final String REQUEST_UPDATE = "update Answer set description = ?, isTrue = ?, cpt = ? where Answer.id = ?";
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
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, obj.getDesc());
		statement.setBoolean(2, obj.isTrue());
		statement.setInt(3, obj.getCpt());
		statement.setInt(4, obj.getIdQuestion());
		statement.executeUpdate();
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if(!generatedKeys.next()) throw new IllegalStateException("no generated keys");
		obj.setId(generatedKeys.getInt(1));
	}

	@Override
	public void update(Answer obj) throws SQLException {
		// TODO Auto-generated method stub
		if(obj.getId() == null) {
			persist(obj);
			return;
		}
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
		statement.setString(1, obj.getDesc());
		statement.setBoolean(2, obj.isTrue());
		statement.setInt(3, obj.getCpt());
		statement.setInt(4, obj.getId());
		statement.executeUpdate();
	}

	@Override
	public void delete(Answer obj) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_DELETE);
		statement.setInt(1, obj.getId());
		statement.executeUpdate();
	}

	@Override
	public Answer retrieve(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
		statement.setInt(1, id);
		ResultSet set = statement.executeQuery();
		if(! set.next()) return null;
		Answer answer = new Answer();
		answer.setId(id);
		answer.setDesc(set.getString(2));
		answer.setTrue(set.getBoolean(3));
		answer.setCpt(set.getInt(4));
		return answer;
	}
	
}

package rdg;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tools.IPersistableWithId;
import entity.Result;

public class ResultRdg implements IPersistableWithId<Result>{
	
	public static final String REQUEST_PERSIST = "insert into Result(date, nbParticipants, successRate, questionId) values(?,?,?,?)";
	public static final String REQUEST_RETRIEVE = "select * from Result where Result.id = ?";
	public static final String REQUEST_UPDATE = "update Result set date = ?, nbParticpants = ?, successRate = ?  where Result.id = ?";
	public static final String REQUEST_DELETE = "delete from Result where Result.id = ?";
	
	private final Connection connection;
	
	public ResultRdg(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void persist(Result obj) throws SQLException {
		if(obj.getId() != null) {
			update(obj);
			return;
		}
		try {
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST, Statement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, new Date(System.currentTimeMillis()).getTime());
			statement.setInt(2, obj.getNbParticipants());
			statement.setDouble(3, obj.getSuccessRate());
			statement.setInt(4, obj.getQuestionId());
			statement.executeUpdate();
			checkGeneratedKey(statement, obj);
		} catch(IllegalStateException e) {
			throw e;
		}
	}

	@Override
	public void update(Result obj) throws SQLException {
		if(obj.getId() == null) {
			persist(obj);
			return;
		}
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
		statement.setDouble(1, obj.getDate().getTime());
		statement.setInt(2, obj.getNbParticipants());
		statement.setDouble(3, obj.getSuccessRate());
		statement.setInt(4, obj.getId());
		statement.executeUpdate();
	}

	@Override
	public void delete(Integer id) throws SQLException {
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_DELETE);
		statement.setInt(1, id);
		statement.executeUpdate();
	}

	@Override
	public Result retrieve(Integer id) throws SQLException {
		Result result = null;
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
		statement.setInt(1, id);
		ResultSet set = statement.executeQuery();
		if(set.next()) {
			result = new Result();
			result.setId(id);
			result.setQuestionId(set.getInt(2));
			result.setDate(new Date((long)set.getDouble(3)));
			result.setNbParticipants(set.getInt(4));
			result.setSuccessRate(set.getFloat(5));
		}
		return result;
	}
	
	private void checkGeneratedKey(PreparedStatement statement, Result result) throws SQLException, IllegalStateException{
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if(!generatedKeys.next()) throw new IllegalStateException("no generated keys");
		result.setId(generatedKeys.getInt(1));
	}

}

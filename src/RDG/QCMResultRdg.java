package rdg;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tools.IPersistableWithId;
import entity.QCMResult;

public class QCMResultRdg implements IPersistableWithId<QCMResult>{
	
	public static final String REQUEST_PERSIST = "insert into Qcmresult(date, nbParticipants, duration, successRate, QCMId) values(?,?,?,?,?)";
	public static final String REQUEST_RETRIEVE = "select * from Qcmresult where Qcmresult.id = ?";
	public static final String REQUEST_UPDATE = "update Qcmresult set date = ?, nbParticpants = ?, successRate = ?  where Qcmresult.id = ?";
	public static final String REQUEST_DELETE = "delete from Qcmresult where Qcmresult.id = ?";
	
	private final Connection connection;
	
	public QCMResultRdg(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void persist(QCMResult obj) throws SQLException {
		// TODO Auto-generated method stub
		try {
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new Date(System.currentTimeMillis()));
			statement.setInt(2, obj.getNbParticipants());
			statement.setInt(3, obj.getDuration());
			statement.setDouble(4, obj.getSuccessRate());
			statement.setInt(5, obj.getIdQcm());
			statement.executeUpdate();
			checkGeneratedKey(statement, obj);
		} catch(IllegalStateException e) {
			throw e;
		}
	}

	@Override
	public void update(QCMResult obj) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
		statement.setInt(1, obj.getId());
		statement.executeUpdate();
	}

	@Override
	public void delete(QCMResult obj) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_DELETE);
		statement.setInt(1, obj.getId());
		statement.executeUpdate();
	}

	@Override
	public QCMResult retrieve(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		QCMResult result = null;
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
		statement.setInt(1, id);
		ResultSet set = statement.executeQuery();
		if(set.next()) {
			result = new QCMResult();
			result.setId(id);
			result.setIdQcm(set.getInt(2));
			result.setDate(set.getDate(3));
			result.setNbParticipants(set.getInt(4));
			result.setSuccessRate(set.getFloat(5));
			result.setDuration(set.getInt(6));
		}
		return result;
	}
	
	private void checkGeneratedKey(PreparedStatement statement, QCMResult qcmResult) throws SQLException, IllegalStateException{
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if(!generatedKeys.next()) throw new IllegalStateException("no generated keys");
		qcmResult.setId(generatedKeys.getInt(1));
	}

}

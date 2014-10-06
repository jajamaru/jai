package RDG;

import java.sql.Connection;
import java.sql.SQLException;

import DBUtils.IPersistableWithId;
import Entity.QCM;

public class QCMRdg implements IPersistableWithId<QCM>{
	
	public static final String REQUEST_PERSIST = "insert into QCM(title, duration) values(?,?)";
	public static final String REQUEST_RETRIEVE = "select * from QCM where QCM.id = ?";
	public static final String REQUEST_UPDATE = "update QCM set title = ?, duration = ? where QCM.id = ?";
	public static final String REQUEST_DELETE = "delete from QCM where QCM.id = ?";
	
	private Connection connection;
	
	public QCMRdg(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void persist(QCM obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(QCM obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(QCM obj) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QCM retrieve(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}

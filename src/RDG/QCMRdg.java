package RDG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Entity.QCM;
import Entity.Question;
import Tools.IPersistableWithId;

public class QCMRdg implements IPersistableWithId<QCM>{
	
	public static final String REQUEST_PERSIST = "insert into Qcm(title) values(?)";
	public static final String REQUEST_RETRIEVE = "select * from Qcm where Qcm.id = ?";
	public static final String REQUEST_RETRIEVE_QUESTIONS = "select idQuestion from Qcm where Qcm.id = ?";
	public static final String REQUEST_UPDATE = "update Qcm set title = ? where Qcm.id = ?";
	public static final String REQUEST_DELETE = "delete from Qcm where Qcm.id = ?";
	
	private final Connection connection;
	private final QuestionRdg questionRdg;
	
	public QCMRdg(Connection connection) {
		this.connection = connection;
		this.questionRdg = new QuestionRdg(connection);
	}

	@Override
	public void persist(QCM obj) throws SQLException {
		// TODO Auto-generated method stub
		if(obj.getId() != null) {
			update(obj);
			return;
		}
		try {
			this.connection.setAutoCommit(false);
			persistQuestions(obj.getQuestions());
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_PERSIST, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, obj.getTitle());
			statement.executeUpdate();
			checkGeneratedKey(statement, obj);
		} catch(IllegalArgumentException e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		} catch(IllegalStateException e1) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e1;
		} catch(Exception e2) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e2;
		}
	}

	@Override
	public void update(QCM obj) throws SQLException {
		// TODO Auto-generated method stub
		if(obj.getId() == null) {
			persist(obj);
			return;
		}
		try {
			this.connection.setAutoCommit(false);
			updateQuestions(obj.getQuestions());
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_UPDATE);
			statement.setString(1, obj.getTitle());
			statement.setInt(2, obj.getId());
			statement.executeUpdate();
		} catch(Exception e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
	}

	@Override
	public void delete(QCM obj) throws SQLException {
		// TODO Auto-generated method stub
		try {
			this.connection.setAutoCommit(false);
			deleteQuestions(obj.getQuestions());
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
	public QCM retrieve(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		QCM qcm = null;
		try {
			this.connection.setAutoCommit(false);
			PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			if(set.next()) {
				qcm = new QCM();
				qcm.setId(id);
				qcm.setTitle(set.getString(2));
				qcm.setQuestions(retrieveQuestions(id));
			}
		} catch(Exception e) {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			throw e;
		}
		return qcm;
	}
	
	private void persistQuestions(Collection<Question> questions) throws SQLException {
		for(Question q : questions) {
			questionRdg.persist(q);
		}
	}
	
	private void updateQuestions(Collection<Question> questions) throws SQLException {
		for(Question q : questions) {
			questionRdg.update(q);
		}
	}
	
	private void deleteQuestions(Collection<Question> questions) throws SQLException {
		for(Question q : questions) {
			questionRdg.delete(q);
		}
	}
	
	private Collection<Question> retrieveQuestions(Integer id) throws SQLException {
		Collection<Question> questions = new ArrayList<Question>();
		PreparedStatement statement = this.connection.prepareStatement(REQUEST_RETRIEVE_QUESTIONS);
		statement.setInt(1, id);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			questions.add(questionRdg.retrieve(set.getInt(1)));
		}
		return questions;
	}
	
	private void checkGeneratedKey(PreparedStatement statement, QCM qcm) throws SQLException, IllegalStateException{
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if(!generatedKeys.next()) throw new IllegalStateException("no generated keys");
		qcm.setId(generatedKeys.getInt(1));
	}

}

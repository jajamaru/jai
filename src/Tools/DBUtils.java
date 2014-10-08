package Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	
	public static final String CONNECTION_REQUEST = "jdbc:derby:romain_huret_jai;create=true";
	
	public static final String[] CREATE_TABLE_STATEMENTS = new String[] {
		"create table Answer (id integer not null generated always as identity, description VARCHAR(1024), isTrue boolean default false, cpt integer, primary key(id))",
		"create table Question (id integer not null generated always as identity, description VARCHAR(2048) not null, idAnswer integer, primary key(id), foreign key(idAnswer) references Answer(id))",
		"create table Qcm (id integer not null generated always as identity, title VARCHAR(255) not null, idQuestion integer, primary key(id), foreign key(idQuestion) references Question(id))",
		"create table Qcmresult (id integer not null generated always as identity, QCMId integer not null, date DATE not null, nbParticipants integer not null, successRate decimal(4,2) not null, duration integer, primary key(id), foreign key(QCMId) references Qcm(id))"
		};
	
	public static final String[] DROP_TABLE_STATEMENTS = new String[] {
		"drop table Qcmresult", "drop table Qcm",
		"drop table Question","drop table Answer"
		};
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_REQUEST);
	}
	
	public static void createDatabase(Connection connection) throws SQLException {
		createDatabase(connection, true);
	}
	
	public static void createDatabase(Connection connection, boolean throwExceptions) throws SQLException {
		executeRequest(connection, throwExceptions, CREATE_TABLE_STATEMENTS);
	}
	
	public static void destroyDatabase(Connection connection) throws SQLException {
		destroyDatabase(connection, true);
	}
	
	public static void destroyDatabase(Connection connection, boolean throwExceptions) throws SQLException {
		executeRequest(connection, throwExceptions, DROP_TABLE_STATEMENTS);
	}
	
	public static void resetDatabase(Connection connection)  throws SQLException {
		destroyDatabase(connection, false);
		createDatabase(connection);
	}
	
	private static void executeRequest(Connection connection, boolean throwException,
			String[] request) throws SQLException {
		for(String str : request) {
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(str);
			} catch(SQLException e) {
				if(throwException) throw e;
			}
		}
	}

}

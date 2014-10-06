package DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	
	public static final String CONNECTION_REQUEST = "jdbc:derby:romain_huret_jai;create=true;";
	
	public static final String[] CREATE_TABLE_STATEMENTS = new String[] {
		"create table authentification (login VARCHAR(255) not null primary key, password VARCHAR(255) not null)",
		"create table qcm (id integer not null generated always as identity primary key, title VARCHAR(255) not null, duration integer not null)",
		"create table user (id integer not null generated always as identity primary key, name VARCHAR(255) not null, firstname VARCHAR(255) not null, birthday DATE not null, profession VARCHAR(255) not null, login VARCHAR(255) not null, foreign key (login) references authentification(login))",
		"create table qcmcompleted (id integer not null generated always as identity primary key, userId integer not null, QCMId integer not null, date DATE not null, nbParticipants integer not null, successRate decimal(4,2) not null, foreign key(QCMId) references QCM(id), foreign key(userId) references user(id))"};
	
	public static final String[] DROP_TABLE_STATEMENTS = new String[] {
		"drop table qcmcompleted", "drop table user",
		"drop table qcm","drop table authentification"};
	
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

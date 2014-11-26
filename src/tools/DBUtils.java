package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	
	private static final String CONNECTION_REQUEST = "jdbc:derby:romain_huret_jai;create=true";
	private static final String CONNECTION_CLOSING = "jdbc:derby:romain_huret_jai;shutdown=true";
	
	private static final String[] CREATE_TABLE_STATEMENTS = new String[] {
		"create table Question (id int not null generated always as identity (start with 1, increment by 1), description VARCHAR(2048) not null, primary key(id))",
		"create table Answer (id int not null generated always as identity (start with 1, increment by 1), description VARCHAR(1024), isTrue boolean default false, idQuestion integer, primary key(id), foreign key(idQuestion) references Question(id))",
		"create table Result (id int not null generated always as identity (start with 1, increment by 1), questionId integer not null, date double not null, nbParticipants integer not null, successRate decimal(4,2) not null, primary key(id), foreign key(questionId) references Question(id))"
		};
	
	private static final String[] DROP_TABLE_STATEMENTS = new String[] {
		"drop table Result", "drop table Answer", "drop table Question"};
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_REQUEST);
	}
	
	public static void closeConnection() throws SQLException {
		DriverManager.getConnection(CONNECTION_CLOSING);
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

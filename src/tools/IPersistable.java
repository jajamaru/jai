package tools;

import java.sql.SQLException;

public interface IPersistable<OBJET, T_KEY> {
	
	void persist(OBJET obj) throws SQLException;
	
	void update(OBJET obj) throws SQLException;
	
	void delete(Integer id) throws SQLException;
	
	OBJET retrieve(T_KEY id) throws SQLException;

}

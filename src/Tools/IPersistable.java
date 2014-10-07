package Tools;

import java.sql.SQLException;

public interface IPersistable<OBJET, T_KEY> {
	
	void persist(OBJET obj) throws SQLException;
	
	void update(OBJET obj) throws SQLException;
	
	void delete(OBJET obj) throws SQLException;
	
	OBJET retrieve(T_KEY id) throws SQLException;

}

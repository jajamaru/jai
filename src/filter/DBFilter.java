package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import tools.DBUtils;

@WebFilter("/*")
public class DBFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		try {
			Connection connection = DBUtils.getConnection();
			DBUtils.resetDatabase(connection);
			arg2.doFilter(arg0, arg1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			arg0.getServletContext().log("Impossible de charger la base de données",e);
			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

package tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AdressServer extends SimpleTagSupport{
	
	private static final String URI_STUDENT = "/student/display/question";
	private static final String HTTP = "http://";
	private static final String HTTPS = "https://";
	private static final String FTP = "ftp://";
	
	private String getProtocole(HttpServletRequest request) {
		String protocole = HTTP;
		String pt = request.getProtocol();
		if(pt.startsWith("HTTPS")) {
			protocole = HTTPS;
		} else if(pt.startsWith("FTP")) {
			protocole = FTP;
		}
		return protocole;
	}
	
	private String getAdressServer(PageContext pageContext) {
		StringBuffer sb = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		sb.append(getProtocole(request));
		sb.append(request.getLocalName());
		sb.append(":");
		sb.append(request.getLocalPort());
		sb.append(URI_STUDENT);
		return sb.toString();
	}

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<code>");
		sb.append(getAdressServer(pageContext));
		sb.append("</code>\n");
		
		out.println(sb);
	}
	
}

package tools;

import org.json.JSONException;
import org.json.JSONObject;

public interface Jsonable<OBJECT> {

	public abstract JSONObject getJson() throws JSONException;
	
	public abstract String stringify() throws JSONException;

}

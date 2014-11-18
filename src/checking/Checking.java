package checking;

import java.util.HashMap;
import java.util.Map;

public class Checking {
	
	protected static Map<String, Boolean> errors = new HashMap<String, Boolean>();
	
	public static Map<String, Boolean> getErrors() {
		return errors;
	}

}

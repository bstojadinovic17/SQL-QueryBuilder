package db.settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsImpl implements Settings{
	
	private Map parameters = new HashMap<>();
	
	@Override
	public Object getParameter(String parameter) {
		return this.parameters.get(parameter);
	}

	@Override
	public void addParameter(String parmeter, Object value) {
		// TODO Auto-generated method stub
		this.parameters.put(parmeter, value);
	}
	
}

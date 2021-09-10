package db.settings;

public interface Settings {
	
	Object getParameter(String parameter);
	
	void addParameter(String parmeter, Object value);
}

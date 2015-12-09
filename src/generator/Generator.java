package generator;

import java.util.HashMap;
import java.util.Vector;

public interface Generator {
	
	public void setData(HashMap<String, Vector<Object>> data);
	
	public void setData(String fileName);
	
	public StringBuffer getD3JsHeaderStr();
	
	public StringBuffer getD3CSSHeaderStr();
	
	public StringBuffer getD3FunctionsStr();
	
	public StringBuffer getJsDataStr();
	
	public StringBuffer getFilterStr();
}

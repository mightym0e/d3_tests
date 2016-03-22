package generator;

import j2html.tags.Tag;

import java.util.HashMap;
import java.util.Vector;

public interface Generator {
	
	public void setData(HashMap<String, Vector<Object>> data);
	
	public void setData(String fileName, String delimiter, int rowBegin, int[] columns, boolean firstRowTitle);
	
	public Vector<Tag> getD3JsHeaderStr();
	
	public Vector<Tag> getD3CSSHeaderStr();
	
	public StringBuffer getD3FunctionsStr();
	
	public StringBuffer getJsDataStr();
	
	public Tag getFilterStr();
}

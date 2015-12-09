package generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import util.CSVReader;

public class ScatterPlot3dGenerator implements Generator {

	private HashMap<String, Vector<Object>> data;
	private StringBuffer jsDataStr;
	
	@Override
	public void setData(HashMap<String, Vector<Object>> data) {
		this.data = data;
	}

	@Override
	public StringBuffer getD3JsHeaderStr() {
		
		StringBuffer header = new StringBuffer();
		
		header.append("<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-latest.min.js\"></script>");
	    header.append("<script type=\"text/javascript\" src=\"http://d3js.org/d3.v3.min.js\"></script>");
	    header.append("<script type=\"text/javascript\" src=\"http://x3dom.org/x3dom/dist/x3dom-full.js\"></script>");
	    header.append("<script type=\"text/javascript\" src=\"js/scatter_plot_3d_demo.js\"></script>");
		
		return header;
	}

	@Override
	public StringBuffer getD3FunctionsStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getD3CSSHeaderStr() {

		StringBuffer cssHeader = new StringBuffer();
		
		cssHeader.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.x3dom.org/download/dev/x3dom.css\"/>");
		
		return cssHeader;
	}

	@Override
	public void setData(String fileName) {
		CSVReader reader = new CSVReader();
	    
	    ArrayList<String[]> data = reader.run(fileName);
	    
	    this.jsDataStr = reader.getJsString(data);
	}

	@Override
	public StringBuffer getJsDataStr() {
		return this.jsDataStr;
	}

	@Override
	public StringBuffer getFilterStr() {

		StringBuffer filter = new StringBuffer();
		
		filter.append("<div id=\"selects\">");
		filter.append("<select class=\"axis_select\" id=\"x\">");
//		filter.append(	"<option value=\"1\">Dataset 1</option>");
		filter.append(	"<option selected=\"selected\" value=\"2\">Dataset 2</option>");
		filter.append(	"<option value=\"3\">Dataset 3</option>");
		filter.append("</select>");
//		filter.append("<select class=\"axis_select\" id=\"y\">");
//		filter.append(	"<option value=\"1\">Dataset 1</option>");
//		filter.append(	"<option selected=\"selected\" value=\"2\">Dataset 2</option>");
//		filter.append(	"<option value=\"3\">Dataset 3</option>");
//		filter.append("</select>");
		filter.append("<select class=\"axis_select\" id=\"z\">");
//		filter.append(	"<option value=\"1\">Dataset 1</option>");
		filter.append(	"<option value=\"2\">Dataset 2</option>");
		filter.append(	"<option selected=\"selected\" value=\"3\">Dataset 3</option>");
		filter.append("</select>");
		filter.append("</div>");
		
		return filter;
	}

}

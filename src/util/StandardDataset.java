package util;

import org.json.simple.JSONArray;

	public class StandardDataset extends DataSet {

	private String fillColor;
	private String strokeColor;
	
	private String highlightFill;
	private String highlightStroke;
	
	private String pointColor;
	private String pointStrokeColor;
	private String pointHighlightFill;
	private String pointHighlightStroke;
	
	private JSONArray data;
	
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.fillColor = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.strokeColor = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getHighlightFill() {
		return highlightFill;
	}
	public void setHighlightFill(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.highlightFill = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getHighlightStroke() {
		return highlightStroke;
	}
	public void setHighlightStroke(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.highlightStroke = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getPointColor() {
		return pointColor;
	}
	public void setPointColor(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.pointColor = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getPointStrokeColor() {
		return pointStrokeColor;
	}
	public void setPointStrokeColor(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.pointStrokeColor = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getPointHighlightFill() {
		return pointHighlightFill;
	}
	public void setPointHighlightFill(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.pointHighlightFill = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getPointHighlightStroke() {
		return pointHighlightStroke;
	}
	public void setPointHighlightStroke(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.pointHighlightStroke = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public JSONArray getData() {
		return data;
	}
	public void setData(JSONArray data) {
		this.data = data;
	}
	
}

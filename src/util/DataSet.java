package util;

import org.json.simple.JSONArray;

public class DataSet {

	// ---------------------STANDARD CHARTS--------------------- //
	
	private String label;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean checkColorInput(int r, int g, int b, float a){
		if(r<0||g<0||b<0||r>255||g>255||b>255||a<0||a>1)return false;
		return true;
	}
	
}

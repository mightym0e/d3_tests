package util;

public class RadarPolarPieDataset extends DataSet {
	
	private Integer value;
	private String color;
	private String highlight;
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.color = "rgba("+r+","+g+","+b+","+a+")";
	}
	
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(int r, int g, int b, float a) {
		if(!checkColorInput(r, g, b, a))throw new IllegalArgumentException();
		this.highlight = "rgba("+r+","+g+","+b+","+a+")";
	}
}

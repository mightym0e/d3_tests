package util;

import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.*;

public abstract class Data {

	public enum ChartType {
		Line,Bar,Radar,Polar,Pie,Doughnut
	};

	private JSONArray labels;
	private JSONArray datasets;
	protected ChartType chartType;

	public Data(){
		this.labels = new JSONArray();
		this.datasets = new JSONArray();
	}

	public Data(JSONArray labels, JSONArray datasets){
		this.labels = labels;
		this.datasets = datasets;
	}

	public void addLabel(String label){
		this.labels.add(label);
	}

	public void removeDataset(int index){
		this.labels.remove(index);
	}

	public JSONArray getLabels() {
		return labels;
	}
	public void setLabels(JSONArray labels) {
		this.labels = labels;
	}

	public JSONArray getDatasets() {
		return datasets;
	}

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

}

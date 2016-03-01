package util;

import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.*;

public class Data {

	public enum ChartType {
		Line,Bar,Radar,Polar,Pie,Doughnut
	};

	private JSONArray labels;
	private JSONArray datasets;
	private ChartType chartType = ChartType.Line;

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

	public void addDataset(DataSet dataset){
		JSONObject temp = getJsonObjectFromDataset(dataset);
		this.labels.add(temp);
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
	public void setDatasets(Collection<DataSet> datasets) {
		
		for(DataSet dataset : datasets){
			JSONObject temp = getJsonObjectFromDataset(dataset);
			this.labels.add(temp);
		}
	}

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObjectFromDataset(DataSet dataset){
		JSONObject ret = new JSONObject();
		
		if(dataset instanceof StandardDataset){
			
			StandardDataset standard = (StandardDataset) dataset;
			
			if(standard.getLabel()!=null)ret.put("label", standard.getLabel());
			
			if(standard.getFillColor()!=null)ret.put("fillColor", standard.getFillColor());
			if(standard.getStrokeColor()!=null)ret.put("strokeColor", standard.getStrokeColor());
			
			if(standard.getHighlightFill()!=null)ret.put("highlightFill", standard.getHighlightFill());
			if(standard.getHighlightStroke()!=null)ret.put("highlightStroke", standard.getHighlightStroke());
			
			if(standard.getPointColor()!=null)ret.put("pointColor", standard.getPointColor());
			if(standard.getPointStrokeColor()!=null)ret.put("pointStrokeColor", standard.getPointStrokeColor());
			if(standard.getPointHighlightFill()!=null)ret.put("pointHighlightFill", standard.getPointHighlightFill());
			if(standard.getPointHighlightStroke()!=null)ret.put("pointHighlightStroke", standard.getPointHighlightStroke());
			
		} else if(dataset instanceof RadarPolarPieDataset){
			RadarPolarPieDataset radarPolarPie = (RadarPolarPieDataset) dataset;
			
			if(radarPolarPie.getLabel()!=null)ret.put("label", radarPolarPie.getLabel());
			
			if(radarPolarPie.getValue()!=null)ret.put("value", radarPolarPie.getValue());
			if(radarPolarPie.getColor()!=null)ret.put("color", radarPolarPie.getColor());
			if(radarPolarPie.getHighlight()!=null)ret.put("highlight", radarPolarPie.getHighlight());
		}
		
		return ret;
	}

}

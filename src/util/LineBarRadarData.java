package util;

import java.util.Collection;

import org.json.simple.*;

public class LineBarRadarData extends Data {

	private JSONArray labels;
	private JSONArray datasets;

	public LineBarRadarData(){
		this.labels = new JSONArray();
		this.datasets = new JSONArray();
		this.chartType = ChartType.Line;
	}

	public LineBarRadarData(JSONArray labels, JSONArray datasets){
		this.labels = labels;
		this.datasets = datasets;
		this.chartType = ChartType.Line;
	}

	public void addLabel(String label){
		this.labels.add(label);
	}

	public void addDataset(LineBarRadarDataset dataset){
		JSONObject temp = getJsonObjectFromDataset(dataset);
		this.datasets.add(temp);
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
	public void setDatasets(Collection<LineBarRadarDataset> datasets) {

		for(LineBarRadarDataset dataset : datasets){
			addDataset(dataset);
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObjectFromDataset(DataSet dataset){
		JSONObject ret = new JSONObject();

		LineBarRadarDataset standard = (LineBarRadarDataset) dataset;

		if(standard.getLabel()!=null)ret.put("label", standard.getLabel());

		if(standard.getFillColor()!=null)ret.put("fillColor", standard.getFillColor());
		if(standard.getStrokeColor()!=null)ret.put("strokeColor", standard.getStrokeColor());

		if(standard.getHighlightFill()!=null)ret.put("highlightFill", standard.getHighlightFill());
		if(standard.getHighlightStroke()!=null)ret.put("highlightStroke", standard.getHighlightStroke());

		if(standard.getPointColor()!=null)ret.put("pointColor", standard.getPointColor());
		if(standard.getPointStrokeColor()!=null)ret.put("pointStrokeColor", standard.getPointStrokeColor());
		if(standard.getPointHighlightFill()!=null)ret.put("pointHighlightFill", standard.getPointHighlightFill());
		if(standard.getPointHighlightStroke()!=null)ret.put("pointHighlightStroke", standard.getPointHighlightStroke());

		if(standard.getData()!=null)ret.put("data", standard.getData());


		return ret;
	}

}

package util;
import java.util.Collection;

import org.json.simple.*;

import util.Data.ChartType;

public class RadarPolarPieData extends Data {

	private JSONArray datasets;
	public RadarPolarPieData(){
		this.datasets = new JSONArray();
		this.chartType = ChartType.Radar;
	}

	public RadarPolarPieData(JSONArray labels, JSONArray datasets){
		this.datasets = datasets;
		this.chartType = ChartType.Radar;
	}

	public void removeDataset(int index){
		this.datasets.remove(index);
	}

	public JSONArray getDatasets() {
		return datasets;
	}
	
	public void addDataset(RadarPolarPieDataset dataset){
		JSONObject temp = getJsonObjectFromDataset(dataset);
		this.datasets.add(temp);
	}
	
	public void setDatasets(Collection<RadarPolarPieDataset> datasets) {

		for(RadarPolarPieDataset dataset : datasets){
			addDataset(dataset);
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject getJsonObjectFromDataset(DataSet dataset) {
		JSONObject ret = new JSONObject();

		RadarPolarPieDataset radarPolarPie = (RadarPolarPieDataset) dataset;
		
		if(radarPolarPie.getLabel()!=null)ret.put("label", radarPolarPie.getLabel());
		
		if(radarPolarPie.getValue()!=null)ret.put("value", radarPolarPie.getValue());
		if(radarPolarPie.getColor()!=null)ret.put("color", radarPolarPie.getColor());
		if(radarPolarPie.getHighlight()!=null)ret.put("highlight", radarPolarPie.getHighlight());

		return ret;
	}

}

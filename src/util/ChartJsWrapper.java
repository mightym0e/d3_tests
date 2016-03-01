package util;

import static j2html.TagCreator.script;

import org.json.simple.JSONObject;

import util.Data.ChartType;

public class ChartJsWrapper {

	private static String scriptHeader = script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js").render();
	
	private Data data;
	
	private String chartId = "myChart";
	
	private int width = 400;
	private int height = 400;
	
	private String containerName;

	public static String getScriptHeader() {
		return scriptHeader;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	
	@SuppressWarnings("unchecked")
	public StringBuffer getJsString(){
		
		String chartInit = "var myLineChart = new Chart(ctx).";
		String chartData = "var data = ";
		
		JSONObject temp = null;
		
		if(data.getChartType()==ChartType.Line||data.getChartType()==ChartType.Bar||data.getChartType()==ChartType.Radar){
			temp = new JSONObject();
			temp.put("labels", data.getLabels());
			temp.put("datasets", data.getDatasets());
		}
		
		switch (data.getChartType()) {
			case Line:
				chartInit += "Line(data);";
				chartData += temp.toJSONString();
				break;      
			case Bar:       
				chartInit += "Bar(data);";
				chartData += temp.toJSONString();
				break;      
			case Radar:     
				chartInit += "Radar(data);";
				chartData += temp.toJSONString();
				break;      
			case Polar:     
				chartInit += "PolarArea(data);";
				chartData += data.getDatasets().toJSONString();
				break;      
			case Pie:       
				chartInit += "Pie(data);";
				chartData += data.getDatasets().toJSONString();
				break;      
			case Doughnut:  
				chartInit += "Doughnut(data);";
				chartData += data.getDatasets().toJSONString();
				break;
			default:
				break;
		}
		
		chartData += ";";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<canvas id=\""+this.chartId+"\" width=\""+this.width+"\" height=\""+this.height+"\"></canvas>");
	
		ret.append(script().withType("text/javascript").withContent(
					"var ctx = document.getElementById(\""+this.chartId+"\").getContext(\"2d\");"+
					chartData+
					chartInit)
				);
		
		return ret;
	}

	public String getChartId() {
		return chartId;
	}

	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	
	
}

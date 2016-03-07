package util;

import static j2html.TagCreator.script;
import static j2html.TagCreator.ul;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import j2html.tags.Tag;

import org.json.simple.JSONObject;

import util.Data.ChartType;

public class ChartJsWrapper {

	private static String scriptHeader = script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js").render();
	
	private Data data;
	
	private JSONObject options;
	
	private String chartId = "myChart";
	
	private int width = 400;
	private int height = 400;
	
	private String containerId;
	
	private boolean addLegend = false;
	
	private String detailServlet = "AjaxDetailServlet";
	
	public static Collection<Color> generateColors(int count){
		ArrayList<Color> ret = new ArrayList<Color>();
		
		for(int i = 0; i < 360; i += 360 / count) {

		    float hue = i/360f;
		    float saturation = (90 + (int)(Math.random() * 10))/100f;
		    float lightness = (50 + (int)(Math.random() * 10))/100f;

		    ret.add(Color.getHSBColor(hue, saturation, lightness));
		}
		
		return ret;
	}
	
	public ChartJsWrapper(){
		this.options = new JSONObject();
	}

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
	
	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerName) {
		this.containerId = containerName;
	}
	
	@SuppressWarnings("unchecked")
	public StringBuffer getJsString(){
		
		String chartInit = "var chart_"+this.chartId+" = new Chart(ctx_"+this.chartId+").";
		String chartData = "var data_"+this.chartId+" = ";
		
		StringBuffer onclick = new StringBuffer();
		
		JSONObject temp = null;
		
		String legend = "";
		
		if(data.getChartType()==ChartType.Line||data.getChartType()==ChartType.Bar||data.getChartType()==ChartType.Radar){
			temp = new JSONObject();
			temp.put("labels", data.getLabels());
			temp.put("datasets", data.getDatasets());
			legend = "<ul class=\"<%=name.toLowerCase()%>-legend\">"
					+ "<% for (var i=0; i<datasets.length; i++){%><li>"
						+ "<span onClick=\"showDetails('<%if(datasets[i].label){%><%=datasets[i].label%><%}%>')\" style=\"background-color:<%=datasets[i].strokeColor%>\">"
						+ "<%if(datasets[i].label){%><%=datasets[i].label%><%}%>"
						+ "</span>"
					+ "</li>"
				+ "<%}%>"
				+ "</ul>";
		} else {
			legend = "<ul class=\"<%=name.toLowerCase()%>-legend\">"
				+ "<% for (var i=0; i<segments.length; i++){%>"
					+ "<li>"
						+ "<span style=\"background-color:<%=segments[i].fillColor%>\">"
						+ "<%if(segments[i].label){%><%=segments[i].label%><%}%>"
						+ "</span>"
					+ "</li>"
				+ "<%}%>"
				+ "</ul>";
		}
		
		this.options.put("legendTemplate", legend);
		this.options.put("responsive", true);
		this.options.put("yAxisLabel", "Test Label");
		
		switch (data.getChartType()) {
			case Line:
				chartInit += "Line";
				chartData += temp.toJSONString();
				onclick.append("var holder = document.getElementById('"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("holder.onclick = function(evt){"+System.getProperty("line.separator"));
				onclick.append("    var activePoints = chart_"+this.chartId+".getPointsAtEvent(evt);"+System.getProperty("line.separator"));
//				onclick.append("    console.log(activePoints);"+System.getProperty("line.separator"));
				onclick.append("    if(activePoints)showDetailData('"+this.detailServlet+"', activePoints, 'divDetail"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("};"+System.getProperty("line.separator"));
				break;      
			case Bar:       
				chartInit += "Bar";
				chartData += temp.toJSONString();
				onclick.append("var holder = document.getElementById('"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("holder.onclick = function(evt){"+System.getProperty("line.separator"));
				onclick.append("    var activePoints = chart_"+this.chartId+".getBarsAtEvent(evt);"+System.getProperty("line.separator"));
//				onclick.append("    console.log(activePoints);"+System.getProperty("line.separator"));
				onclick.append("    if(activePoints)showDetailData('"+this.detailServlet+"', activePoints, 'divDetail"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("};"+System.getProperty("line.separator"));
				break;      
			case Radar:     
				chartInit += "Radar";
				chartData += temp.toJSONString();
				break;      
			case Polar:     
				chartInit += "PolarArea";
				chartData += data.getDatasets().toJSONString();
				break;      
			case Pie:       
				chartInit += "Pie";
				chartData += data.getDatasets().toJSONString();
				onclick.append("var holder = document.getElementById('"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("holder.onclick = function(evt){"+System.getProperty("line.separator"));
				onclick.append("    var activePoints = chart_"+this.chartId+".getSegmentsAtEvent(evt);"+System.getProperty("line.separator"));
//				onclick.append("    console.log(activePoints);"+System.getProperty("line.separator"));
				onclick.append("    if(activePoints)showDetailData('"+this.detailServlet+"', activePoints, 'divDetail"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("};"+System.getProperty("line.separator"));
				break;      
			case Doughnut:  
				chartInit += "Doughnut";
				chartData += data.getDatasets().toJSONString();
				break;
			default:
				break;
		}
		
		chartData += ";"+System.getProperty("line.separator");
		chartInit += "(data_"+this.chartId+
				((this.options!=null&&this.options.size()>0)?(", "+this.options.toJSONString()):"")
				+");"
				+System.getProperty("line.separator");
		
		StringBuffer ret = new StringBuffer();
		
		Tag script = script().withType("text/javascript");
		
		ret.append(script.renderOpenTag()+System.getProperty("line.separator"));
		
		ret.append("var canvas_"+this.chartId+" = document.createElement(\"canvas\");"+System.getProperty("line.separator"));
		ret.append("canvas_"+this.chartId+".setAttribute(\"width\", \""+this.width+"\");"+System.getProperty("line.separator"));
		ret.append("canvas_"+this.chartId+".setAttribute(\"height\", \""+this.height+"\");"+System.getProperty("line.separator"));
		ret.append("canvas_"+this.chartId+".setAttribute(\"id\", \""+this.chartId+"\");"+System.getProperty("line.separator"));
		ret.append("canvas_"+this.chartId+".setAttribute(\"style\", \"padding:10px\");"+System.getProperty("line.separator"));
		
		ret.append("document.getElementById(\""+this.containerId+"\").appendChild(canvas_"+this.chartId+");"+System.getProperty("line.separator"));
	
		ret.append("var ctx_"+this.chartId+" = document.getElementById(\""+this.chartId+"\").getContext(\"2d\");"+System.getProperty("line.separator"));
		ret.append(chartData+System.getProperty("line.separator"));
		ret.append(chartInit+System.getProperty("line.separator"));
		
		if(this.addLegend){
			ret.append("var legend_"+this.chartId+" = document.createElement(\"div\");"+System.getProperty("line.separator"));
			ret.append("legend_"+this.chartId+".setAttribute(\"id\", \""+this.chartId+"_legend\");"+System.getProperty("line.separator"));
			ret.append("document.getElementById(\""+this.containerId+"\").appendChild(legend_"+this.chartId+");");
			ret.append("document.getElementById(\""+this.chartId+"_legend\").innerHTML = chart_"+this.chartId+".generateLegend();");
		}
		
		// CLICK FUNCTION
		
		ret.append(onclick.toString()+System.getProperty("line.separator"));
		
		ret.append(script.renderCloseTag()+System.getProperty("line.separator"));
		
		return ret;
	}

	public String getChartId() {
		return chartId;
	}

	public void setChartId(String chartId) {
		this.chartId = chartId;
	}

	public boolean isAddLegend() {
		return addLegend;
	}

	public void setAddLegend(boolean addLegend) {
		this.addLegend = addLegend;
	}
	
	public void setOption(String option, String value) {
		this.options.put(option, value);
	}

	public String getDetailServlet() {
		return detailServlet;
	}

	public void setDetailServlet(String detailServlet) {
		this.detailServlet = detailServlet;
	}
	
	
	
}

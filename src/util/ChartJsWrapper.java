package util;

import static j2html.TagCreator.div;
import static j2html.TagCreator.script;
import static j2html.TagCreator.table;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import j2html.tags.ContainerTag;
import j2html.tags.Tag;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import util.Data.ChartType;

// TODO: Auto-generated Javadoc
/**
 * The Class ChartJsWrapper.
 */
public class ChartJsWrapper {
	
	/** The data. */
	private Data data;
	
	/** The options. */
	private JSONObject options;
	
	/** The chart id. */
	private String chartId = "myChart";
	
	/** The width. */
	private int width = 400;
	
	/** The height. */
	private int height = 400;
	
	/** The container id. */
	private String containerId;
	
	/** The add legend. */
	private boolean addLegend = false;
	
	/** The add download. */
	private boolean addDownload = false;
	
	/** The add legend interaction. */
	private boolean addLegendInteraction = false;
	
	/** The always show tooltip. */
	private boolean alwaysShowTooltip = false;
	
	/** The detail servlet. */
	private String detailServlet = "AjaxDetailServlet";
	
	/**
	 * Generate colors.
	 *
	 * @param count the count
	 * @return the collection
	 */
	public static Collection<Color> generateColors(int count){
		if(count<1)throw new IllegalArgumentException("Die Anzahl der zu generierenden Farben muss größer als 0 sein.");
		ArrayList<Color> ret = new ArrayList<Color>();
		
		for(int i = 0; i < 360; i += 360 / count) {

		    float hue = i/360f;
		    float saturation = (80 + (int)(Math.random() * 20))/100f;
		    float lightness = (25 + (int)(Math.random() * 50))/100f;

		    ret.add(Color.getHSBColor(hue, saturation, lightness));
		}
		
		Collections.shuffle(ret);
		
		return ret;
	}
	
	/**
	 * Instantiates a new chart js wrapper.
	 */
	public ChartJsWrapper(){
		this.options = new JSONObject();
	}

	/**
	 * Gets the script header.
	 *
	 * @return the script header
	 */
	public static String getScriptHeader() {
		
		StringBuffer scriptHeaderStr = new StringBuffer();
		Properties props = new Properties();
		InputStream input = null;
		
		try {
			String resource = ChartJsWrapper.class.getResource("../files.properties").toString();
			resource = resource.toString().substring("file:/".length(),resource.length());
			input = new FileInputStream(resource);
			props.load(input);
			
			scriptHeaderStr.append(script().withType("text/javascript").withSrc(props.getProperty("jquery")));
			scriptHeaderStr.append(script().withType("text/javascript").withSrc(props.getProperty("chartjs")));
			scriptHeaderStr.append(script().withType("text/javascript").withSrc(props.getProperty("domtoimage")));
			scriptHeaderStr.append(script().withType("text/javascript").withSrc(props.getProperty("chartJsExtensions")));
			scriptHeaderStr.append(script().withType("text/javascript").withSrc(props.getProperty("stackedBar")));
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return scriptHeaderStr.toString();
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Gets the container id.
	 *
	 * @return the container id
	 */
	public String getContainerId() {
		return containerId;
	}

	/**
	 * Sets the container id.
	 *
	 * @param containerName the new container id
	 */
	public void setContainerId(String containerName) {
		this.containerId = containerName;
	}
	
	/**
	 * Gets the js string.
	 *
	 * @return the js string
	 */
	@SuppressWarnings("unchecked")
	public StringBuffer getJsString(){
		
		String chartInit = "var chart_"+this.chartId+" = new Chart(ctx_"+this.chartId+").";
		String chartData = "var data_"+this.chartId+" = ";
		
		StringBuffer onclick = new StringBuffer();
		
		JSONObject temp = new JSONObject();
		
		//---------------------------DATASETS, TOOLTIP AND LEGEND INTERACTION
		
		String legend = "";
		
		if(data.getChartType()==ChartType.Line||data.getChartType()==ChartType.Bar||data.getChartType()==ChartType.StackedBar||data.getChartType()==ChartType.Radar){
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
			if(this.addDownload){
				StringBuffer onAnimationCompleteFunction = new StringBuffer();
				onAnimationCompleteFunction.append("function(){addDownloadButton('"+this.containerId+"');");
				if(this.addLegendInteraction){
					onAnimationCompleteFunction.append("addLegendInteraction(chart_"+this.chartId+",legend_"+this.chartId+",'datasets');");
				}
				if(this.alwaysShowTooltip){
					onAnimationCompleteFunction.append("this.showTooltip(this.dimensions[0], true);");
				}
				onAnimationCompleteFunction.append("}");
				this.options.put("onAnimationComplete", new JSONVariable(onAnimationCompleteFunction.toString()));
			}
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
			if(this.addDownload){
				StringBuffer onAnimationCompleteFunction = new StringBuffer();
				onAnimationCompleteFunction.append("function(){addDownloadButton('"+this.containerId+"');");
				if(this.addLegendInteraction){
					onAnimationCompleteFunction.append("addLegendInteraction(chart_"+this.chartId+",legend_"+this.chartId+",'segments');");
				}
				if(this.alwaysShowTooltip){
					onAnimationCompleteFunction.append("this.showTooltip(this.segments, true);");
				}
				onAnimationCompleteFunction.append("}");
				this.options.put("onAnimationComplete", new JSONVariable(onAnimationCompleteFunction.toString()));
			}
		}
		
		this.options.put("legendTemplate", legend);
		this.options.put("responsive", true);
		this.options.put("yAxisLabel", "Test Label");
		
		//---------------------------CHART DATA BY TYPE
		
		switch (data.getChartType()) {
			case Line:
				chartInit += "Line";
				chartData += temp.toJSONString();
				onclick.append("var holder = document.getElementById('"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("holder.onclick = function(evt){"+System.getProperty("line.separator"));
				onclick.append("    var activePoints = chart_"+this.chartId+".getPointsAtEvent(evt);"+System.getProperty("line.separator"));
				onclick.append("    if(activePoints)showDetailData('"+this.detailServlet+"', activePoints, 'divDetail"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("};"+System.getProperty("line.separator"));
				break;      
			case Bar:       
				chartInit += "Bar";
				chartData += temp.toJSONString();
				onclick.append("var holder = document.getElementById('"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("holder.onclick = function(evt){"+System.getProperty("line.separator"));
				onclick.append("    var activePoints = chart_"+this.chartId+".getBarsAtEvent(evt);"+System.getProperty("line.separator"));
				onclick.append("    if(activePoints)showDetailData('"+this.detailServlet+"', activePoints, 'divDetail"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("};"+System.getProperty("line.separator"));
				break;      
			case StackedBar:       
				chartInit += "StackedBar";
				chartData += temp.toJSONString();
				this.options.put("stacked", "true");
				onclick.append("var holder = document.getElementById('"+this.containerId+"');"+System.getProperty("line.separator"));
				onclick.append("holder.onclick = function(evt){"+System.getProperty("line.separator"));
				onclick.append("    var activePoints = chart_"+this.chartId+".getBarsAtEvent(evt);"+System.getProperty("line.separator"));
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
		
		//---------------------------CHART INITIALIZATION
		
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
		
		//---------------------------LEGEND
		
		if(this.addLegend){
			ret.append("var legend_"+this.chartId+" = document.createElement(\"div\");"+System.getProperty("line.separator"));
			ret.append("legend_"+this.chartId+".setAttribute(\"id\", \""+this.chartId+"_legend\");"+System.getProperty("line.separator"));
			ret.append("document.getElementById(\""+this.containerId+"\").appendChild(legend_"+this.chartId+");");
			ret.append("document.getElementById(\""+this.chartId+"_legend\").innerHTML = chart_"+this.chartId+".generateLegend();");
		}
		
		//---------------------------CLICK FUNCTION
		
		ret.append(onclick.toString()+System.getProperty("line.separator"));
		
		ret.append(script.renderCloseTag()+System.getProperty("line.separator"));
		
		return ret;
	}

	/**
	 * Gets the chart id.
	 *
	 * @return the chart id
	 */
	public String getChartId() {
		return chartId;
	}

	/**
	 * Sets the chart id.
	 *
	 * @param chartId the new chart id
	 */
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}

	/**
	 * Checks if is adds the legend.
	 *
	 * @return true, if is adds the legend
	 */
	public boolean isAddLegend() {
		return addLegend;
	}

	/**
	 * Sets the adds the legend.
	 *
	 * @param addLegend the new adds the legend
	 */
	public void setAddLegend(boolean addLegend) {
		this.addLegend = addLegend;
	}
	
	/**
	 * Sets the option.
	 *
	 * @param option the option
	 * @param value the value
	 */
	@SuppressWarnings("unchecked")
	public void setOption(Object option, Object value) {
		this.options.put(option, value);
	}

	/**
	 * Gets the detail servlet.
	 *
	 * @return the detail servlet
	 */
	public String getDetailServlet() {
		return detailServlet;
	}

	/**
	 * Sets the detail servlet.
	 *
	 * @param detailServlet the new detail servlet
	 */
	public void setDetailServlet(String detailServlet) {
		this.detailServlet = detailServlet;
	}
	
	/**
	 * Gets the detail div.
	 *
	 * @return the detail div
	 */
	public StringBuffer getDetailDiv(){
		StringBuffer ret = new StringBuffer();
		
		ContainerTag div = div().withClass("divDetail").withId("divDetail"+this.containerId).with(
	    		table()
	    		);
		
		ret.append(div);
		
		return ret;
	}

	/**
	 * Checks if is adds the download.
	 *
	 * @return true, if is adds the download
	 */
	public boolean isAddDownload() {
		return addDownload;
	}

	/**
	 * Sets the adds the download.
	 *
	 * @param addDownload the new adds the download
	 */
	public void setAddDownload(boolean addDownload) {
		this.addDownload = addDownload;
	}
	
	/**
	 * The Class JSONVariable.
	 */
	private static class JSONVariable implements JSONAware { // implements JSONAware with com.googlecode.json-simple
		/** The name. */
		private final String name;

		/**
		 * Instantiates a new JSON variable.
		 *
		 * @param name the name
		 */
		public JSONVariable(String name) {
			this.name = name;
		}

		/* (non-Javadoc)
		 * @see org.json.simple.JSONAware#toJSONString()
		 */
		@Override
		public String toJSONString() {
			return name;
		}
	}

	/**
	 * Checks if is adds the legend interaction.
	 *
	 * @return true, if is adds the legend interaction
	 */
	public boolean isAddLegendInteraction() {
		return addLegendInteraction;
	}

	/**
	 * If is set to true, the legend interacts with the chart, when hovered.
	 * 
	 * Only works for Polar, Pie, Doughnut Charts
	 *
	 * @param addLegendInteraction the new adds the legend interaction
	 */
	public void setAddLegendInteraction(boolean addLegendInteraction) {
		this.addLegendInteraction = addLegendInteraction;
	}

	/**
	 * Checks if is always show tooltip.
	 *
	 * @return true, if is always show tooltip
	 */
	public boolean isAlwaysShowTooltip() {
		return alwaysShowTooltip;
	}

	/**
	 * Sets the always show tooltip.
	 * 
	 * Only works for Polar, Pie, Doughnut Charts
	 *
	 * @param alwaysShowTooltip the new always show tooltip
	 */
	public void setAlwaysShowTooltip(boolean alwaysShowTooltip) {
		this.alwaysShowTooltip = alwaysShowTooltip;
	}
	
}

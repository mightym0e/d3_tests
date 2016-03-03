package servlet;

import generator.ScatterPlot3dGenerator;
import j2html.attributes.Attr;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.CSVReader;
import util.ChartJsWrapper;
import util.Data;
import util.LineBarRadarData;
import util.LineBarRadarDataset;
import util.Data.ChartType;
import util.DataSet;
import util.RadarPolarPieData;
import util.RadarPolarPieDataset;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/PuenktlichkeitChartJsServlet")
public class PuenktlichkeitChartJsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PuenktlichkeitChartJsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		// ------------------- Container
		
		String containerName = "chartDiv";
		String containerName1 = "chartDiv1";
		String containerName2 = "chartDiv2";
		
		ChartJsWrapper wrapper = new ChartJsWrapper();
		
		ArrayList<String[]> dataFromCsv = CSVReader.lineChartDataFromPuenktData("D:\\Dokumente\\Uni\\WebApplications\\rails\\d3_tests\\lib\\puenkt_agM_Kw08.csv",false);
		HashMap<String, Integer[]> messPunkte = new HashMap<String, Integer[]>();
		
		int run = 0;
		for(String[] line : dataFromCsv){
			if(run==0){
				
			} else {
				if(messPunkte.get(line[0])==null){
					Integer[] numbers = new Integer[]{0,0,0};
					messPunkte.put(line[0], numbers);
				} 
				if(line[1].contains("Plan")){
					messPunkte.get(line[0])[0]++;
				} else if(line[1].contains("Leicht")){
					messPunkte.get(line[0])[1]++;
				} else if(line[1].contains("Versp")){
					messPunkte.get(line[0])[2]++;
				}
			}
			run++;
		}
		
		// ------------------- Line and Bar for Messstellen
		
		Collection<LineBarRadarDataset> datasets_standard = new ArrayList<LineBarRadarDataset>();
		LineBarRadarDataset standard = new LineBarRadarDataset();
		JSONArray labels = new JSONArray();
		Set<String> keys = messPunkte.keySet();
		
		labels.addAll(keys);
		Collections.sort(labels);
		
		JSONArray dataArr = new JSONArray();
		
		for(Object label : labels){
			
			String label_str = (String) label;
			
			dataArr.add(messPunkte.get(label_str)[0]);
		}

		standard.setLabel("Plan");
		
		standard.setFillColor(0, 250, 0, 0.2f);
		standard.setStrokeColor(0, 250, 0, 0.8f);
		standard.setHighlightFill(100, 250, 100, 0.5f);
		standard.setHighlightStroke(100, 250, 100, 0.5f);
		standard.setPointColor(100, 250, 100, 0.5f);
		standard.setPointHighlightFill(150, 250, 150, 0.5f);
		standard.setPointHighlightStroke(50, 150, 50, 0.5f);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		standard = new LineBarRadarDataset();
		dataArr = new JSONArray();
		
		for(Object label : labels){
			
			String label_str = (String) label;
			
			dataArr.add(messPunkte.get(label_str)[1]);
		}

		standard.setLabel("Leicht Verspätet");
		
		standard.setFillColor(250, 250, 0, 0.2f);
		standard.setStrokeColor(250, 250, 0, 0.8f);
		standard.setHighlightFill(250, 250, 100, 0.5f);
		standard.setHighlightStroke(250, 250, 100, 0.5f);
		standard.setPointColor(250, 250, 100, 0.5f);
		standard.setPointHighlightFill(250, 250, 150, 0.5f);
		standard.setPointHighlightStroke(250, 150, 50, 0.5f);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		standard = new LineBarRadarDataset();
		dataArr = new JSONArray();
		
		for(Object label : labels){
			
			String label_str = (String) label;
			
			dataArr.add(messPunkte.get(label_str)[2]);
		}

		standard.setLabel("Verspätet");
		
		standard.setFillColor(250, 0, 0, 0.2f);
		standard.setStrokeColor(250, 0, 0, 0.8f);
		standard.setHighlightFill(250, 100, 100, 0.5f);
		standard.setHighlightStroke(250, 100, 100, 0.5f);
		standard.setPointColor(250, 100, 100, 0.5f);
		standard.setPointHighlightFill(250, 100, 150, 0.5f);
		standard.setPointHighlightStroke(250, 100, 50, 0.5f);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		// ------------------- Line and Bar for Zug
		
		dataFromCsv = CSVReader.lineChartDataFromPuenktData("D:\\Dokumente\\Uni\\WebApplications\\rails\\d3_tests\\lib\\puenkt_agM_Kw08_1.csv",true);
		HashMap<String, Integer[]> tage = new HashMap<String, Integer[]>();
		JSONArray labels_zug = new JSONArray();
		
		run = 0;
		int runTag = 0;
		for(String[] line : dataFromCsv){
			String tag = line[2];
			tag = tag.replaceAll("\"", "");

			String delta_an = line[6];
			delta_an = delta_an.replaceAll("\"", "");

			String delta_ab = line[9];
			delta_ab = delta_ab.replaceAll("\"", "");

			if(tage.get(tag)==null){
				Integer[] numbers = new Integer[]{0,0,0,0,0,0,0,0,0,0};
				tage.put(tag, numbers);
				runTag = 0;
			} 

			if(delta_an.length()>0){
				String label = line[0].replaceAll("\"", "");
				label += " an";

				if(!labels_zug.contains(label)){
					labels_zug.add(label);
				}
			}
			
			if(delta_ab.length()>0){
				String label = line[0].replaceAll("\"", "");
				label += " ab";

				if(!labels_zug.contains(label)){
					labels_zug.add(label);
				}
			}
			
			if(delta_an.length()>0&&!delta_an.startsWith("-")){

				tage.get(tag)[runTag] = Integer.parseInt(delta_an.split(":")[0])*3600+Integer.parseInt(delta_an.split(":")[1])*60+Integer.parseInt(delta_an.split(":")[2]);
				runTag++;
			} else if (delta_an.startsWith("-")){
				runTag++;
			}
			if(delta_ab.length()>0&&!delta_ab.startsWith("-")){

				tage.get(tag)[runTag] = Integer.parseInt(delta_ab.split(":")[0])*3600+Integer.parseInt(delta_ab.split(":")[1])*60+Integer.parseInt(delta_ab.split(":")[2]);
				runTag++;
			} else if (delta_ab.startsWith("-")){
				runTag++;
			}
			run++;
		}
		
		Collection<LineBarRadarDataset> datasets_standard_zug = new ArrayList<LineBarRadarDataset>();
		LineBarRadarDataset standard_zug = new LineBarRadarDataset();
		JSONArray tage_keys = new JSONArray();
		keys = tage.keySet();

		tage_keys.addAll(keys);
		Collections.sort(tage_keys);

		JSONArray dataArr_zug = new JSONArray();

		for(Integer value : tage.get((String)tage_keys.get(0))){

			dataArr_zug.add(value);
		}

		standard_zug.setLabel((String)tage_keys.get(0));

		standard_zug.setFillColor(0, 250, 0, 0.2f);
		standard_zug.setStrokeColor(0, 250, 0, 0.8f);
		standard_zug.setHighlightFill(100, 250, 100, 0.5f);
		standard_zug.setHighlightStroke(100, 250, 100, 0.5f);
		standard_zug.setPointColor(100, 250, 100, 0.5f);
		standard_zug.setPointHighlightFill(150, 250, 150, 0.5f);
		standard_zug.setPointHighlightStroke(50, 150, 50, 0.5f);
		standard_zug.setData(dataArr_zug);
		datasets_standard_zug.add(standard_zug);

		standard_zug = new LineBarRadarDataset();
		dataArr_zug = new JSONArray();

		for(Integer value : tage.get((String)tage_keys.get(1))){

			dataArr_zug.add(value);
		}

		standard_zug.setLabel((String)tage_keys.get(1));

		standard_zug.setFillColor(250, 250, 0, 0.2f);
		standard_zug.setStrokeColor(250, 250, 0, 0.8f);
		standard_zug.setHighlightFill(250, 250, 100, 0.5f);
		standard_zug.setHighlightStroke(250, 250, 100, 0.5f);
		standard_zug.setPointColor(250, 250, 100, 0.5f);
		standard_zug.setPointHighlightFill(250, 250, 150, 0.5f);
		standard_zug.setPointHighlightStroke(250, 150, 50, 0.5f);
		standard_zug.setData(dataArr_zug);
		datasets_standard_zug.add(standard_zug);

		standard_zug = new LineBarRadarDataset();
		dataArr_zug = new JSONArray();

		for(Integer value : tage.get((String)tage_keys.get(2))){

			dataArr_zug.add(value);
		}

		standard_zug.setLabel((String)tage_keys.get(2));

		standard_zug.setFillColor(250, 0, 0, 0.2f);
		standard_zug.setStrokeColor(250, 0, 0, 0.8f);
		standard_zug.setHighlightFill(250, 100, 100, 0.5f);
		standard_zug.setHighlightStroke(250, 100, 100, 0.5f);
		standard_zug.setPointColor(250, 100, 100, 0.5f);
		standard_zug.setPointHighlightFill(250, 100, 150, 0.5f);
		standard_zug.setPointHighlightStroke(250, 100, 50, 0.5f);
		standard_zug.setData(dataArr_zug);
		datasets_standard_zug.add(standard_zug);
		
		// ------------------- Servlet
		
		response.setContentType(CONTENT_TYPE);
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Max-Age", 0);
	      
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\"?>");
	    out.println(DOC_TYPE);
	    
	    Tag html = html().attr("xmlns", "http://www.w3.org/1999/xhtml").attr("xml:lang","en").attr(Attr.LANG,"en");
	    Tag body = body().withId("mainwindow");
	    
		out.println(html.renderOpenTag());
		out.println(head().renderOpenTag());
		
		out.println(meta().attr(Attr.HTTP_EQUIV, "X-UA-Compatible").attr(Attr.CONTENT, "chrome=1").render());
		out.println(meta().attr(Attr.HTTP_EQUIV, "cache-control").attr(Attr.CONTENT, "no-cache").render());
		out.println(meta().attr(Attr.HTTP_EQUIV, "expires").attr(Attr.CONTENT, "0").render());
		out.println(meta().attr(Attr.HTTP_EQUIV, "pragma").attr(Attr.CONTENT, "no-cache").render());
		out.println(script()
				.withType("text/javascript")
				.withSrc("http://code.jquery.com/jquery-latest.min.js"));
	    out.println(link().withRel("stylesheet").withType("text/css").withHref("css/chartbuilder.css"));
	    out.println(script().withType("text/javascript").withSrc("js/chartjs_functions.js"));
		
		out.println(ChartJsWrapper.getScriptHeader());
		
	    out.println(head().renderCloseTag());
	    
	    out.println(body.renderOpenTag()); 
	    
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(div().withId(containerName).withClass("chart"));
	    out.println(div().withClass("divDetail").withId("divDetail"+containerName).with(
	    		table().with(
	    				tr().withClass("head").with(th("Kurz"),th("Lang"),th("Datum"),th("Abfahrt"))
	    				)
	    		));
	    
	    out.println(div().renderCloseTag());
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(div().withId(containerName1).withClass("chart"));
	    out.println(div().withClass("divDetail").withId("divDetail"+containerName1).with(
	    		table().with(
	    				tr().withClass("head").with(th("Kurz"),th("Lang"),th("Datum"),th("Abfahrt"))
	    				)
	    		));
	    
	    out.println(div().renderCloseTag());
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(div().withId(containerName2).withClass("chart"));
	    out.println(div().withClass("divDetail").withId("divDetail"+containerName2).with(
	    		table().with(
	    				tr().withClass("head").with(th("Kurz"),th("Lang"),th("Datum"),th("Abfahrt"))
	    				)
	    		));
	    
	    out.println(div().renderCloseTag());
		
		//---------------------------Chart
		
		LineBarRadarData data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Line);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName);
		wrapper.setChartId("chart"+containerName);
		
		out.println(wrapper.getJsString());
		
		data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Bar);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName1);
		wrapper.setChartId("chart"+containerName1);
		
		out.println(wrapper.getJsString());
		
		data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Line);
		data_standard.setDatasets(datasets_standard_zug);
		data_standard.setLabels(labels_zug);
		
		wrapper.setData(data_standard);
		wrapper.setAddLegend(true);
		wrapper.setContainerId(containerName2);
		wrapper.setChartId("chart"+containerName2);
		
		out.println(wrapper.getJsString());
	    
		// -------------------
		
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}

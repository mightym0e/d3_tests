package servlet;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.meta;
import static j2html.TagCreator.script;
import static j2html.TagCreator.table;
import j2html.attributes.Attr;
import j2html.tags.Tag;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import util.CSVReader;
import util.ChartJsWrapper;
import util.Data.ChartType;
import util.LineBarRadarData;
import util.LineBarRadarDataset;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/PuenktlichkeitChartJsServlet")
public class PuenktlichkeitChartJsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
	private String currentDir = null;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PuenktlichkeitChartJsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	currentDir = this.getServletContext().getRealPath("");
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
		
		//-------------------- Colors
		
		Color green = new Color(0, 1, 0, 0f);
		Color greenFill = new Color(0, 1, 0, 1f);
		Color greenStroke = new Color(0, 1, 0, 0.8f);
		Color greenHighlightFill = new Color(100, 250, 100, 127);
		Color greenHighlightStroke = new Color(100, 250, 100, 127);
		Color greenPointColor = new Color(100, 250, 100, 127);
		Color greenPointHighlightFill = new Color(150, 250, 150, 127);
		Color greenPointHighlightStroke = new Color(50, 150, 50, 127);
		
		Color red = new Color(1, 0, 0, 0f);
		Color redFill = new Color(1, 0, 0, 1f);
		Color redStroke = new Color(1, 0, 0, 0.8f);
		Color redHighlightFill = new Color(250, 100, 100, 127);
		Color redHighlightStroke = new Color(250, 100, 100, 127);
		Color redPointColor = new Color(250, 100, 100, 127);
		Color redPointHighlightFill = new Color(250, 150, 150, 127);
		Color redPointHighlightStroke = new Color(150, 50, 50, 127);
		
		Color yellow = new Color(1, 1, 0, 0f);
		Color yellowFill = new Color(1, 1, 0, 1f);
		Color yellowStroke = new Color(1, 1, 0, 0.8f);
		Color yellowHighlightFill = new Color(250, 250, 100, 127);
		Color yellowHighlightStroke = new Color(250, 250, 100, 127);
		Color yellowPointColor = new Color(250, 250, 100, 127);
		Color yellowPointHighlightFill = new Color(250, 250, 150, 127);
		Color yellowPointHighlightStroke = new Color(150, 150, 50, 127);
		
		// ------------------- Container
		
		String containerName = "chartDiv";
		String containerName1 = "chartDiv1";
		String containerName2 = "chartDiv2";
		
		ChartJsWrapper wrapper = new ChartJsWrapper();
				
		ArrayList<String[]> dataFromCsv = CSVReader.lineChartDataFromPuenktData(currentDir+"\\puenkt_agM_Kw08.csv",false);
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
		
		standard.setFillColor(greenFill);
		standard.setStrokeColor(greenStroke);
		standard.setHighlightFill(greenHighlightFill);
		standard.setHighlightStroke(greenHighlightStroke);
		standard.setPointColor(greenPointColor);
		standard.setPointHighlightFill(greenPointHighlightFill);
		standard.setPointHighlightStroke(greenPointHighlightStroke);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		standard = new LineBarRadarDataset();
		dataArr = new JSONArray();
		
		for(Object label : labels){
			
			String label_str = (String) label;
			
			dataArr.add(messPunkte.get(label_str)[1]);
		}

		standard.setLabel("Leicht Verspätet");
		
		standard.setFillColor(yellowFill);
		standard.setStrokeColor(yellowStroke);
		standard.setHighlightFill(yellowHighlightFill);
		standard.setHighlightStroke(yellowHighlightStroke);
		standard.setPointColor(yellowPointColor);
		standard.setPointHighlightFill(yellowPointHighlightFill);
		standard.setPointHighlightStroke(yellowPointHighlightStroke);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		standard = new LineBarRadarDataset();
		dataArr = new JSONArray();
		
		for(Object label : labels){
			
			String label_str = (String) label;
			
			dataArr.add(messPunkte.get(label_str)[2]);
		}

		standard.setLabel("Verspätet");
		
		standard.setFillColor(redFill);
		standard.setStrokeColor(redStroke);
		standard.setHighlightFill(redHighlightFill);
		standard.setHighlightStroke(redHighlightStroke);
		standard.setPointColor(redPointColor);
		standard.setPointHighlightFill(redPointHighlightFill);
		standard.setPointHighlightStroke(redPointHighlightStroke);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		// ------------------- Line and Bar for Zug
		
		dataFromCsv = CSVReader.lineChartDataFromPuenktData(currentDir+"\\puenkt_agM_Kw08_1.csv",true);
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
				tage.get(tag)[runTag] = Integer.parseInt(delta_an.split(":")[0])*3600+Integer.parseInt(delta_an.split(":")[1])*60+Integer.parseInt(delta_an.split(":")[2]);
				tage.get(tag)[runTag] = tage.get(tag)[runTag]*-1;
				runTag++;
			}
			if(delta_ab.length()>0&&!delta_ab.startsWith("-")){

				tage.get(tag)[runTag] = Integer.parseInt(delta_ab.split(":")[0])*3600+Integer.parseInt(delta_ab.split(":")[1])*60+Integer.parseInt(delta_ab.split(":")[2]);
				runTag++;
			} else if (delta_ab.startsWith("-")){
				tage.get(tag)[runTag] = Integer.parseInt(delta_ab.split(":")[0])*3600+Integer.parseInt(delta_ab.split(":")[1])*60+Integer.parseInt(delta_ab.split(":")[2]);
				tage.get(tag)[runTag] = tage.get(tag)[runTag]*-1;
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

		standard_zug.setFillColor(green);
		standard_zug.setStrokeColor(greenStroke);
		standard_zug.setHighlightFill(greenHighlightFill);
		standard_zug.setHighlightStroke(greenHighlightStroke);
		standard_zug.setPointColor(greenPointColor);
		standard_zug.setPointHighlightFill(greenPointHighlightFill);
		standard_zug.setPointHighlightStroke(greenPointHighlightStroke);
		standard_zug.setData(dataArr_zug);
		datasets_standard_zug.add(standard_zug);

		standard_zug = new LineBarRadarDataset();
		dataArr_zug = new JSONArray();

		for(Integer value : tage.get((String)tage_keys.get(1))){

			dataArr_zug.add(value);
		}

		standard_zug.setLabel((String)tage_keys.get(1));

		standard_zug.setFillColor(yellow);
		standard_zug.setStrokeColor(yellowStroke);
		standard_zug.setHighlightFill(yellowHighlightFill);
		standard_zug.setHighlightStroke(yellowHighlightStroke);
		standard_zug.setPointColor(yellowPointColor);
		standard_zug.setPointHighlightFill(yellowPointHighlightFill);
		standard_zug.setPointHighlightStroke(yellowPointHighlightStroke);
		standard_zug.setData(dataArr_zug);
		datasets_standard_zug.add(standard_zug);

		standard_zug = new LineBarRadarDataset();
		dataArr_zug = new JSONArray();

		for(Integer value : tage.get((String)tage_keys.get(2))){

			dataArr_zug.add(value);
		}

		standard_zug.setLabel((String)tage_keys.get(2));

		standard_zug.setFillColor(red);
		standard_zug.setStrokeColor(redStroke);
		standard_zug.setHighlightFill(redHighlightFill);
		standard_zug.setHighlightStroke(redHighlightStroke);
		standard_zug.setPointColor(redPointColor);
		standard_zug.setPointHighlightFill(redPointHighlightFill);
		standard_zug.setPointHighlightStroke(redPointHighlightStroke);
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
		
		out.println(ChartJsWrapper.getScriptHeader());
				
	    out.println(head().renderCloseTag());
	    
	    out.println(body.renderOpenTag()); 
	    
	    wrapper.setAddLegend(true);
	    wrapper.setAddLegendInteraction(true);
	    wrapper.setAddDownload(true);
	    wrapper.setAlwaysShowTooltip(true);
	    //---------------------------Chart1
	    
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    LineBarRadarData data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Line);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName);
		wrapper.setAddLegend(true);
		wrapper.setChartId("chart"+containerName);
	    
	    out.println(div().withId(containerName).withClass("chart"));
	    out.println(wrapper.getDetailDiv());
	    
	    out.println(wrapper.getJsString());
	    
	    out.println(div().renderCloseTag());
	    
	    //---------------------------Chart2
	    
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(div().withId(containerName1).withClass("chart"));
	    
	    data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.StackedBar);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName1);
		wrapper.setChartId("chart"+containerName1);
	    
	    out.println(wrapper.getDetailDiv());
	    
	    out.println(wrapper.getJsString());
	    
	  //---------------------------Chart3
	    
	    out.println(div().renderCloseTag());
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(div().withId(containerName2).withClass("chart"));
	    
	    data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Line);
		data_standard.setDatasets(datasets_standard_zug);
		data_standard.setLabels(labels_zug);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName2);
		wrapper.setChartId("chart"+containerName2);
	    
	    out.println(wrapper.getDetailDiv());
	    
	    out.println(wrapper.getJsString());
	    
	    out.println(div().renderCloseTag());
	    
		// -------------------
		
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}

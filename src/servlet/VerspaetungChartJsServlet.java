package servlet;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.meta;
import static j2html.TagCreator.script;
import static j2html.TagCreator.table;
import static j2html.TagCreator.span;
import static j2html.TagCreator.button;
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
import util.RadarPolarPieData;
import util.RadarPolarPieDataset;
import util.Data.ChartType;
import util.LineBarRadarData;
import util.LineBarRadarDataset;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/VerspaetungChartJsServlet")
public class VerspaetungChartJsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
	private String currentDir = null;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerspaetungChartJsServlet() {
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
		
		
		// ------------------- Container
		
		String containerName = "chartDiv";
		String containerName1 = "chartDiv1";
		String containerName2 = "chartDiv2";
		
		ChartJsWrapper wrapper = new ChartJsWrapper();
		
		ArrayList<String[]> dataFromCsv = CSVReader.barChartDataFromPuenktData(currentDir+"\\verspaetungsursachen.csv");
		HashMap<String, Integer[]> ursachen = new HashMap<String, Integer[]>();
		
		for(String[] line : dataFromCsv){
			
			String dataPoint = line[3] + " - " + line[4];
			
			if(ursachen.get(dataPoint)==null){
				Integer[] numbers = new Integer[]{0,0};
				ursachen.put(dataPoint, numbers);
			} 
			Integer verspMin = 0;

			if(line[8].length()>0){
				verspMin = Integer.parseInt(line[8].trim());
			}

			ursachen.get(dataPoint)[0]++;
			ursachen.get(dataPoint)[1] += verspMin;
		}
		
		
		Collection<RadarPolarPieDataset> datasets = new ArrayList<RadarPolarPieDataset>();
		Collection<RadarPolarPieDataset> datasetAbs = new ArrayList<RadarPolarPieDataset>();
		RadarPolarPieDataset set = null;
		
		//-------------------- Colors
		
		ArrayList<Color> colors = (ArrayList<Color>)ChartJsWrapper.generateColors(ursachen.size()); 
		
		//-------------------- Data
		
		int count = 0;
		for(String key : ursachen.keySet()){
			
			set = new RadarPolarPieDataset();
			set.setColor(colors.get(count));
			set.setHighlight(colors.get(count).brighter());
			set.setLabel(key);
			set.setValue(ursachen.get(key)[0]);
			datasetAbs.add(set);	
			
			count++;
		}
		
		count = 0;
		for(String key : ursachen.keySet()){
			
			set = new RadarPolarPieDataset();
			set.setColor(colors.get(count));
			set.setHighlight(colors.get(count).brighter());
			set.setLabel(key);
			set.setValue(ursachen.get(key)[1]);
			datasets.add(set);	
			
			count++;
		}
		
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
	    out.println(link().withRel("stylesheet").withType("text/css").withHref("css/chartbuilder.css"));
		
		out.println(ChartJsWrapper.getScriptHeader());
		
	    out.println(head().renderCloseTag());
	    
	    out.println(body.renderOpenTag()); 
	    
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(span().withText("Minuten"));
	    out.println(div().withId(containerName).withClass("chart"));
	    out.println(div().withClass("divDetail").withId("divDetail"+containerName).with(
	    		table().with(
	    				//tr().withClass("head").with(th("Kurz"),th("Lang"),th("Datum"),th("Abfahrt"))
	    				)
	    		));
	    
	    out.println(div().renderCloseTag());
	    out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    //---------------------------Chart1
	    
	    wrapper.setAddLegend(true);
	    wrapper.setAddDownload(true);
	    wrapper.setAddLegendInteraction(true);
	    RadarPolarPieData data = new RadarPolarPieData();
		data.setChartType(ChartType.Pie);
		data.setDatasets(datasets);
		
		wrapper.setData(data);
		wrapper.setContainerId(containerName);
		wrapper.setChartId("chart"+containerName);
		
	    out.println(span().withText("Absolut"));
	    out.println(div().withId(containerName1).withClass("chart"));
		
	    out.println(wrapper.getDetailDiv());
	    
		out.println(wrapper.getJsString());
		
		out.println(div().renderCloseTag());
	    
		//---------------------------Chart2
		
		data = new RadarPolarPieData();
		data.setChartType(ChartType.Pie);
		data.setDatasets(datasetAbs);
		
		wrapper.setData(data);
		wrapper.setContainerId(containerName1);
		wrapper.setChartId("chart"+containerName1);
		
		out.println(div().withClass("mainDiv").renderOpenTag()); 
	    
	    out.println(div().withId(containerName2).withClass("chart"));
	    
	    out.println(wrapper.getDetailDiv());
		
		out.println(wrapper.getJsString());		
		
	    out.println(div().renderCloseTag());

		// -------------------
		
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}

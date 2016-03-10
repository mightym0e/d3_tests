package servlet;

import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.meta;
import j2html.attributes.Attr;
import j2html.tags.Tag;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import util.ChartJsWrapper;
import util.Data.ChartType;
import util.LineBarRadarData;
import util.LineBarRadarDataset;
import util.RadarPolarPieData;
import util.RadarPolarPieDataset;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/ChartJsServlet")
public class ChartJsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartJsServlet() {
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		// ------------------- Container
		
		String containerName = "chartDiv";
		String containerName1 = "chartDiv1";
		String containerName2 = "chartDiv2";
		String containerName3 = "chartDiv3";
		String containerName4 = "chartDiv4";
		String containerName5 = "chartDiv5";
		String containerName6 = "chartDiv6";
		
		// ------------------- Pie etc. Diagramm Data
		
		ChartJsWrapper wrapper = new ChartJsWrapper();
		
		Collection<RadarPolarPieDataset> datasets = new ArrayList<RadarPolarPieDataset>();
		RadarPolarPieDataset set = new RadarPolarPieDataset();
		set.setColor(250, 0, 0, 1);
		set.setHighlight(250, 100, 100, 1);
		set.setLabel("Nummer 1");
		set.setValue(300);
		datasets.add(set);
		set = new RadarPolarPieDataset();
		set.setColor(0, 250, 0, 1);
		set.setHighlight(100, 250, 100, 1);
		set.setLabel("Nummer 2");
		set.setValue(100);
		datasets.add(set);
		set = new RadarPolarPieDataset();
		set.setColor(0, 0, 250, 1);
		set.setHighlight(100, 100, 250, 1);
		set.setLabel("Nummer 3");
		set.setValue(50);
		datasets.add(set);
		
		// ------------------- Line etc. Diagramm Data
		
		Collection<LineBarRadarDataset> datasets_standard = new ArrayList<LineBarRadarDataset>();
		LineBarRadarDataset standard = new LineBarRadarDataset();
		JSONArray labels = new JSONArray();
		labels.addAll(Arrays.asList("Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag","Sonntag"));
		
		JSONArray dataArr = new JSONArray();
		
		dataArr.add(112);
		dataArr.add(11);
		dataArr.add(68);
		dataArr.add(61);
		dataArr.add(86);
		dataArr.add(23);
		dataArr.add(6);

		standard.setLabel("48107");
		
		standard.setFillColor(250, 0, 0, 1);
		standard.setStrokeColor(250, 0, 0, 1);
		standard.setHighlightFill(250, 100, 100, 0.5f);
		standard.setHighlightStroke(250, 100, 100, 0.5f);
		standard.setPointColor(250, 100, 100, 0.5f);
		standard.setPointHighlightFill(250, 150, 150, 0.5f);
		standard.setPointHighlightStroke(150, 50, 50, 0.5f);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
		standard = new LineBarRadarDataset();
		dataArr = new JSONArray();
		
		dataArr.add(11);
		dataArr.add(6);
		dataArr.add(89);
		dataArr.add(14);
		dataArr.add(75);
		dataArr.add(7);
		dataArr.add(68);

		standard.setLabel("42661");
		
		standard.setFillColor(0, 250, 0, 1);
		standard.setStrokeColor(0, 250, 0, 1);
		standard.setHighlightFill(100, 250, 100, 0.5f);
		standard.setHighlightStroke(100, 250, 100, 0.5f);
		standard.setPointColor(100, 250, 100, 0.5f);
		standard.setPointHighlightFill(150, 250, 150, 0.5f);
		standard.setPointHighlightStroke(50, 150, 50, 0.5f);
		standard.setData(dataArr);
		datasets_standard.add(standard);
		
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
	    
	    out.println(div().withId(containerName).withClass("chart"));
	    out.println(div().withId(containerName1).withClass("chart"));
	    out.println(div().withId(containerName2).withClass("chart"));
	    out.println(br());
		out.println(br());
		out.println(br());
		out.println(br());
		out.println(br());
		out.println(br());
	    out.println(div().withId(containerName3).withClass("chart"));
	    out.println(div().withId(containerName4).withClass("chart"));
	    out.println(div().withId(containerName5).withClass("chart"));
	    out.println(div().withId(containerName6).withClass("chart"));
	    
	    //---------------------------------PIE POLAR DOUGH
	    
	    wrapper.setAddLegend(true);
	    RadarPolarPieData data = new RadarPolarPieData();
		data.setChartType(ChartType.Pie);
		data.setDatasets(datasets);
		
		wrapper.setData(data);
		wrapper.setContainerId(containerName);
		wrapper.setChartId("chart"+containerName);
		
		out.println(wrapper.getJsString());
		
		data = new RadarPolarPieData();
		data.setChartType(ChartType.Polar);
		data.setDatasets(datasets);
		
		wrapper.setData(data);
		wrapper.setContainerId(containerName1);
		wrapper.setChartId("chart"+containerName1);
		
		out.println(wrapper.getJsString());
		
		data = new RadarPolarPieData();
		data.setChartType(ChartType.Doughnut);
		data.setDatasets(datasets);
		
		wrapper.setData(data);
		wrapper.setContainerId(containerName2);
		wrapper.setChartId("chart"+containerName2);
		
		out.println(wrapper.getJsString());
		
		//---------------------------STANDARD
		
		LineBarRadarData data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Line);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName3);
		wrapper.setChartId("chart"+containerName3);
		
		out.println(wrapper.getJsString());
		
		data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Bar);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName4);
		wrapper.setChartId("chart"+containerName4);
		
		out.println(wrapper.getJsString());
		
		data_standard = new LineBarRadarData();
		data_standard.setChartType(ChartType.Radar);
		data_standard.setDatasets(datasets_standard);
		data_standard.setLabels(labels);
		
		wrapper.setData(data_standard);
		wrapper.setContainerId(containerName5);
		wrapper.setChartId("chart"+containerName5);
		
		out.println(wrapper.getJsString());
	    
		// -------------------
		
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}

package servlet;

import generator.ScatterPlot3dGenerator;
import j2html.attributes.Attr;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ChartJsWrapper;
import util.Data;
import util.Data.ChartType;
import util.DataSet;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String containerName = "chartDiv";
		
		ChartJsWrapper wrapper = new ChartJsWrapper();
		
		Collection<DataSet> datasets = new ArrayList<DataSet>();
		RadarPolarPieDataset set = new RadarPolarPieDataset();
		set.setColor(250, 0, 0, 1);
		set.setHighlight(250, 100, 100, 1);
		set.setLabel("Nummer 1");
		datasets.add(set);
		set = new RadarPolarPieDataset();
		set.setColor(0, 250, 0, 1);
		set.setHighlight(100, 250, 100, 1);
		set.setLabel("Nummer 2");
		datasets.add(set);
		set = new RadarPolarPieDataset();
		set.setColor(0, 0, 250, 1);
		set.setHighlight(100, 100, 250, 1);
		set.setLabel("Nummer 3");
		datasets.add(set);
		
		Data data = new Data();
		data.setChartType(ChartType.Pie);
		data.setDatasets(datasets);
		
		wrapper.setData(data);
		wrapper.setContainerName(containerName);
		
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
	    
		out.println(ChartJsWrapper.getScriptHeader());
		
	    out.println(head().renderCloseTag());
	    
	    out.println(body.renderOpenTag()); 
	    
	    out.println(div().withId(containerName));
	    
	    out.println(wrapper.getJsString());
	    
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}

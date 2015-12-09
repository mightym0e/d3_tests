package servlet;

import generator.ScatterPlot3dGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CSVReader;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/ScatterServlet")
public class ScatterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScatterServlet() {
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
		HttpSession s = request.getSession(false);
		
		ScatterPlot3dGenerator generator = new ScatterPlot3dGenerator();
		
		response.setContentType(CONTENT_TYPE);
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Max-Age", 0);
	      
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\"?>");
	    out.println(DOC_TYPE);
		
	    out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
	    out.println("<head>");
	    out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"chrome=1\" />");
	    out.println("<meta http-equiv=\"cache-control\" content=\"no-cache\" />");
	    out.println("<meta http-equiv=\"expires\" content=\"0\" />");
	    out.println("<meta http-equiv=\"pragma\" content=\"no-cache\" />");
	    
	    out.println(generator.getD3CSSHeaderStr().toString());
	    
	    out.println("</head>");
	    out.println ("<body id=\"mainwindow\">"); 
	    
	    out.println(generator.getD3JsHeaderStr());
	    
	    out.println(generator.getFilterStr());
	    
	    out.println("<div id=\"divPlot\"></div>");
	    
	    generator.setData("D:\\Projekte\\IC\\D3\\export.csv");
	    out.println(generator.getJsDataStr()!=null?generator.getJsDataStr().toString():"");
	    
	    out.println("<script type=\"text/javascript\" src=\"js/custom_scatter.js\"></script>");
	    
	    out.print(	"</body>" + 
	    			"</html>");
	}

}

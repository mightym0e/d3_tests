package servlet;

import static j2html.TagCreator.meta;
import j2html.attributes.Attr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;

import util.CSVReader;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/AjaxDetailServlet")
public class AjaxDetailServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxDetailServlet() {
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
		
		response.setContentType(CONTENT_TYPE);
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Max-Age", 0);
		
		String label = request.getParameter("dataset");
		
		JSONArray arr = new JSONArray();
		
		if(label!=null && label.length()>0){
			ArrayList<String[]> dataFromCsv = CSVReader.lineChartDataFromPuenktData("D:\\Dokumente\\Uni\\WebApplications\\rails\\d3_tests\\lib\\puenkt_agM_Kw08.csv",true);
			HashMap<String, Integer[]> messPunkte = new HashMap<String, Integer[]>();
			
			int run = 0;
			for(String[] line : dataFromCsv){
				if(run==0){
					
				} else {
					if(line[0].equals(label)){
						JSONArray inner = new JSONArray();
						
						inner.add(line[0]);
						inner.add(line[1]);
						inner.add(line[2]);
						inner.add(line[12]);
						
						arr.add(inner);
					} 
				}
				run++;
			}
		}
		
		response.getWriter().print(arr.toJSONString());
	    
	}
                                                                                              
}
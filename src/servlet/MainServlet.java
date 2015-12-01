package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
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
	    out.println("<meta http-equiv=\"cache-control\" content=\"no-cache\" />");
	    out.println("<meta http-equiv=\"expires\" content=\"0\" />");
	    out.println("<meta http-equiv=\"pragma\" content=\"no-cache\" />");
	    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\" />");
	    out.println("</head>");
	    out.println ("<body id=\"mainwindow\">"); 
	    out.println("<script type=\"text/javascript\" src=\"http://d3js.org/d3.v3.min.js\"></script>");
	    out.println("<script type=\"text/javascript\" src=\"http://x3dom.org/x3dom/dist/x3dom-full.js\"></script>");
	    out.println("<script type=\"text/javascript\" src=\"scatter_plot_3d_demo.js\"></script>");
	    	    
	    out.print(	"</body>\r\n" + 
	    			"</html>");
	    
	}
                                                                                              
}
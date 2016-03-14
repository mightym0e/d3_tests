package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import util.CSVReader;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/AjaxDetailServlet")
public class AjaxDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private String currentDir = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxDetailServlet() {
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONArray inner;

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Max-Age", 0);
		
		if(request.getParameter("dataset")==null)return;

		String label = request.getParameter("dataset").replace("\"", "");
		String datasetLabel = request.getParameter("datasetLabel").replace("\"", "");

		JSONArray arr = new JSONArray();

		if(label!=null && label.length()>0 && !label.contains("ab") && !label.contains("an")){
			ArrayList<String[]> dataFromCsv = CSVReader.lineChartDataFromPuenktData(currentDir+"\\puenkt_agM_Kw08.csv",true);

			inner = new JSONArray();

			inner.add("Kurz");
			inner.add("Lang");
			inner.add("Datum");
			inner.add("Abfahrt");

			arr.add(inner);

			int run = 0;
			for(String[] line : dataFromCsv){
				if(run==0){

				} else {
					if(line[0].replace("\"", "").equals(label)){
						inner = new JSONArray();

						inner.add(line[0]);
						inner.add(line[1]);
						inner.add(line[2]);
						inner.add(line[12]);

						arr.add(inner);
					} 
				}
				run++;
			}
		} else {
			//			label = label.replace(" an", "").replace(" ab", "");

			ArrayList<String[]> dataFromCsv = CSVReader.lineChartDataFromPuenktData(currentDir+"\\puenkt_agM_Kw08_1.csv",true);

			inner = new JSONArray();

			inner.add("Kurz");
			inner.add("Lang");
			inner.add("Datum");

			if(label.contains(" an")){

				inner.add("Delta an");

				arr.add(inner);

			} else {

				inner.add("Delta ab");

				arr.add(inner);
			}

			for(String[] line : dataFromCsv){
				if(line[0].equals(label.replace(" an", "").replace(" ab", ""))){
					if (label.contains(" an")) {
						inner = new JSONArray();
						inner.add(line[0]);
						inner.add(line[1]);
						inner.add(line[2]);
						inner.add(line[6]);
						arr.add(inner);
					} else if (label.contains(" ab")) {
						inner = new JSONArray();
						inner.add(line[0]);
						inner.add(line[1]);
						inner.add(line[2]);
						inner.add(line[9]);
						arr.add(inner);
					}
				}
			}
		}

		response.getWriter().print(arr.toJSONString());

	}

}
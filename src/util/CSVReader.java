package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.IllegalFormatFlagsException;

public class CSVReader {

  public static void main(String[] args) {

	  CSVReader obj = new CSVReader();
	  obj.run("");

  }

  public ArrayList<String[]> run(String filename) {
	ArrayList<String[]> ret = new ArrayList<String[]>();

	String csvFile = filename;
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = "\t";

	try {

		br = new BufferedReader(new FileReader(csvFile));
//		line = br.readLine();
		
		while ((line = br.readLine()) != null) {

			String[] data_temp = line.split(cvsSplitBy);

			String[] data = new String[]{data_temp[0],data_temp[1],data_temp[2],data_temp[3]};
			
			ret.add(data);

		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	System.out.println("Done");
	return ret;
  }
  
  public static ArrayList<String[]> lineChartDataFromPuenktData(String filename, boolean getDetail) {
		ArrayList<String[]> ret = new ArrayList<String[]>();

		String csvFile = filename;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			int line_number = 0;
			
			while ((line = br.readLine()) != null) {

				if (line_number>7) {
					String[] data_temp = line.split(cvsSplitBy);
					if (data_temp.length>12) {
						String[] data = null;
						if(getDetail)data = new String[] { data_temp[0], data_temp[1], data_temp[2], data_temp[3], data_temp[4], data_temp[5], data_temp[6], data_temp[7], data_temp[8], data_temp[9], data_temp[10], data_temp[11], data_temp[12] };
						else data = new String[] { data_temp[0], data_temp[12] };
						ret.add(data);
					}
				}
				
				line_number++;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		return ret;
	  }
  
  public static ArrayList<String[]> barChartDataFromPuenktData(String filename) {
		ArrayList<String[]> ret = new ArrayList<String[]>();

		String csvFile = filename;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			int line_number = 0;
			
			while ((line = br.readLine()) != null) {

				if (line_number>5) {
					String[] data_temp = line.split(cvsSplitBy);
					if (data_temp.length>9) {
						String[] data = null;
						data = new String[] { data_temp[0], data_temp[1], data_temp[2], data_temp[3], data_temp[4], data_temp[5], data_temp[6], data_temp[7], data_temp[8], data_temp[9] };
						ret.add(data);
					}
				}
				
				line_number++;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		return ret;
	  }
  
  public StringBuffer getJsString(ArrayList<String[]> input) throws IllegalFormatException {
	  StringBuffer ret = new StringBuffer();
	  ret.append("<script type=\"text/javascript\">\n");
	  ret.append("var rowsMap = {};\n");
	  String[] header = {"x","y","z"};
	  Double[] scales = {0d,0d,0d};
	  
	  try {
		  boolean first = true;
		  for(String[] csvRow : input){
			  if(first){
				  first = false;
				  ret.append("var header = {x: '"+csvRow[1]+"', y: '"+csvRow[2]+"', z: '"+csvRow[3]+"', data: '"+csvRow[0]+"'};");
				  header[0] = csvRow[1];
				  header[1] = csvRow[2];
				  header[2] = csvRow[3];
			  } else {
				  ret.append("if('"+csvRow[0]+"' in rowsMap){ "
				  		   + "rowsMap['"+csvRow[0]+"'].push({'"+header[0]+"': "+csvRow[1]+", '"+header[1]+"': "+csvRow[2]+", '"+header[2]+"': "+csvRow[3]+"});"
				  		   + "}"
				  		   + "else {"
				  		   + "var rowInside = [];"
				  		   + "rowInside.push({'"+header[0]+"': "+csvRow[1]+", '"+header[1]+"': "+csvRow[2]+", '"+header[2]+"': "+csvRow[3]+"});"
				  		   + "rowsMap['"+csvRow[0]+"'] = rowInside;"
				  		   + "}");
				  if(scales[0]<Double.parseDouble(csvRow[1]))scales[0]=Double.parseDouble(csvRow[1]);
				  if(scales[1]<Double.parseDouble(csvRow[2]))scales[1]=Double.parseDouble(csvRow[2]);
				  if(scales[2]<Double.parseDouble(csvRow[3]))scales[2]=Double.parseDouble(csvRow[3]);
			  }
			  ret.append("var data_scales = {x: "+scales[0]+",y: "+scales[1]+",z: "+scales[2]+"};");
		  }
	  } catch (Exception e) {
		  throw new IllegalFormatFlagsException("Daten m�ssen 3-Dimensional formatiert sein!");
	  }

	  ret.append("</script>");
	  return ret;
  }

}
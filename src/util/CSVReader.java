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
	String cvsSplitBy = ";";

	try {

		br = new BufferedReader(new FileReader(csvFile));
		br.readLine();
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
  
  public StringBuffer getJsString(ArrayList<String[]> input) throws IllegalFormatException {
	  StringBuffer ret = new StringBuffer();
	  ret.append("<script type=\"text/javascript\">\n");
	  ret.append("var rowsMap = {};\n");
	  
	  try {
		  for(String[] arr : input){
			  ret.append("if('"+arr[0]+"' in rowsMap){ "
			  		   + "rowsMap['"+arr[0]+"'].push({x: "+arr[1]+", y: "+arr[2]+", z: "+arr[3]+"});"
			  		   + "}"
			  		   + "else {"
			  		   + "var rowInside = [];"
			  		   + "rowInside.push({x: "+arr[1]+", y: "+arr[2]+", z: "+arr[3]+"});"
			  		   + "rowsMap['"+arr[0]+"'] = rowInside;"
			  		   + "}");
			  
		  }
	  } catch (Exception e) {
		  throw new IllegalFormatFlagsException("Daten müssen 3-Dimensional formatiert sein!");
	  }

	  ret.append("</script>");
	  return ret;
  }
  
//  public StringBuffer getJsString(ArrayList<String[]> input) throws IllegalFormatException {
//	  StringBuffer ret = new StringBuffer();
//	  ret.append("<script type=\"text/javascript\">\n");
//	  ret.append("var data1 = [];\n");
//	  ret.append("var data2 = [];\n");
//	  ret.append("var data3 = [];\n");
//	  
//	  try {
//		  for(String[] arr : input){
//			  ret.append("data1.push('"+arr[0]+"');\n");
//			  ret.append("data2.push('"+arr[1]+"');\n");
//			  ret.append("data3.push('"+arr[2]+"');\n");
//		  }
//	  } catch (Exception e) {
//		  throw new IllegalFormatFlagsException("Daten müssen 3-Dimensional formatiert sein!");
//	  }
//
//	  ret.append("</script>");
//	  return ret;
//  }

}
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
		while ((line = br.readLine()) != null) {

			String[] data_temp = line.split(cvsSplitBy);

			String[] data = new String[]{data_temp[1],data_temp[2],data_temp[3]};
			
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
  
  public StringBuffer getJsString(ArrayList<String[]> input){
	  StringBuffer ret = new StringBuffer();
	  ret.append("<script type=\"text/javascript\">\n");
	  ret.append("var data1 = [];\n");
	  ret.append("var data2 = [];\n");
	  ret.append("var data3 = [];\n");
	  
	  for(String[] arr : input){
		  ret.append("data1.push('"+arr[0]+"');\n");
		  ret.append("data2.push('"+arr[1]+"');\n");
		  ret.append("data3.push('"+arr[2]+"');\n");
	  }
	  
	  ret.append("</script>");
	  return ret;
  }

}
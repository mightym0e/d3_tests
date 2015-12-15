package generator;

import j2html.attributes.Attr;
import j2html.tags.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Vector;

import static j2html.TagCreator.*;
import util.CSVReader;

public class ScatterPlot3dGenerator implements Generator {

	private HashMap<String, Vector<Object>> data;
	private StringBuffer jsDataStr;
	
	@Override
	public void setData(HashMap<String, Vector<Object>> data) {
		this.data = data;
	}

	@Override
	public Vector<Tag> getD3JsHeaderStr() {
		
		Vector<Tag> ret = new Vector<Tag>();
		
		ret.add(script()
				.withType("text/javascript")
				.withSrc("http://code.jquery.com/jquery-latest.min.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("http://d3js.org/d3.v3.min.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("http://x3dom.org/x3dom/dist/x3dom-full.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("js/scatter_plot_3d_demo.js"));
		
		return ret;
	}

	@Override
	public StringBuffer getD3FunctionsStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag getD3CSSHeaderStr() {

		return link().withRel("stylesheet").withType("text/css").withHref("http://www.x3dom.org/download/dev/x3dom.css");
		
	}

	@Override
	public void setData(String fileName) throws IllegalFormatException {
		CSVReader reader = new CSVReader();
	    
	    ArrayList<String[]> data = reader.run(fileName);
	    
	    this.jsDataStr = reader.getJsString(data);
	}

	@Override
	public StringBuffer getJsDataStr() {
		return this.jsDataStr;
	}

	@Override
	public Tag getFilterStr() {
		
		return div().withId("selects").with
				(
						select().withClass("axis_select").withId("x").with
							(
								option().attr(Attr.SELECTED, Attr.SELECTED).withValue("2").withText("Dataset 2"),
								option().withValue("3").withText("Dataset 3")
							),
						select().withClass("axis_select").withId("z").with
							(
								option().withValue("2").withText("Dataset 2"),
								option().attr(Attr.SELECTED, Attr.SELECTED).withValue("3").withText("Dataset 3")
							)
				);
	}

}

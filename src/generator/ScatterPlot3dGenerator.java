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
		ret.add(script()
				.withType("text/javascript")
				.withSrc("http://gabelerner.github.io/canvg/rgbcolor.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("http://gabelerner.github.io/canvg/StackBlur.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("http://gabelerner.github.io/canvg/canvg.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("js/jquery.pnglink.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("js/FileSaver.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("js/svgConversion.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("js/legend.js"));
		ret.add(script()
				.withType("text/javascript")
				.withSrc("js/detail.js"));
		return ret;
	}

	@Override
	public StringBuffer getD3FunctionsStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Tag> getD3CSSHeaderStr() {
		
		Vector<Tag> ret = new Vector<Tag>();
		
		ret.add(link().withRel("stylesheet").withType("text/css").withHref("http://www.x3dom.org/download/dev/x3dom.css"));
		ret.add(link().withRel("stylesheet").withType("text/css").withHref("css/main.css"));
		ret.add(link().withRel("stylesheet").withType("text/css").withHref("css/detail.css"));

		return ret;
		
	}

	@Override
	public void setData(String fileName, String delimiter, int rowBegin, int[] columns, boolean firstRowTitle) throws IllegalFormatException {
		if(columns.length!=3)throw new IllegalArgumentException("Die Spaltendefinition muss mindestens 3 Elemente besitzen.");
		
		CSVReader reader = new CSVReader();
	    
	    ArrayList<String[]> data = reader.readDataFromCsv(fileName, delimiter, rowBegin, columns, firstRowTitle);
	    
	    this.jsDataStr = reader.getJsString(data);
	}

	@Override
	public StringBuffer getJsDataStr() {
		return this.jsDataStr;
	}

	@Override
	public Tag getFilterStr() {
		
		return div().withId("filterBox").with(div().withId("selects").with
				(
						label().withText("x-Achse: "),
						select().withClass("axis_select").withId("x").with
							(
								option().withValue("x").attr(Attr.SELECTED, Attr.SELECTED).withText("Dataset 1"),
								option().withValue("y").withText("Dataset 2"),
								option().withValue("z").withText("Dataset 3")
							),
						label().withText("y-Achse: "),	
						select().withClass("axis_select").withId("y").with
							(
								option().withValue("x").withText("Dataset 1"),
								option().withValue("y").attr(Attr.SELECTED, Attr.SELECTED).withText("Dataset 2"),
								option().withValue("z").withText("Dataset 3")
							),
						label().withText("z-Achse: "),	
						select().withClass("axis_select").withId("z").with
							(
								option().withValue("x").withText("Dataset 1"),
								option().withValue("y").withText("Dataset 2"),
								option().withValue("z").attr(Attr.SELECTED, Attr.SELECTED).withText("Dataset 3")
							)
				)
				,div().withId("filterDetail").with(
						label().withText("Detail Modus: "),
						select().withClass("mode_select").withId("mode_select").with
							(
								option().withValue("line").attr(Attr.SELECTED, Attr.SELECTED).withText("Linie"),
								option().withValue("dot").withText("Punkt")
							))
				,button().withText("download").withId("exportSvg"));
	}

}

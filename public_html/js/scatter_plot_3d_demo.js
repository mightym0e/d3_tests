function scatterPlot3d( parent )
{
	var x3d = parent  
	.append("x3d")
	.style( "width", parseInt(parent.style("width"))+"px" )
	.style( "height", parseInt(parent.style("height"))+"px" )
	.style( "border", "none" )

	var scene = x3d.append("scene")

	scene.append("orthoviewpoint")
	.attr( "centerOfRotation", [5, 5, 5])
	.attr( "fieldOfView", [-25, -25, 35, 25])
	.attr( "orientation", [-0.5, 1, 0.2, 1.12*Math.PI/4])
	.attr( "position", [8, 4, 15])
	.attr( "zNear", -15 )

	//var rows = initializeDataGrid(data1,data2,data3,false);
	var axisRange = [[0, data_scales[0]],[0, data_scales[1]],[0, data_scales[2]]];
	var scales = [];
	var initialDuration = 0;
	var defaultDuration = 800;
	var ease = 'linear';
	var time = 0;
	var axisKeys = [header["x"], header["y"], header["z"]];
	var labelFontSize = 0.8;

	// Helper functions for initializeAxis() and drawAxis()
	function axisName( name, axisIndex ) {
		return [header["x"],header["y"],header["z"]][axisIndex] + name;
	}

	function constVecWithAxisValue( otherValue, axisValue, axisIndex ) {
		var result = [otherValue, otherValue, otherValue];
		result[axisIndex] = axisValue;
		return result;
	}

	// Used to make 2d elements visible
	function makeSolid(selection, color) {
		selection.append("appearance")
		.append("material")
		.attr("diffuseColor", color||"black")
		return selection;
	}

	// Initialize the axes lines and labels.
	function initializePlot() {
		initializeAxis(0);
		initializeAxis(1);
		initializeAxis(2);
	}

	function initializeAxis( axisIndex )
	{
		var key = axisKeys[axisIndex];
		drawAxis( axisIndex, key, initialDuration );

		var scaleMin = axisRange[axisIndex][0];
		var scaleMax = axisRange[axisIndex][1];

		// the axis line
		var newAxisLine = scene.append("transform")
		.attr("class", axisName("Axis", axisIndex))
		.attr("rotation", ([[0,0,0,0],[0,0,1,Math.PI/2],[0,1,0,-Math.PI/2]][axisIndex]))
		.append("shape");
		newAxisLine
		.append("appearance")
		.append("material")
		.attr("emissiveColor", "lightgray");
		newAxisLine
		.append("polyline2d")
		// Line drawn along y axis does not render in Firefox, so draw one
		// along the x axis instead and rotate it (above).
		.attr("lineSegments", "0 0," + scaleMax + " 0");

		// axis labels
		var newAxisLabel = scene.append("transform")
		.attr("class", axisName("AxisLabel", axisIndex))
		.attr("translation", constVecWithAxisValue( 0, 23, axisIndex ));

		var newAxisLabelShape = newAxisLabel
		.append("billboard")
		.attr("axisOfRotation", "0 0 0") // face viewer
		.append("shape")
		.call(makeSolid);

		newAxisLabelShape
		.append("text")
		.attr("class", axisName("AxisLabelText", axisIndex))
		.attr("solid", "true")
		.attr("string", key)
		.append("fontstyle")
		.attr("size", labelFontSize)
		.attr("family", "SANS")
		.attr("justify", "END MIDDLE" );
	}

	// Assign key to axis, creating or updating its ticks, grid lines, and labels.
	function drawAxis( axisIndex, key, duration ) {

		var scale = d3.scale.linear()
		.domain( [axisRange[axisIndex][0],axisRange[axisIndex][1]] ) // demo data range
		.range( [0,23] );

		scales[axisIndex] = scale;

		var numTicks = axisRange[axisIndex][1];
		var tickSize = 0.1;
		var tickFontSize = 0.5;

		// ticks along each axis
		var ticks = scene.selectAll( "."+axisName("Tick", axisIndex) )
		.data( scale.ticks( numTicks ));
		var newTicks = ticks.enter()
		.append("transform")
		.attr("class", axisName("Tick", axisIndex));
		newTicks.append("shape").call(makeSolid)
		.append("box")
		.attr("size", tickSize + " " + tickSize + " " + tickSize);
		// enter + update
		ticks.transition().duration(duration)
		.attr("translation", function(tick) { 
			return constVecWithAxisValue( 0, scale(tick), axisIndex ); });
		ticks.exit().remove();

		// tick labels
		var tickLabels = ticks.selectAll("billboard shape text")
		.data(function(d) { return [d]; });
		var newTickLabels = tickLabels.enter()
		.append("billboard")
		.attr("axisOfRotation", "0 0 0")     
		.append("shape")
		.call(makeSolid);
		newTickLabels.append("text")
		.attr("string", scale.tickFormat(10))
		.attr("solid", "true")
		.append("fontstyle")
		.attr("size", tickFontSize)
		.attr("family", "SANS")
		.attr("justify", "END MIDDLE" );
		tickLabels // enter + update
		.attr("string", scale.tickFormat(10));
		tickLabels.exit().remove();

		// base grid lines
		if (axisIndex==0 || axisIndex==2) {

			var gridLines = scene.selectAll( "."+axisName("GridLine", axisIndex))
			.data(scale.ticks( numTicks ));
			gridLines.exit().remove();

			var newGridLines = gridLines.enter()
			.append("transform")
			.attr("class", axisName("GridLine", axisIndex))
			.attr("rotation", axisIndex==0 ? [0,1,0, -Math.PI/2] : [0,0,0,0])
			.append("shape");

			newGridLines.append("appearance")
			.append("material")
			.attr("emissiveColor", "gray")
			newGridLines.append("polyline2d");

			gridLines.selectAll("shape polyline2d").transition().duration(duration)
			.attr("lineSegments", "0 0, " + axisRange[axisIndex][1] + " 0");

			gridLines.transition().duration(duration)
			.attr("translation", axisIndex==0
					? function(d) { return scale(d) + " 0 0"; }
			: function(d) { return "0 0 " + scale(d); }
			)
		}  
	}

	function plotData( duration, rowsIn, color, index ) {
		//console.log("plot Data"+data2[0]+"---"+data3[0]);
		if (!rowsIn) {
			console.log("no rows to plot.")
			return;
		}

		var x = scales[0], y = scales[1], z = scales[2];
		var sphereRadius = 0.2;

		// Draw a sphere at each x,y,z coordinate.
		var datapoints = scene.selectAll(".datapoint"+index).data( rowsIn );
		datapoints.exit().remove();

		var newDatapoints = datapoints.enter()
		.append("transform")
		.attr("class", "datapoint"+index)
		.attr("scale", [sphereRadius, sphereRadius, sphereRadius])
		.append("shape");
		newDatapoints
		.append("appearance")
		.append("material");
		newDatapoints
		.append("sphere");

		datapoints.selectAll("shape appearance material")
		.attr("diffuseColor", color );

		datapoints.transition().ease(ease).duration(duration)
		.attr("translation", function(row) { 
			return x(row[axisKeys[0]]) + " " + y(row[axisKeys[1]]) + " " + z(row[axisKeys[2]])});

		// Draw a stem from the x-z plane to each sphere at elevation y.
		// This convention was chosen to be consistent with x3d primitive ElevationGrid. 
		var stems = scene.selectAll(".stem"+index).data( rowsIn );
		stems.exit().remove();

		var newStems = stems.enter()
		.append("transform")
		.attr("class", "stem"+index)
		.append("shape");
		newStems
		.append("appearance")
		.append("material")
		.attr("emissiveColor", "gray")
		newStems
		.append("polyline2d")
		.attr("lineSegments", function(row) { return "0 1, 0 0"; })

		stems.transition().ease(ease).duration(duration)
		.attr("translation", 
				function(row) { return x(row[axisKeys[0]]) + " 0 " + z(row[axisKeys[2]]); })
		.attr("scale",
				function(row) { return [1, y(row[axisKeys[1]])]; });

		// Sphere Data Label ---------------------------------------------------------------------------
		
//		var sphereLabels = scene.selectAll(".dataLabel"+index).data( rowsIn );
//
//		sphereLabels.exit().remove();  
//		
//		var newSphereLabels = sphereLabels.enter()
//		.append("transform")
//		.append("billboard")
//		.attr("axisOfRotation", "0 0 0")     
//		.append("shape")
//		.call(makeSolid);
//		
//		sphereLabels.selectAll("transform billboard shape").attr("class", "dataLabel"+index);
//		
//		newSphereLabels.append("text")
//		.attr("string", function(row) { 
//			return y(row[axisKeys[1]]);
//		})
//		.attr("solid", "true")
//		.attr("class", "dataLabel"+index);
//		
//		newSphereLabels.append("fontstyle")
//		.attr("size", 5)
//		.attr("family", "SANS")
//		.attr("justify", "END MIDDLE" );
//		
//		sphereLabels.transition().ease(ease).duration(duration)
//		.attr("translation", function(row) { 
//			return x(row[axisKeys[0]])+1 + " " + y(row[axisKeys[1]]) + " " + z(row[axisKeys[2]])+1;
//		});
		
		// END Sphere Data Label ---------------------------------------------------------------------------
		
	}

	function initializeDataGrid(data1in, data2in, data3in, reverse) {
		var rows = [];
		// Follow the convention where y(x,z) is elevation.
		for (var x=0; x<data1in.length; x+=1) {
			rows.push({x: reverse?data3in[x]/100:data2in[x]/100, y: x, z: reverse?data2in[x]/100:data3in[x]/100});
		}
		return rows;
	}

	function updateData() {
		time += Math.PI/8;
		var colors = ["red","blue","green","yellow","purple"];
		var run = 0;
		if ( x3d.node() && x3d.node().runtime ) {
			for (var key in rowsMap) {
				if (rowsMap.hasOwnProperty(key)) {
					plotData( defaultDuration, rowsMap[key], colors[run], ""+(run+1) );
				}
				run++;
			}
		} else {
			setTimeout( updateData, defaultDuration );
		}
	}

	initializePlot();
	updateData();

	var val1=$("#x").val();
//	var val2=$("#y").val();
	var val3=$("#z").val();

//	$(".axis_select").change(function(){
//		var selected = $(this).val();
//		var id = $(this).attr("id");
//		var oldVal;
//
//		if($(this).attr("id")=='x'){
//			oldVal=val1;
//			val1=selected;
//		} else {
//			oldVal=val3;
//			val3=selected;
//		}
//
//		$(".axis_select").each(function(){
//			if($(this).attr("id")!=id){
//				if($(this).val()==selected){
//					$(this).val(oldVal);
//				}
//			}
//		});
//
//		reverseData=!reverseData;
//		rows = initializeDataGrid(data1,data2,data3,reverseData);
//		rows1 = initializeDataGrid(data1,data2,data3,!reverseData);
//		updateData();
//
//	});

}

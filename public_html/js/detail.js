function buildDetailPlot(xAxisValue,yAxisValue,mode){

	var parseDate = d3.time.format("%Y%m%d").parse;

	var x = d3.scale.linear()
	.range([0, width]);

	var y = d3.scale.linear()
	.range([height, 0]);

	var xAxis = d3.svg.axis()
	.scale(x)
	.orient("bottom");

	var yAxis = d3.svg.axis()
	.scale(y)
	.orient("left");

	var line = d3.svg.line()
	.interpolate("basis")
	.x(function(d) { return d?x(d.day):0; })
	.y(function(d) { return d?y(d.diff):0; });
	
	var svg = d3.select("#divDetail").append("svg")
	.attr("width", width + margin.left + margin.right)
	.attr("height", height + margin.top + margin.bottom)
	.append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	//-----------------------------------------------------------------------------

	var dimensions = scatter_colors.domain().map(function(name) {
		return {
			name: name,
			values: rowsMap[name].map(function(d) {

				return {day: d[header[xAxisValue]], diff: d[header[yAxisValue]]};

			})
		};
	});

	x.domain([0,data_scales[xAxisValue]]);

	y.domain([0,data_scales[yAxisValue]]);

	svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis)
	.append("text")
	.attr("y", -10)
	.attr("transform", "translate(" + width + ",0)")
	.attr("dy", ".71em")
	.style("text-anchor", "begin")
	.text(header[xAxisValue]);

	svg.append("g")
	.attr("class", "y axis")
	.call(yAxis)
	.append("text")
	.attr("transform", "rotate(-90)")
	.attr("y", 3)
	.attr("dy", ".71em")
	.style("text-anchor", "end")
	.text(header[yAxisValue]);

	if(mode=='dot'){
		
		for (var i = 0; i<dimensions.length; i++) {
			var key = dimensions[i].name;

			var dimension = svg.selectAll(".dimension-"+key)
			.data(dimensions[i].values)
			.enter().append("g")
			.attr("class", "dimension-"+key);

			dimension.append("circle")
			.attr("class", function(d) { return ("dot dot-"+key); })
			.attr("r", 3.5)
			.attr("cx", function(d) { return x(d.day); })
			.attr("cy", function(d) { return y(d.diff); })
			.style("fill", function() { return scatter_colors(key); });
		}
		
	} else if (mode=='line'){
		
		var dimension = svg.selectAll(".dimension")
		.data(dimensions)
		.enter().append("g")
		.attr("class", "dimension");
		
		dimension.append("path")
		.attr("class", function(d) { return ("line line-"+d.name); })
		.attr("d", function(d) { return line(d.values); })
		.style("stroke", function(d) { return scatter_colors(d.name); });
	}

}

function buildLegends(){
	var verticalLegend = d3.svg.legend().labelFormat("none").cellPadding(5).orientation("vertical").units(header['data']).cellWidth(10).cellHeight(6).inputScale(scatter_colors).cellStepping(2);

	d3.selectAll("svg")
	.append("g")
	.attr("transform", function(){ 
		return "translate("+(width_absolute-margin.right+margin.right/3)+","+margin.top+")"; 
	})
	.attr("class", "legend")
	.call(verticalLegend);
}



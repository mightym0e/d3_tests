$(document).ready(function(){

	function buildPlot1(){
		var margin = {top: 20, right: 80, bottom: 30, left: 50},
		width = 600 - margin.left - margin.right,
		height = 200 - margin.top - margin.bottom;

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

		var messpunkte = scatter_colors.domain().map(function(name) {
			var cache = [];
			return {
				name: name,
				values: rowsMap[name].map(function(d) {

					return {day: d[header['y']], diff: d[header['x']]};

				})
			};
		});

		x.domain([0,data_scales[1]]);

		y.domain([0,data_scales[0]]);

		svg.append("g")
		.attr("class", "x axis")
		.attr("transform", "translate(0," + height + ")")
		.call(xAxis);

		svg.append("g")
		.attr("class", "y axis")
		.call(yAxis)
		.append("text")
		.attr("transform", "rotate(-90)")
		.attr("y", 6)
		.attr("dy", ".71em")
		.style("text-anchor", "end")
		.text(header[0]);

		var messpunkt = svg.selectAll(".messpunkt")
		.data(messpunkte)
		.enter().append("g")
		.attr("class", "messpunkt");

		messpunkt.append("path")
		.attr("class", "line")
		.attr("d", function(d) { return line(d.values); })
		.style("stroke", function(d) { return scatter_colors(d.name); });

//		messpunkt.append("text")
//		.datum(function(d) { return {name: d.name, value: d.values[d.values.length - 1]}; })
//		.attr("transform", function(d) { return "translate(" + x(d.value.day) + "," + y(d.value.diff) + ")"; })
//		.attr("x", 3)
//		.attr("dy", ".35em")
//		.text(function(d) { return d.name; });
	}

	function buildPlot2(){
		var margin = {top: 20, right: 80, bottom: 30, left: 50},
		width = 600 - margin.left - margin.right,
		height = 200 - margin.top - margin.bottom;

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

		var messpunkte = scatter_colors.domain().map(function(name) {
			var cache = [];
			return {
				name: name,
				values: rowsMap[name].map(function(d) {

					return {day: d[header['z']], diff: d[header['x']]};

				})
			};
		});

		x.domain([0,data_scales[2]]);

		y.domain([0,data_scales[0]]);

		svg.append("g")
		.attr("class", "x axis")
		.attr("transform", "translate(0," + height + ")")
		.call(xAxis);

		svg.append("g")
		.attr("class", "y axis")
		.call(yAxis)
		.append("text")
		.attr("transform", "rotate(-90)")
		.attr("y", 6)
		.attr("dy", ".71em")
		.style("text-anchor", "end")
		.text(header[0]);

		var messpunkt = svg.selectAll(".messpunkt")
		.data(messpunkte)
		.enter().append("g")
		.attr("class", "messpunkt");

		messpunkt.append("path")
		.attr("class", "line")
		.attr("d", function(d) { return line(d.values); })
		.style("stroke", function(d) { return scatter_colors(d.name); });

//		messpunkt.append("text")
//		.datum(function(d) { return {name: d.name, value: d.values[d.values.length - 1]}; })
//		.attr("transform", function(d) { return "translate(" + x(d.value.day) + "," + y(d.value.diff) + ")"; })
//		.attr("x", 3)
//		.attr("dy", ".35em")
//		.text(function(d) { return d.name; });
		
		var verticalLegend = d3.svg.legend().labelFormat("none").cellPadding(5).orientation("vertical").units("Messpunkte").cellWidth(10).cellHeight(6).inputScale(scatter_colors).cellStepping(2);

		d3.selectAll("svg").append("g").attr("transform", "translate(550,0)").attr("class", "legend").call(verticalLegend);
	}

	buildPlot1();
	buildPlot2();

	//-----------------------------------------------------------------------------
});



d3.select('html').style('height','100%').style('width','100%')
d3.select('body').style('height','100%').style('width','100%')
d3.select('#divPlot').style('width', "1000px").style('height', "600px")
scatterPlot3d( d3.select('#divPlot'));

$('#exportSvg').click(function(){
	var fileName = "test.png";
	var canvas = $(".x3dom-canvas")[0];
	
	downloadPng(fileName, canvas);
});
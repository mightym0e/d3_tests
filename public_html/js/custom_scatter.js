d3.select('html').style('height','100%').style('width','100%');
d3.select('body').style('height','100%').style('width','100%');
d3.select('#divPlot').style('height', "600px");
initScatterPlot(d3.select('#divPlot'));

$('#exportSvg').click(function(){
	var fileName = "test.png";
	var canvas = $(".x3dom-canvas")[0];
	
	downloadPng(fileName, canvas);
});

var val1=$("#x").val();
var val2=$("#y").val();
var val3=$("#z").val();

$(".axis_select").change(function(){
	var selected = $(this).val();
	var id = $(this).attr("id");
	var oldVal;

	if(id=='x'){
		oldVal=val1;
		val1=selected;	
	} else if(id=='y'){
		oldVal=val2;
		val2=selected;
	} else {
		oldVal=val3;
		val3=selected;
	}

	$(".axis_select").each(function(){
		if($(this).attr("id")!=id){
			if($(this).val()==selected){
				$(this).val(oldVal);
			}
		}
	});
	
	changeHeader(oldVal, selected);
	$("#divDetail").empty();
	
	reloadPlotData();
	buildDetailPlot('y','x','line');
	buildDetailPlot('z','x','line');
	buildLegends();
});

function changeData(first, seccond){
	
	for (var key in rowsMap) {
		if (rowsMap.hasOwnProperty(key)) {
			for (var int = 0; int < rowsMap[key]; int++) {
				var map = rowsMap[key][int];
				var temp = map[header[first]];
				map[header[first]]=map[header[seccond]];
				map[header[seccond]]=temp;
			}
		}
	}
	
}

function changeHeader(first, seccond){
	
	var temp = data_scales[first];
	data_scales[first]=data_scales[seccond];
	data_scales[seccond]=temp;
	
	temp = header[first];
	header[first]=header[seccond];
	header[seccond]=temp;
	
}
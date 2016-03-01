// ----------------- GLOBAL VARIABLES 

var x3d;

var scene;

var scatter_colors;
var axisRange;
var scales = [];
var initialDuration = 0;
var defaultDuration = 800;
var ease = 'linear';
var axisScale = 23;
var normalizeScale = true;
var axisKeys;
var labelFontSize = 0.8;

var margin = {top: 20, right: 100, bottom: 30, left: 50},
width_absolute = 600,
height_absolute = 200,
width = width_absolute - margin.left - margin.right,
height = height_absolute - margin.top - margin.bottom;

var detail_mode = 'line';

var val1,val2,val3;

// ------------------------------------------

d3.select('html').style('height','100%').style('width','100%');
d3.select('body').style('height','100%').style('width','100%');
d3.select('#divPlot').style('height', "600px");
initScatterPlot(d3.select('#divPlot'));

$(document).ready(function(){
	
	//-----------------------SET ACTUAL AXIS DESCRIPTION
	
	$('.axis_select option[value="x"]').text(header['x']);
	$('.axis_select option[value="y"]').text(header['y']);
	$('.axis_select option[value="z"]').text(header['z']);
	
	//--------------------------------------------------
	
	val1=$("#x").val();
	val2=$("#y").val();
	val3=$("#z").val();
	
	detail_mode=$("#mode_select").val();
	
	$('#exportSvg').click(function(){
		var fileName = "test.png";
		var canvas = $(".x3dom-canvas")[0];
		
		downloadPng(fileName, canvas);
	});
	
	//--------------CHANGE AXIS

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
		buildDetailDiagrams();
	});
	
	//-----------------CHANGE DETAIL DIAGRAM MODE
	
	$(".mode_select").change(function(){
		detail_mode=$(this).val();
		$("#divDetail").empty();
		
		buildDetailDiagrams();
	});
	
	//-----------------BUILD DETAIL DIAGRAMS
	
	buildDetailDiagrams();
	
});

function buildDetailDiagrams(){
	buildDetailPlot('y','x',detail_mode);
	buildDetailPlot('z','x',detail_mode);
	buildDetailPlot('z','y',detail_mode);
	buildLegends();
}

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
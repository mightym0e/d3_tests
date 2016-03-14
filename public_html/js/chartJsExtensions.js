Chart.types.Line.extend({
    name: "chartchartDiv1",
    initialize: function (data) {
        if (this.options.yAxisLabel) this.options.scaleLabel = '         ' + this.options.scaleLabel;

        Chart.types.Line.prototype.initialize.apply(this, arguments);

        if (this.options.yAxisLabel) this.scale.yAxisLabel = this.options.yAxisLabel;
    },
    draw: function () {
        Chart.types.Line.prototype.draw.apply(this, arguments);

        if (this.scale.yAxisLabel) {
            var ctx = this.chart.ctx;
            ctx.save();
            // text alignment and color
            ctx.textAlign = "center";
            ctx.textBaseline = "bottom";
            ctx.fillStyle = this.options.scaleFontColor;
            // position
            var x = this.scale.xScalePaddingLeft * 0.2;
            var y = this.chart.height / 2;
            // change origin
            ctx.translate(x, y)
            // rotate text
            ctx.rotate(-90 * Math.PI / 180);
            ctx.fillText(this.scale.yAxisLabel, 0, 0);
            ctx.restore();
        }
    }
});

Chart.defaults.global.scaleLabel = ' <%=value%>';

function showDetails(dataset_name){
//	alert(dataset_name);
}

function showDetailData(servlet, object, div){
	if(object==undefined || object.length==0){
		return;
	}
	
	var dataset = object[0].label;
	var datasetLabel = object[0].datasetLabel;
	
	var xmlhttp = new XMLHttpRequest();
	var url = servlet;
	var parameters="dataset="+encodeURIComponent(dataset)+"&datasetLabel="+encodeURIComponent(datasetLabel)

	xmlhttp.onreadystatechange = function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	    	var data = JSON.parse(xmlhttp.responseText);
	    	if (data.length>0) {
	    		$("#"+div+" table tr").remove();
		    	
	    		appendDetailData(data[0], "#"+div+" table", "th", "head");
		    	
		    	for(var i = 1; i< (data.length>100?100:data.length); i++){
		    		appendDetailData(data[i], "#"+div+" table", "td", "tr "+(i%2==0?"odd":"even"));
		    	}
			}
	    }
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length", parameters.length);
	xmlhttp.send(parameters);
	
//	$.ajax({
//	    type: 'POST',
//	    url: servlet,
//	    dataType: "json",
//	    data: { 
//	        'dataset': dataset,
//	        'datasetLabel': datasetLabel
//	    },
//	    success: function(data, msg){
//	    	
//	    	if (data.length>0) {
//	    		$("#"+div+" table tr").remove();
//		    	
//	    		appendDetailData(data[0], "#"+div+" table", "th", "head");
//		    	
//		    	for(var i = 1; i< (data.length>100?100:data.length); i++){
//		    		appendDetailData(data[i], "#"+div+" table", "td", "tr "+(i%2==0?"odd":"even"));
//		    	}
//			}
//	    	
////	    	console.log(data);
//	    }
//	});
}

function appendDetailData(data, container, columnType, cssClass){
	
	var detail_line = "<tr class=\""+cssClass+"\">";
	
	for ( var data_el in data) {
		detail_line += "<"+columnType+">"+data[data_el]+"</"+columnType+">";
	}
	
	detail_line += "</tr>";
	
	$(container).append(detail_line);
	
}

function addDownloadButton(chartContainerId){
	var div = document.getElementById(chartContainerId);
	domtoimage.toPng(div).then(function (pngDataUrl) {
		var img = new Image();
		img.src = pngDataUrl;
		img.style.display = "none";
		div.appendChild(img);
		var button = document.createElement('a');
		button.innerHTML = 'Download';
		button.href = document.getElementById(chartContainerId).getElementsByTagName('img')[0].src;
		button.download = 'diagramm.png';
		document.getElementById(chartContainerId).appendChild(button);
	});
}

function addLegendInteraction(chartObject,chartLegend,type){
	var helpers = Chart.helpers;
	helpers.each(chartLegend.firstChild.childNodes, function(legendNode, index){
	    helpers.addEvent(legendNode, 'mouseover', function(){
	            if (type=='segments') {
					var activeSegment = chartObject.segments[index];
					activeSegment.save();
					activeSegment.fillColor = activeSegment.highlightColor;
					chartObject.showTooltip([ activeSegment ]);
					activeSegment.restore();
				} else {
//					var activeSegment = chartObject.datasets[index];
//					var color = activeSegment.fillColor;
//					activeSegment.fillColor = activeSegment.highlightColor;
//					chartObject.showTooltip([ activeSegment ]);
//					setTimeout(function(){activeSegment.fillColor = color;chartObject.showTooltip([ activeSegment ]);}, 2000);
				}
	    });
	});
}

//$(document).ready(function(){
//	
//	//TODO: mainDiv in Image konvertieren, um Diagramm und Legende zusammen zu exportieren
//	
//	var div = document.getElementById("chartDiv");
//	domtoimage.toPng(div).then(function (pngDataUrl) {
////		while (div.getElementsByTagName('img')[0]) {
////			div.removeChild(div.getElementsByTagName('img')[0]);
////		}
//		var img = new Image();
//        img.src = pngDataUrl;
//        img.style.visibility = "hidden";
//        div.appendChild(img);
//	});
//	
//	var div1 = document.getElementById("chartDiv1");
//	domtoimage.toPng(div1).then(function (pngDataUrl) {
////		while (div1.getElementsByTagName('img')[0]) {
////			div1.removeChild(div1.getElementsByTagName('img')[0]);
////		}
//		var img = new Image();
//        img.src = pngDataUrl;
//        img.style.visibility = "hidden";
//        div1.appendChild(img);
//	});
//	
//});
//
//function downloadAll(){
//	
//	var elements = [];
//	var zip = new JSZip();
//	
//	var count = 0;
//	var img = document.getElementById("chartDiv").getElementsByTagName('img')[0];
//	var img1 = document.getElementById("chartDiv1").getElementsByTagName('img')[0];
//	
//
//	var png = img.src.substr(img.src.indexOf(',')+1);
//
//	var png1 = img1.src.substr(img1.src.indexOf(',')+1);
//	
//	elements.push(png);
//	elements.push(png1);
//	
////	$(".chart").each(function(){
////		var canvas = $(this).children("canvas").eq(0);
////		var div = $(this).children("div").eq(0);
////		var png;
////		domtoimage.toBlob(div)
////	    .then(function (blob) {
////	    	png = blob;
////	    });
////		elements.push(getPng('canvas_'+count,canvas));
////		elements.push(png);
////		count++;
////	});
//	
//	for(var i = 0; i<elements.length; i++){
//		zip.file("image"+i+".png", elements[i],{base64: true});
//	}
//	
//	var blob = zip.generate({type:"blob"});
//	window.saveAs(blob, "test.zip");
//	
//}
//
////TODO: Legend hovers interacting with diagramm
//
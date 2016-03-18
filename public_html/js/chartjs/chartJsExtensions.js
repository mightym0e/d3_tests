

Chart.defaults.global.scaleLabel = ' <%=value%>';

function showDetails(dataset_name){
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
				}
	    });
	});
}
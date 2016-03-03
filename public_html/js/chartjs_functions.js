function showDetails(dataset_name){
	alert(dataset_name);
}

function showDetailData(object, div){
	$.ajax({
	    type: 'POST',
	    url: 'AjaxDetailServlet',
	    dataType: "json",
	    data: { 
	        'dataset': object.label
	    },
	    success: function(data, msg){
	    	
	    	$("#"+div+" table tr.row").remove();
	    	
	    	for(var i = 0; i< 100; i++){
	    		var detail_line = "<tr class=\"row\"><td>"+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"+data[i][2]+"</td><td>"+data[i][3]+"</td></tr>";
	    		$("#"+div+" table").append(detail_line);
	    	}
	    	
//	    	console.log(data);
	    }
	});
}
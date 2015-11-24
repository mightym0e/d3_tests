var yaw=0.5,pitch=0.5, width=700, height=400, drag=false;

  function dataFromFormular(func){
    var output=[];
    for(var x=-20;x<20;x++){
      var f0=[];            
      output.push(f0);
      for(var y=-20;y<20;y++){
          f0.push(func(x,y));
      }
    }
    return output;
  }

  var surfaces=[
    {
      name: 'Dataset 1',
      data: dataFromFormular(function(x,y){
          return Math.sin(Math.sqrt(x*x+y*y)/5*Math.PI)*50;
        })
    },
    {
      name: 'Dataset 2',
      data: dataFromFormular(function(x,y){
          return Math.cos(x/15*Math.PI)*Math.cos(y/15*Math.PI)*60+Math.cos(x/8*Math.PI)*Math.cos(y/10*Math.PI)*40;
        })
    },
    {
      name: 'Dataset 3',
      data: dataFromFormular(function(x,y){
          return -(Math.cos(Math.sqrt(x*x+y*y)/6*Math.PI)+1)*300/(Math.pow(x*x+y*y+1,0.3)+1)+50;
        })
    }
  ];
  var selected=surfaces[0];

  var ul=d3.select('body')
           .append('ul');
  var svg=d3.select('body')
          .append('svg')
            .attr('height',height)
            .attr('width',width);

  var group = svg.append("g");

  var md=group.data([surfaces[0].data])
    .surface3D(width,height)
      .surfaceHeight(function(d){ 
        return d;
      }).surfaceColor(function(d){
        var c=d3.hsl((d+100), 0.6, 0.5).rgb();
        return "rgb("+parseInt(c.r)+","+parseInt(c.g)+","+parseInt(c.b)+")";
      });

  ul.selectAll('li')
    .data(surfaces)
      .enter().append('li')
        .html(function(d){
          return d.name
        }).on('mousedown',function(){
          md.data([d3.select(this).datum().data]).surface3D()
            .transition().duration(500)
            .surfaceHeight(function(d){
              return d;
            }).surfaceColor(function(d){
              var c=d3.hsl((d+100), 0.6, 0.5).rgb();
              return "rgb("+parseInt(c.r)+","+parseInt(c.g)+","+parseInt(c.b)+")";
            });
        });

  svg.on("mousedown",function(){
    drag=[d3.mouse(this),yaw,pitch];
  }).on("mouseup",function(){
    drag=false;
  }).on("mousemove",function(){
    if(drag){            
      var mouse=d3.mouse(this);
      yaw=drag[1]-(mouse[0]-drag[0][0])/50;
      pitch=drag[2]+(mouse[1]-drag[0][1])/50;
      pitch=Math.max(-Math.PI/2,Math.min(Math.PI/2,pitch));
      md.turntable(yaw,pitch);
    }
  });

  var axisRange = [0, 40];
  var axisKeys = ["x", "y", "z"];
  var initialDuration = 0;
  var scales = [];
  
  function makeSolid(selection, color) {
	    selection.append("appearance")
	      .append("material")
	         .attr("diffuseColor", color||"black")
	    return selection;
	  }
  
  function constVecWithAxisValue( otherValue, axisValue, axisIndex ) {
	    var result = [otherValue, otherValue, otherValue];
	    result[axisIndex] = axisValue;
	    return result;
  }
  
  function axisName( name, axisIndex ) {
	    return ['x','y','z'][axisIndex] + name;
  }
  
  function initializePlot() {
	  initializeAxis(0);
	  initializeAxis(1);
	  initializeAxis(2);
  }

  function initializeAxis( axisIndex )
  {
	  var key = axisKeys[axisIndex];
	  drawAxis( axisIndex, key, initialDuration );

	  var scaleMin = axisRange[0];
	  var scaleMax = axisRange[1];

	  // the axis line
	  var newAxisLine = svg.append("transform")
	  .attr("class", axisName("Axis", axisIndex))
	  .attr("rotation", ([[0,0,0,0],[0,0,1,Math.PI/2],[0,1,0,-Math.PI/2]][axisIndex]))
	  .append("shape")
	  newAxisLine
	  .append("appearance")
	  .append("material")
	  .attr("emissiveColor", "lightgray")
	  newAxisLine
	  .append("polyline2d")
	  // Line drawn along y axis does not render in Firefox, so draw one
	  // along the x axis instead and rotate it (above).
	  .attr("lineSegments", "0 0," + scaleMax + " 0")

	  // axis labels
	  var newAxisLabel = svg.append("transform")
	  .attr("class", axisName("AxisLabel", axisIndex))
	  .attr("translation", constVecWithAxisValue( 0, scaleMin + 1.1 * (scaleMax-scaleMin), axisIndex ))

	  var newAxisLabelShape = newAxisLabel
	  .append("billboard")
	  .attr("axisOfRotation", "0 0 0") // face viewer
	  .append("shape")
	  .call(makeSolid)

	  var labelFontSize = 0.6;

	  newAxisLabelShape
	  .append("text")
	  .attr("class", axisName("AxisLabelText", axisIndex))
	  .attr("solid", "true")
	  .attr("string", key)
	  .append("fontstyle")
	  .attr("size", labelFontSize)
	  .attr("family", "SANS")
	  .attr("justify", "END MIDDLE" )
  }
  
  function drawAxis( axisIndex, key, duration ) {

	    var scale = d3.scale.linear()
	      .domain( [-20,20] ) // demo data range
	      .range( axisRange )
	    
	    scales[axisIndex] = scale;

	    var numTicks = 8;
	    var tickSize = 0.1;
	    var tickFontSize = 0.5;

	    // ticks along each axis
	    var ticks = svg.selectAll( "."+axisName("Tick", axisIndex) )
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
	         return constVecWithAxisValue( 0, scale(tick), axisIndex ); })
	    ticks.exit().remove();

	    // tick labels
	    var tickLabels = ticks.selectAll("billboard shape text")
	      .data(function(d) { return [d]; });
	    var newTickLabels = tickLabels.enter()
	      .append("billboard")
	         .attr("axisOfRotation", "0 0 0")     
	      .append("shape")
	      .call(makeSolid)
	    newTickLabels.append("text")
	      .attr("string", scale.tickFormat(10))
	      .attr("solid", "true")
	      .append("fontstyle")
	        .attr("size", tickFontSize)
	        .attr("family", "SANS")
	        .attr("justify", "END MIDDLE" );
	    tickLabels // enter + update
	      .attr("string", scale.tickFormat(10))
	    tickLabels.exit().remove();

	    // base grid lines
	    if (axisIndex==0 || axisIndex==2) {

	      var gridLines = svg.selectAll( "."+axisName("GridLine", axisIndex))
	         .data(scale.ticks( numTicks ));
	      gridLines.exit().remove();
	      
	      var newGridLines = gridLines.enter()
	        .append("transform")
	          .attr("class", axisName("GridLine", axisIndex))
	          .attr("rotation", axisIndex==0 ? [0,1,0, -Math.PI/2] : [0,0,0,0])
	        .append("shape")

	      newGridLines.append("appearance")
	        .append("material")
	          .attr("emissiveColor", "gray")
	      newGridLines.append("polyline2d");

	      gridLines.selectAll("shape polyline2d").transition().duration(duration)
	        .attr("lineSegments", "0 0, " + axisRange[1] + " 0")

	      gridLines.transition().duration(duration)
	         .attr("translation", axisIndex==0
	            ? function(d) { return scale(d) + " 0 0"; }
	            : function(d) { return "0 0 " + scale(d); }
	          )
	    }  
	  }
  
  initializePlot();

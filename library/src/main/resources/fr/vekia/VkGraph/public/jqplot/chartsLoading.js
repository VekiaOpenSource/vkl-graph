function loadCharts() {
	$(document)
			.ready(
					function() {

						$.jqplot.config.enablePlugins = true;
						s1 = [ [ '23-May-08', 1 ], [ '24-May-08', 4 ],
								[ '25-May-08', 2 ], [ '26-May-08', 6 ] ];

						// for ( var counter = 0; counter < labels.lenght - 1;
						// counter++) {
						// var label =
						// labels[counter]@fr.vekia.gquery.client.ChartValue::label;
						// alert('label = '+ label);
						// }

						plot1 = $
								.jqplot(
										'chart1',
										[ s1 ],
										{
											title : 'Highlighting, Dragging, Cursor and Trend Line',
											axes : {
												xaxis : {
													renderer : $.jqplot.DateAxisRenderer,
													tickOptions : {
														formatString : '%#m/%#d/%y'
													},
													numberTicks : 4
												},
												yaxis : {
													tickOptions : {
														formatString : '$%.2f'
													}
												}
											},
											highlighter : {
												sizeAdjust : 10,
												tooltipLocation : 'n',
												tooltipAxes : 'y',
												tooltipFormatString : '<b><i><span style="color:red;">hello</span></i></b> %.2f',
												useAxesFormatters : false
											},
											cursor : {
												show : true
											}
										});
					});
}
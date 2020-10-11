$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	var top10 = findTop10();
	
	callChart(top10, "2018", "2019");
	
	//Reporting stats
	var allTop10 = reportTop10(top10);
	
	function findTop10() {
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/top10Clients", 
			dataType: "json",
			async: false,
			type: "GET",
			success: function(data) {				
				dataSet = data;
			},
			error: function(data) {
				dataSet = "Error";
			}
		});
		
		return dataSet;
	}
	
	
	
	
	
	function callChart(top10, fromDateText, toDateText){
		
		var freq = [];
		var clients = [];
		for (x of top10) {
			freq.push(x.frequency);
			clients.push(x.client);
		}

		freq.push(1);
		//freq.push(12);


		// Data
		data = 
		{
			labels: clients,
			datasets: [{ 
				backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850", "#343a40", "#ffc107", "#e83e8c", "#fd7e14", "#28a745"],
				data: freq
			  }]

		};
		//Set up
		var ctx = document.getElementById("graph").getContext('2d');
		
		var graph = new Chart(ctx, {
			type: 'horizontalBar',
		 // The data for the dataset
			data: data,
		 // Configuration options
			options: {
					responsive: true,
					maintainAspectRatio: false,
					legend: { display: false },
					 title: 
					 {
						 display: true,
						 text: 'Top 10 clients for '+fromDateText+' and '+toDateText,
						 fontSize: 20
					 }
				}
		});
	}
	
	//search button
	$('#search-btn').click(function(event) {
		$('.notifyjs-corner').remove();
		var dateFrom = $('#fromDate').val();
		var dateTo = $('#toDate').val();
		
		if(dateFrom && dateTo && dateFrom < dateTo) {
			
			$.ajax({
			url:"/altHealth/report/top10Clients/"+ dateFrom+"/"+dateTo, 
			dataType: "json",
			async: false,
			type: "GET",
			success: function(data) {				
				var top10 = data;
				var fromDateText = dateFrom.substring(0, 4);
				var toDateText = dateTo.substring(0, 4);
				
				var canvas = $('#graph');
				var chart = new Chart(canvas);
				chart.destroy();
				clearHTMLCanvas();
				callChart(top10, fromDateText, toDateText);
				reportTop10(top10);
			},
			error: function(data) {
				dataSet = "Error";
			}
		});
			
		}else {	
			$.notify("Heads up! Please select a valid from/to date range", "error");
		}
	});
	
	function clearHTMLCanvas(){
		$('#graph').remove(); // this is my <canvas> element
		$('#graph-container').append('<canvas id="graph"  width="800" height="450"></canvas>');
	}

	
	function showActiveNav() {
		$('#topNav').addClass('active');
	}
	
	function includeHTML() {
		  var z, i, elmnt, file, xhttp;
		  /*loop through a collection of all HTML elements:*/
		  z = document.getElementsByTagName("*");
		  for (i = 0; i < z.length; i++) 
		  {
		    elmnt = z[i];
		    /*search for elements with a certain atrribute:*/
		    file = elmnt.getAttribute("w3-include-html");
		    if (file) 
		    {
		      /*make an HTTP request using the attribute value as the file name:*/
		      xhttp = new XMLHttpRequest();
		      xhttp.onreadystatechange = function() 
		      {
		        if (this.readyState == 4) 
		        {
		          if (this.status == 200) {elmnt.innerHTML = this.responseText;}
		          if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
		          /*remove the attribute, and call this function once more:*/
		          elmnt.removeAttribute("w3-include-html");
		          includeHTML();
		        }
		      }
		      xhttp.open("GET", file, true);
		      xhttp.send();
		      /*exit the function:*/
		      return;
		    }
		  }
		  showActiveNav();
	}
	
	////////////////////////////////////////////////////////////////REPORTING////////////////////////////////////////////////////////////////
	
	function reportTop10(top10){
		var dataSet = [];
					
		dataSet = top10;
		
		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var aaTable = $("#tblTop10").DataTable({
			dom: '<f<t>lip>',
			buttons: [
				   {
					   extend: 'excel',
					   title: 'Top 10 Clients For 2018 and 2019',
					   filename: 'Top_10_Clients_' + today
				   }
				],
			order: [[ 1, "desc" ], [ 0, 'asc' ]],
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'client'},
				{data: 'frequency'}
			]
		});
		
		return dataSet;
	}
	
	$(document).on('click', '.downloadReport', function()  {
		var table = $("#tblTop10").DataTable();
		
		//var data = test.buttons.exportData();
		
		table.button( '0' ).trigger();
		
	});
	
});
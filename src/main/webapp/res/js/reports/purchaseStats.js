$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	var purchaseStats = findPurchaseStats();
	
	//Reporting stats
	var allPurchaseStats = reportPurchaseStats(purchaseStats);
	
	function findPurchaseStats() {
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/purchasesStatistics", 
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
	
	var purchases = [];
	var month = [];
	for (x of purchaseStats) {
		purchases.push(x.numOfPurchases);
		month.push(x.month);
	}
	
	// Data
	data = 
	{
		labels: month,
		datasets: [{ 
			data: purchases,
			label: "Purchases",
			borderColor: "#3cba9f",
			fill: false
		  }]

	};
	
	//Set up
	var ctx = document.getElementById("graph").getContext('2d');
	
	var homeChart = new Chart(ctx, {
	    type: 'line',
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
	    			 text: 'Purchases statistics (2012 - current)',
	    			 fontSize: 20
	    		 }
	    	}
	});
	
	function showActiveNav()
	{
		$('#purStatsNav').addClass('active');
	}
	
	function includeHTML() 
	{
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
	
	function reportPurchaseStats(purchaseStats){
		var dataSet = [];
					
		dataSet = purchaseStats;
		
		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var aaTable = $("#tblPurchasStats").DataTable({
			dom: '<f<t>lip>',
			buttons: [
				   {
					   extend: 'excel',
					   title: 'Purchases Statistics',
					   filename: 'Purchases_Statistics_' + today
				   }
				],
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'numOfPurchases'},
				{data: 'month'}
			]
		});
		
		return dataSet;
	}
	
	$(document).on('click', '.downloadReport', function() 
	{
		var table = $("#tblPurchasStats").DataTable();
		
		//var data = test.buttons.exportData();
		
		table.button( '0' ).trigger();
		
	});
	
});
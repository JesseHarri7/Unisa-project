$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	var unpaidInvoices = findUnpaidInvoices();
	
	var birthdays = findBirthdays();
	
	var minStockLevels = findMinStockLevels();
	
	var clientInfoQuery = findClientInfoQuery();
	
	var top10 = findTop10();
	
	var purchaseStats = findPurchaseStats();
	
	//Reporting stats
	var allUnpaidInvoices = reportUnpaidInvoices(unpaidInvoices);
	
	var allBirthdays = reportBirthdays(birthdays);
	
	var allMinStockLevels = reportMinStockLevels(minStockLevels);
	
	var allClientInfoQuery = reportClientInfoQuery(clientInfoQuery);
	
	var allTop10 = reportTop10(top10);
	
	var allPurchaseStats = reportPurchaseStats(purchaseStats);
	
	//Reporting
	
	//Displaying total UnpaidInvoices
	document.getElementById("reportUnpaidInvoices").innerHTML = unpaidInvoices.length;
	
	//Displaying total Birthdays
	document.getElementById("reportBirthdays").innerHTML = birthdays.length;
	
	//Displaying total minStockLevels
	document.getElementById("reportMinStockLevels").innerHTML = minStockLevels.length;
	
	//Displaying total clientInfoQuery
	document.getElementById("reportClientInfoQuery").innerHTML = clientInfoQuery.length;
	
	//Find all assets from the database
	function findUnpaidInvoices()
	{
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/unpaidInvoices",
			dataType: "json",
			async: false,
			type: "GET",
			success: function(data)
			{
				dataSet = data;
			},
			error: function(data)
			{
				dataSet = "Error";
			}
		});
		
		return dataSet;
	}
	
	//Find all Employees from the database
	function findBirthdays()
	{
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/birthdaysForToday", 
			dataType: "json",
			async: false,
			type: "GET",
			success: function(data)
			{
				dataSet = data;
			},
			error: function(data)
			{
				dataSet = "Error";
			}
		});
		
		return dataSet;
	}
	
	function findMinStockLevels() {
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/minimumStockLevels", 
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
	
	function findClientInfoQuery() {
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/clientInformationQuery", 
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
	
	// Data
	data = 
	{
		// These labels appear in the legend and in the tooltips when hovering different arcs
	    labels: [
	        'UnpaidInvoices',
	        'Birthdays',
	        'MinStockLevels',
			'ClientInfoQuery'
	    ],
	    
		datasets: [{
			data: [unpaidInvoices.length, birthdays.length, minStockLevels.length, clientInfoQuery.length],
			
			backgroundColor: [
	            /*'#cbb2ae',
	            '#aec9cb',*/
	            '#8e5ea2',
				'#FF6384',
				'#36A2EB',
				'#FFCD56'
				
	        ]
		}]

	};
	
	//Set up
	var ctx = document.getElementById("homeChart").getContext('2d');
	
	var homeChart = new Chart(ctx, {
	    type: 'doughnut',
	 // The data for the dataset
	    data: data,
	 // Configuration options
	    options: {
	    		responsive: true,
	    		maintainAspectRatio: false,
	    		 title: 
	    		 {
	    			 display: true,
	    			 text: 'Overview',
	    			 fontSize: 20
	    		 }
	    	}
	});
	
	function showActiveNav()
	{
		$('#hNav').addClass('active');
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
	
	//Find all assets from the database
	function reportUnpaidInvoices(unpaidInvoices)
	{
		var dataSet = [];
		
		dataSet = unpaidInvoices;

		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var unpaidInvoicesTable = $("#unpaidInvoices").DataTable({
			dom: '<f<t>lip>',
			buttons: [
//			           'excel',
			   {
				   extend: 'excel',
				   title: 'Unpaid-Invoices',
				   filename: 'Unpaid-Invoices_' + today 
			   }
			],
			responsive: true,
			retrieve: true,
			select: true,
			rowId: 'clientId',
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'clientName'},
				{data: 'invNum'},
				{data: 'invDate'}
			]
		});
		
		return dataSet;
	}
	
	$(document).on('click', '.reportUnpaidInvoices', function() 
	{
		var UnpaidInvoices = $("#unpaidInvoices").DataTable();
		
		//var data = test.buttons.exportData();
		
		UnpaidInvoices.button( '0' ).trigger();
		
	});
	
	//Set birthdays to hidden table
	function reportBirthdays(birthdays) {
		
		var dataSet = [];
		
		dataSet = birthdays;
		
		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var aaTable = $("#tblBirthdays").DataTable({
			dom: '<f<t>lip>',
			buttons: [
				   {
					   extend: 'excel',
					   title: 'Birthdays-for-today',
					   filename: 'Birthdays-for-today_' + today
				   }
				],
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'clientName'}
			]
		});
		
		return dataSet;
	}

	$(document).on('click', '.reportBirthdays', function() 
	{		
		var birthdays = $("#tblBirthdays").DataTable();
		
		//var data = test.buttons.exportData();
		
		birthdays.button( '0' ).trigger();
		
	});
	
	function reportMinStockLevels(minStockLevels) {
		var dataSet = [];
					
		dataSet = minStockLevels;
		
		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var aaTable = $("#tblMinStockLevels").DataTable({
			dom: '<f<t>lip>',
			buttons: [
				   {
					   extend: 'excel',
					   title: 'Min-Stock-Levels',
					   filename: 'Min-Stock-Levels_' + today
				   }
				],
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'supplementId'},
				{data: 'supplierinformation'},
				{data: 'suppMinLevels'},
				{data: 'suppCurrentStockLevels'}
			]
		});
		
		return dataSet;
	}
	
	$(document).on('click', '.reportMinStockLevels', function() 
	{
		var components = $("#tblMinStockLevels").DataTable();
		
		//var data = test.buttons.exportData();
		
		components.button( '0' ).trigger();
		
	});
	
	function reportClientInfoQuery(clientInfoQuery){
		var dataSet = [];
					
		dataSet = clientInfoQuery;
		
		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var aaTable = $("#tblClientInfoQuery").DataTable({
			dom: '<f<t>lip>',
			buttons: [
				   {
					   extend: 'excel',
					   title: 'Client-Info-Query',
					   filename: 'Client-Info-Query_' + today
				   }
				],
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'cTelH'},
				{data: 'cTelW'},
				{data: 'cTelCell'},
				{data: 'cEmail'}
			]
		});
		
		return dataSet;
	}
	
	$(document).on('click', '.reportClientInfoQuery', function() 
	{
		var table = $("#tblClientInfoQuery").DataTable();
		
		//var data = test.buttons.exportData();
		
		table.button( '0' ).trigger();
		
	});
	
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
	
	$(document).on('click', '.reportTop10', function() 
	{
		var table = $("#tblTop10").DataTable();
		
		//var data = test.buttons.exportData();
		
		table.button( '0' ).trigger();
		
	});
	
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
	
	$(document).on('click', '.reportPurchaseStats', function() 
	{
		var table = $("#tblPurchasStats").DataTable();
		
		//var data = test.buttons.exportData();
		
		table.button( '0' ).trigger();
		
	});
	
});
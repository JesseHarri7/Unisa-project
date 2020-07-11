$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	var unpaidInvoices = findUnpaidInvoices();
	
	//Reporting stats
	var allUnpaidInvoices = reportUnpaidInvoices(unpaidInvoices);
	
	//Find all Unpaid-Invoices
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
	
	function showActiveNav()
	{
		$('#unpaidNav').addClass('active');
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
	
	//Report all unpaidInvoices from the database
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
			order: [[ 3, "asc" ]],
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
	
	$(document).on('click', '.downloadReport', function() 
	{
		var UnpaidInvoices = $("#unpaidInvoices").DataTable();
		
		//var data = test.buttons.exportData();
		
		UnpaidInvoices.button( '0' ).trigger();
		
	});
	
});
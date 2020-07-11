$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	var minStockLevels = findMinStockLevels();
	
	//Reporting stats
	var allMinStockLevels = reportMinStockLevels(minStockLevels);
	
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
	
	function showActiveNav()
	{
		$('#minNav').addClass('active');
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
	
	$(document).on('click', '.downloadReport', function() 
	{
		var components = $("#tblMinStockLevels").DataTable();
		
		//var data = test.buttons.exportData();
		
		components.button( '0' ).trigger();
		
	});
	
});
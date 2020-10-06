$(document).ready(function()
{
	var dataSet = [];
	var count = 0;
	
	//All data fields on start up
	findAll();
	
	//Add temps html files
	includeHTML();
	
	//find all
	function findAll() {
		$.ajax({
			url:"/altHealth/invoice/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data)
			{
				dataSet = data;
				
				dataList(dataSet);
				
			}
		});
	}		
	
	function dataList(dataSet) {
		var table = $("#invoice-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'invNum'},
				{data: 'clientId'},
				{data: 'invDate'},
				{data: 'invPaid'},
				{data: 'invPaidDate'},
			]
		});
		
		return table;
	}
	
	//Select
	$('#invoice-table tbody').on('click','tr', function() {
		$(this).toggleClass('selected');
	} );
	
	//Select button
	$('#select-btn').click(function(event) {				
		$('.notifyjs-corner').remove();
		resetLocal();
		
		var table = $('#invoice-table').DataTable();

		//Get data of the selected row
		var data = table.rows( '.selected' ).data();
		
		if(data.length != 0) {
			if (data.length > 1) {
				$.notify("Heads up! Please select only one invoice to view.", "error");
			}else{
				saveInvoice(data[0]);
			}
		}else {	
			$.notify("Heads up! Please select a invoice to view.", "error");
		}
	});
	
	//Saving selected rows to local storage
	function saveInvoice(data) {		
		if (data) {
			localStorage.setItem('invoice', JSON.stringify(data));
			window.location = "../invoice/";
		}

	}
	
	function resetLocal() {
		localStorage.clear();
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
	
	function showActiveNav() {
		$('#invoiceNav').addClass('active');
	}

});
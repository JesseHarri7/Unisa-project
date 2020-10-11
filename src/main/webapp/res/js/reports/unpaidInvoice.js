$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	yearSelector();
	
	var unpaidInvoices = findUnpaidInvoices();
	
	//Reporting stats
	var allUnpaidInvoices = reportUnpaidInvoices(unpaidInvoices);
	
	//Find all Unpaid-Invoices
	function findUnpaidInvoices() {
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/unpaidInvoices",
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
	
	function yearSelector(){
		for (i = new Date().getFullYear(); i > 2011; i--) {
			$('#yearpicker').append($('<option />').val(i).html(i));
		}
	}
	
	$(document).on('click', '#yearpicker', function() {
		$('.notifyjs-corner').remove();
		searchYear(this);
	}); 
	
	function searchYear(yearSelector){
		var year = yearSelector.value;
		
		$.ajax({
			url:"/altHealth/report/unpaidInvoices/" + year, 
			dataType: "json",
			type: "GET",
			success: function(data) {
				var table = $('#unpaidInvoices').DataTable();
				table.clear().draw();
				
				for (x of data) {
					var unpaidInvObj = {clientId:x.clientId, clientName:x.clientName, invNum:x.invNum, invDate:x.invDate};
					table.row.add(unpaidInvObj).draw();
				}
				
				//reportUnpaidInvoices(dataSet);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				$.notify("Status 405", "error");
			}
		});	
	}
	
	//Select
	$('#unpaidInvoices tbody').on('click','tr', function() {
		$(this).toggleClass('selected');
	} );
	
	//Select button
	$('#select-btn').click(function(event) {				
		$('.notifyjs-corner').remove();
		resetLocal();
		
		var table = $('#unpaidInvoices').DataTable();

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
	
	function showActiveNav() {
		$('#unpaidNav').addClass('active');
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
	
	//Report all unpaidInvoices from the database
	function reportUnpaidInvoices(unpaidInvoices) {
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
	
	$(document).on('click', '.downloadReport', function() {
		var UnpaidInvoices = $("#unpaidInvoices").DataTable();
		
		//var data = test.buttons.exportData();
		
		UnpaidInvoices.button( '0' ).trigger();
		
	});
	
});
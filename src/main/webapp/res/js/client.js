$(document).ready(function()
{
	var dataSet = [];
	var count = 0;
	
	//All data fields on start up
	findAll();
	
	//Add temps html files
	includeHTML();
	
	//find all
	function findAll()
	{
		$.ajax({
			url:"/altHealth/client/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data)
			{
				dataSet = data;
				
				clientList(dataSet);
				
			}
		});
	}	
	
	//Select
	$('#client-table tbody').on('click','tr', function()
	{
		$(this).toggleClass('selected');
		
		/*if ( $(this).hasClass('selected') ) 
		{
            $(this).removeClass('selected');
            $('#setEmp-btn').prop('disabled', true);
		}
		else 
		{
            $('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            $('#setEmp-btn').prop('disabled', false);
        }*/
	} );	
	
	function clientList(dataSet)
	{
		var clientTable = $("#client-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'cTelH'},
				{data: 'cTelW'},
				{data: 'cTelCell'},
				{data: 'cEmail'},
				{data: 'referenceId'}
			]
		});
		
		return clientTable;
	}
	
	//Create button
	$('#create-btn').click(function(event) 
	{
		//Clear form red border css
		clearFormBorder();
		
		//Clear form content if any
		document.getElementById("create").reset();
		
		$('.notifyjs-corner').remove();
	});
	
	//Modal form create button
	$('#form-create-btn').click(function(event) 
	{
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		
		//Check to see if all fields are filled in
		//if(validate())
		//{
			//if all fields are filled then create asset
			create();
		//}
	});
	
	function create()
	{
		dataSet = [];
		var table = $('#client-table').DataTable();
		
		var clientId = $('#id').val();
		var cName = $('#cName').val();
		var cSurname = $('#cSurname').val();
		var cEmail = $('#cEmail').val();
		var cTelH = $('#cTelH').val();
		var cTelW = $('#cTelW').val();
		var cTelCell = $('#cTelCell').val();
		var referenceId = $('#refId').val();
		
		//Set as object
		var client = {clientId, cName, cSurname, cEmail, cTelH, cTelW: cTelCell, referenceId};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(client);
		
		//Before creating, first check to see if the asset already exists
		//var exists = findId(assetCode);
		
		//if(exists.length == 0)
		//{
			$.ajax(
			{
				headers: {
			        'Accept': 'application/json',
			        'Content-Type': 'application/json' 
			    },
				url:"/altHealth/client/formCreateBtn", 
				dataType: "json",
				data: data_json,
				type: "POST",
				success: success()
			});
			function success()
			{
				$.notify("Success! Asset " + clientId + " has been created.", "success");
				
//				displayAlertT("Asset " + assetCode + " has been created.", "success", "Success!");
				
				//alert("Asset " + assetCode + " has been created") + table.row.add(asset).draw()
				
				//table.row.add(asset).draw()
			}
			
			//Clear data from the modal form
			document.getElementById("create").reset();
			$('#createModal').modal('hide');
		//}
		//else
		//{
		//	$.notify("Error! The Asset " + assetCode + " you're trying to create already exists.", "error");
			
//			displayAlertT("The Asset " + assetCode + " you're trying to create already exists.", "danger", "Error!");

			//alert("The Asset "+ assetCode + " you're trying to create already exists");
		//}

	}
	
	function clearFormBorder()
	{
		//create form
		$('#id').removeClass("form-fill-error");
		$('#name').removeClass("form-fill-error");
		$('#type').removeClass("form-fill-error");
		$('#brand').removeClass("form-fill-error");
		$('#datePurchased').removeClass("form-fill-error");
		
		//Update form
		$('#uId').removeClass("form-fill-error");
		$('#uName').removeClass("form-fill-error");
		$('#uType').removeClass("form-fill-error");
		$('#uBrand').removeClass("form-fill-error");
		$('#uDatePurchased').removeClass("form-fill-error");
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
	
	function showActiveNav()
	{
		$('#aNav').addClass('active');
		/*var url = window.location.pathname;
		
		if(url == "/assetManagement/pages/asset")
		{
			$('#aNav').addClass('active');
		}*/
	}

});
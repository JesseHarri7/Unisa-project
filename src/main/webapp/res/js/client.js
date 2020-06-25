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
		var id = document.forms["create"]["id"].value;
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		
		var requiredFields = validateEmptyFields();
		
		if(requiredFields){
			//Validate ID field
			if(validateId(id)) {
				create();
			}
		}
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
		var address = $('#address').val();
		var code = $('#code').val();
		var referenceId = $('#refId').val();
		
		//Set as object
		var client = {clientId, cName, cSurname, cEmail, cTelH, cTelW, cTelCell, address, code, referenceId};
		
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
				success: function(response)
				{
					if(response.status == "true") {
						$.notify(response.msg, "success");
					
						table.row.add(client).draw()
						
						//Clear data from the modal form
						document.getElementById("create").reset();
						$('#createModal').modal('hide');
					}else {
						for (x of response.result) {
							$.notify(x, "error");
						}
						
						for (x of response.idTags) {
							$(x).addClass("form-fill-error");
						}
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
					$.notify("Status 405", "error");
				}
			});

	}
	
	function validateId(id) {
		var valid;
		var numbers = /^[0-9]+$/;
		var idLength = 13;
		
		if(id.match(numbers)) {
			valid = true;
		}else {
			$.notify("Heads up! Please input numeric characters only.", "error");
			$('#id').addClass("form-fill-error");
			valid = false;
		}
		
		if(id.length != idLength) {
			$.notify("Heads up! Please input 13 characters.", "error");
			$('#id').addClass("form-fill-error");
			valid = false;
		}
		
		return valid;
	}
	
	function validateEmptyFields()
	{
		var id = document.forms["create"]["id"].value;
		var cName = document.forms["create"]["cName"].value;
		var cSurname = document.forms["create"]["cSurname"].value;
		var cEmail = document.forms["create"]["cEmail"].value;
		var cTelH = document.forms["create"]["cTelH"].value;
		var cTelW = document.forms["create"]["cTelW"].value;
		var cTelCell = document.forms["create"]["cTelCell"].value;
		
		if (id == "" || cName == "" || cSurname == "" || cEmail == "" || cTelH == "" || cTelW == "" || cTelCell == "") {
			displayFormBorder(id, cName, cSurname, cEmail, cTelH, cTelW, cTelCell);
			$.notify("Heads up! All fields must be filled out.", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function displayFormBorder(id, cName, cSurname, cEmail, cTelH, cTelW, cTelCell)
	{
		if(!id)
		{
			$('#id').addClass("form-fill-error");
			$('#uId').addClass("form-fill-error");
		}	
		
		if(!cName)
		{
			$('#cName').addClass("form-fill-error");
			//$('#uName').addClass("form-fill-error");
		}
		
		if(!cSurname)
		{
			$('#cSurname').addClass("form-fill-error");
			//$('#uSurname').addClass("form-fill-error");
		}
		
		if(!cEmail)
		{
			$('#cEmail').addClass("form-fill-error");
			//$('#uEmail').addClass("form-fill-error");
		}
		
		if(!cTelH)
		{
			$('#cTelH').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}
		
		if(!cTelW)
		{
			$('#cTelW').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}
		
		if(!cTelCell)
		{
			$('#cTelCell').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}
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
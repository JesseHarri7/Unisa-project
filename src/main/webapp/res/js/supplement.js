$(document).ready(function()
{
	var dataSet = [];
	var count = 0;
	
	//All data fields on start up
	findAll();
	
	//Get data from the supplier table
	populateSuppDropDown();
	
	//Add temps html files
	includeHTML();
	
	//find all
	function findAll() {
		$.ajax({
			url:"/altHealth/supplement/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data)
			{
				dataSet = data;
				
				suppList(dataSet);
				
			}
		});
	}		
	
	function suppList(dataSet) {
		var suppTable = $("#supplement-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'supplementId'},
				{data: 'supplierId'},
				{data: 'supplementDescription'},
				{data: 'costExcl'},
				{data: 'costIncl'},
				{data: 'minLevels'},
				{data: 'currentStockLevels'},
				{data: 'nappiCode'}
			]
		});
		
		return suppTable;
	}
	
	//Select
	$('#supplement-table tbody').on('click','tr', function() {
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
		
		//var requiredFields = validateEmptyFields();
		
		if(validateEmptyFields() && validateNumberFields()){
			create();
		}
	});
	
	function create()
	{
		dataSet = [];
		var table = $('#supplement-table').DataTable();
		
		var supplementId = $('#id').val();
		var supplierId = $('#supplierId').val();
		var supplementDescription = $('#supplementDescription').val();
		var costExcl = $('#costExcl').val();
		var minLevels = $('#minLevels').val();
		var currentStockLevels = $('#currentStockLevels').val();
		var nappiCode = $('#nappiCode').val();
		
		costExcl = costExcl.substr(1);
		
		//Set as object
		var supplement = {supplementId, supplierId, supplementDescription, costExcl, minLevels, currentStockLevels, nappiCode};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(supplement);
		
		$.ajax( {
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/supplement/formCreateBtn", 
			dataType: "json",
			data: data_json,
			type: "POST",
			success: function(response)
			{
				if(response.status == "true") {
					$.notify(response.msg, "success");
				
					table.row.add(response.result).draw()
					
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
	
	function populateSuppDropDown(){
		$.ajax({
			url:"/altHealth/supplier/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data)
			{
				var li="";
				for (supp of data) {
					li+='<option>'+supp.supplierId+'</option>';
				}
				
				$('#supplierId').append(li);
			}
		});
	}
	
	function validateEmptyFields() {
		var id = document.forms["create"]["id"].value;
		var supplierId = document.forms["create"]["supplierId"].value;
		var supplementDescription = document.forms["create"]["supplementDescription"].value;		
		var costExcl = document.forms["create"]["costExcl"].value;
		var minLevels = document.forms["create"]["minLevels"].value;
		var currentStockLevels = document.forms["create"]["currentStockLevels"].value;
		//var nappiCode = document.forms["create"]["nappiCode"].value;
		
		if (id == "" || supplierId == "" || supplementDescription == "" || costExcl == "" || minLevels == "" || currentStockLevels == "" || nappiCode == "") {
			displayFormBorder(id, supplierId, supplementDescription, costExcl, minLevels, currentStockLevels);
			$.notify("Heads up! All required fields must be filled out.", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function displayFormBorder(id, supplierId, supplementDescription, costExcl, minLevels, currentStockLevels) {
		if(!id)
		{
			$('#id').addClass("form-fill-error");
			//$('#uId').addClass("form-fill-error");
		}	
		
		if(!supplierId)
		{
			$('#supplierId').addClass("form-fill-error");
			//$('#uName').addClass("form-fill-error");
		}
		
		if(!supplementDescription)
		{
			$('#supplementDescription').addClass("form-fill-error");
			//$('#uSurname').addClass("form-fill-error");
		}
		
		if(!costExcl)
		{
			$('#costExcl').addClass("form-fill-error");
			//$('#uEmail').addClass("form-fill-error");
		}
		
		if(!minLevels)
		{
			$('#minLevels').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}
		
		if(!currentStockLevels)
		{
			$('#currentStockLevels').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}

	}
	
	function validateNumberFields() {
		//var costExcl = document.forms["create"]["costExcl"].value;
		var minLevels = document.forms["create"]["minLevels"].value;
		var currentStockLevels = document.forms["create"]["currentStockLevels"].value;
		
		var valid = true;
		var numbers = /^[0-9]+$/;
		
/*		if(!costExcl.match(numbers)) {
			$('#costExcl').addClass("form-fill-error");
			valid = false;
		} */
		
		if(!minLevels.match(numbers)) {
			$('#minLevels').addClass("form-fill-error");
			valid = false;
		}
		
		if(!currentStockLevels.match(numbers)) {
			$('#currentStockLevels').addClass("form-fill-error");
			valid = false;
		}
		
		if(!valid){
			$.notify("Heads up! Please input numeric characters only.", "error");
		}
			
		return valid;
	}
	
	function clearFormBorder()
	{
		//create form
		$('#id').removeClass("form-fill-error");
		$('#supplierId').removeClass("form-fill-error");
		$('#supplementDescription').removeClass("form-fill-error");
		$('#costExcl').removeClass("form-fill-error");
		$('#minLevels').removeClass("form-fill-error");
		$('#currentStockLevels').removeClass("form-fill-error");
		$('#nappiCode').removeClass("form-fill-error");
		
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
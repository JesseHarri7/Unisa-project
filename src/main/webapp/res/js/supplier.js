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
			url:"/altHealth/supplier/findAll", 
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
		var suppTable = $("#supplier-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'supplierId'},
				{data: 'contactPerson'},
				{data: 'supplierTel'},
				{data: 'supplierEmail'},
				{data: 'bank'},
				{data: 'bankCode'},
				{data: 'supplierBankNum'},
				{data: 'supplierTypeBankAccount'}
			]
		});
		
		return suppTable;
	}
	
	//Select
	$('#supplier-table tbody').on('click','tr', function() {
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
		
		var requiredFields = validateEmptyFields();
		
		if(requiredFields){
			create();
		}
	});
	
	function create()
	{
		dataSet = [];
		var table = $('#supplier-table').DataTable();
		
		var supplierId = $('#id').val();
		var contactPerson = $('#contactPerson').val();
		var supplierEmail = $('#supplierEmail').val();
		var supplierTel = $('#supplierTel').val();
		var supplierBankNum = $('#supplierBankNum').val();
		var supplierTypeBankAccount = $('#supplierTypeBankAccount').val();
		var bank = $('#bank').val();
		var bankCode = $('#bankCode').val();
		
		//Set as object
		var supplier = {supplierId, contactPerson, supplierEmail, supplierTel, supplierBankNum, supplierTypeBankAccount, bank, bankCode};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(supplier);
		
		$.ajax( {
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/supplier/formCreateBtn", 
			dataType: "json",
			data: data_json,
			type: "POST",
			success: function(response)
			{
				if(response.status == "true") {
					$.notify(response.msg, "success");
				
					table.row.add(supplier).draw()
					
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
	
	function validateEmptyFields() {
		var id = document.forms["create"]["id"].value;
		var contactPerson = document.forms["create"]["contactPerson"].value;
		var supplierEmail = document.forms["create"]["supplierEmail"].value;		
		var supplierBankNum = document.forms["create"]["supplierBankNum"].value;
		var supplierTypeBankAccount = document.forms["create"]["supplierTypeBankAccount"].value;
		var bank = document.forms["create"]["bank"].value;
		var bankCode = document.forms["create"]["bankCode"].value;
		
		if (id == "" || contactPerson == "" || supplierEmail == "" || supplierBankNum == "" || supplierTypeBankAccount == "" || bank == "" || bankCode == "") {
			displayFormBorder(id, contactPerson, supplierEmail, supplierBankNum, supplierTypeBankAccount, bank, bankCode);
			$.notify("Heads up! All required fields must be filled out.", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function displayFormBorder(id, contactPerson, supplierEmail, supplierBankNum, supplierTypeBankAccount, bank, bankCode) {
		if(!id)
		{
			$('#id').addClass("form-fill-error");
			//$('#uId').addClass("form-fill-error");
		}	
		
		if(!contactPerson)
		{
			$('#contactPerson').addClass("form-fill-error");
			//$('#uName').addClass("form-fill-error");
		}
		
		if(!supplierEmail)
		{
			$('#supplierEmail').addClass("form-fill-error");
			//$('#uSurname').addClass("form-fill-error");
		}
		
		if(!supplierBankNum)
		{
			$('#supplierBankNum').addClass("form-fill-error");
			//$('#uEmail').addClass("form-fill-error");
		}
		
		if(!supplierTypeBankAccount)
		{
			$('#supplierTypeBankAccount').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}
		
		if(!bank)
		{
			$('#bank').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}
		
		if(!bankCode)
		{
			$('#bankCode').addClass("form-fill-error");
			//$('#uDateStart').addClass("form-fill-error");
		}

	}
	
	function clearFormBorder()
	{
		//create form
		$('#id').removeClass("form-fill-error");
		$('#contactPerson').removeClass("form-fill-error");
		$('#supplierEmail').removeClass("form-fill-error");
		$('#supplierTel').removeClass("form-fill-error");
		$('#supplierBankNum').removeClass("form-fill-error");
		$('#supplierTypeBankAccount').removeClass("form-fill-error");
		$('#bank').removeClass("form-fill-error");
		$('#bankCode').removeClass("form-fill-error");
		
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
		$('#supplierNav').addClass('active');
		/*var url = window.location.pathname;
		
		if(url == "/assetManagement/pages/asset")
		{
			$('#aNav').addClass('active');
		}*/
	}

});
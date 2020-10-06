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
	} );
	
	//Create button
	$('#create-btn').click(function(event) {
		//Clear form red border css
		clearFormBorder();
		
		//Clear form content if any
		document.getElementById("create").reset();
		
		$('.notifyjs-corner').remove();
	});
	
	//Modal form create button
	$('#form-create-btn').click(function(event) {
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		
		var requiredFields = validateEmptyFields();
		
		if(requiredFields){
			create();
		}
	});
	
	function create() {
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
	
	//Update button
	$('#edit-btn').click(function(event) {
		//Clear form red border css
		clearFormBorder();
		
		//Clear form content if any
		document.getElementById("update").reset();
		
		$('.notifyjs-corner').remove();
		
		var table = $('#supplier-table').DataTable();

		//Get data of the selected row
		var update = table.rows( '.selected' ).data();
		if(update.length != 0) {
			if (update.length > 1) {
				$.notify("Heads up! Please select only one supplier to update.", "error");
			}else{
				//Data in the update variable gets saved as an object
				//Take that data and display it in the modal form
				displaySupplier(update[0]);
				$('#updateModal').modal('show');
			}
		}else {	
			$.notify("Heads up! Please select a supplier to edit.", "error");
		}
	});
	
	//Modal form update button
	$('#form-update-btn').click(function(event) {
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		
		var requiredFields = validateUpdateEmptyFields();
		
		if(requiredFields){
			update();
		}
	});
	
	function update() {
		dataSet = [];
		var table = $('#supplier-table').DataTable();
		
		var supplierId = $('#uid').val();
		var contactPerson = $('#ucontactPerson').val();
		var supplierEmail = $('#usupplierEmail').val();
		var supplierTel = $('#usupplierTel').val();
		var supplierBankNum = $('#usupplierBankNum').val();
		var supplierTypeBankAccount = $('#usupplierTypeBankAccount').val();
		var bank = $('#ubank').val();
		var bankCode = $('#ubankCode').val();
		
		//Set as object
		var supplier = {supplierId, contactPerson, supplierEmail, supplierTel, supplierBankNum, supplierTypeBankAccount, bank, bankCode};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(supplier);
		
		$.ajax( {
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/supplier/formUpdateBtn", 
			dataType: "json",
			data: data_json,
			type: "POST",
			success: function(response) {
				if(response.status == "true") {
					$.notify(response.msg, "success");
				
					table.row( '.selected' ).data(response.result).draw();
					
					//Clear data from the modal form
					document.getElementById("update").reset();
					$('#updateModal').modal('hide');
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
	
	function validateUpdateEmptyFields() {
		var id = document.forms["update"]["uid"].value;
		var contactPerson = document.forms["update"]["ucontactPerson"].value;
		var supplierEmail = document.forms["update"]["usupplierEmail"].value;		
		var supplierBankNum = document.forms["update"]["usupplierBankNum"].value;
		var supplierTypeBankAccount = document.forms["update"]["usupplierTypeBankAccount"].value;
		var bank = document.forms["update"]["ubank"].value;
		var bankCode = document.forms["update"]["ubankCode"].value;
		
		if (id == "" || contactPerson == "" || supplierEmail == "" || supplierBankNum == "" || supplierTypeBankAccount == "" || bank == "" || bankCode == "") {
			displayFormBorder(id, contactPerson, supplierEmail, supplierBankNum, supplierTypeBankAccount, bank, bankCode);
			$.notify("Heads up! All required fields must be filled out.", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function displaySupplier(supplier){
		document.forms["update"]["uid"].value = supplier.supplierId;
		document.forms["update"]["ucontactPerson"].value = supplier.contactPerson;
		document.forms["update"]["usupplierEmail"].value = supplier.supplierEmail;
		document.forms["update"]["usupplierTel"].value = supplier.supplierTel;
		document.forms["update"]["usupplierBankNum"].value = supplier.supplierBankNum;
		document.forms["update"]["usupplierTypeBankAccount"].value = supplier.supplierTypeBankAccount;
		document.forms["update"]["ubank"].value = supplier.bank;
		document.forms["update"]["ubankCode"].value = supplier.bankCode;
	}
	
	function displayFormBorder(id, contactPerson, supplierEmail, supplierBankNum, supplierTypeBankAccount, bank, bankCode) {
		if(!id) {
			$('#id').addClass("form-fill-error");
			$('#uid').addClass("form-fill-error");
		}	
		
		if(!contactPerson) {
			$('#contactPerson').addClass("form-fill-error");
			$('#ucontactPerson').addClass("form-fill-error");
		}
		
		if(!supplierEmail) {
			$('#supplierEmail').addClass("form-fill-error");
			$('#usupplierEmail').addClass("form-fill-error");
		}
		
		if(!supplierBankNum) {
			$('#supplierBankNum').addClass("form-fill-error");
			$('#usupplierBankNum').addClass("form-fill-error");
		}
		
		if(!supplierTypeBankAccount) {
			$('#supplierTypeBankAccount').addClass("form-fill-error");
			$('#usupplierTypeBankAccount').addClass("form-fill-error");
		}
		
		if(!bank) {
			$('#bank').addClass("form-fill-error");
			$('#ubank').addClass("form-fill-error");
		}
		
		if(!bankCode) {
			$('#bankCode').addClass("form-fill-error");
			$('#ubankCode').addClass("form-fill-error");
		}

	}
	
	function clearFormBorder() {
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
		$('#uid').removeClass("form-fill-error");
		$('#ucontactPerson').removeClass("form-fill-error");
		$('#usupplierEmail').removeClass("form-fill-error");
		$('#usupplierTel').removeClass("form-fill-error");
		$('#usupplierBankNum').removeClass("form-fill-error");
		$('#usupplierTypeBankAccount').removeClass("form-fill-error");
		$('#ubank').removeClass("form-fill-error");
		$('#ubankCode').removeClass("form-fill-error");
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
		$('#supplierNav').addClass('active');
		/*var url = window.location.pathname;
		
		if(url == "/assetManagement/pages/asset")
		{
			$('#aNav').addClass('active');
		}*/
	}

});
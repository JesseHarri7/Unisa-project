$(document).ready(function() {
	var dataSet = [];
	var count = 0;
	
	//All data fields on start up
	findAll();
	
	//Get data from the ref table
	populateRefDropDown();
	
	//Add temps html files
	includeHTML();
	
	//find all
	function findAll() {
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
	$('#client-table tbody').on('click','tr', function() {
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
	
	function clientList(dataSet) {
		var clientTable = $("#client-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'cName'},
				{data: 'cSurname'},
				{data: 'cTelH'},
				{data: 'cTelW'},
				{data: 'cTelCell'},
				{data: 'cEmail'},
				{data: 'address'},
				{data: 'code'},
				{data: 'referenceId'}
			]
		});
		
		if(localStorage.length > 0) {
			var localData = JSON.parse(localStorage.getItem('clientInfoReport'));
			
			if(localData){
				var clientID = localData.clientId;
				var table = $('#client-table').DataTable();
				table.search(clientID).draw();
				resetLocal()
			}
			

		}
		
		return clientTable;
	}
	
	function populateRefDropDown(){
		$.ajax({
			url:"/altHealth/reference/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data)
			{
				var li="";
				for (ref of data) {
					li+='<option value="'+ref.referenceId+'">'+ref.description+'</option>';
				}
				
				$('#refId').append(li);
				$('#urefId').append(li);
			}
		});
	}
	
	//Create button
	$('#create-btn').click(function(event) {		
		//Clear form red border css
		clearFormBorder();
		
		//Clear form content if any
		document.getElementById("create").reset();
		
		$('.notifyjs-corner').remove();
	});
	
	//Edit button
	$('#edit-btn').click(function(event) {		
		//Clear form red border css
		clearFormBorder();
		
		//Clear form content if any
		document.getElementById("update").reset();
		
		$('.notifyjs-corner').remove();
		
		var table = $('#client-table').DataTable();

		//Get data of the selected row
		var update = table.rows( '.selected' ).data();
		if(update.length != 0) {
			if (update.length > 1) {
				$.notify("Heads up! Please select only one client to update.", "error");
			}else{
				//Data in the update variable gets saved as an object
				//Take that data and display it in the modal form
				displayClient(update[0]);
				$('#updateModal').modal('show');
			}
		}else {	
			$.notify("Heads up! Please select a client to edit.", "error");
		}
		
	});
	
	//Modal form create button
	$('#form-create-btn').click(function(event) {
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
	
	function create() {
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
	
	function update() {
		dataSet = [];
		var table = $('#client-table').DataTable();
		
		var clientId = $('#uid').val();
		var cName = $('#ucName').val();
		var cSurname = $('#ucSurname').val();
		var cEmail = $('#ucEmail').val();
		var cTelH = $('#ucTelH').val();
		var cTelW = $('#ucTelW').val();
		var cTelCell = $('#ucTelCell').val();
		var address = $('#uaddress').val();
		var code = $('#ucode').val();
		var referenceId = $('#urefId').val();
		
		//Set as object
		var client = {clientId, cName, cSurname, cEmail, cTelH, cTelW, cTelCell, address, code, referenceId};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(client);
		
		//if(exists.length == 0)
		//{
			$.ajax( {
				headers: {
			        'Accept': 'application/json',
			        'Content-Type': 'application/json' 
			    },
				url:"/altHealth/client/formUpdateBtn", 
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
	
	function editFromReport(){
		if(localStorage.length > 0) {
			var localData = JSON.parse(localStorage.getItem('clientInfoReport'));
			
			if(localData){
				var clientID = localData.clientId;
				var table = $('#client-table').DataTable();
				table.search(clientID).draw();
				resetLocal()
			}
		}
	}
	
	//Add client to cart
	$('#addToCart-btn').click(function(event) {
		var table = $('#client-table').DataTable();			

		//Returns an array of the selected rows
		var clientToAdd = table.rows( '.selected' ).data();
		
		$('.notifyjs-corner').remove();
		
		if (clientToAdd.length == 0) {
			$.notify("Heads up! Please select client to add to the cart.", "error");
		}
		else if (clientToAdd.length >= 2) {
			$.notify("Heads up! Please only select one client to add.", "error");
		}else {
			addClientToSession(clientToAdd);
		}
		$('tr.selected').toggleClass('selected');
		
	});
	
	function addClientToSession(clientToAdd){
		
		//Set as object
		var client = clientToAdd[0];
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(client);
		
		$.ajax(
		{
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/client/addClientToCart", 
			dataType: "json",
			data: data_json,
			type: "POST",
			success: function(response) {
				if(response.status == "true") {
					$.notify(response.msg, "success");
				}else {
					for (x of response.result) {
						$.notify(x, "error");
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
	
	function validateEmptyFields() {
		var id = document.forms["create"]["id"].value;
		var cName = document.forms["create"]["cName"].value;
		var cSurname = document.forms["create"]["cSurname"].value;
		var cEmail = document.forms["create"]["cEmail"].value;
		var address = document.forms["create"]["address"].value;
		var code = document.forms["create"]["code"].value;
		//var cTelCell = document.forms["create"]["cTelCell"].value;
		
		if (id == "" || cName == "" || cSurname == "" || cEmail == "" || address == "" || code == "") {
			displayFormBorder(id, cName, cSurname, cEmail, address, code);
			$.notify("Heads up! All required fields must be filled out.", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function validateUpdateEmptyFields() {
		var id = document.forms["update"]["uid"].value;
		var cName = document.forms["update"]["ucName"].value;
		var cSurname = document.forms["update"]["ucSurname"].value;
		var cEmail = document.forms["update"]["ucEmail"].value;
		var address = document.forms["update"]["uaddress"].value;
		var code = document.forms["update"]["ucode"].value;
		
		if (id == "" || cName == "" || cSurname == "" || cEmail == "" || address == "" || code == "") {
			displayFormBorder(id, cName, cSurname, cEmail, address, code);
			$.notify("Heads up! All required fields must be filled out.", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function displayFormBorder(id, cName, cSurname, cEmail, address, code) {
		if(!id)
		{
			$('#id').addClass("form-fill-error");
			$('#uid').addClass("form-fill-error");
		}	
		
		if(!cName)
		{
			$('#cName').addClass("form-fill-error");
			$('#ucName').addClass("form-fill-error");
		}
		
		if(!cSurname)
		{
			$('#cSurname').addClass("form-fill-error");
			$('#ucSurname').addClass("form-fill-error");
		}
		
		if(!cEmail)
		{
			$('#cEmail').addClass("form-fill-error");
			$('#ucEmail').addClass("form-fill-error");
		}
		
		if(!address)
		{
			$('#address').addClass("form-fill-error");
			$('#uaddress').addClass("form-fill-error");
		}
		
		if(!code)
		{
			$('#code').addClass("form-fill-error");
			$('#ucode').addClass("form-fill-error");
		}
	}
	
	function displayClient(client){
		document.forms["update"]["uid"].value = client.clientId;
		document.forms["update"]["ucName"].value = client.cName;
		document.forms["update"]["ucSurname"].value = client.cSurname;
		document.forms["update"]["ucEmail"].value = client.cEmail;
		document.forms["update"]["ucTelH"].value = client.cTelH;
		document.forms["update"]["ucTelW"].value = client.cTelW;
		document.forms["update"]["ucTelCell"].value = client.cTelCell;
		document.forms["update"]["uaddress"].value = client.address;
		document.forms["update"]["ucode"].value = client.code;
		document.forms["update"]["urefId"].value = client.refId;
	}
	
	function clearFormBorder() {
		//create form
		$('#id').removeClass("form-fill-error");
		$('#cName').removeClass("form-fill-error");
		$('#cSurname').removeClass("form-fill-error");
		$('#cEmail').removeClass("form-fill-error");
		$('#cTelH').removeClass("form-fill-error");
		$('#cTelW').removeClass("form-fill-error");
		$('#cTelCell').removeClass("form-fill-error");
		$('#address').removeClass("form-fill-error");
		$('#code').removeClass("form-fill-error");
		$('#refId').removeClass("form-fill-error");
		
		//Update form
		$('#uid').removeClass("form-fill-error");
		$('#ucName').removeClass("form-fill-error");
		$('#ucSurname').removeClass("form-fill-error");
		$('#ucEmail').removeClass("form-fill-error");
		$('#ucTelH').removeClass("form-fill-error");
		$('#ucTelW').removeClass("form-fill-error");
		$('#ucTelCell').removeClass("form-fill-error");
		$('#uaddress').removeClass("form-fill-error");
		$('#ucode').removeClass("form-fill-error");
		$('#urefId').removeClass("form-fill-error");
	}
	
	//Notify class
	function createNotify() {
		//add a new style 'foo'
		$.notify.addStyle('foo', {
		  html: 
		    "<div>" +
		      "<div class='clearfix'>" +
		        "<div class='title' data-notify-html='title'/>" +
		        "<div class='buttons'>" +
		          "<button class='btn btn-secondary no'>Cancel</button>" +
		          "<button class='btn btn-secondary yes' data-notify-text='button'></button>" +
		        "</div>" +
		      "</div>" +
		    "</div>"
		});
	}
	
	//listen for click events from this style
	//If no
	$(document).on('click', '.notifyjs-foo-base .no', function() {
		//programmatically trigger propogating hide event
		$(this).trigger('notify-hide');
		
	});

	//if Yes
	$(document).on('click', '.notifyjs-foo-base .yes', function() {	
		//Function
		updateVat();
		//hide notification
		$(this).trigger('notify-hide');
		
	});
	
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
		$('#clientNav').addClass('active');
		/*var url = window.location.pathname;
		
		if(url == "/assetManagement/pages/asset")
		{
			$('#aNav').addClass('active');
		}*/
	}
	
	function resetLocal() {
		localStorage.clear();
	}

});
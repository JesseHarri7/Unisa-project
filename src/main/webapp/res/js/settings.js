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
			url:"/altHealth/sysParameters/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data)
			{
				dataSet = data;
				
				settingsList(dataSet[0]);
				
			}
		});
	}		
	
	function settingsList(dataSet) {
		/*
		var table = $("#settings-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'address'},
				{data: 'telNo'},
				{data: 'email'},
				{data: 'emailPass'},
				{data: 'vatPercent'}
			]
		});
		
		return table;
		*/
		document.forms["settings"]["address"].value = dataSet.address;
		document.forms["settings"]["telNo"].value = dataSet.telNo;
		document.forms["settings"]["email"].value = dataSet.email;
		document.forms["settings"]["emailPass"].value = dataSet.emailPass;
		document.forms["settings"]["vatPercent"].value = dataSet.vatPercent;
	}
	
	//Select
	$('#settings-table tbody').on('click','tr', function() {
		$(this).toggleClass('selected');
	} );
	
	//Form update button
	$('#edit-btn').click(function(event) 
	{
		//Clear form red border css
		clearFormBorder();
		//Clear .notify space
		$('.notifyjs-corner').remove();
		
		var table = $('#settings-table').DataTable();

		//Get data of the selected row
		var update = table.row( '.selected' ).data();
		if(update) {
			//Data in the update variable gets saved as an object
			//Take that data and display it in the modal form
			displayUpdateModel(update);
			$('#updateModal').modal('show');
		}else {	
			$.notify("Heads up! Please select an field to edit.", "error");
		}
	});
	
	function displayUpdateModel(update) {
		
		document.forms["update"]["vatPercent"].value = update.vatPercent;
		
	}
	
	//Modal form update button
	$('#form-update-btn').click(function(event) 
	{
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		//Check to see if all fields are filled in
		if(validateUpdate())
		{
			//if all fields are filled then update asset
			update();
		}
	});
	
	function validateUpdate()
	{
		var address = document.forms["settings"]["address"].value;
		var telNo = document.forms["settings"]["telNo"].value;
		var email = document.forms["settings"]["email"].value;
		var emailPass = document.forms["settings"]["emailPass"].value;
		var vatPercent = document.forms["settings"]["vatPercent"].value;
		 
	    if(vatPercent == "" || address == "" || telNo == "" || email == "" || emailPass == "") {
	    	displayFormBorder(vatPercent, address, telNo, email, emailPass);
	    	$.notify("Heads up! All fields must be filled out.", "error");
	        return false;
	    }else {
	    	return true;
	    }
	}
	
	function update() {
		var table = $('#settings-table').DataTable();
		
		var address = document.forms["settings"]["address"].value;
		var telNo = document.forms["settings"]["telNo"].value;
		var email = document.forms["settings"]["email"].value;
		var emailPass = document.forms["settings"]["emailPass"].value;
		var vatPercent = document.forms["settings"]["vatPercent"].value;
		
		var sysParameters = {vatPercent, address, telNo, email, emailPass};
		
		var data_json = JSON.stringify(sysParameters);
		
		$.ajax({
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			url:"/altHealth/sysParameters/formUpdateBtn", 
			dataType: "json",
			data: data_json,
			type: "PUT",
			success: function(response){
				if(response.status == "true") {
					$.notify(response.msg, "success");
					
					table.row( '.selected' ).data(sysParameters).draw();
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
	
	//Modal form update button
	$('#update-vat-btn').click(function(event) 
	{
		$('.notifyjs-corner').remove();
		
		createNotify();
		
		$.notify({
				  title: 'Are you sure you want to update all?',
				  button: 'Confirm'
				}, {
				  style: 'foo',
				  className: 'info',
				  autoHide: false,
				  clickToHide: false
				});
	});
	
	
	function displayFormBorder(vatPercent, address, telNo, email, emailPass) {
		if(!vatPercent) {
			$('#vatPercent').addClass("form-fill-error");
		}
		if(!address) {
			$('#address').addClass("form-fill-error");
		}
		if(!telNo) {
			$('#telNo').addClass("form-fill-error");
		}
		if(!email) {
			$('#email').addClass("form-fill-error");
		}
		if(!emailPass) {
			$('#emailPass').addClass("form-fill-error");
		}
	}
	
	function clearFormBorder()
	{
		//create form
		$('#vatPercent').removeClass("form-fill-error");
		$('#address').removeClass("form-fill-error");
		$('#telNo').removeClass("form-fill-error");
		$('#email').removeClass("form-fill-error");
		$('#emailPass').removeClass("form-fill-error");
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
	
	function updateVat(){
		$.ajax({
			url:"/altHealth/sysParameters/formUpdateVatBtn", 
			dataType: "json",
			type: "GET",
			success: function(response)
			{
				if(response.status == "true") {
					$.notify(response.msg, "success");
				}else {
					for (x of response.result) {
						$.notify(x, "warn");
					}
				}
			}
		});
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
		$('#settingsNav').addClass('active');
	}

});
$(document).ready(function()
{
	//Add template files
	includeHTML();
	
	var birthdays = findBirthdays();
	
	//Reporting stats
	var allBirthdays = reportBirthdays(birthdays);
	
	//Find all birthdays from the database
	function findBirthdays()
	{
		var dataSet = [];
		
		$.ajax({
			url:"/altHealth/report/birthdaysForToday", 
			dataType: "json",
			async: false,
			type: "GET",
			success: function(data)
			{
				dataSet = data;
			},
			error: function(data)
			{
				dataSet = "Error";
			}
		});
		
		return dataSet;
	}
	
	function showActiveNav()
	{
		$('#birthdayNav').addClass('active');
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
	
	//Set birthdays to hidden table
	function reportBirthdays(birthdays) {
		
		var dataSet = [];
		
		dataSet = birthdays;
		
		//Date for file names
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		
		today = yyyy + '-' + mm + '-' + dd;
		
		var aaTable = $("#tblBirthdays").DataTable({
			dom: '<f<t>lip>',
			buttons: [
				   {
					   extend: 'excel',
					   title: 'Birthdays-for-today',
					   filename: 'Birthdays-for-today_' + today
				   }
				],
			retrieve: true,
			responsive: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'clientName'}
			]
		});
		
		return dataSet;
	}

	$(document).on('click', '.downloadReport', function() 
	{		
		var birthdays = $("#tblBirthdays").DataTable();
		
		//var data = test.buttons.exportData();
		
		birthdays.button( '0' ).trigger();
		
	});
	
});
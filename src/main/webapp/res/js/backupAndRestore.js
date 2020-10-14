$(document).ready(function()
{
	//All data fields on start up
	findAll();
	
	//Add temps html files
	includeHTML();
	
	//find all
	function findAll() {
		$.ajax({
			url:"/altHealth/backupAndRestore/findAll", 
			dataType: "json",
			type: "GET",
			success: function(data) {
				var fileNameList = [];
				for (x of data.resultList) {
					var fileObj = {fileName:x};
					fileNameList.push(fileObj);
				}
				dataTable(fileNameList);
				
			}
		});
	}		
	
	//Select
	$('#backup-table tbody').on('click','tr', function() {
		$(this).toggleClass('selected');
	} );
	
	function dataTable(data) {
		var clientTable = $("#backup-table").DataTable({
			dom: '<f<t>lip>',
			retrieve: true,
			responsive: true,
			select: true,
			data: data,
			columns: 
			[
				{data: 'fileName'},
			]
		});
		
		//return clientTable;
	}
	
	//Create button
	$('#create-btn').click(function(event) {
		$('.notifyjs-corner').remove();
		var table = $('#backup-table').DataTable();
		
		$.ajax({
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/backupAndRestore/createBackup", 
			dataType: "json",
			type: "POST",
			success: function(response) {
				if(response.status == "true") {
					$.notify(response.msg, "success");
					var fileObj = {fileName:response.result};
				
					table.row.add(fileObj).draw();
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
	});
	
	//Restore button
	$('#restore-btn').click(function(event) {
		$('.notifyjs-corner').remove();
		var table = $('#backup-table').DataTable();
		//Get data of the selected row
		var selected = table.rows( '.selected' ).data();
		
		if(selected.length != 0) {
			if (selected.length > 1) {
				$.notify("Heads up! Please select only one file to restore.", "error");
			}else{
				restore(selected[0].fileName);
			}
		}else {	
			$.notify("Heads up! Please select a file to restore.", "error");
		}
	});
	
	function restore(selected){
		
		var data_json = JSON.stringify(selected);
		
		$.ajax({
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/backupAndRestore/restoreDB/"+selected,
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
		$('#backupAndRestoreNav').addClass('active');
	}

});
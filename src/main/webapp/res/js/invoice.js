
$(document).ready(function() {

var fadeTime = 300;

	//Add temps html files
	includeHTML(); 
	
	//Company info
	getSettings();
	
	//Display Selected Invoice
	getSelectedInv();
	
	//search button
	$('#searchInv-btn').click(function(event) {
		var invNum = $('#sInvNum').val();
		
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		
		if(validateEmpty(invNum)){
			getCartInfo(invNum)
		}
	});
	
	//Invoice list button
	$('#invList-btn').click(function(event) {		
		//Clear form red border css
		clearFormBorder();
		$('.notifyjs-corner').remove();
		
		window.location = "../invoiceList/";
	});
	
	//Get cart info
	function getCartInfo(invNum) {
		$.ajax({
			url:"/altHealth/invoice/getInvoiceInfo/" + invNum, 
			dataType: "json",
			type: "GET",
			success: function(response) {
				
				if(response.status == "true") {
					clearItemsTable();
					//Client
					clientInfoTable(response.clientInfo);
					
					//Invoice
					invoiceInfo(response.invoiceInfo);
					
					//Invoice Items
					var itemsDataSet = [response.invoiceItems];
					cartItemsTable(itemsDataSet[0]);
					
				}else {
					for (x of response.result) {
						$.notify(x, "error");
					}
				}
				
				recalculateCart();
			},
			error : function(e) {
				console.log("ERROR: ", e);
				$.notify("Status 405", "error");
			}
		});		
	}
	
	function clientInfoTable(dataSet){
		$('#cNameSurname').html(dataSet.cName + ' ' + dataSet.cSurname);
		$('#clientId').html(dataSet.clientId);
		$('#cAddress').html(dataSet.address);
		$('#cTelCell').html(dataSet.cTelCell);
		$('#cEmail').html(dataSet.cEmail);
	}
	
	function invoiceInfo(dataSet){
		$('#invNum').html(dataSet.invNum);
		$('#invNum2').html(dataSet.invNum);
		$('#invDate').html(dataSet.invDate);
		$('#invPaidDate').html(dataSet.invPaidDate);
		$('#invPaid').html(dataSet.invPaid);
	}
	
	function cartItemsTable(itemsDataSet){
		var tr="";
		for (ref of itemsDataSet) {
			tr+= '<tr class="dataRow">' +
					'<td class="no">' +
						'<h6 class="title text-truncate">' + ref.supplementId + '</h6>' +
					'</td>' +
					'<td class="text-left">' +
						ref.supplementDescription +
					'</td>' +
					'<td class="qty">' + 
						ref.itemQuantity +
					'</td>' +
					'<td class="unit costExcl">' + 
						ref.itemPrice +
					'</td>' +
					'<td class="unit total">' + 
						ref.lineTotal +
					'</td>' +
				'</tr>';
		}
		
		$('#cartItems').append(tr);
	}
	
	/* Recalculate cart */
	function recalculateCart() {
	  var subtotal = 0;
	   
	  /* Sum up row totals */
	  $('.dataRow').each(function () {
		subtotal += parseFloat($(this).children('.unit.total').text());
	  });
	   
	  /* Calculate totals */
	  var vat = $('.vat').text();
	  var vatPerc = vat / 100;
	  var tax = subtotal * vatPerc;
	  var total = subtotal + tax;
	   
	  /* Update totals display */
	  $('.totals-value').fadeOut(fadeTime, function() {
		$('#cart-subtotal').html(subtotal.toFixed(2));
		$('#cart-tax').html(tax.toFixed(2));
		$('#cart-total').html(total.toFixed(2));
		if(total == 0){
		  $('.btn btn-info').fadeOut(fadeTime);
		}else{
		  $('.btn btn-info').fadeIn(fadeTime);
		}
		$('.totals-value').fadeIn(fadeTime);
	  });
	}
	
	//find settings
	function getSettings() {
		$.ajax({
			url:"/altHealth/sysParameters/1", 
			dataType: "json",
			type: "GET",
			success: function(data) {
				$('#vat').html(data.vatPercent);
				$('#sysAddress').html(data.address);
				$('#sysTelNo').html(data.telNo);
				$('#sysEmail').html(data.email);
				
			}
		});
	}
	
	$('#printInvoice').click(function(){
		
		var html = $('.invoice')[0].outerHTML;
		var fileName = $('#invNum').text();
		var email = $('#cEmail').text();
		
		if(html != "" && fileName != "" && fileName != " "){
			if(email != "" && email != " "){
				sendPDF(fileName, html, email);
			}else{
				$.notify("Error! Client is missing email address", "error");
			}
		}else{
			$.notify("Error! cannot send a blank invoice", "error");
		}
	
	});
	
	function getSelectedInv(){
		if(localStorage.length > 0) {
			var localData = JSON.parse(localStorage.getItem('invoice'));
			
			if(localData){
				var invNum = localData.invNum;
				$('#sInvNum').html(invNum);
				getCartInfo(invNum);
				resetLocal();
			}
		}
	}
	
	function sendPDF(invNum, html, email){
		
		//Set as object
		var htmlFile = {invNum, html, email};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(htmlFile);
		
		$.ajax({
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/invoice/sendPDF",
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
	
	$('#printLocal').click(function(){	
		Popup($('.invoice')[0].outerHTML);
		function Popup(data) {
			window.print();
			return true;
		}
	});
	
	function clearItemsTable(){
		var tr="";
		$('#cartItems').html(tr);
	}
	
	function validateEmpty(invNum){
		if (invNum == "" || invNum == " ") {
			displayFormBorder(invNum);
			$.notify("Heads up! Please Enter a valid invoice number", "error");
			return false;
		}else {
			return true;
		}	
	}
	
	function displayFormBorder(invNum) {
		if(!invNum) {
			$('#sInvNum').addClass("form-fill-error");
		}
	}
	
	function clearFormBorder() {
		$('#sInvNum').removeClass("form-fill-error");
	}
	
	function resetLocal() {
		localStorage.clear();
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
		$('#invoiceNav').addClass('active');
	}
 
});
 

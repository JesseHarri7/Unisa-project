
$(document).ready(function() {
 
/* Set rates + misc */
var fadeTime = 300;
var emptyArray = [];

	//Add temps html files
	includeHTML(); 
	
	includeCurrDate();
	
	getCartInfo();
	
	getSettings();
	
	//Get cart info
	function getCartInfo() {
		$.ajax({
			url:"/altHealth/cart/getCartInfo", 
			dataType: "json",
			type: "GET",
			success: function(response) {
				
				if(response.status == "true") {
					//Client
					clientInfoTable(response.clientInfo);
					
					//Supplements
					var suppDataSet = [response.supplementList];
					cartItemsTable(suppDataSet[0]);
					
					//Invoice
					invoiceInfo(response.invoiceInfo);
					
					$('#cart-tax').html(response.VAT);
				}else {
					//If data is missing then display what is available
					for (x of response.result) {
						$.notify(x, "error");
					}
					
					if(response.clientInfo != null){
						//Client
						clientInfoTable(response.clientInfo);
					}
					
					if(response.supplementList.length > 0){
						//Supplements
						var suppDataSet = [response.supplementList];
						cartItemsTable(suppDataSet[0]);
					}
					
					if(response.invoiceInfo != null){
						//Invoice
						invoiceInfo(response.invoiceInfo);
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
	
	//Populate invoice with the clients info from the session
	function clientInfoTable(dataSet){
		$('#cNameSurname').html(dataSet.cName + ' ' + dataSet.cSurname);
		$('#clientId').html(dataSet.clientId);
		$('#cAddress').html(dataSet.address);
		$('#cTelCell').html(dataSet.cTelCell);
		$('#cEmail').html(dataSet.cEmail);
		$('#addClient-btn').addClass('hidden');
		$('#clientRm-btn').removeClass('hidden');
	}
	
	//Populate the invoice with the Invoice data from the session
	function invoiceInfo(dataSet){
		$('#invNum').html(dataSet.invNum);
		$('#invNum2').html(dataSet.invNum);
	}
	
	//Populate the invoice with the supplements from the session
	function cartItemsTable(suppDataSet){
		var tr="";
		for (ref of suppDataSet) {
			
			var dropOpt = "";
			for (i = 1; i <= ref.currentStockLevels; i++) {
				dropOpt+= '<option value="'+i+'">' + i + '</option>'
			}
			
			tr+= '<tr class="dataRow">' +
					'<td class="no">' +
						'<h6 class="title text-truncate">' + ref.supplementId + '</h6>' +
					'</td>' +
					'<td class="text-left">' +
						ref.supplementDescription +
					'</td>' +
					'<td class="qty">' + 
						'<select class="form-control" id="qty">' +
							dropOpt +
						'</select>' +
					'</td>' +
					'<td class="unit costExcl">' + 
						ref.costExcl +
					'</td>' +
					'<td class="unit total">' + 
						ref.costExcl +
					'</td>' +
					'<td class="text-right removal">' +
						'<button class="btn btn-outline-danger-item"> × Remove</button>' +
					'</td>' +
				'</tr>';
		}
		
		$('#cartItems').append(tr);
		$('#clearCart-btn').removeClass('hidden');
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
	
	//listen for click events from this style
	$(document).on('click', '.qty', function() {
		
		updateQuantity(this);
		
	});
	
	//Form update button
	$('#addSupp-btn').click(function(event) {
		window.location = "/altHealth/supplement/";
	});
	 
	$(document).on('click', '.btn.btn-outline-danger-item', function() {
	  removeItem(this);
	}); 
	
	$('#clientRm-btn').click(function(event) {
		removeClient();
	});
	
	$('#clearCart-btn').click(function(event) {
		clearCart();
	});
	
	//Form addClient button
	$('#addClient-btn').click(function(event) {
		window.location = "/altHealth/client/";
	});
	
	/* Update quantity */
	function updateQuantity(quantityInput) {
	  /* Calculate line price */
	  var productRow = $(quantityInput).parent();
	  var price = parseFloat(productRow.children('.unit.costExcl').text());
	  var quantity = $(quantityInput).children().val();
	  var linePrice = price * quantity;
	   
	  /* Update line price display and recalc cart totals */
	  productRow.children('.total').each(function () {
		$(this).fadeOut(fadeTime, function() {
		  $(this).text(linePrice.toFixed(2));
		  recalculateCart();
		  $(this).fadeIn(fadeTime);
		});
	  });  
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
 
	/* Remove item from cart */
	function removeItem(removeButton) {
	  /* Remove row from DOM and recalc cart total */
	  var productRow = $(removeButton).parent().parent();
		removeSupplementFromSession(productRow);
	}
	
	function removeSupplementFromSession(productRow){
		var supplementId = productRow.children('.no').text();
		
		$.ajax({
			url:"/altHealth/cart/removeSupplement/" + supplementId, 
			dataType: "json",
			type: "POST",
			success: function(response) {
				if(response.status == "true") {
					productRow.slideUp(fadeTime, function() {
						productRow.remove();
						recalculateCart();
					});
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
	
	function removeClient(){
		$.ajax({
			url:"/altHealth/cart/removeSessionClient/", 
			dataType: "json",
			type: "POST",
			success: function(response) {
				if(response.status == "true") {
					$.notify(response.msg, "success");
					var dataSet = {cName:"", cSurname:"", clientId:"", clientId:"", address:"", cTelCell:"", cEmail:""};

					clientInfoTable(dataSet);
					$('#clientRm-btn').addClass('hidden');
					$('#addClient-btn').removeClass('hidden');
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
	
	function clearCart(){
		$.ajax({
			url:"/altHealth/cart/clearCartSession/", 
			dataType: "json",
			type: "POST",
			success: function(response) {
				if(response.status == "true") {
					$.notify(response.msg, "success");
					var clientDataSet = {cName:"", cSurname:"", clientId:"", clientId:"", address:"", cTelCell:"", cEmail:""};
					var tr="";
					
					clientInfoTable(clientDataSet);
					invoiceInfo(response.invoiceInfo);
					$('#cartItems').html(tr);
					$('#clientRm-btn').addClass('hidden');
					$('#addClient-btn').removeClass('hidden');
					$('#clearCart-btn').addClass('hidden');
					recalculateCart();
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
	
	$('#createInvoice').click(function(){
		var fileName = $('#invNum').text();		
		var email = $('#cEmail').text();
		var html = $('.invoice')[0].outerHTML;
		
		if(html != "" && fileName != "" && fileName != " "){
			if(email != "" && email != " "){
				createInvoice();
			}else{
				$.notify("Error! Client Email is required", "error");
			}
		}else{
			$.notify("Error! cannot send a blank invoice", "error");
		}
	
	});
	
	function createInvoice(){
		var fileName = $('#invNum').text();		
		
		n =  new Date();
		y = n.getFullYear();
		m = '' + (n.getMonth() + 1);
		d = '' + n.getDate();
		
		if (m.length < 2) {
			m = '0' + m;
		}
		if (d.length < 2) {
			d = '0' + d;
		}

		var date = [y, m, d].join('-');
		//var date = y + "-" + m + "-" + d;
		
		//Invoice
		var invNum = fileName;
		var clientId = $('#clientId').text();
		var invDate = date;
		//$('#currDate').text();
		
		var invoice = {invNum, clientId, invDate};
		
		//Invoice Item
		var suppIdQty = [];
		var rows = $(".dataRow");
		
		for(row of rows){
			var inv = [];
			
			//ID
			inv.push(row.childNodes[0].innerText);
			//Qty
			var td = row.childNodes[2];
			var qty = $(td).children().val();
			inv.push(qty);
			//price	
			inv.push(row.childNodes[3].innerText);
			
			suppIdQty.push(inv);
		}
		
		var invoiceItems = new Array();
		
		for(item of suppIdQty){
			var invNum = invNum;
			var supplementId = item[0];
			var itemPrice = item[2];
			var itemQuantity = item[1];
		
			var invoiceItem = {invNum, supplementId, itemPrice, itemQuantity};
			invoiceItems.push(invoiceItem);
		}
		
		var cartModel = {invoice, invoiceItems};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(cartModel);
		
		$.ajax(
		{
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/cart/sendInvoice", 
			dataType: "json",
			data: data_json,
			type: "POST",
			success: function(response)
			{
				if(response.status == "true") {
					$.notify(response.msg, "success");
					
					//Only send PDF on successful Invoice create
					sendPDF(fileName);
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
	
	function sendPDF(invNum){
		
		var html = $('.invoice')[0].outerHTML;
		//var fileName = $('#invNum').text();
		var email = $('#cEmail').text();
		
		//Set as object
		var htmlFile = {invNum, html, email};
		
		//Translate so that JSON can read it
		var data_json = JSON.stringify(htmlFile);
		
		$.ajax({
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json' 
			},
			url:"/altHealth/cart/sendPDF",
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
	
	
	function includeCurrDate(){
		n =  new Date();
		y = n.getFullYear();
		m = n.getMonth() + 1;
		d = n.getDate();
		document.getElementById("currDate").innerHTML = d + "/" + m + "/" + y;
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
		$('#cartNav').addClass('active');
	}
 
});
 

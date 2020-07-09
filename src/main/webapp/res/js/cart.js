
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
				}else {
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
	
	function clientInfoTable(dataSet){
		$('#cNameSurname').html(dataSet.cName + ' ' + dataSet.cSurname);
		$('#clientId').html(dataSet.clientId);
		$('#cAddress').html(dataSet.address);
		$('#cTelCell').html(dataSet.cTelCell);
		$('#cEmail').html(dataSet.cEmail);
	}
	
	function invoiceInfo(dataSet){
		$('#invNum').html(dataSet.invNum);
	}
	
	function cartItemsTable(suppDataSet){
		var tr="";
		for (ref of suppDataSet) {
			tr+= '<tr class="dataRow">' +
					'<td class="no">' +
						'<h6 class="title text-truncate">' + ref.supplementId + '</h6>' +
					'</td>' +
					'<td class="text-left">' +
						ref.supplementDescription +
					'</td>' +
					'<td class="qty">' + 
						'<input type="number" class="form-control" id="qty" value="1" min="1" ' +
							'max="' + ref.currentStockLevels + '" required>' +
					'</td>' +
					'<td class="unit costExcl">' + 
						ref.costExcl +
					'</td>' +
					'<td class="unit costIncl">' + 
						ref.costIncl + 
					'</td>' +
					'<td class="unit total">' + 
						ref.costExcl +
					'</td>' +
					'<td class="text-right removal">' +
						'<button class="btn btn-outline-danger"> × Remove</button>' +
					'</td>' +
				'</tr>';
		}
		
		$('#cartItems').append(tr);
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
	 
	$(document).on('click', '.btn.btn-outline-danger', function() {
	  removeItem(this);
	}); 
	
	/* Update quantity */
	function updateQuantity(quantityInput) {
	  /* Calculate line price */
	  var productRow = $(quantityInput).parent();
	  //var tr = productRow[0];
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
	
	$('#printInvoice').click(function(){
		
		var html = $('.invoice')[0].outerHTML;
		var fileName = $('#invNum').text();
		
		//TODO: for pdf get invoice num from invoiceCreate
		//Only send PDF on successful Invoice create
		//sendPDF(fileName, html);
	
	});
	
	function sendPDF(invNum, html){
		
		//Set as object
		var htmlFile = {invNum, html};
		
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
 

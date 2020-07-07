
$(document).ready(function() {
 
/* Set rates + misc */
var taxRate = 0.05;
var fadeTime = 300;

	//Add temps html files
	includeHTML(); 
	
	includeCurrDate();
	
	getClientInfo();
	
	getCartInfo();	
	
	//Get client info
	function getClientInfo() {
		$.ajax({
			url:"/altHealth/cart/getClientInfo", 
			dataType: "json",
			type: "GET",
			success: function(response) {
				
				if(response.status == "true") {
					dataSet = [response.result];
					clientInfoTable(dataSet);
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
	
	function clientInfoTable(dataSet){
		var suppTable = $("#client-table").DataTable({
			dom: '<rt>',
			retrieve: true,
			select: true,
			data: dataSet,
			columns: 
			[
				{data: 'clientId'},
				{data: 'cName'},
				{data: 'cSurname'},
				{data: 'address'},
				{data: 'cTelH'},
				{data: 'cTelW'},
				{data: 'cTelCell'},
				{data: 'cEmail'},
				
			]
		});
	}
	
	function getCartInfo(){
		$.ajax({
			url:"/altHealth/cart/getCartItems", 
			dataType: "json",
			type: "GET",
			success: function(response) {
				
				if(response.status == "true") {
					dataSet = [response.result];
					cartItemsTable(dataSet[0]);
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
	
	function cartItemsTable(dataSet){
		var tr="";
		for (ref of dataSet) {
			tr+= '<tr>' +
					'<td>' +
						'<figure class="media">' +
							'<figcaption class="media-body">' +
								'<h6 class="title text-truncate">' + ref.supplementId + '</h6>' +
								'<dl class="param param-inline small">' +
								  '<dt>Supplement description: </dt>' +
								  '<dd>' + ref.supplementDescription + '</dd>' +
								'</dl>' +
							'</figcaption>' +
						'</figure>' + 
					'</td>' +
					'<td>' + 
						'<input type="number" class="form-control" id="qty" value="1" min="1" ' +
							'max="' + ref.currentStockLevels + '" required>' +
					'</td>' +
					'<td>' + 
						'<div class="price-wrap">' + 
							'<var class="price">R ' + ref.costExcl + '</var>' +
						'</div>' +
					'</td>' +
					'<td>' + 
						'<div class="price-wrap">' + 
							'<var class="price">R ' + ref.costIncl + '</var>' + 
						'</div>' +
					'</td>' +
					'<td class="text-right">' +
					'<a href="" class="btn btn-outline-danger"> × Remove</a>' +
					'</td>' +
				'</tr>';
		}
		
		$('#cartItems').append(tr);
	}
	
	//Form update button
	$('#addSupp-btn').click(function(event) {
		window.location = "/altHealth/supplement/";
	});
 
	/* Assign actions */
	$('.product-quantity input').change( function() {
	  updateQuantity(this);
	});
	 
	$('.product-removal button').click( function() {
	  removeItem(this);
	});
 
 
	/* Recalculate cart */
	function recalculateCart()
	{
	  var subtotal = 0;
	   
	  /* Sum up row totals */
	  $('.product').each(function () {
		subtotal += parseFloat($(this).children('.product-line-price').text());
	  });
	   
	  /* Calculate totals */
	  var tax = subtotal * taxRate;
	  var shipping = (subtotal > 0 ? shippingRate : 0);
	  var total = subtotal + tax + shipping;
	   
	  /* Update totals display */
	  $('.totals-value').fadeOut(fadeTime, function() {
		$('#cart-subtotal').html(subtotal.toFixed(2));
		$('#cart-tax').html(tax.toFixed(2));
		$('#cart-shipping').html(shipping.toFixed(2));
		$('#cart-total').html(total.toFixed(2));
		if(total == 0){
		  $('.checkout').fadeOut(fadeTime);
		}else{
		  $('.checkout').fadeIn(fadeTime);
		}
		$('.totals-value').fadeIn(fadeTime);
	  });
	}
 
 
	/* Update quantity */
	function updateQuantity(quantityInput)
	{
	  /* Calculate line price */
	  var productRow = $(quantityInput).parent().parent();
	  var price = parseFloat(productRow.children('.product-price').text());
	  var quantity = $(quantityInput).val();
	  var linePrice = price * quantity;
	   
	  /* Update line price display and recalc cart totals */
	  productRow.children('.product-line-price').each(function () {
		$(this).fadeOut(fadeTime, function() {
		  $(this).text(linePrice.toFixed(2));
		  recalculateCart();
		  $(this).fadeIn(fadeTime);
		});
	  });  
	}
 
 
	/* Remove item from cart */
	function removeItem(removeButton)
	{
	  /* Remove row from DOM and recalc cart total */
	  var productRow = $(removeButton).parent().parent();
	  productRow.slideUp(fadeTime, function() {
		productRow.remove();
		recalculateCart();
	  });
	}
	
	function includeCurrDate(){
		n =  new Date();
		y = n.getFullYear();
		m = n.getMonth() + 1;
		d = n.getDate();
		document.getElementById("currDate").innerHTML = m + "/" + d + "/" + y;
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
 

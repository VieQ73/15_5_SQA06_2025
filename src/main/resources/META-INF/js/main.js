$(document).ready(function(){
	main();
});


	function maxNum(sl){
		var aaa = Number($('#qty').val());
		if(aaa > sl)
		{
			alert("Số lượng sản phẩm không đủ");
			$('#qty').val(sl);
		}
	};

function addP(id, gia, maxSl){
	var aaa = Number($('.so_luong_sp'+id).val());
	if(aaa > maxSl)
	{
		var lk = Number($('.slBD'+id).val());
		alert("Số lượng sản phẩm không đủ");
		$('.so_luong_sp'+id).val(lk);
	}
	else{
		var data = {};
			data["productId"] = id;
			data["soluong"] = aaa;
			
			$.ajax({
				url: "/update-sp-gio-hang",
				type: "post",
				contentType: "application/json", 
				data: JSON.stringify(data), 
				
				dataType: "json", 
				success: function(jsonResult) { 
					$('.slBD'+id).val(aaa);
					$('.giatien'+id).html(jsonResult.data.ngang);
					$("#tongtienT").html(jsonResult.data.doc);
				},
				error: function (jqXhr, textStatus, errorMessage) { // error callback 
			        
			    }
			});
	}
}

function checkPhone(){
	var phone = Number($('#phone').val());
	console.log(phone);
	$.ajax({
		url: "/check-phone",
		type: "post",
		contentType: "application/json", 
		data: JSON.stringify(phone), 
		
		dataType: "json", 
		success: function(jsonResult) { 
			$('#name').val(jsonResult.data.name);
			$('#email').val(jsonResult.data.email);
			$('#address').val(jsonResult.data.address);
		},
		error: function (jqXhr, textStatus, errorMessage) { // error callback 
	        
	    }
	});
}


function main(){
	$('.drop-menu > li').click(function(){
		if($(this).hasClass('active')){
			$(this).children('.sub-menu').slideUp();
			$(this).removeClass('active');
			
		}else{
			$(this).children('.sub-menu').slideDown();
			$(this).addClass('active');
		}
	});

	$(window).scroll(function(){
		if($(this).scrollTop()>=400){
			$('.go-to-top').fadeIn();
			
		}else{
			$('.go-to-top').fadeOut();
			
		}
	});

	$('.go-to-top').click(function(){
		$('html, body').animate({ scrollTop: 0});
	});

	

	pos = $('.nav1').position();

	$(window).scroll(function(){
		if($(this).scrollTop() >= parseInt(pos.top) ){
			$('.nav1').addClass('fixed');
			
		}else{
			$('.nav1').removeClass('fixed');
		}
	});
	
}

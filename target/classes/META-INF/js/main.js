$(document).ready(function(){
	main();
});

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
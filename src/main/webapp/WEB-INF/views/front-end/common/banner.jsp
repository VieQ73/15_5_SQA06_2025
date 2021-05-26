<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	* {box-sizing:border-box}
		
			/* Slideshow container */
			.slideshow-container {
			  max-width: 1000px;
			  position: relative;
			  margin: auto;
			}
			
			/* Hide the images by default */
			.mySlides {
			  display: none;
			}
			
			/* Next & previous buttons */
			.prev, .next {
			  cursor: pointer;
			  position: absolute;
			  top: 50%;
			  width: auto;
			  margin-top: -22px;
			  padding: 16px;
			  color: white;
			  font-weight: bold;
			  font-size: 18px;
			  transition: 0.6s ease;
			  border-radius: 0 3px 3px 0;
			  user-select: none;
			}
			
			/* Position the "next button" to the right */
			.next {
			  right: 0;
			  border-radius: 3px 0 0 3px;
			}
			
			/* On hover, add a black background color with a little bit see-through */
			.prev:hover, .next:hover {
			  background-color: rgba(0,0,0,0.8);
			}
			
			/* The dots/bullets/indicators */
			.dot {
			  cursor: pointer;
			  height: 15px;
			  width: 15px;
			  margin: 0 2px;
			  background-color: #bbb;
			  border-radius: 50%;
			  display: inline-block;
			  transition: background-color 0.6s ease;
			}
			
			.active, .dot:hover {
			  background-color: #717171;
			}
			
			/* Fading animation */
			.fade {
			  -webkit-animation-name: fade;
			  -webkit-animation-duration: 1.5s;
			  animation-name: fade;
			  animation-duration: 1.5s;
			}
			
			@-webkit-keyframes fade {
			  from {opacity: .4}
			  to {opacity: 1}
			}
			
			@keyframes fade {
			  from {opacity: .4}
			  to {opacity: 1}
			}
</style>
<div class="banner">
	<div class="banner-main ">
		<div class="container ">
			<div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel">
			  <div class="carousel-inner">
			    <div class="carousel-item active">
			      <img class="d-block w-100" src="${pageContext.request.contextPath}/images/-139.jpg"
			        alt="First slide">
			    </div>
			    <div class="carousel-item">
			      <img class="d-block w-100" src="${pageContext.request.contextPath}/images/-29.jpg"
			        alt="Second slide">
			    </div>
			    <div class="carousel-item">
			      <img class="d-block w-100" src="${pageContext.request.contextPath}/images/bg21.jpg"
			        alt="Third slide">
			    </div>
			    <div class="carousel-item">
			      <img class="d-block w-100" src="${pageContext.request.contextPath}/images/mua-xuan-2020-0.jpg"
			        alt="Third slide">
			    </div>
			  </div>
			  <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
			</div>
		</div>
	</div>
	<div class="menu-top-content grid1 wide1 container" >
		<div class="row1">
			<div class="col1 l-2 m1-4 c-6 grid1 wide1 container menu-top-conten-element">
				<div class="row1">
					<div class="col1 l-3">
						<img
							src="${pageContext.request.contextPath}/images/ho-tro-247--1.jpg">
					</div>
					<div class="col1 l-9">
						<div class="menu-top-content-text1">HỖ TRỢ 24/7</div>
						<div class="menu-top-content-text2">0911 403 114</div>
					</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 grid1 wide1 container menu-top-conten-element">
				<div class="row1">
					<div class="col1 l-3">
						<img
							src="${pageContext.request.contextPath}/images/thong-tin--1.jpg">
					</div>
					<div class="col1 l-9">
						<div class="menu-top-content-text1">THÔNG TIN</div>
						<div class="menu-top-content-text2">Bảo mật</div>
					</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 grid1 wide1 container menu-top-conten-element">
				<div class="row1">
					<div class="col1 l-3">
						<img
							src="${pageContext.request.contextPath}/images/qua-tang--1.jpg">
					</div>
					<div class="col1 l-9">
						<div class="menu-top-content-text1">QUÀ TẶNG</div>
						<div class="menu-top-content-text2">Mua là có</div>
					</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 grid1 wide1 container menu-top-conten-element">
				<div class="row1">
					<div class="col1 l-3">
						<img
							src="${pageContext.request.contextPath}/images/hoan-tra-tien--1.jpg">
					</div>
					<div class="col1 l-9">
						<div class="menu-top-content-text1">HOÀN TIỀN</div>
						<div class="menu-top-content-text2">Trong 7 ngày</div>
					</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 grid1 wide1 container menu-top-conten-element">
				<div class="row1">
					<div class="col1 l-3">
						<img
							src="${pageContext.request.contextPath}/images/thanh-toan--1.jpg">
					</div>
					<div class="col1 l-9">
						<div class="menu-top-content-text1">THANH TOÁN</div>
						<div class="menu-top-content-text2">Khi nhận hàng</div>
					</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 grid1 wide1 container menu-top-conten-element">
				<div class="row1">
					<div class="col1 l-3">
						<img
							src="${pageContext.request.contextPath}/images/giao-hang--1.jpg">
					</div>
					<div class="col1 l-9">
						<div class="menu-top-content-text1">GIAO HÀNG</div>
						<div class="menu-top-content-text2">Tận nơi</div>
					</div>
				</div>
			</div>
			<%-- <div class="col1 l-2 m1-4 c-6 menu-top-conten-element">
				<div class="icon">
					<img
						src="${pageContext.request.contextPath}/images/thong-tin--1.jpg">
				</div>
				<div class="menu-top-content-text">
					<div class="menu-top-content-text1">THÔNG TIN</div>
					<div class="menu-top-content-text2">
						Bảo mật tuyệt đối
						</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 menu-top-conten-element">
				<div class="icon">
					<img
						src="${pageContext.request.contextPath}/images/qua-tang--1.jpg">
				</div>
				<div class="menu-top-content-text">
					<div class="menu-top-content-text1">QUÀ TẶNG</div>
					<div class="menu-top-content-text2">Mua là có quà</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 menu-top-conten-element">
				<div class="icon">
					<img
						src="${pageContext.request.contextPath}/images/hoan-tra-tien--1.jpg">
				</div>
				<div class="menu-top-content-text">
					<div class="menu-top-content-text1">HOÀN TRẢ TIỀN</div>
					<div class="menu-top-content-text2">Trong 7 ngày</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 menu-top-conten-element">
				<div class="icon">
					<img
						src="${pageContext.request.contextPath}/images/thanh-toan--1.jpg">
				</div>
				<div class="menu-top-content-text">
					<div class="menu-top-content-text1">THANH TOÁN</div>
					<div class="menu-top-content-text2">Khi nhận hàng</div>
				</div>
			</div>
			<div class="col1 l-2 m1-4 c-6 menu-top-conten-element">
				<div class="icon">
					<img
						src="${pageContext.request.contextPath}/images/giao-hang--1.jpg">
				</div>
				<div class="menu-top-content-text">
					<div class="menu-top-content-text1">GIAO HÀNG</div>
					<div class="menu-top-content-text2">Tận nơi toàn quốc</div>
				</div>
			</div> --%>
		</div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
		<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>









<head>
	<title>Home</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
	<style type="text/css">
		.sale{
			position: absolute;
			top: 0.5rem;
			right: 1rem;
			z-index: 2;
			color: red;
			font-weight:bold;
			animation: xoayvong 3s linear 0s infinite;
		    -webkit-animation: xoayvong 3s linear 0s infinite;
		    -moz-animation: xoayvong 3s linear 0s infinite;
		    -o-animation: xoayvong 3s linear 0s infinite;
		}
		@-webkit-keyframes xoayvong{
			from{
			        -webkit-transform:rotateY(0deg);
			        -moz-transform:rotateY(0deg);
			        -o-transform:rotateY(0deg);
			    }
		    to{
			        -webkit-transform:rotateY(360deg);
			        -moz-transform:rotateY(360deg);
			        -o-transform:rotateY(360deg);
			}
		}

	</style>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>

	<div class="go-to-top btn btn-secondary" id="go-to-top" >
		<i class="fas fa-angle-up"></i>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
	<div class="content grid1 wide1">
		<jsp:include page="/WEB-INF/views/front-end/common/banner.jsp"></jsp:include>
	</div>
	<div class="bg">
		<!-- end banner -->
		<div class="spbc grid1 wide1">
			<div class="spbc-top">SẢN PHẨM NỔI BẬT</div>
			<div class="spbc-main row1">
			
				<c:forEach var = "productSelling" items = "${productSelling }">
					 
	                  <div class="spbc-body l-2 m1-4 c-6">
						<c:if test="${productSelling.discount != 0 }">
							<p class="sale">- ${productSelling.discount}%</p>
						</c:if>
						<div class="spbc-img ">
							<a href="${pageContext.request.contextPath}/products/${productSelling.product.seo }">
								<c:choose>
									<c:when test = "${empty productSelling.product.productImages }">
										<img class="card-img-top" src="http://placehold.it/700x400" alt="">
									</c:when>
									<c:otherwise>
										<img class="card-img-top" src="${pageContext.request.contextPath}/images/upload/${productSelling.product.productImages.get(0).path }" alt="">
									</c:otherwise>
								</c:choose>
							</a>
							<div class="spbc-addcart">
								<button class="btn btn-warning" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productSelling.product.id}, 1);"><i class="fas fa-cart-plus"></i>Thêm giỏ hàng</button>
							</div>
						</div>
						<div class="spbc-title">
							<a href="${pageContext.request.contextPath}/products/${productSelling.product.seo }">${productSelling.product.title }</a>
						</div>
						<div class="spbc-price">
							<c:choose>
								<c:when test="${productSelling.discount == 0 }">
									<div class="product-price" style="line-height: 2rem;">
										<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${productSelling.price_sale }" /> đ
									</div>
								</c:when>
								<c:otherwise>
									<div class="product-price" style="display: flex;">
										<p style="margin: auto; text-decoration: line-through; font-size: 0.7rem; color: #999999 ; line-height: 2rem;" >
										<fmt:formatNumber type="number" maxIntegerDigits="13"
											value="${productSelling.product.price }" /> đ
									</p>
									<p style=" margin: auto;font-size: 1.2rem; color: #ea5209;">
										<fmt:formatNumber type="number" maxIntegerDigits="13"
											value="${productSelling.price_sale }" /> đ
									</p>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
		                  
				</c:forEach>
			</div>
		</div>
		<!-- end spbc -->
		
		<div class="body grid1 wide1">
			<div class="body-top">
				<div class="body-top-right">
					<a href="#">MỸ PHẨM TRANG ĐIỂM</a>
				</div>
				<div class="body-top-mid">
					<a href="#" class="btn">Son môi</a> <a href="#" class="btn">Kem nền</a> <a href="#" class="btn">Phấn nước</a> <a
						href="#" class="btn">Che khuyết điểm</a>
				</div>
				<div class="body-top-left">
					<a href="#" class="btn">Xem thêm</a>
				</div>
			</div>
			<div class="menu-banner-repon">
				<img src="${pageContext.request.contextPath}/images/my-pham-trang-diem-menu-banner.jpg">
			</div>
			<div class="body-main row1">
				<div class="body-main-banner">
					<a href="#"> 
						<img src="${pageContext.request.contextPath}/images/my-pham-trang-diem-menu-left.jpg">
					</a>
				</div>
				<div class="col1 l-9 m1-12 c-12 grid1 wide1">
					<div class="row1">
						<c:forEach var = "productCustom1" items = "${productCustom1 }">				
							<div class="col1 l-3 m1-4 c-6">
								<div class="card h-100 product-home">
									<c:if test="${productCustom1.discount!= 0 }">
										<p class="sale">- ${productCustom1.discount}%</p>
									</c:if>
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${productCustom1.product.seo }">
											<c:choose>
											<c:when test = "${empty productCustom1.product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/images/upload/${productCustom1.product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
										<div class="product-addcart">
											<button class="btn btn-warning" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom1.product.id}, 1);"><i class="fas fa-cart-plus"></i>Thêm giỏ hàng</button>
										</div>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${productCustom1.product.seo }">${productCustom1.product.title }</a>
										</div>
										
										<c:choose>
											<c:when test="${productCustom1.discount == 0 }">
												<div class="product-price">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${productCustom1.price_sale }" /> đ
												</div>
											</c:when>
											<c:otherwise>
											
												<div class="product-price" style="display: flex;">
													<p style="margin: auto; text-decoration: line-through; font-size: 0.5rem; color: #999999 ; line-height: 2rem;" >
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom1.product.price }" /> đ
												</p>
												
												<p style=" margin: auto;font-size: 1rem; color: #ea5209;">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom1.price_sale }" /> đ
												</p>
												</div>
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<div class="body grid1 wide1">
			<div class="body-top">
				<div class="body-top-right">
					<a href="#">HỖ TRỢ ĐIỀU TRỊ</a>
				</div>
				<div class="body-top-mid">
					<a href="#" class="btn">Hỗ Trợ Điều Trị Mụn</a>
					<a href="#" class="btn">Giảm Cân Tan Mỡ Bụng</a>
					<a href="#" class="btn">Hỗ Trợ Điều Trị Rụng Tóc</a>
				</div>
				<div class="body-top-left">
					<a href="#" class="btn">Xem thêm</a>
				</div>
			</div>
			<div class="menu-banner-repon">
				<img src="${pageContext.request.contextPath}/images/ho-tro-dieu-tri-menu-banner.jpg">
			</div>
			<div class="body-main row1">
				<div class="body-main-banner">
					<a href="#">
					<img src="${pageContext.request.contextPath}/images/ho-tro-dieu-tri-menu-left.jpg">
					</a>
				</div>
				<div class="col1 l-9 m1-12 c-12 grid1 wide1">
					<div class="row1">
						<c:forEach var = "productCustom2" items = "${productCustom2 }">				
							<div class="col1 l-3 m1-4 c-6">
								<div class="card h-100 product-home">
									<c:if test="${productCustom2.discount != 0 }">
										<p class="sale">- ${productCustom2.discount}%</p>
									</c:if>
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${productCustom2.product.seo }">
											<c:choose>
											<c:when test = "${empty productCustom2.product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/images/upload/${productCustom2.product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
										<div class="product-addcart">
											<button class="btn btn-warning" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom2.product.id}, 1);"><i class="fas fa-cart-plus"></i>Thêm giỏ hàng</button>
										</div>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${productCustom2.product.seo }">${productCustom2.product.title }</a>
										</div>
										<c:choose>
											<c:when test="${productCustom2.discount == 0 }">
												<div class="product-price">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${productCustom2.price_sale }" /> đ
												</div>
											</c:when>
											<c:otherwise>
											
												<div class="product-price" style="display: flex;">
													<p style="margin: auto; text-decoration: line-through; font-size: 0.5rem; color: #999999 ; line-height: 2rem;" >
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom2.product.price }" /> đ
												</p>
												
												<p style=" margin: auto;font-size: 1rem; color: #ea5209;">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom2.price_sale }" /> đ
												</p>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="body grid1 wide1">
			<div class="body-top">
				<div class="body-top-right">
					<a href="#">CHĂM SÓC DA MẶT</a>
				</div>
				<div class="body-top-mid">
					<a href="#" class="btn">Sữa Rửa Mặt</a> <a href="#" class="btn">Nước
						Tẩy Trang</a> <a href="#" class="btn">Dưỡng Trắng Da Mặt</a> <a
						href="#" class="btn">Chống Lão Hóa Da Mặt</a>
				</div>
				<div class="body-top-left">
					<a href="#" class="btn">Xem thêm</a>
				</div>
			</div>
			<div class="menu-banner-repon">
				<img src="${pageContext.request.contextPath}/images/cham-soc-da-mat-menu-banner.jpg">
			</div>
			<div class="body-main row1">
				<div class="body-main-banner">
					<a href="#"> <img
						src="${pageContext.request.contextPath}/images/cham-soc-da-mat-menu-left.jpg">
					</a>
				</div>
				<div class="col1 l-9 m1-12 c-12 grid1 wide1">
					<div class="row1">
						<c:forEach var = "productCustom3" items = "${productCustom3 }">				
							<div class="col1 l-3 m1-4 c-6">
								<div class="card h-100 product-home">
								<c:if test="${productCustom3.discount != 0 }">
									<p class="sale">- ${productCustom3.discount}%</p>
								</c:if>
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${productCustom3.product.seo }">
											<c:choose>
											<c:when test = "${empty productCustom3.product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/images/upload/${productCustom3.product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
										<div class="product-addcart">
											<button class="btn btn-warning" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom3.product.id}, 1);"><i class="fas fa-cart-plus"></i>Thêm giỏ hàng</button>
										</div>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${productCustom3.product.seo }">${productCustom3.product.title }</a>
										</div>
										<c:choose>
											<c:when test="${productCustom3.discount == 0 }">
												<div class="product-price">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${productCustom3.price_sale }" /> đ
												</div>
											</c:when>
											<c:otherwise>
											
												<div class="product-price" style="display: flex;">
													<p style="margin: auto; text-decoration: line-through; font-size: 0.5rem; color: #999999 ; line-height: 2rem;" >
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom3.product.price }" /> đ
												</p>
												
												<p style=" margin: auto;font-size: 1rem; color: #ea5209;">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom3.price_sale }" /> đ
												</p>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="body grid1 wide1">
			<div class="body-top">
				<div class="body-top-right">
					<a href="#">CHĂM SÓC TOÀN THÂN</a>
				</div>
				<div class="body-top-mid">
					<a href="#" class="btn">Kem Tẩy Lông</a> <a href="#" class="btn">Kem
						Chống Nắng</a> <a href="#" class="btn">Sữa Tắm Trắng Da</a> <a
						href="#" class="btn">Kem Dưỡng Da Tay</a>
				</div>
				<div class="body-top-left">
					<a href="#" class="btn">Xem thêm</a>
				</div>
			</div>
			<div class="menu-banner-repon">
				<img src="${pageContext.request.contextPath}/images/cham-soc-toan-than-menu-banner.jpg">
			</div>
			<div class="body-main row1">
				<div class="body-main-banner">
					<a href="#"> <img
						src="${pageContext.request.contextPath}/images/cham-soc-toan-than-menu-left.jpg">
					</a>
				</div>
				<div class="col1 l-9 m1-12 c-12 grid1 wide1">
					<div class="row1">
						<c:forEach var = "productCustom4" items = "${productCustom4 }">				
							<div class="col1 l-3 m1-4 c-6">
								<div class="card h-100 product-home">
									<c:if test="${productCustom4.discount != 0 }">
										<p class="sale">- ${productCustom4.discount}%</p>
									</c:if>
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${productCustom4.product.seo }">
											<c:choose>
											<c:when test = "${empty productCustom4.product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/images/upload/${productCustom4.product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
										<div class="product-addcart">
											<button class="btn btn-warning" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom4.product.id}, 1);"><i class="fas fa-cart-plus"></i>Thêm giỏ hàng</button>
										</div>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${productCustom4.product.seo }">${productCustom4.product.title }</a>
										</div>
										<c:choose>
											<c:when test="${productCustom4.discount == 0 }">
												<div class="product-price">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${productCustom4.price_sale }" /> đ
												</div>
											</c:when>
											<c:otherwise>
											
												<div class="product-price" style="display: flex;">
													<p style="margin: auto; text-decoration: line-through; font-size: 0.5rem; color: #999999 ; line-height: 2rem;" >
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom4.product.price }" /> đ
												</p>
												
												<p style=" margin: auto;font-size: 1rem; color: #ea5209;">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
														value="${productCustom4.price_sale }" /> đ
												</p>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<!-- end body product -->
		<div class="banner-up grid1 wide1" style="margin-top: 40px;">
			<img src="images/2new.jpg">
		</div>
		<div class="news grid1 wide1" style="margin-top: 40px;">
			<div class="news-top">TIN NỔI BẬT</div>
			<div class="news-main row1 ">
				<c:forEach items="${news}" var="news">
					<div class=" col1 l-3 m1-6 c-12 news-col container">
						<div class="news-img">
							<a href="${pageContext.request.contextPath}/newsDetail/${news.id }">
								<c:choose>
									<c:when test = "${empty news.newsImages }">
										<img src="http://placehold.it/700x400" alt="">
									</c:when>
									<c:otherwise>
										<img src="${pageContext.request.contextPath}/images/upload/${news.newsImages.get(0).path }" alt="">
									</c:otherwise>
								</c:choose>
							</a>
						</div>
						<div class="news-body" style="max-height: 10rem; overflow: hidden;">
							<a href="${pageContext.request.contextPath}/newsDetail/${news.id }">${news.title } </a>
							<p>${news.short_news }</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- end content -->
		<!-- footer -->
		<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>
	<script type="text/javascript" src="https://sites.google.com/site/iristipsblogger/file/hoamai-hoadao.js"></script>
	<script type="text/javascript">
	$('#carouselExample').on('slide.bs.carousel', function (e) {

		  
	    var $e = $(e.relatedTarget);
	    var idx = $e.index();
	    var itemsPerSlide = 4;
	    var totalItems = $('.carousel-item').length;
	    
	    if (idx >= totalItems-(itemsPerSlide-1)) {
	        var it = itemsPerSlide - (totalItems - idx);
	        for (var i=0; i<it; i++) {
	            // append slides to end
	            if (e.direction=="left") {
	                $('.carousel-item').eq(i).appendTo('.carousel-inner');
	            }
	            else {
	                $('.carousel-item').eq(0).appendTo('.carousel-inner');
	            }
	        }
	    }
	});


	  $('#carouselExample').carousel({ 
	                interval: 2000
	        });


	  $(document).ready(function() {
	/* show lightbox when clicking a thumbnail */
	    $('a.thumb').click(function(event){
	      event.preventDefault();
	      var content = $('.modal-body');
	      content.empty();
	        var title = $(this).attr("title");
	        $('.modal-title').html(title);        
	        content.html($(this).html());
	        $(".modal-profile").modal({show:true});
	    });

	  });
	</script>
</body>
</html>
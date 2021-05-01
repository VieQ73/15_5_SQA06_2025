<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="java.text.DecimalFormat" %>

<!DOCTYPE html>
<html>
<head>
	<title>products</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
	<style type="text/css">
		.img-saleicon{
			color: red;
			font-weight: bold;
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

</head>
<body>
	<div id="fb-root"></div>
	<script async defer crossorigin="anonymous" src="https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v9.0" nonce="nrKNkviN"></script>
	<div class="wapper">
		<div class="go-to-top"> <button id="go-to-top"><i class="fas fa-chevron-up"></i></button></div>
		
		<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
		
			<div class="pro-content">
				<div class="pro-left">
					<div class="pro-left-top">
						<div class="pro-img">
							<div id="big-img">
								<c:choose>
									<c:when test = "${empty productCustom.product.productImages }">
										<img  class="card-img-top" src="http://placehold.it/700x400" alt="">
									</c:when>
									<c:otherwise>
										<img  class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${productCustom.product.productImages.get(0).path }" alt="">
									</c:otherwise>
								</c:choose>
							</div>
							<div id="sub-img" class="container" style="margin-top: 15px;">
								<c:forEach var="img" items= "${productCustom.product.productImages}">
									<img src="${pageContext.request.contextPath}/file/upload/${img.getPath()}">
								</c:forEach>
							</div>
						</div>
						<div class="pro-infor">
							<p class="pro-infor-title">${productCustom.product.title }</p>
							<ul class="description">
								${productCustom.product.shortDes}
							</ul>
							<div class="price">
								<p class="price-text">Giá bán:</p>
								<c:choose>
									<c:when test="${productCustom.discount == 0 }">
										<p class="price-num">
											<fmt:formatNumber type="number" maxIntegerDigits="13"
												value="${productCustom.product.price }" /> đ
										</p>
									</c:when>
									<c:otherwise>
										
										<p style="margin-left: 10px; text-decoration: line-through; font-size: 18px; color: #424242; margin-right: 20px;" >
											<fmt:formatNumber type="number" maxIntegerDigits="13"
												value="${productCustom.product.price }" /> đ
										</p>
										
										<p class="img-saleicon">- ${productCustom.discount} %</p>
										
										<p class="price-num">
											<fmt:formatNumber type="number" maxIntegerDigits="13"
												value="${productCustom.price_sale }" /> đ
										</p>
									</c:otherwise>
								</c:choose>
								
							</div>
							<!-- <div class="discount">
								<p class="discount-text">Trạng thái:</p>
								<p class="discount-code">Còn hàng</p>
							</div> -->
							<ul class="note">
								<li><i class="fas fa-check-circle"></i>Nhập Khẩu Chính Hãng Mỹ.</li>
								<li><i class="fas fa-check-circle"></i>Đổi trả miễn phí trong 7 ngày.</li>
								<li><i class="fas fa-check-circle"></i>Sáng đặt chiều giao: Áp dụng HCM.</li>
							</ul>
							<div class="dathang">
								<c:choose>
									<c:when test="${productCustom.product.amount > 0 }">
										<button onClick="var result = document.getElementById('qty'); var qty = result.value; if( !isNaN( qty ) &amp;&amp; qty &gt; 1 ) result.value--;return false;" class="btn btn-info" type="button"><i class="fas fa-minus"></i></button>     
			                          	<input onchange="maxNum(${productCustom.product.amount })" type="text" class="input-text qty"  title="Qty" value="1" maxlength="12" id="qty"  name="qty">
			                          	<button onClick="var result = document.getElementById('qty'); var qty = result.value; if(qty &lt; ${productCustom.product.amount}) result.value++; return false;" class="btn btn-info" type="button"><i class="fas fa-plus"></i></button>
		                          		<button type="button" class="btn btn-warning addhi" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom.product.id}, parseInt($('#qty').val()));"><i class="fas fa-cart-plus"></i>Thêm Vào Giỏ Hàng</button>
									</c:when>
									<c:otherwise>
										<img src="${pageContext.request.contextPath}/images/hethang.jpg" width="120px" height="70px">
									</c:otherwise>
								</c:choose>
							</div>
							<c:if test="${productCustom.product.gift != null }">
							<div class="bonus">
								<p class="bonus-top">Quà tặng khi mua sản phầm</p>
								<div class="bonus-products">
									<img src="${pageContext.request.contextPath}/file/upload/${productCustom.product.gift.giftImages.get(0).path }">
									<div class="bonus-info">
										<a href="#">${productCustom.product.gift.title }</a>
										<div class="bonus-price">
											<p class="bonus-price-text">Trị giá:</p>
											<p class="bonus-price-num"><fmt:formatNumber type="number" maxIntegerDigits="13"
												value="${productCustom.product.gift.price }" /> đ</p>
										</div>
										<a href="#"><div class="bonus-them"> Xem ngay quà tặng</div></a>
									</div>
								</div>
							</div>
							</c:if>
						</div>
					</div>
					<div class="pro-info">
						<div class="pro-info-top">
							THÔNG TIN SẢN PHẨM
						</div>
						<div class="pro-info-main">
							${productCustom.product.shortDetails }
						</div>
					</div>
					<div class="pro-info">
						<div class="pro-info-top">
							PHẢN HỒI CỦA KHÁCH HÀNG
						</div>
						<div class="pro-info-main">
							<div class="fb-comments" data-href="http://localhost:9090/products/${product.seo }" data-numposts="5" data-width="100%"></div>
						</div>
					</div>
			</div>
			<div class="pro-right">
				<div id="khung">
					<div class="pro-right-top">
						<p class="pro-right-top1">CHÍNH SÁCH BÁN HÀNG</p>
						<p class="pro-right-top2">Tất cả vì khách hàng</p>
					</div>
					<div class="pro-right-main">
						<ul>
							<li class="pl-item">
								<h3>
									<img title="GIAO HÀNG TOÀN QUỐC" src="${pageContext.request.contextPath}/images/mien-phi-van-chuyen--2.jpg">
									GIAO HÀNG TOÀN QUỐC
								</h3>
								<p>Khi mua hàng bạn sẽ được giao hàng tận nhà trên toàn quốc dù bạn có ở vùng núi hay xa xôi nhất.
								</p>
							</li>
							<li class="pl-item">
								<h3>
									<img title="THANH TOÁN" src="${pageContext.request.contextPath}/images/thanh-toan--2.jpg">THANH TOÁN
								</h3>
								<p>Thủ tục thanh toán đơn giản, bạn sẽ thanh toán tiền tại nhà và chỉ thanh toán tiền hàng sau khi nhận hàng.
								</p>
							</li>
							<li class="pl-item">
								<h3>
									<img title="ĐỔI TRẢ MIỄN PHÍ" src="${pageContext.request.contextPath}/images/doi-tra-mien-phi--2.jpg">
									ĐỔI TRẢ MIỄN PHÍ
								</h3>
								<p>Đổi trả miễn phí tận nhà hoặc tại cửa hàng của chúng tôi trong 7 ngày, nếu hàng hóa gặp lỗi do nhà sản xuất hoặc lỗi do quá trình vận chuyển.</p>
							</li>
							<li class="pl-item">
								<h3>
									<img title="MIỄN PHÍ SHIP" src="${pageContext.request.contextPath}/images/mien-phi-ship--2.jpg">
									MIỄN PHÍ SHIP
								</h3>
								<p>Miễn phí giao hàng toàn quốc với đơn hàng có giá trị từ 400.000đ</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- end content -->
		</div>
		<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
	</div>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>
	<script type="text/javascript">
	$(document).ready(function(){
		var pos1 = $('#khung').position();

		$(window).scroll(function(){
			if($(this).scrollTop() >= parseInt(pos1.top) ){
				document.getElementById('khung').setAttribute("style", "position:fixed; top: 55px;");
			}else{
				document.getElementById('khung').setAttribute("style", "position:none;");
			}
		});
		
		var bigImg = document.getElementById('big-img');
		var subImg = document.getElementById('sub-img').getElementsByTagName('img');

		for (var i = 0; i < subImg.length; i++) {
			subImg[i].addEventListener('click', function(){
				var imgSrc = this.getAttribute('src');
				bigImg.innerHTML = "<img src ="+ imgSrc+">";
			});
		}
		

	});
	</script>
	
</body>
</html>
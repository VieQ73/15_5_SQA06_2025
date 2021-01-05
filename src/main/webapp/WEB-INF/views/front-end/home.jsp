<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
		<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>home</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
</head>
<body>

	<div class="go-to-top">
		<button id="go-to-top">
			<i class="fas fa-angle-up"></i>
		</button>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>

	<div class="bg">
		<div class="content">
			<jsp:include page="/WEB-INF/views/front-end/common/banner.jsp"></jsp:include>
		</div>
		<!-- end banner -->
		<div class="spbc">
			<div class="spbc-top">SẢN PHẨM BÁN CHẠY</div>
			<div class="spbc-main">
				<c:forEach var = "product" items = "${productSelling }">
					<div class="spbc-body">
						<div class="spbc-img">
							<a href="${pageContext.request.contextPath}/products/${product.seo }">
								<c:choose>
									<c:when test = "${empty product.productImages }">
										<img class="card-img-top" src="http://placehold.it/700x400" alt="">
									</c:when>
									<c:otherwise>
										<img class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${product.productImages.get(0).path }" alt="">
									</c:otherwise>
								</c:choose>
							</a>
						</div>
						<div class="spbc-title">
							<a href="${pageContext.request.contextPath}/products/${product.seo }">${product.title }</a>
						</div>
						<div class="spbc-price">
							<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${product.price }" /> đ
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- end spbc -->
		<div class="body">
			<div class="body-top">
				<div class="body-top-right">
					<a href="#">MỸ PHẨM TRANG ĐIỂM</a>
				</div>
				<div class="body-top-mid">
					<a href="#" class="btn">Son Môi</a> <a href="#" class="btn">Kem
						Nền</a> <a href="#" class="btn">Phấn Nước</a> <a href="#" class="btn">Che
						Khuyết Điểm</a>
				</div>
				<div class="body-top-left">
					<a href="#" class="btn">Xem thêm</a>
				</div>
			</div>
			<div class="body-main row">
				<div class="body-main-banner col-xl-3">
					<a href="#"> <img
						src="${pageContext.request.contextPath}/images/my-pham-trang-diem-menu-left.jpg">
					</a>
				</div>
				<div class="body-main-product col-xl-9">
					<div class="row">
						<c:forEach var = "product" items = "${products }">				
							<div class="col-xl-3">
								<div class="card h-100 product-home">
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${product.seo }">
											<c:choose>
											<c:when test = "${empty product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${product.seo }">${product.title }</a>
										</div>
										<div class="product-price">${product.price } đ</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="body">
			<div class="body-top">
				<div class="body-top-right">
					<a href="#">HỖ TRỢ ĐIỀU TRỊ</a>
				</div>
				<div class="body-top-mid">
					<a href="#" class="btn">Hỗ Trợ Điều Trị Mụn</a> <a href="#"
						class="btn">Giảm Cân Tan Mỡ Bụng</a> <a href="#" class="btn">Hỗ
						Trợ Điều Trị Rụng Tóc</a>
				</div>
				<div class="body-top-left">
					<a href="#" class="btn">Xem thêm</a>
				</div>
			</div>
			<div class="body-main row">
				<div class="body-main-banner">
					<a href="#"> <img
						src="${pageContext.request.contextPath}/images/ho-tro-dieu-tri-menu-left.jpg">
					</a>
				</div>
				<div class="body-main-product col-xl-9">
					<div class="row">
						<c:forEach var = "product" items = "${products2 }">				
							<div class="col-xl-3">
								<div class="card h-100 product-home">
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${product.seo }">
											<c:choose>
											<c:when test = "${empty product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${product.seo }">${product.title }</a>
										</div>
										<div class="product-price">${product.price } đ</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="body">
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
			<div class="body-main row">
				<div class="body-main-banner">
					<a href="#"> <img
						src="${pageContext.request.contextPath}/images/cham-soc-da-mat-menu-left.jpg">
					</a>
				</div>
				<div class="body-main-product col-xl-9">
					<div class="row">
						<c:forEach var = "product" items = "${products3 }">				
							<div class="col-xl-3">
								<div class="card h-100 product-home">
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${product.seo }">
											<c:choose>
											<c:when test = "${empty product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${product.seo }">${product.title }</a>
										</div>
										<div class="product-price">${product.price } đ</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="body">
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
			<div class="body-main">
				<div class="body-main-banner">
					<a href="#"> <img
						src="${pageContext.request.contextPath}/images/cham-soc-toan-than-menu-left.jpg">
					</a>
				</div>
				<div class="body-main-product col-xl-9">
					<div class="row">
						<c:forEach var = "product" items = "${products4 }">				
							<div class="col-xl-3">
								<div class="card h-100 product-home">
									<div class="product-img">
										<a href="${pageContext.request.contextPath}/products/${product.seo }">
											<c:choose>
											<c:when test = "${empty product.productImages }">
												<img class="card-img-top" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${product.productImages.get(0).path }" alt="">
											</c:otherwise>
										</c:choose>
										</a>
									</div>
									<div class="card-body">
										<div class="product-title">
											<a href="${pageContext.request.contextPath}/products/${product.seo }">${product.title }</a>
										</div>
										<div class="product-price">${product.price } đ</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<!-- end body product -->
		<div class="banner-up">
			<img src="images/2.jpg">
		</div>
		<div class="news">
			<div class="news-top">TIN NỔI BẬT</div>
			<div class="news-main">
				<ul>
					<li>
						<div class="news-img">
							<a href="#"> <img
								src="${pageContext.request.contextPath}/images/Cach-tri-seo-lom-thuy-dau-tu-thien-nhien-an-toan-hieu-qua-.jpg">
							</a>
						</div>
						<div class="news-body">
							<a href="#"> Cách trị sẹo lõm thủy đậu từ thiên nhiên an
								toàn, hiệu quả </a>
							<p>Chúng ta đều biết thủy đậu nếu như không điều trị kịp thời
								đúng cách thì sẽ gây viêm nhiễm và dễ để lại sẹo lõm. Vậy đâu
								mới là cách trị sẹo lõm an toàn và hiệu quả</p>
						</div>
					</li>
					<li>
						<div class="news-img">
							<a href="#"> <img
								src="${pageContext.request.contextPath}/images/BI-QUYET-TRI-RUNG-TOC-HIEU-QUA-TAI-NHA-.jpg">
							</a>
						</div>
						<div class="news-body">
							<a href="#"> Bí quyết trị rụng tóc hiệu quả tại nhà </a>
							<p>Trị rụng tóc sẽ đơn giản hơn khi bạn tìm được cho mình được nguyên nhân gây 
							rụng tóc cũng như biết kết hợp giữa các biện pháp chữa rụng tóc và bổ sung thực phẩm, 
							chất dinh dưỡng cho tóc.</p>
						</div>
					</li>
					<li>
						<div class="news-img">
							<a href="#"> <img
								src="${pageContext.request.contextPath}/images/CACH-CHUA-RUNG-TOC-DAN-GIAN-DON-GIAN-HIEU-QUA-.png">
							</a>
						</div>
						<div class="news-body">
							<a href="#"> Cách chữ rụng tóc dân gian, hiệu quả </a>
							<p>Khi tình trạng rụng tóc chưa trầm trọng, bạn hãy áp dụng những cách chữa rụng tóc dân gian cùng những mẹo 
							vặt sau đây để cải thiện tình trạng của mình nhé.</p>
						</div>
					</li>
					<li>
						<div class="news-img">
							<a href="#"> <img
								src="${pageContext.request.contextPath}/images/BI-QUYET-CHUA-TOC-RUNG-NHIEU-TU-THIEN-NHIEN-.jpg">
							</a>
						</div>
						<div class="news-body">
							<a href="#"> Bí quyết chữa rụng tóc nhiều từ thiên nhiên </a>
							<p>Nếu như bạn đang tìm cho mình cách chữa rụng tóc nhiều thì không nên bỏ qua bài viết này nhé!</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<!-- end content -->
		<!-- footer -->
		<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<!DOCTYPE html>
<html>
<head>
	<title>History Cart Detail</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
</head>
<body>
	<div class="wapper">
		<div class="go-to-top"> <button id="go-to-top"><i class="fas fa-chevron-up"></i></button></div>
		
		<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
			<div class="container">
			<div class="col-lg-12">
				<div class="row mb-4">
					<div class="col">
			<h2 class="title-5 m-b-35">Đơn hàng:</h2>
					<table class="table">
						<tr>
							<td>Trạng thái:</td>
							<td>	
								<c:if test="${historyCart.status == 0}">
									<span class="badge" style="background: red;"><h6>Đặt hàng</h6></span>
								</c:if>
								<c:if test= "${historyCart.status == 1}">
									<span class="badge" style="background: blue;"><h6>Đơn hàng của bạn đang được giao</h6></span>
								</c:if>
								<c:if test= "${historyCart.status == 2}">
									<span class="badge" style="background: green;"><h6>Nhận hàng</h6></span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td>Tên khách hàng:</td>
							<td><span>${historyCart.user.name}</span></td>
						</tr>
						<tr>
							<td>Địa chỉ: </td>
							<td><span>${historyCart.address}</span></td>
						</tr>
					<tr>
						<td>Số điện thoại:</td>
						<td><span>${historyCart.user.phone}</span></td>
					</tr>
					<tr>
						<td>Ngày đặt:</td>
						<td>					
							<c:if test="${historyCart.status == 0}">
								<span  style="color: red;"><fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" /></span>
							</c:if>
							<c:if test= "${historyCart.status == 1}">
								<span  style="color: blue;"><fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" /></span>
							</c:if>
							<c:if test= "${historyCart.status == 2}">
								<span  style="color: green;"><fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" /></span>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>Ghi chú:</td>
						<td><span>${historyCart.note}</span></td>
					</tr>
					</table>
					<h2 class="title-5 m-b-35">Chi tiết đơn hàng:</h2>
					<div class="table-responsive table-responsive-data2" style="background: #FFEFDB;">
						
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Sản phẩm</th>
									<th>Hình ảnh</th>
									<th>Số lượng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="historyCartDetail" items="${historyCartDetails }" varStatus="loop">
									<tr class="tr-shadow">
										<th scope="row">${loop.index + 1}</th>
										<td>
											<a style="color: black;" href="${pageContext.request.contextPath}/products/${historyCartDetail.product.seo }" >${historyCartDetail.product.title}</a>
										</td>
										<td>
											<c:choose>
											<c:when test = "${empty historyCartDetail.product.productImages }">
												<img style="width: 70px; height: 90px;" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img style="width: 70px; height: 90px;" src="${pageContext.request.contextPath}/file/upload/${historyCartDetail.product.productImages.get(0).path }" alt="">
											</c:otherwise>
											</c:choose>
										</td>
										<td>${historyCartDetail.quality}</td>
									</tr>
								</c:forEach>
								<tr class="tr-shadow">
									<td></td>
									<td></td>
									<td style="text-align: right; font-weight: bold;">Tổng tiền:</td>
									<td >
									<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${historyCart.total}" /> đ
									
									</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td>
										<c:if test="${historyCart.status == 1}">
											<a class="btn btn-primary" 
											href="${pageContext.request.contextPath}/historyCartDetail/${historyCart.id}">Nhận hàng</a>
										</c:if>
										<c:if test="${historyCart.status == 0}">
											<a class="btn" style="background: red; color: white;" 
											href="${pageContext.request.contextPath}/user/historyCartDetail/${historyCart.id}">Hủy đơn</a>
										</c:if>
									</td>
								</tr>
								
							</tbody>
						</table>
							</div>
							</div>
							</div>
							</div>
					</div>
			<!-- end content -->
		</div>
		<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>
</body>
</html>





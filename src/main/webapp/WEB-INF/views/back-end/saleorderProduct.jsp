<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- SPRING FORM -->
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- SPRING FORM -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE HTML>
<html>
<head>
<title>Admin saleorder detail</title>
<meta charset="utf-8">
<jsp:include page="/WEB-INF/views/back-end/commonAdmin/css.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.css">
</head>
<body class="cbp-spmenu-push">

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/back-end/commonAdmin/menu.jsp"></jsp:include>


		<!-- header-starts -->
		<jsp:include page="/WEB-INF/views/back-end/commonAdmin/header.jsp"></jsp:include>

		<!-- //header-ends -->
		<!-- main content start-->
		<div class="content-main">
			<div class="row">
				<div class="col-md-12">
					<!-- DATA TABLE -->
					<h2 class="title-5 m-b-35">Đơn hàng:</h2>
					<table class="table">
						<tr>
							<td>Trạng thái:</td>
							<td>	
								<c:if test="${saleorder.status == 0}">
									<span class="badge" style="background: red;"><h4>Đặt hàng</h4></span>
								</c:if>
								<c:if test= "${saleorder.status == 1}">
									<span class="badge" style="background: blue;"><h4>Giao hàng</h4></span>
								</c:if>
								<c:if test= "${saleorder.status == 2}">
									<span class="badge" style="background: green;"><h4>Nhận hàng</h4></span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td>Tên khách hàng:</td>
							<td><span>${saleorder.user.name}</span></td>
						</tr>
						<tr>
							<td>Địa chỉ: </td>
							<td><span>${saleorder.user.address}</span></td>
						</tr>
					<tr>
						<td>Số điện thoại:</td>
						<td><span>${saleorder.user.phone}</span></td>
					</tr>
					
					<tr>
						<td>Ngày:</td>
						<td>
							<c:if test="${saleorder.status == 0}">
								<span  style="color: red;"><fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${saleorder.createdDate}" /></span>
							</c:if>
							<c:if test= "${saleorder.status == 1}">
								<span style="color: blue;"><fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${saleorder.createdDate}" /></span>
							</c:if>
							<c:if test= "${saleorder.status == 2}">
								<span  style="color: green;"><fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${saleorder.createdDate}" /></span>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>Ghi chú:</td>
						<td><span>${saleorder.note_by_customer}</span></td>
					</tr>
					</table>
					<h2 class="title-5 m-b-35">Chi tiết đơn hàng:</h2>
					<div class="table-responsive table-responsive-data2">
						
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Sản phẩm</th>
									<th>Hình ảnh</th>
									<th>Số lượng</th>
								</tr>
							</thead>
							<tbody >
								<c:forEach var="saleorderproduct" items="${saleorderproducts }" varStatus="loop">
									<tr class="tr-shadow" >
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 18px;">
											<a href="${pageContext.request.contextPath}/products/${saleorderproduct.product.seo}" >${saleorderproduct.product.title}</a>
										</td>
										<td>
											<c:choose>
											<c:when test = "${empty saleorderproduct.product.productImages }">
												<img style="width: 70px; height: 90px;" src="http://placehold.it/700x400" alt="">
											</c:when>
											<c:otherwise>
												<img style="width: 70px; height: 90px;" src="${pageContext.request.contextPath}/file/upload/${saleorderproduct.product.productImages.get(0).path }" alt="">
											</c:otherwise>
											</c:choose>
										</td>
										<td style="font-size: 18px;">${saleorderproduct.quality}</td>
									</tr>
								</c:forEach>
								<tr class="tr-shadow">
									<td></td>
									<td></td>
									<td style="text-align: right; font-weight: bold; font-size: 18px;">Tổng tiền:</td>
									<td style="font-size: 18px; font-weight: bold;">
										<span class="badge" style="font-size: 18px; font-weight: bold; margin-right: 10px;"><fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${saleorder.total}" /></span>VNĐ
									
									</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td>
										
										<c:if test="${saleorder.status == 0}">
											<a style="margin-right: 30px;" class="btn btn-danger"
											href="${pageContext.request.contextPath}/admin/confirm_saleProduct/${saleorder.id}?status=3">Hủy</a>
											<a class="btn btn-primary" 
											href="${pageContext.request.contextPath}/admin/confirm_saleProduct/${saleorder.id}?status=1">Xác nhận</a>
										</c:if>
										<c:if test="${saleorder.status == 1}">
											<a class="btn btn-primary" 
											href="${pageContext.request.contextPath}/admin/confirm_saleProduct/${saleorder.id}?status=2">Nhận hàng</a>
										</c:if>
									</td>
								</tr>
								
							</tbody>
						</table>
							
					</div>
					<!-- END DATA TABLE -->
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/footer.jsp"></jsp:include>
	<!-- js-->
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/js.jsp"></jsp:include>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.js"></script>
	<script type="text/javascript">
	
	</script>
</body>
</html>
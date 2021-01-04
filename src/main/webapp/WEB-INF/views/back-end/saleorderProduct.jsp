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
<title>Admin</title>
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
								<c:if test="${saleorder.status_ok == 0}">
									<span class="badge" style="background: red;"><h4>Đặt hàng</h4></span>
								</c:if>
								<c:if test= "${saleorder.status_ok == 1}">
									<span class="badge" style="background: blue;"><h4>Giao hàng</h4></span>
								</c:if>
								<c:if test= "${saleorder.status_ok == 2}">
									<span class="badge" style="background: green;"><h4>Nhận hàng</h4></span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td>Tên khách hàng:</td>
							<td><span>${saleorder.customerName}</span></td>
						</tr>
						<tr>
							<td>Địa chỉ: </td>
							<td><span>${saleorder.customerAddress}</span></td>
						</tr>
					<tr>
						<td>Số điện thoại:</td>
						<td><span>${saleorder.phone}</span></td>
					</tr>
					<tr>
						<td>Ngày:</td>
						<td>
							<c:if test="${saleorder.status_ok == 0}">
								<span  style="color: red;">${saleorder.createdDate}</span>
							</c:if>
							<c:if test= "${saleorder.status_ok == 1}">
								<span style="color: blue;">${saleorder.createdDate}</span>
							</c:if>
							<c:if test= "${saleorder.status_ok == 2}">
								<span  style="color: green;">${saleorder.createdDate}</span>
							</c:if>
						</td>
					</tr>
					</table>
					<h2 class="title-5 m-b-35">Chi tiết đơn hàng:</h2>
					<div class="table-responsive table-responsive-data2">
						
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Sản phẩm</th>
									<th>Số lượng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="saleorderproduct" items="${saleorderproducts }" varStatus="loop">
									<tr class="tr-shadow">
										<th scope="row">${loop.index + 1}</th>
										<td>${saleorderproduct.product.title}</td>
										<td>${saleorderproduct.quality}</td>
									</tr>
								</c:forEach>
								<tr class="tr-shadow">
									<td></td>
									<td style="text-align: right; font-weight: bold;">Tổng tiền:</td>
									<td >${saleorder.total}đ</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td>
										<c:if test="${saleorder.status_ok == 0}">
											<a class="btn btn-primary" 
											href="${pageContext.request.contextPath}/confirm_saleProduct/${saleorder.id}">Xác nhận</a>
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
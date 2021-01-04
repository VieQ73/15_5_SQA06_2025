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
<style type="text/css">
	div.dataTables_wrapper {
        margin-bottom: 3em;
    }
</style>
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
					<h3 class="title-5 m-b-35">Danh sách đơn hàng</h3>

					<div class="table-responsive table-responsive-data2">
						
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Tên khách hàng</th>
									<th>Địa chỉ nhận</th>
									<th>Số điện thoại</th>
									<th>Tổng giá</th>
									<th>Ngày đặt</th>
									<th>Trạng thái</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="saleorder" items="${saleorders }" varStatus="loop">
									<tr class="tr-shadow">
										<th scope="row">${loop.index + 1}</th>
										<td>${saleorder.customerName}</td>
										<td>${saleorder.customerAddress}</td>
										<td>${saleorder.phone}</td>
										<td>
											<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${saleorder.total}" /> 
										đ</td>
										<td>
											<c:if test="${saleorder.status_ok == 0}">
												<span class="badge" style="background: red;">Đặt hàng</span>
											</c:if>
											<c:if test= "${saleorder.status_ok == 1}">
												<span class="badge" style="background: blue;">Giao hàng</span>
											</c:if>
											<c:if test= "${saleorder.status_ok == 2}">
												<span class="badge" style="background: green;">Nhận hàng</span>
											</c:if>
										</td>
										<td>
											<c:if test="${saleorder.status_ok == 0}">
												<span style="color: red;">${saleorder.createdDate}</span>
											</c:if>
											<c:if test= "${saleorder.status_ok == 1}">
												<span style="color: blue;">
													${saleorder.createdDate}
												</span>
											</c:if>
											<c:if test= "${saleorder.status_ok == 2}">
												<span style="color: green;">
												
												${saleorder.createdDate}
												
												</span>
											</c:if>
										</td>
										<td>							
											<a href="${pageContext.request.contextPath}/admin/saleorder/${saleorder.id}">
												<button
													class="item" data-toggle="tooltip" data-placement="top"
													title="Edit">
													<span class="badge">Xem chi tết</span>
												</button>
											</a>
										</td>
									</tr>
								</c:forEach>
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
	$(document).ready(function() {
	    $('table.display').DataTable();
	} );
	</script>
</body>
</html>

<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
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
<title>Admin order</title>
<meta charset="utf-8">
<jsp:include page="/WEB-INF/views/back-end/commonAdmin/css.jsp"></jsp:include>
<style type="text/css">
	div.dataTables_wrapper {
        margin-bottom: 3em;
    }
    #table1 th, td{
    	white-space: nowrap;
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

					<div class="table-responsive table-responsive-data2" style="">
						
						<table class="table table-data2 display" id="table1" style="max-width: 1900px ;width: 100%;">
							<thead>
								<tr>
									<th>#</th>
									<th>Tên khách hàng</th>
									<th>Địa chỉ nhận</th>
									<th>Số điện thoại</th>
									<th>Tổng giá</th>
									<th style="min-width: 2rem;">Ngày đặt</th>
									<th>Trạng thái</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="order" items="${order }" varStatus="loop">
									<input type="hidden" value="${order.status}" id="tr${order.id }">
									<tr class="tr-shadow" onclick="show(${order.id})">
										<th scope="row">${loop.index + 1}</th>
										<td>${order.user.name}</td>
										<td>${order.user.address}</td>
										<td>${order.user.phone}</td>
										<td>
											<fmt:formatNumber type="number" maxIntegerDigits="13" value="${order.total}" /> đ</td>
										<td>
											<fmt:formatDate pattern = "dd-MM-yyyy" value = "${order.createdDate}" />
										</td>
										<td>
											<c:if test="${order.status == 0}">
												<span class="badge" style="background: red;">Đặt hàng</span>
											</c:if>
											<c:if test= "${order.status == 1}">
												<span class="badge" style="background: blue;">Giao hàng</span>
											</c:if>
											<c:if test= "${order.status == 2}">
												<span class="badge" style="background: green;">Nhận hàng</span>
											</c:if>
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
	    $('table.display').DataTable({
	    	"scrollX": true
	    });
	    
	});
	function show(id){
		window.location = "${pageContext.request.contextPath}/admin/order/"+id;
	}
	</script>
</body>
</html>
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
<title>Admin list sale</title>
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
			${ messsage }
			<div class="row">
				<div class="col-md-12">
					<!-- DATA TABLE -->
					<h3 class="title-5 m-b-35">Danh sách khuyến mãi</h3>

					<div class="table-responsive table-responsive-data2">
						
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Tên đợt khuyến mãi</th>
									<th>Ngày bắt đầu</th>
									<th>Ngày kết thúc</th>
									<th>Trạng thái</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="sale" items="${sale }" varStatus="loop">
									
									<tr class="tr-shadow" onclick="showDetail(${sale.id})">
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 16px;">${sale.sale_name}</td>
										<td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${sale.start_date}" /></td>
										<td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${sale.end_date}" /></td>
										<td>
											<c:choose>
												<c:when test="${sale.status}">
													<span class="badge" style="background: green;">Hoạt động</span>
												</c:when>
												<c:otherwise>
													<span class="badge" style="background: red;">Ngừng hoạt động</span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div class="table-data-feature">
												<a href="${pageContext.request.contextPath}/admin/sale/${sale.id}">
													<button
														class="item" data-toggle="tooltip" data-placement="top"
														title="Edit">
														<i class="far fa-edit"></i>
													</button>
												</a>
											</div>
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
	});
	function showDetail(id){
		console.log(id);
		window.location = "${pageContext.request.contextPath}/admin/listProductSale/"+id;
	}
	</script>
</body>
</html>
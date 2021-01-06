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
<title>Admin list category</title>
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
					<h3 class="title-5 m-b-35">Danh sách danh mục</h3>

					<div class="table-responsive table-responsive-data2">
						
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th>Description</th>
									<th>Status</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="category" items="${categorys }" varStatus="loop">
									<tr class="tr-shadow">
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 16px;">${category.name}</td>
										<td>${category.description}</td>
										<td>
											<c:choose>
												<c:when test="${category.status}">
													<span class="badge" style="background: green;"><i class="fas fa-check" ></i></span>
												</c:when>
												<c:otherwise>
													<span class="badge" style="background: red;"><i class="fas fa-times"></i></span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div class="table-data-feature">

												<a href="${pageContext.request.contextPath}/admin/category/${category.id}">
													<button
														class="item" data-toggle="tooltip" data-placement="top"
														title="Edit">
														<i class="far fa-edit"></i>
													</button>
												</a>
												<a href="#" data-target="#deleteAjax" data-toggle="modal">
													<button
														class="item" data-toggle="tooltip" data-placement="top"
														title="Delete" onclick="Productjs.deleteCategory(${category.id});">
														<i class="far fa-trash-alt"></i>
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
	} );
	</script>
</body>
</html>
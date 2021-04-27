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
    .table-responsive{
    	overflow-y: overlay;
		overflow-x: auto;
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
					<h3 class="title-5 m-b-35">Danh sách sản phẩm</h3>

					<div class="table-responsive table-responsive-data2" >
						
						<table class="table table-hover table-data2 display " style="font-size: 20px;" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>title</th>
									<th style="witdh:90px; min-height:100px;">Hình ảnh</th>
									<th>category</th>
									<th>price</th>
									<th>Status</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="product" items="${products }" varStatus="loop">
									<tr class="tr-shadow" >
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 16px;">${product.title}</td>
										<td>
											<c:choose>
												<c:when test = "${empty product.productImages }">
													<img style="width: 70px; height: 90px;" class="card-img-top" src="http://placehold.it/700x400" alt="">
												</c:when>
												<c:otherwise>
													<img style="width: 70px; height: 90px;" class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${product.productImages.get(0).path }" alt="">
												</c:otherwise>
											</c:choose>
										</td>
										<td>${product.category.name}</td>
										<td>
											<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${product.price}" /> đ
										</td>
										<td>
											<input type="hidden" id="status${product.id }" value="${product.status}">
											<c:choose>
												<c:when test="${product.status}">
													<span class="badge" style="background: green;" id="TStatus${product.id }"><i id="TiStatus${product.id }" class="fas fa-check" ></i></span>
												</c:when>
												<c:otherwise>
													<span class="badge" style="background: red;" id="TStatus${product.id }"><i id="TiStatus${product.id }" class="fas fa-times"></i></span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div class="table-data-feature">

												<a href="${pageContext.request.contextPath}/admin/product/${product.id}">
													<button
														class="item" data-toggle="tooltip" data-placement="top"
														title="Edit">
														<i class="far fa-edit"></i>
													</button>
												</a>
												<a href="#" data-target="#deleteAjax" data-toggle="modal">
													 <button type="button" class="item"  data-toggle="modal" data-target="#myModal${product.id}" >
													 	<i class="far fa-trash-alt"></i>
													 </button></a>
													<div class="modal fade" id="myModal${product.id}" role="dialog">
													    <div class="modal-dialog">
													      <!-- Modal content-->
													      <div class="modal-content">
													        <div class="modal-header">
													          <button type="button" class="close" data-dismiss="modal">&times;</button>
													          <h4 class="modal-title">Xác nhận thay đổi trạng thái sản phẩm</h4>
													        </div>
													        <div class="modal-body">
													          <p><span style="font-size: 18px; color: red;">${product.title}</span> ?</p>
													        </div>
													        <div class="modal-footer">
													          <button type="button" class="btn btn-danger" onclick="Productjs.deleteProduct(${product.id});" data-dismiss="modal">Đồng ý</button>
													          <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
													          
													        </div>
													      </div>
													    </div>
													</div>
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
	    $('table.display').DataTable({
	    	"scrollX": true
	    });
	} );
	</script>
</body>
</html>
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
<title>Admin list ProductSale</title>
<meta charset="utf-8">
<jsp:include page="/WEB-INF/views/back-end/commonAdmin/css.jsp"></jsp:include>
<style type="text/css">
	div.dataTables_wrapper {
        margin-bottom: 3em;
    }
    table{
    	font-size: 20px;
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
					<div class="form-body" style="margin-left: 15rem;">
						<div class="form-group">
							<label>Tên đợt khuyến mại:</label>
							<select style="margin-left: 1rem; height: 2rem; max-width: 15rem;" id="myselect" onchange="select();">
								<c:forEach items="${sale }" var="sale">
									<option value="${sale.id}">${sale.sale_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<input type="hidden" id="saleid" value="${saleid}">
					<!-- DATA TABLE -->
					<h4 class="title-5 m-b-35" style="margin-top: 5rem;">Danh sách sản phẩm khuyến mại</h4>
					<div class="table-responsive table-responsive-data2">
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th style="font-size: 18px;">STT</th>
									<th style="font-size: 18px;">Tên sản phẩm</th>
									<th style="font-size: 18px;">Phần trăm giảm giá</th>
									<th ></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="productSale" items="${productSale }" varStatus="loop">
									<tr class="tr-shadow" id="row${productSale.id }">
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 18px;">${productSale.product.title}</td>
										<td style="font-size: 18px;">${productSale.discount} %</td>
										<td>
											<div class="table-data-feature">
												<button class="item" data-toggle="modal" data-toggle="modal" data-target="#updateProductSale${productSale.id }"
													title="Edit" >
													<i class="far fa-edit"></i>
												</button>
												<div class="modal fade" id="updateProductSale${productSale.id }" tabindex="-1" role="dialog" aria-labelledby="updateProductSale" aria-hidden="true">
												  <div class="modal-dialog" role="document">
												    <div class="modal-content">
												      <div class="modal-header">
												        <h4 class="modal-title" id="exampleModalLabel">Sửa sản phẩm khuyến mại</h4>
												        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
												          <span aria-hidden="true">&times;</span>
												        </button>
												      </div>
												      <div class="modal-body">
														<form action="${pageContext.request.contextPath}/admin/addProductSale " method="post">
															<table class="table">
																<tbody>
																<tr>
																	<td style="max-width: 16rem;">
																		<label style="margin-left: -5rem; width: 15rem;">Tên đợt khuyến mại:</label>
																	</td>
																	<td>
																		<label style="font-size: 18px;">${productSale.sale.sale_name}</label>
																	</td>
																</tr>
																<tr>
																<td>
																		<label style="margin-left: -5rem; width: 15rem;">Tên sản phẩm:</label>
																	</td>
																	<td>
																		<label style="font-size: 18px;">${productSale.product.title}</label>
																	</td>
																</tr>
																<tr>
																	<td>
																		<label style="margin-left: -5rem; width: 15rem;">Phần trăm giảm giá:</label>
																	</td>
																	<td>
																		<input style="max-width: 15rem; height: 2rem; margin-left: 1rem;" type="number" name="discountU" class="form-control">
																	</td>
																</tr>
																</tbody>
															</table>
															<input type="hidden" name="productId" value="${productSale.product.id }">
															<input type="hidden" name="saleId" value="${productSale.sale.id }">
															<input type="hidden" name="productSaleId" value="${productSale.id }">
															<div class="modal-footer">
															 	<button type="submit" class="btn btn-warning">Lưu</button>
													          	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
													        </div>
														</form>
												      </div>
												    </div>
												  </div>
												</div>
												
												 <button type="button" class="item"  data-toggle="modal" data-target="#deleteProductSale${productSale.id }">
												 	<i class="far fa-trash-alt"></i>
												 </button>
												<div class="modal fade" id="deleteProductSale${productSale.id }" role="dialog">
												    <div class="modal-dialog">
												      <!-- Modal content-->
												      <div class="modal-content">
												        <div class="modal-header">
												          <button type="button" class="close" data-dismiss="modal">&times;</button>
												          <h4 class="modal-title">Xác nhận xóa sản phẩm</h4>
												        </div>
												        <div class="modal-body">
												          <p><span style="font-size: 18px; color: red;"> ${productSale.product.title } </span></p>
												        </div>
												        <div class="modal-footer">
												          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="xoaSP(${productSale.id });">Đồng ý</button>
												          <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
												        </div>
												      </div>
												    </div>
												</div>
											</div>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<th></th>
									<td></td>
									<td>
										<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addProductSale">Thêm sản phẩm</button>
										<div class="modal fade" id="addProductSale" tabindex="-1" role="dialog" aria-labelledby="addProductSale" aria-hidden="true">
										  <div class="modal-dialog" role="document">
										    <div class="modal-content">
										      <div class="modal-header">
										        <h4 class="modal-title" id="exampleModalLabel">Thêm sản phẩm khuyến mại</h4>
										        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
										          <span aria-hidden="true">&times;</span>
										        </button>
										      </div>
										      <div class="modal-body">
												<form action="${pageContext.request.contextPath}/admin/addProductSale " method="post">
													<div class="form-group">
														<label>Tên sản phẩm</label>
														<select id="productIDsale" style="max-width: 15rem; height: 2rem; margin-left: 1rem; font-size: 18px;" onchange="getProductId();">
															<option value="">-- Chọn tên sản phẩm --</option>
															<c:forEach items="${product }" var="product">
																<option value="${product.id }">${product.title}</option>
															</c:forEach>
														</select>
														<span class="form-message alert-danger"></span>
													</div>
													<input type="hidden" id="productId" name="productId">
													<input type="hidden" id="saleId" name="saleId">
													<div class="form-group" style="display: flex;">
														<label style="margin-left: 1rem;">Phần trăm giảm giá:</label>
														<input style="max-width: 15rem; height: 2rem; margin-left: 1rem;" type="number" class="form-control" id="discount" name="discount">
														<span class="form-message alert-danger"></span>
													</div>
													<div class="modal-footer">
													 	<button type="submit" class="btn btn-warning">Lưu</button>
											          	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											        </div>
												</form>
										      </div>
										    </div>
										  </div>
										</div>
									</td>
									<td>
										<button type="button" class="btn btn-warning" onclick="history.back();">Quay lại</button>
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
	$(document).ready(function() {
	    var id = $("#saleid").val();
	    $('#myselect option[value='+id+']').attr('selected','selected');
	    $("#saleId").val(id);
	});
	function select(){
		var id = $("#myselect option:selected").val();
		window.location = "${pageContext.request.contextPath}/admin/listProductSale/"+id;
	}
	function getProductId(){
		var id= $("#productIDsale option:selected").val();
		$("#productId").val(id);
	}
	function xoaSP(id){
		var data = {};
		data["id"] = id;
		
		
		$.ajax({
			url: "/admin/deleteProductSale",
			type: "post",
			contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
			data: JSON.stringify(data), // object json -> string json
			
			dataType: "json", // dữ liệu từ web-service trả về là json.
			success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
				if(jsonResult.status == 200) {
					
					$('#row'+id).remove();
					alert('Xóa thành công!');
				} else {
					alert('Xóa không thành công!');
				}
			},
			error: function (jqXhr, textStatus, errorMessage) { // error callback 
		    }
		});
	}
	</script>
</body>
</html>
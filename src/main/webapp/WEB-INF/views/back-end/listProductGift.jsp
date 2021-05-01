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
					<div class="form-body" style="margin-left: 10rem;">
						<div class="form-group">
							<label>Tên quà tặng:</label>
							<select style="margin-left: 1rem; height: 2rem; max-width: 19rem;" id="myselect" onchange="select();">
								<c:forEach items="${gift }" var="gift">
									<option value="${gift.id}">${gift.title}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label style="padding-left: 2rem;">Hình ảnh:</label>
							<img style="margin-left: 5rem; border: solid 1px black;" width="200rem" height="200rem" src="${pageContext.request.contextPath}/file/upload/${giftI.giftImages.get(0).path }">
						</div>
					</div>
					<input type="hidden" id="giftid" value="${id}">
					<!-- DATA TABLE -->
					<h3 class="title-5 m-b-35" style="margin-top: 2rem;">Danh sách sản tặng quà</h3>
					<div class="table-responsive table-responsive-data2">
						<table class="table table-data2 display" id="">
							<thead>
								<tr>
									<th style="font-size: 18px;">STT</th>
									<th style="font-size: 18px;">Tên sản phẩm</th>
									<th style="font-size: 18px;">Hình ảnh</th>	
									<th ></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="productGift" items="${productGift }" varStatus="loop">
									<tr class="tr-shadow" id="row${productGift.id }">
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 18px;">${productGift.title}</td>
										<td>
											<c:choose>
												<c:when test = "${empty productGift.productImages }">
													<img style="width: 70px; height: 90px;" class="card-img-top" src="http://placehold.it/700x400" alt="">
												</c:when>
												<c:otherwise>
													<img style="width: 70px; height: 90px;" class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${productGift.productImages.get(0).path }" alt="">
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div class="table-data-feature">
												 <button type="button" class="item"  data-toggle="modal" data-target="#deleteProductGift${productGift.id }">
												 	<i class="far fa-trash-alt"></i>
												 </button>
												<div class="modal fade" id="deleteProductGift${productGift.id }" role="dialog">
												    <div class="modal-dialog">
												      <!-- Modal content-->
												      <div class="modal-content">
												        <div class="modal-header">
												          <button type="button" class="close" data-dismiss="modal">&times;</button>
												          <h4 class="modal-title">Xác nhận xóa sản phẩm</h4>
												        </div>
												        <div class="modal-body">
												          <p><span style="font-size: 18px; color: red;"> ${productGift.title } </span></p>
												        </div>
												        <div class="modal-footer">
												          <button type="button" class="btn btn-danger" onclick="xoaSP(${productGift.id})">Đồng ý</button>
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
												<form action="${pageContext.request.contextPath}/admin/addProductGift " method="post">
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
													<input type="hidden" id="giftID" name="giftID">
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
									<td><button style="margin-left: 2rem;" type="button" class="btn btn-warning" onclick="Gback();">Quay lại</button></td>
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
	    var id = $("#giftid").val();
	    $('#myselect option[value='+id+']').attr('selected','selected');
	    $('#giftID').val(id);
	});
	function select(){
		var id = $("#myselect option:selected").val();
		window.location = "${pageContext.request.contextPath}/admin/listProductGift/"+id;
	}
	function Gback(){
		window.location = "${pageContext.request.contextPath}/admin/listGift";
	}
	function getProductId(){
		var id= $("#productIDsale option:selected").val();
		$("#productId").val(id);
	}
	function xoaSP(id){
		var data = {};
		data["id"] = id;
		
		
		$.ajax({
			url: "/admin/deleteProductGift",
			type: "post",
			contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
			data: JSON.stringify(data), // object json -> string json
			
			dataType: "json", // dữ liệu từ web-service trả về là json.
			success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
				if(jsonResult.status == 200) {
					alert('Xóa thành công!');
					location.reload();
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
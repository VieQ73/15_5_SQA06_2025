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
<title>Admin list gift</title>
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
					<h3 class="title-5 m-b-35">Danh sách quà tặng</h3>
					<div class="table-responsive table-responsive-data2" style="margin-left: 5rem;">
						<table class="table table-hover table-data2 display " style="font-size: 20px; margin-left: 0rem;" id="">
							<thead >
								<tr>
									<th>#</th>
									<th>Tên sản phẩm</th>
									<th style="witdh:90px; min-height:100px;">Hình ảnh</th>
									<th>Giá</th>
									<th>Trạng thái</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="gift" items="${gift }" varStatus="loop">
									<tr class="tr-shadow" onclick="showDetail(${gift.id})">
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 16px;">${gift.title}</td>
										<td>
											<c:choose>
												<c:when test = "${empty gift.giftImages }">
													<img style="width: 70px; height: 90px;" class="card-img-top" src="http://placehold.it/700x400" alt="">
												</c:when>
												<c:otherwise>
													<img style="width: 70px; height: 90px;" class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${gift.giftImages.get(0).path }" alt="">
												</c:otherwise>
											</c:choose>
										</td>
										
										<td>
											<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${gift.price}" /> đ
										</td>
										<td>
											<input type="hidden" id="status${gift.id }" value="${gift.status}">
											<c:choose>
												<c:when test="${gift.status}">
													<span class="badge" style="background: green;" id="TStatus${gift.id }"><i id="TiStatus${gift.id }" class="fas fa-check" ></i></span>
												</c:when>
												<c:otherwise>
													<span class="badge" style="background: red;" id="TStatus${gift.id }"><i id="TiStatus${gift.id }" class="fas fa-times"></i></span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div class="table-data-feature">

												<a href="${pageContext.request.contextPath}/admin/gift/${gift.id}">
													<button
														class="item" data-toggle="tooltip" data-placement="top"
														title="Edit">
														<i class="far fa-edit"></i>
													</button>
												</a>
												<a href="#" data-target="#deleteAjax" data-toggle="modal">
													 <button type="button" class="item"  data-toggle="modal" data-target="#myModal${gift.id}" >
													 	<i class="far fa-trash-alt"></i>
													 </button></a>
													<div class="modal fade" id="myModal${gift.id}" role="dialog">
													    <div class="modal-dialog">
													      <!-- Modal content-->
													      <div class="modal-content">
													        <div class="modal-header">
													          <button type="button" class="close" data-dismiss="modal">&times;</button>
													          <h4 class="modal-title">Xác nhận thay đổi trạng thái quà tặng</h4>
													        </div>
													        <div class="modal-body">
													          <p><span style="font-size: 18px; color: red;">${gift.title}</span> ?</p>
													        </div>
													        <div class="modal-footer">
													          <button type="button" class="btn btn-danger" onclick="deleteGift(${gift.id});" data-dismiss="modal">Đồng ý</button>
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
	});
	function showDetail(id){
		window.location = "${pageContext.request.contextPath}/admin/listProductGift/"+id;
	}
	function deleteGift(idP){
		var data = {};
		data["id"] = idP;
		
		
		$.ajax({
			url: "/delete-gift-with-ajax",
			type: "post",
			contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
			data: JSON.stringify(data), // object json -> string json
			
			dataType: "json", // dữ liệu từ web-service trả về là json.
			success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
				if(jsonResult.status == 200) {
					var sta = $('#status'+idP).val();
					console.log(sta);
					if(sta == "true"){
						$('#TStatus'+idP).css('background','red');
						$('#TiStatus'+idP).removeClass("fa-check").addClass("fa-times");
						$('#status'+idP).val("false");
					}
					else{
						$('#TStatus'+idP).css('background','green');
						$('#TiStatus'+idP).removeClass("fa-times").addClass("fa-check");
						$('#status'+idP).val("true");
					}
					
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
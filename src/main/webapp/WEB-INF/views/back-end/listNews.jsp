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
<title>Admin list news</title>
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
			<div class="">
				<div class="">
					<!-- DATA TABLE -->
					<h3 class="title-5 m-b-35">Danh sách quà tặng</h3>
					<div class="table-responsive table-responsive-data2" style="margin-left: 1rem;">
						<table class="table table-hover table-data2 display " style="font-size: 20px; margin-left: 0rem;" id="">
							<thead >
								<tr>
									<th>#</th>
									<th>Bản tin</th>
									<th style="min-witdh:100px; min-height:100px;">Hình ảnh</th>
									<th>Vắn tắt</th>
									<th>Trạng thái hiển thị</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="news" items="${news }" varStatus="loop">
									<tr class="tr-shadow" >
										<th scope="row">${loop.index + 1}</th>
										<td style="font-size: 16px;">${news.title}</td>
										<td>
											<c:choose>
												<c:when test = "${empty news.newsImages }">
													<img style="width: 70px; height: 90px;" class="card-img-top" src="http://placehold.it/700x400" alt="">
												</c:when>
												<c:otherwise>
													<img style="width: 70px; height: 90px;" class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${news.newsImages.get(0).path }" alt="">
												</c:otherwise>
											</c:choose>
										</td>
										
										<td>
											<p>${news.short_news}</p>
										</td>
										<td>
											<input type="hidden" id="status${news.id }" value="${news.status}">
											<c:choose>
												<c:when test="${news.status}">
													<span class="badge" style="background: green;" id="TStatus${news.id }"><i id="TiStatus${news.id }" class="fas fa-check" ></i></span>
												</c:when>
												<c:otherwise>
													<span class="badge" style="background: red;" id="TStatus${news.id }"><i id="TiStatus${news.id }" class="fas fa-times"></i></span>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<div class="table-data-feature">

												<a href="${pageContext.request.contextPath}/admin/news/${news.id}">
													<button
														class="item" data-toggle="tooltip" data-placement="top"
														title="Edit">
														<i class="far fa-edit"></i>
													</button>
												</a>
												<a href="#" data-target="#deleteAjax" data-toggle="modal">
													 <button type="button" class="item"  data-toggle="modal" data-target="#myModal${news.id}" >
													 	<i class="far fa-trash-alt"></i>
													 </button></a>
													<div class="modal fade" id="myModal${news.id}" role="dialog">
													    <div class="modal-dialog">
													      <!-- Modal content-->
													      <div class="modal-content">
													        <div class="modal-header">
													          <button type="button" class="close" data-dismiss="modal">&times;</button>
													          <h4 class="modal-title">Xác nhận thay đổi trạng thái tin tức</h4>
													        </div>
													        <div class="modal-body">
													          <p><span style="font-size: 18px; color: red;">${news.title}</span> ?</p>
													        </div>
													        <div class="modal-footer">
													          <button type="button" class="btn btn-danger" onclick="deleteNews(${news.id});" data-dismiss="modal">Đồng ý</button>
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
	function deleteNews(idP){
		var data = {};
		data["id"] = idP;
		
		
		$.ajax({
			url: "/delete-news-with-ajax",
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
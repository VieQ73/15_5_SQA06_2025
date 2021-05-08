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
			<div class="wrap-contact2 container" style="margin-top:-1rem;border: solid 1px black; background: #ccffff;">
				<div class="form" style="margin-top:-2rem; margin-bottom: -3rem; ">
					<h2 class="title">Chọn khoảng thời gian</h2>
					<div class="form-grids row widget-shadow" style="margin-top:2rem;"
						data-example-id="basic-forms">
						<div class="form-body" style="">
							<form:form action="${pageContext.request.contextPath}/admin/thongke/search">
								<input type="hidden" name="action" value="" id="action">
								<input type="hidden" value="${bd }" id="bd">
								<input type="hidden" value="${kt }" id="kt">
								<div class="form-group">
									<label>Từ ngày: </label> <input type="date" name="ngayBD"
										class="form-control" id="ngayBD">
								</div>
								<div class="form-group">
									<label> Đến ngày: </label> <input type="date" name="ngayKT"
										class="form-control" id="ngayKT">
								</div>
								<div class="container-contact2-form-btn" style="margin-left: -5rem;">
									<div class="wrap-contact2-form-btn">
										<div class="contact2-form-bgbtn"></div>
										<button class="contact2-form-btn" type="submit" onclick="isThongke();">Thống kê</button>
									</div>
									
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
			<div id="thongkeList" style="margin-top: 3rem;">
			<h2>Danh sách sản phẩm</h2>
				<table class="table table-striped table-dark">
				  <thead>
				    <tr>
				      <th scope="col">#</th>
				      <th scope="col">Tên sản phẩm</th>
				      <th scope="col">Số lượng</th>
				      <th scope="col">Tổng giá</th>
				    </tr>
				  </thead>
				  <tbody>
				    <c:forEach items="${thongke}" var="thongke" varStatus="loop">
				    	<tr>
					      <th scope="row">${loop.index + 1}</th>
					      <td>${thongke.product.title}</td>
					      <td>${thongke.tongSoLuong}</td>
					      <td>
					      	<fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${thongke.tongGia}" /> đ
					      </td>
					    </tr>
				    </c:forEach>
				    <tr>
				    	<th></th>
				    	<td style="text-align: right; font-weight: bold;">Tổng:</td>
				    	<td style="font-weight: bold;">${tongSL}</td>
				    	<td style="font-weight: bold;"><fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${tongG}" /> đ</td>
				    </tr>
				    
				  </tbody>
				</table>
			</div>
			<div id="editor"></div>
			<button class="btn btn-success" id="cmd" onclick="isExport()">
				    			<i class="fas fa-download" style="margin-right: 1rem;"></i>Xuất excel</button>
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
	    
	    /* $('#thongkeList').hide(); */
	    var d = $('#bd').val();
	    var today = $('#kt').val();

	    $('#ngayKT').val(today);
	    $('#ngayBD').val(d);
	} );
	function isThongke(){
		$('#action').val("thongke");
		$('#thongkeList').show();
	}
	function isExport(){
		
		location.replace("${pageContext.request.contextPath}/admin/thongke/export/"+ $('#ngayBD').val()+"/"+ $('#ngayKT').val());
	}
	</script>
	
	
	
</body>
</html>
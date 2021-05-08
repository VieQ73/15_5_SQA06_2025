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
			<div class="wrap-contact2 container" style="margin-top:-1rem;border: solid 1px black;">
				<div class="form" style="margin-top:-2rem;">
					<h2 class="title">Chọn khoảng thời gian</h2>
					<div class="form-grids row widget-shadow" style="margin-top:2rem;"
						data-example-id="basic-forms">
						<div class="form-body" style="">
							<form:form action="${pageContext.request.contextPath}/admin/thongke/search">
								<input type="hidden" name="action" value="" id="action">
								<div class="form-group">
									<label>Từ ngày: </label> <input type="date" name="ngayBD"
										class="form-control" id="ngayBD">
								</div>
								<div class="form-group">
									<label> Đến ngày: </label> <input type="date" name="ngayKT"
										class="form-control" id="ngayKT">
								</div>
								<div class="container-contact2-form-btn">
									<div class="wrap-contact2-form-btn">
										<div class="contact2-form-bgbtn"></div>
										<button class="contact2-form-btn" type="submit" onclick="isThongke();"
										data-toggle="collapse" data-target="#thongkeList" aria-expanded="false" aria-controls="thongkeList">Thống kê</button>
									</div>
								</div>
							</form:form>
						</div>
					</div>
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
	    
	    /* $('#thongkeList').hide(); */
	    var now = new Date();
	    var today = new Date().toISOString().substring(0, 10);
	    
	    var d = new Date(now.getFullYear(), now.getMonth()-1, now.getDate()).toISOString().substring(0, 10);
	    $('#ngayKT').val(today);
	    $('#ngayBD').val(d);
	} );
	function isThongke(){
		$('#action').val("thongke");
		$('#thongkeList').show();
	}
	</script>
</body>
</html>
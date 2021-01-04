<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>Admin</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/css.jsp"></jsp:include>
	
</head> 
<body class="cbp-spmenu-push">

	<div class="main-content">
		<jsp:include page="/WEB-INF/views/back-end/commonAdmin/menu.jsp"></jsp:include>
		
		
		<!-- header-starts -->
		<jsp:include page="/WEB-INF/views/back-end/commonAdmin/header.jsp"></jsp:include>
		
		<!-- //header-ends -->
		<!-- main content start-->
		<div class="content-main">
			<div class="bg-contact2" style="background-image: url('${pageContext.request.contextPath}/images/bg-01.jpg');">
		<div class="container-contact2">
			<div class="wrap-contact2">
				<div class="forms">
				<h2 class="title1">Thêm dach mục</h2>
				<div class="form-grids row widget-shadow" data-example-id="basic-forms"> 
					<div class="form-body">
						<sf:form action="${pageContext.request.contextPath}/admin/addCategory" method="post" modelAttribute="category">
							<sf:hidden path="id"/>
							<div class="form-group">
								<label>Tên danh mục</label> <sf:input type="text" class="form-control" path="name" placeholder="Name"></sf:input>
							</div>
							<div class="form-group">
								<label>Mô tả</label> <sf:textarea id="txtDetailDescription" class="form-control" path="description" placeholder="Description"></sf:textarea>
							</div>
							<div class="container-contact2-form-btn">
						<div class="wrap-contact2-form-btn">
							<div class="contact2-form-bgbtn"></div>
							<button class="contact2-form-btn" type="submit">
								Add
							</button>
						</div>
					</div>
						</sf:form> 
					</div>
				</div>
			</div>
			</div>
		</div>
		</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/footer.jsp"></jsp:include>
    <!-- js-->
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/js.jsp"></jsp:include>
    <script type="text/javascript">
        	$( document ).ready(function() {
        		$('#txtDetailDescription').summernote();
        	});        
        </script>
</body>
</html>
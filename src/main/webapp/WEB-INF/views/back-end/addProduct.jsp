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
			<div class="bg-contact2"
				style="background-image: url('${pageContext.request.contextPath}/images/bg-01.jpg');">
				<div class="container-contact2">
					<div class="wrap-contact2">
						<div class="forms">
							<h2 class="title1">Thêm sản phẩm</h2>
							<div class="form-grids row widget-shadow"
								data-example-id="basic-forms">
								<div class="form-body">
									<form:form
										action="${pageContext.request.contextPath}/admin/addProduct"
										method="post" modelAttribute="product" enctype="multipart/form-data">
										
										<form:hidden path="id"/>
										<div class="form-group">
											<label>Category</label>
											<form:select type="text" class="form-control" path="category.id">
												<form:options items="${categories}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="form-group">
											<label>Tên sản phẩm</label>
											<form:input type="text" class="form-control" path="title"
												placeholder="Tên sản phẩm"></form:input>
										</div>
										<div class="form-group">
											<label>Price</label>
											<form:input type="text" class="form-control" path="price"></form:input>
										</div>
										
										<div class="form-group">
											<label>Short Descrition</label>
											<form:textarea id="txtShortlDescription" class="form-control" path="shortDes"
												placeholder="Thông tin sản phẩm"></form:textarea>
										</div>
										<div class="form-group">
											<label>Images</label>
											<input type="file" name="product_image" class="form-control" multiple="multiple">
										</div>
										<div class="form-group">
											<label>Details</label>
											<form:textarea id="txtDetailDescription" class="form-control" path="shortDetails"
												placeholder="Mô tả"></form:textarea>
										</div>
										
										<div class="container-contact2-form-btn">
											<div class="wrap-contact2-form-btn">
												<div class="contact2-form-bgbtn"></div>
												<button class="contact2-form-btn" type="submit">
													Add</button>
											</div>
										</div>
									</form:form>
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
        		$('#txtDetailDescription').summernote(
        		);
        		$('#txtShortlDescription').summernote(
        		);
        	});        
        </script>
</body>
</html>
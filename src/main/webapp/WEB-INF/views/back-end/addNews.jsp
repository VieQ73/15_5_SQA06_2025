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
<title>Admin add product</title>
<meta charset="utf-8">
<jsp:include page="/WEB-INF/views/back-end/commonAdmin/css.jsp"></jsp:include>
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css" rel="stylesheet">
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
							<h2 class="title1">Thêm quà tặng</h2>
							<div class="form-grids row widget-shadow"
								data-example-id="basic-forms">
								<div class="form-body">
									<form:form
										action="${pageContext.request.contextPath}/admin/addNews"
										method="post" modelAttribute="news" enctype="multipart/form-data" id="form-addnews">
										
										<form:hidden path="id"/>
										
										<div class="form-group">
											<label>Tên bản tin</label>
											<form:input type="text" class="form-control" path="title"
												placeholder="Tên bản tin" id="title"></form:input>
											<span class="form-message alert-danger"></span>
										</div>
										<div class="form-group">
											<label>Vắn tắt</label>
											<form:textarea class="form-control" path="short_news"
												placeholder="Vắn tắt" id="short_news"></form:textarea>
											<span class="form-message alert-danger"></span>
										</div>
										<div class="form-group">
											<label>Hình ảnh</label>
											<input type="file" name="news_image" class="form-control" multiple="multiple">
										</div>
										<div class="form-group">
											<label>Nội dung tin tức</label>
											<form:textarea id="description" class="form-control" path="news_detail"
												placeholder="Nội dung tin tức"></form:textarea>
											<span class="form-message alert-danger"></span>
										</div>
										
										<div class="container-contact2-form-btn">
											<div class="wrap-contact2-form-btn">
												<div class="contact2-form-bgbtn"></div>
												<button class="contact2-form-btn" type="submit">
													Thêm</button>
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
	<script src="${pageContext.request.contextPath}/summernote/summernote.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>
	<script src="${pageContext.request.contextPath}/js/validator.js"></script>
	<script type="text/javascript">
    	$( document ).ready(function() {
    		$('#description').summernote();
    	});        
    </script>
    <script type="text/javascript">
  		Validator({
  			form: '#form-addnews',
  			formGroupSelector: '.form-group',
  			errorSelector: '.form-message',
  			rules:[
  				Validator.isRequired('#title', "Tên bản tin không được để trống!"),
  				Validator.isRequired('#short_news', "Vắn tắt không được để trống!"),
  				Validator.isRequired('#description', "Nội dung tin không được để trống!"),
  			],
  			/* onSubmit: function (data) {
  				// call API
  				console.log(data);
  			} */
  		});
  	</script>
</body>
</html>
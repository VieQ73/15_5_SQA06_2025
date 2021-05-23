<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>Dat hang thanh cong</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
	<style type="text/css">
		.nd-success{
			text-align: center;
			color: rgb(242, 121, 34);
		}
		.nd-success a{
			color: rgb(242, 121, 34);
		}
		.nd-success a:hover{
			text-decoration: none;
			color: black;
		}
	</style>
</head>
<body>
<div class="wapper">
	<div class="go-to-top btn btn-secondary" id="go-to-top" >
		<i class="fas fa-angle-up"></i>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
			<div class="content">
			<div class="nd-success container">
				<a href="${pageContext.request.contextPath}/">
					<img src="${pageContext.request.contextPath}/images/icon_LargeShoppingCart.jpg"/>
					<h1>Đặt hàng thành công!</h1>
					<h3>Đơn hàng sẽ được chuyển trong thời gian sớm nhất</h3>
				</a>
			</div>
			</div>
			<!-- end content -->
			<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>	
</body>
</html>




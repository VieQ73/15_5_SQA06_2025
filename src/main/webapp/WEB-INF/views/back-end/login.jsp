<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login</title>
	<meta charset="UTF-8">

<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/utilLogin.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainLogin.css">
	
<!--===============================================================================================-->
</head>
<body>
	
	
	<div class="container-login100" style="background-image: url('images/bg-01.jpg');">
		<div class="wrap-login100 p-l-55 p-r-55 p-t-80 p-b-30">
			<form class="login100-form validate-form" method="POST" action="/cai-nay-la-thuoc-tinh-action-trong-form-login">
				<span class="login100-form-title p-b-37">
					Sign In
				</span>
				<c:if test="${not empty param.co_loi_xay_ra}">
							<div class="alert alert-danger" role="alert">
							  	Login attempt was not successful, try again.
							</div>
						</c:if>
				<div class="wrap-input100 validate-input " data-validate="Enter username or email">
					<input class="input100 form-control" type="text" name="username" placeholder="username or email">
					<span class="focus-input100"></span>
				</div>

				<div class="wrap-input100 validate-input" style="margin-top:15px;" data-validate = "Enter password">
					<input class="input100 form-control" type="password" name="password" placeholder="password">
					<span class="focus-input100"></span>
				</div>

				<div class="container-login100-form-btn " style="margin-top:25px;">
					<button class="login100-form-btn" type="submit">
						Sign In
					</button>
				</div>

				<div class="text-center p-t-57 p-b-20">
					<span class="txt1">
						Or login with
					</span>
				</div>

				<div class="flex-c p-b-112">
					<a href="#" class="login100-social-item">
						<i class="fa fa-facebook-f"></i>
					</a>

					<a href="#" class="login100-social-item">
						<img src="${pageContext.request.contextPath}/images/icons/icon-google.jpg" alt="GOOGLE">
					</a>
				</div>
				<div class="text-center">
					<a href="${pageContext.request.contextPath}/registration_get" class="txt2 hov1">
						Create Account
					</a>
				</div>
				<div class="text-center">
					<a href="${pageContext.request.contextPath}/" class="txt2 hov1">
						Home
					</a>
				</div>
			</form>

			
		</div>
	</div>
	
	

	<div id="dropDownSelect1"></div>

	<script src="${pageContext.request.contextPath}/js/loginmain.js"></script>
	
</body>
</html>
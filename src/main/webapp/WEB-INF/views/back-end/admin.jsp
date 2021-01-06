<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@page import="com.devpro.entities.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%!
	public String getEmailLogined() {
		String email = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			email = ((User)principal).getEmail();
		}
		return email;
	}
%>
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
			<iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d9704.935602631624!2d105.77083744403993!3d21.040828268014636!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x70d71b071349fa94!2zQ8O0bmcgVHkgQ-G7lSBQaOG6p24gRGV2cHJvIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1609826238547!5m2!1svi!2s" width="100%" height="700" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/footer.jsp"></jsp:include>
</div>
    <!-- js-->
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/js.jsp"></jsp:include>
    
</body>
</html>
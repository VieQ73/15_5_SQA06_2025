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
			<p><%= getEmailLogined()%></p>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/footer.jsp"></jsp:include>
</div>
    <!-- js-->
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/js.jsp"></jsp:include>
    
</body>
</html>
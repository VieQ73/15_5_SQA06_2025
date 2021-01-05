<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@page import="com.devpro.entities.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%!
	public String getEmailLogined() {
		String email = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			email = ((User)principal).getUsername();
		}
		return email;
	}
%>

<header>
	<span></span>
	<div class="header-top">
		<div class="header-top-text">
			Hotline:<b>0911 445 522</b>
		</div>
	</div>
	<div class="header-main">
		<div class="logo1">
			<a href="${pageContext.request.contextPath}/"><img
				src="${pageContext.request.contextPath}/images/logo.jpg"></a>
		</div>
		<div class="logo2">
			<a href="${pageContext.request.contextPath}/"><img
				src="${pageContext.request.contextPath}/images/mua-la-co-qua-ok.jpg"></a>
		</div>
		<div class="search-cart">
			<form action="${pageContext.request.contextPath}/search" method="post">
				<input type="text" class="form-control"
					placeholder="tìm kiếm sản phẩm..." name="keyword" id="keyword">
				<button type="submit" id="btnClear" onclick="clearSearch()">
					<i class="fas fa-search"></i>
				</button>
			</form>
			
				<div class="btn-cart container"  id="cart">
					<a href="${pageContext.request.contextPath}/cart">
						<i class="fas fa-shopping-cart "></i><span>(</span><span id="so_luong_sp"></span><span>)</span>
					</a>
				</div>
			
		</div>
		
	</div>
	<div class="nav1">
		<nav class="navbar navbar-expand-sm navbar-dark">
			<ul class="navbar-nav">
				<li>
					<div class="dropdown">
					    <div class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
					      DANH MỤC SẢN PHẨM
					    </div>
					    <div class="dropdown-menu">
					      	<c:forEach var = "category" items = "${categories }">
								<a class="dropdown-item btn" href="${pageContext.request.contextPath}/category/${category.seo}">${category.name }</a>
							</c:forEach>
					    </div>
					 </div>
				</li>
				<li><a class="nav-link" href="#">HƯỚNG DẪN MUA HÀNG</a></li>
				<li><a class="nav-link" href="#">TIN NỔI BẬT</a></li>
				<li><a class="nav-link" href="#">SẢN PHẨM SALE OFF</a></li>
				<li><a class="nav-link" href="#">KHO QUÀ TẶNG</a></li>
				<li><a class="nav-link" href="#" data-toggle="modal"
					data-target="#exampleModal">LIÊN HỆ</a>
				</li>
				<li>
				<%if(getEmailLogined().isEmpty()) {%>
					<a class="nav-link" href="${pageContext.request.contextPath}/cai-nay-la-mapping-trong-adminlogincontroller">ĐĂNG NHẬP</a>
				<%} else{%>
					<a class="nav-link" href="${pageContext.request.contextPath}/cai-nay-la-thuoc-tinh-href-trong-the-a-logout">ĐĂNG XUẤT</a>
				<%} %>
				</li>
			</ul>
		</nav>
		
	</div>
</header>
<!-- contact -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Contact</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button> 
			</div>
			<div class="modal-body">
				<div class="wrap-input2 validate-input"
					data-validate="Name is required">
					<input class="input2" type="text" id="name"></input> <span
						class="focus-input2" data-placeholder="NAME"></span>
				</div>
				<div class="wrap-input2 validate-input"
					data-validate="Valid email is required: ex@abc.xyz">
					<input class="input2" type="text" id="email"></input> <span
						class="focus-input2" data-placeholder="EMAIL"></span>
				</div>
				<div class="wrap-input2 validate-input"
					data-validate="Message is required">
					<textarea class="input2" id="message"></textarea>
					<span class="focus-input2" data-placeholder="MESSAGE"></span>
				</div>
				<div class="container-contact2-form-btn">
					<div class="wrap-contact2-form-btn">
						<div class="contact2-form-bgbtn"></div>
						<button class="contact2-form-btn" onclick="Shop.saveContact();">
							Send Your Message</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    function clearSearch() {
        window.location = "[[@{/search}]]";
    }
</script>

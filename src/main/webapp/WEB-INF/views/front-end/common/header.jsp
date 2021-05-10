<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<header>
<div id="header_non_repon">
	<span></span>
	<div class="header-top">
		<div class="header-top-text">
			Hotline:<b>0911 445 522</b>
		</div>
	</div>
	<div class="header-main">
		<div class="logo1">
			<a href="${pageContext.request.contextPath}/"><img
				src="${pageContext.request.contextPath}/images/logo_beauty.jpg"></a>
		</div>
		<div class="logo2">
			<a href="${pageContext.request.contextPath}/">
			<img src="${pageContext.request.contextPath}/images/mua-la-co-qua-ok.jpg"></a>
		</div>
		<div class="search-cart">
			<form action="${pageContext.request.contextPath}/search" method="post" id="form-search">
				
				<input type="text" class="form-control" style="width: 300px;"
					placeholder="tìm kiếm sản phẩm..." name="keyword" id="keyword">
				
				<button style="margin-left: 10px; width:60px; margin-top:2px;" type="submit" id="btnClear" onclick="clearSearch()">
					<i class="fas fa-search"></i>
				</button>
			</form>
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
				<li><a class="nav-link" href="${pageContext.request.contextPath}/huongdanmuahang">HƯỚNG DẪN MUA HÀNG</a></li>
				<li><a class="nav-link" href="${pageContext.request.contextPath}/news">TIN NỔI BẬT</a></li>
				<li><a class="nav-link" href="${pageContext.request.contextPath}/saleoff">SẢN PHẨM GIẢM GIÁ</a></li>
				<li><a class="nav-link" href="${pageContext.request.contextPath}/gift">KHO QUÀ TẶNG</a></li>
				<li><a class="nav-link" href="https://www.facebook.com/BeautyShop-110318264452364/">LIÊN HỆ</a>
				</li>
				<li>
					<a class="btn btn-success" href="${pageContext.request.contextPath}/cart"><i class="fas fa-shopping-cart"></i><span id="so_luong_sp" class="badge"></span></a>
				</li>
			</ul>
		</nav>
		
	</div>
	</div>
	<div id="header_repon" style="display: none;">
		<nav class="navbar navbar-expand-md fixed-top navbar-dark">
	        <a class="" href="${pageContext.request.contextPath}/" style="padding: 0px; margin: 0px;">
	        	<img src="${pageContext.request.contextPath}/images/logo_beauty.jpg" width="110rem" height="42rem">
			</a>
			
			<form action="${pageContext.request.contextPath}/search" method="post" style="display: flex">
					<input type="text" class="form-control" style="width: 150px;"
						placeholder="Search..." name="keyword" id="keyword">
						
					<button class="btn" style="margin-left: 5px; width:60px; border: white solid 1px;" type="submit" id="btnClear" onclick="clearSearch()">
						<i class="fas fa-search"></i>
					</button>
				</form>
			<a class="btn btn-success" href="${pageContext.request.contextPath}/cart"><i class="fas fa-shopping-cart"></i><span id="so_luong_sp" class="badge"></span></a>
	        <button type="button" class="navbar-toggler" style="border: white solid 1px;" data-toggle="collapse" data-target="#navbarCollapse">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	
	        <div class="collapse navbar-collapse" id="navbarCollapse">
	            <div class="navbar-nav">
	                <a href="${pageContext.request.contextPath}/" class="nav-item nav-link">Home</a>
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
	                <a class="nav-link" href="${pageContext.request.contextPath}/huongdanmuahang">HƯỚNG DẪN MUA HÀNG</a>
					<a class="nav-link" href="${pageContext.request.contextPath}/news">TIN NỔI BẬT</a>
					<a class="nav-link" href="${pageContext.request.contextPath}/saleoff">SẢN PHẨM SALE OFF</a>
					<a class="nav-link" href="${pageContext.request.contextPath}/gift">KHO QUÀ TẶNG</a>
					<a class="nav-link" href="https://www.facebook.com/BeautyShop-110318264452364/">LIÊN HỆ</a>
	            </div>
	        </div>
	    </nav>

	</div>
</header>

<script type="text/javascript">
    function clearSearch() {
        window.location = "[[@{/search}]]";
    }
    
</script>


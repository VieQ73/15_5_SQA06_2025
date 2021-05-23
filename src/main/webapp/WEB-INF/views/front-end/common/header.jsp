<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<header>
	<div id="header_non_repon">
		<div class="header-topp">
			<span></span>
			<div class="header-top">
				<div class="header-top-text">
					Hotline:<b>0911 445 522</b>
				</div>
			</div>
			<div class="header-main container">
				<div class="logo1">
					<a href="${pageContext.request.contextPath}/"><img
						src="${pageContext.request.contextPath}/images/logo_beauty.jpg"></a>
				</div>
				<div class="logo2">
					<a href="${pageContext.request.contextPath}/">
					<img src="${pageContext.request.contextPath}/images/mua-la-co-qua-ok.jpg"></a>
				</div>
				<div class="search-cart" id="search-cart">
					<form action="${pageContext.request.contextPath}/search" method="post" id="form-search">
						
						<input type="text" class="form-control" style="width: 300px;"
							placeholder="tìm kiếm sản phẩm..." name="keyword" id="keyword" value="">
						
						<button style="margin-left: 10px; width:60px; margin-top:2px;" type="submit" id="btnClear" onclick="clearSearch()">
							<i class="fas fa-search"></i>
						</button>
					</form>
				</div>
			</div>
		</div>
		<div class="nav1">
			<nav class="navbar navbar-expand-sm navbar-dark" id="nav-non-repon">
				<ul class="navbar-nav">
					<li style="margin-left: -10px;">
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
		<div id="nav-repon" style="width: 100%;">
			
			<span></span>
			<div class="header-top">
				<div class="header-top-text">
					Hotline:<b>0911 445 522</b>
				</div>
			</div>
			<div class="header-main container">
				<div class="logo1">
					<a href="${pageContext.request.contextPath}/"><img
						src="${pageContext.request.contextPath}/images/logo_beauty.jpg"></a>
				</div>
				<div class="logo2">
					<a href="${pageContext.request.contextPath}/">
					<img src="${pageContext.request.contextPath}/images/mua-la-co-qua-ok.jpg"></a>
				</div>
			</div>
			
			<div class="nav1">
				<nav class="navbar navbar-expand-sm navbar-dark">
					<ul class="navbar-nav">
						<li>
							<label for="nav-mobile-input" class="btn btn-secondary ">
								<i class="fas fa-bars"></i>
							</label>
						</li>
						<li>
							<div class="search-cart">
								<form action="${pageContext.request.contextPath}/search" method="post" id="form-search" style="display: flex;">
									<input type="text" class="form-control" style="width: 450px;"
										placeholder="tìm kiếm sản phẩm..." name="keyword" id="keyword">
									<button style="margin-left: 10px; width: 60px; margin-top: 2px; height: 39px; color: #ed68c1; font-size: 20px; border: 1px solid #bbbbbb; background: #fff; border-radius: 5px;"
										type="submit" id="btnClear" onclick="clearSearch()">
										<i class="fas fa-search"></i>
									</button>
								</form>
							</div>
						</li>
						
						<li>
							<a class="btn btn-success" href="${pageContext.request.contextPath}/cart"><i class="fas fa-shopping-cart"></i><span id="so_luong_sp" class="badge"></span></a>
						</li>
					</ul>
				</nav>
				<input type="checkbox" hidden="" class="nav_input" id="nav-mobile-input">
				<label for="nav-mobile-input" class="over__lay"></label>
				<nav class="nav_repon" id="">
					<div class="nav-repon-top">
						<a href="${pageContext.request.contextPath}/">
							<img src="${pageContext.request.contextPath}/images/logo_beauty.jpg">
						</a>
						<label for="nav-mobile-input" class="nav_repon-close">
							<i class="fas fa-times" ></i>
						</label>
					</div>
					<ul class="navbar-mobile-nav">
						<li >
							<div class="dropdown dropright btn btn-warning" style="color: white; font-size: 1.4rem; border-bottom: 1px solid white; font-weight: bold;">
							    <div class=" dropdown-toggle" data-toggle="dropdown">
							      DANH MỤC SẢN PHẨM
							    </div>
							    <div class="dropdown-menu">
							      	<c:forEach var = "category" items = "${categories }">
										<a class="dropdown-item btn" href="${pageContext.request.contextPath}/category/${category.seo}">${category.name }</a>
									</c:forEach>
							    </div>
							 </div>
						</li>
						<li><a class="nav-mobile-link" href="${pageContext.request.contextPath}/huongdanmuahang">HƯỚNG DẪN MUA HÀNG</a></li>
						<li><a class="nav-mobile-link" href="${pageContext.request.contextPath}/news">TIN NỔI BẬT</a></li>
						<li><a class="nav-mobile-link" href="${pageContext.request.contextPath}/saleoff">SẢN PHẨM GIẢM GIÁ</a></li>
						<li><a class="nav-mobile-link" href="${pageContext.request.contextPath}/gift">KHO QUÀ TẶNG</a></li>
						<li><a class="nav-mobile-link" href="https://www.facebook.com/BeautyShop-110318264452364/">LIÊN HỆ</a>
						</li>
						
					</ul>
					
				</nav>
			</div>
		</div>
	</div>
</header>

<script type="text/javascript">
    function clearSearch() {
        window.location = "[[@{/search}]]";
    }
    
</script>


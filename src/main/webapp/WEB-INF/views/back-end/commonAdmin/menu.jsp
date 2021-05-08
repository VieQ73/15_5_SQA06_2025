<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>

<div class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left" id="cbp-spmenu-s1">
			<!--left-fixed -navigation-->
			<aside class="sidebar-left">
				<nav class="navbar navbar-inverse">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".collapse" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<h1><a class="navbar-brand" href="${pageContext.request.contextPath}/"><span class="fa fa-area-chart"></span>Imua.com.vn<span class="dashboard_text">Design dashboard</span></a></h1>
					</div>
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="sidebar-menu">
							<li>
								<a href="${pageContext.request.contextPath}/admin">
									<i class="fas fa-home"></i>&nbsp
									<span>Home</span>
								</a>
							</li>
							<li class="treeview">
				                <a href="#">
					                <i class="far fa-edit"></i>
					                <span>Quản lý sản phẩm</span>
					                <i class="fa fa-angle-left pull-right"></i>
				                </a>
				                <ul class="treeview-menu">
				                  <li><a href="${pageContext.request.contextPath}/admin/listProducts"><i class="fa fa-angle-right"></i> Danh sách sản phẩm</a></li>
				                  <li><a href="${pageContext.request.contextPath}/admin/addProduct"><i class="fa fa-angle-right"></i> Thêm sản phẩm</a></li>
				                </ul>
				             </li>
							<li class="treeview">
								<a href="#">
									<i class="far fa-edit"></i>&nbsp
									<span>Quản lý danh mục</span>
									<i class="fa fa-angle-left pull-right"></i>
								</a>
								<ul class="treeview-menu">
				                  <li><a href="${pageContext.request.contextPath}/admin/listCategory"><i class="fa fa-angle-right"></i> Danh sách danh mục</a></li>
				                  <li><a href="${pageContext.request.contextPath}/admin/addCategory"><i class="fa fa-angle-right"></i> Thêm danh mục</a></li>
				                </ul>
							</li>
							<li class="treeview">
								<a href="#">
									<i class="fas fa-gift"></i>&nbsp
									<span>Quản lý quà tặng</span>
									<i class="fa fa-angle-left pull-right"></i>
								</a>
								<ul class="treeview-menu">
				                  <li><a href="${pageContext.request.contextPath}/admin/listGift"><i class="fa fa-angle-right"></i> Danh sách quà tặng</a></li>
				                  <li><a href="${pageContext.request.contextPath}/admin/addGift"><i class="fa fa-angle-right"></i> Thêm quà tặng</a></li>
				                </ul>
							</li>
							<li class="treeview">
								<a href="#">
									<i class="fas fa-newspaper"></i>&nbsp
									<span>Quản lý tin tức</span>
									<i class="fa fa-angle-left pull-right"></i>
								</a>
								<ul class="treeview-menu">
				                  <li><a href="${pageContext.request.contextPath}/admin/listNews"><i class="fa fa-angle-right"></i> Danh sách tin tức</a></li>
				                  <li><a href="${pageContext.request.contextPath}/admin/addNews"><i class="fa fa-angle-right"></i> Thêm tin tức</a></li>
				                </ul>
							</li>
							<li class="treeview">
								<a href="#">
									<i class="fas fa-percent"></i>&nbsp
									<span>Quản lý giảm giá</span>
									<i class="fa fa-angle-left pull-right"></i>
								</a>
								<ul class="treeview-menu">
				                  <li><a href="${pageContext.request.contextPath}/admin/listSale"><i class="fa fa-angle-right"></i> Danh sách đợt khuyến mại</a></li>
				                  <li><a href="${pageContext.request.contextPath}/admin/addSale"><i class="fa fa-angle-right"></i> Thêm khuyến mại</a></li>
				                </ul>
							</li>
							<li class="treeview">
								<a href="#">
									<i class="fas fa-shopping-cart"></i>&nbsp
									<span>Quản lý đơn hàng</span>
									<i class="fa fa-angle-left pull-right"></i>
								</a>
								<ul class="treeview-menu">
				                  <li><a href="${pageContext.request.contextPath}/admin/order"><i class="fa fa-angle-right"></i> Danh sách đơn hàng</a></li>
				                  
				                </ul>
							</li>
							<li class="treeview">
								<a href="${pageContext.request.contextPath}/admin/thongke">
									<i class="far fa-edit"></i>&nbsp
									<span>Thống kê</span>
									<i class="fa fa-angle-left pull-right"></i>
								</a>
					
							</li>
						</ul>
					</div>
					<!-- /.navbar-collapse -->
				</nav>
			</aside>
		</div>
		
		
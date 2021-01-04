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
	public Integer getEmailLogined() {
		Integer id=8;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			id = ((User)principal).getId();
		}
		return id;
	}
%>
<!DOCTYPE html>
<html>
<head>
	<title>products</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
</head>
<body>
	<div class="wapper">
		<div class="go-to-top"> <button id="go-to-top"><i class="fas fa-chevron-up"></i></button></div>
		
		<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
		<div class="container">
			<div class="col-lg-12">
				<div class="row mb-4">
					<div class="col">
			<h3 class="title-5 m-b-35">Danh sách đơn hàng</h3>

					<div class="table-responsive table-responsive-data2">
						
						<table class="table table-data2 display" style="background: #FFEFDB;" id="">
							<thead>
								<tr>
									<th>#</th>
									<th>Tên khách hàng</th>
									<th>Địa chỉ nhận</th>
									<th>Số điện thoại</th>
									<th>Tổng giá</th>
									<th>Ngày đặt</th>
									<th>Trạng thái</th>
									
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="historyCart" items="${historyCarts }" varStatus="loop">
									<tr class="tr-shadow">
										<th scope="row">${loop.index + 1}</th>
										<td>${historyCart.customerName}</td>
										<td>${historyCart.customerAddress}</td>
										<td>${historyCart.phone}</td>
										<td>${historyCart.total}đ</td>
										<td>
											<c:if test="${historyCart.status_ok == 0}">
												<span  style="color: red;">${historyCart.createdDate}</span>
											</c:if>
											<c:if test= "${historyCart.status_ok == 1}">
												<span  style="color: blue;">${historyCart.createdDate}</span>
											</c:if>
											<c:if test= "${historyCart.status_ok == 2}">
												<span  style="color: green;">${historyCart.createdDate}</span>
											</c:if>
										</td>
										<td>
											<c:if test="${historyCart.status_ok == 0}">
												<span class="badge" style="background: red;">Đặt hàng</span>
											</c:if>
											<c:if test= "${historyCart.status_ok == 1}">
												<span class="badge" style="background: blue;">Giao hàng</span>
											</c:if>
											<c:if test= "${historyCart.status_ok == 2}">
												<span class="badge" style="background: green;">Nhận hàng</span>
											</c:if>
										</td>
										<td>							
											<a href="${pageContext.request.contextPath}/user/historyCart/${historyCart.id}">
												<button
													class="item" data-toggle="tooltip" data-placement="top"
													title="Edit">
													<span class="badge" style="background: green;">Xem chi tết</span>
												</button>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
			</div>
			</div>
			</div>
			</div>
			<!-- end content -->
		</div>
		<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>
</body>
</html>





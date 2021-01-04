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
	public Integer getIdLogined() {
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
						<table class="table table-hover" style="background: #FFEFDB;">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">Tên sản phẩm</th>
									<th scope="col">Số lượng</th>
									<th scope="col">Đơn giá</th>
								</tr>
							</thead>
							<tbody>
							    <c:forEach items="${GIO_HANG.sanPhamTrongGioHangs }" var="item" varStatus="loop"> 
						    
								    <tr>
										<th scope="row">${loop.index + 1}</th>
										<td>${item.tenSP }</td>
										<td>${item.soluong }</td>
										<td>
											<fmt:formatNumber type="number" maxIntegerDigits="13"
										value="${item.giaBan }" /> đ
										
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
					</div>
				</div>
				<h1 class="my-4">Gửi đơn hàng</h1>
				<div class="row mb-6">
					<div class="col col-lg-6">
						<form action="${pageContext.request.contextPath}/user/luu_don_hang " method="post">
							<div class="form-group">
								<label for="name">Tên khách hàng:</label>
								<input type="text" class="form-control" id="name" name="name">
							</div>					
							<div class="form-group">
								<label for="phone">Điện thoại liên hệ:</label>
								<input type="text" class="form-control" id="phone" name="phone">
							</div>
							<div class="form-group">
								<label for="address">Địa chỉ giao hàng:</label>
								<input type="text" class="form-control" id="address" name="address">
							</div>
							<button type="submit" class="btn btn-warning">Gửi đơn hàng</button>
						</form>
					</div>
					<div class="col mb-6 col-lg-6">
						<%-- <form action="${pageContext.request.contextPath}/send-email" method="post">
							<div class="form-group">
								<label for="email">Địa chỉ email:</label>
								<input type="email" class="form-control" id="FRIEND_EMAIL" name="FRIEND_EMAIL">
							</div>
							<button type="submit" class="btn btn-warning">Send</button>
						</form> --%>
						<a class="btn btn-success" href="${pageContext.request.contextPath}/user/historyCart">Lịch sử mua hàng</a>
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
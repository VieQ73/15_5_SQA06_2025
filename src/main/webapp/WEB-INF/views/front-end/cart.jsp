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
	<title>Cart</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
</head>
<body>
	<div class="wapper">
		<div class="go-to-top"> <button id="go-to-top"><i class="fas fa-chevron-up"></i></button></div>
		
		<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
			<div class="container">
			<div class="col-lg-12" style="min-height:30em;
			background-image:linear-gradient(40deg, rgba(237,104,193,0.2) 20%, rgba(255,255,255,0));
			background-position: center;
		    background-size: cover;
		    background-attachment: fixed;
			">
				<div class="row mb-4">
					<div class="col">
						<c:choose>
							<c:when test="${empty GIO_HANG.sanPhamTrongGioHangs}">
								<div class="alert alert-danger">
								  <strong>!Không </strong> có sản phẩm trong giỏ hàng
								</div>
								
							</c:when>
							<c:otherwise>
								${ messsage }
								<table class="table table-hover" style="background: #FFEFDB;">
									<thead>
										<tr>
											<th scope="col">#</th>
											<th scope="col">Tên sản phẩm</th>
											<th scope="col">Số lượng</th>
											<th scope="col">Đơn giá</th>
											<th scope="col"></th>
											
										</tr>
									</thead>
									<tbody>
									    <c:forEach items="${GIO_HANG.sanPhamTrongGioHangs }" var="item" varStatus="loop"> 
								    
										    <tr id="sp${item.productId }">
												<th scope="row">${loop.index + 1}</th>
												<td class="tensp" data-masp ="${item.productId }"><a style="color: black;" href="${pageContext.request.contextPath}/products/${item.seo}">${item.tenSP }</a></td>
												<td class="soluong">
													<input class="so_luong_sp${item.productId }" type="number" value="${item.soluong }" min="1" max="${item.amount}" onchange="addP(${item.productId }, ${item.giaBan },${item.amount})">
													<input class="slBD${item.productId }" type="number" value="${item.soluong }" style="display: none;">
												</td>
												
												<td class="giatien${item.productId }">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
												value="${item.tongGia }" /> đ
												
												</td>
												<td>
													<button onclick="Shop.xoa_sp_trong_gio_hang(${item.productId})" type="button" class="item btn btn-danger">
													 	<i class="far fa-trash-alt"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
										<tr>
											<td></td>
											<td></td>
											<td>Tổng tiền</td>
											<td id="tongtienT">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
												value="${tong_gia }" /> đ
												</td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<h1 class="my-4">Gửi đơn hàng</h1>
				<div class="row mb-6">
					<div class="col col-lg-6">
						<form action="${pageContext.request.contextPath}/luu_don_hang " method="post">
							<div class="form-group">
								<label for="phone">Điện thoại liên hệ:</label>
								<input type="tel" class="form-control" id="phone" name="phone" onblur="checkPhone()">
							</div>
							<div class="form-group namef" style="display: none">
								<label for="name">Tên khách hàng:</label>
								<input type="text" class="form-control" id="name" name="name">
							</div>
							<div class="form-group addressf" style="display: none">
								<label for="address">Địa chỉ giao hàng:</label>
								<input type="text" class="form-control" id="address" name="address">
							</div>
							<div class="form-group notef" style="display: none">
								<label for="note">Ghi chú:</label>
								<textarea class="form-control" id="note" name="note"></textarea>
							</div>
							<button type="submit" class="btn btn-warning">Gửi đơn hàng</button>
						</form>
					</div>
					<div class="col mb-6 col-lg-6" style="margin-top: -5px;">
						<h4>Lịch sử mua hàng</h4>
						<div class="input-group">
						  <input type="text" class="form-control" placeholder="Nhập số điện thoại" id="his-phone">
						  <div class="input-group-append" style="margin-left: 5%">
						    <button class="btn btn-success" onclick="search_hisCart()">Tìm kiếm</button>
						  </div>
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
	 <script>
      function quay_lai_trang_truoc(){
          history.back();
      }
  	</script>
  	
</body>
</html>
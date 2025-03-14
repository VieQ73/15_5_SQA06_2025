<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
	<title>Cart</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
</head>
<body>
	<div class="wapper">
		<div class="go-to-top btn btn-secondary" id="go-to-top" >
		<i class="fas fa-angle-up"></i>
	</div>
		
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
							<c:when test="${!empty GIO_HANG.sanPhamTrongGioHangs}">
							<input class="checkNull" type="hidden" value="1"/>
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
							</c:when>
							<c:otherwise>
								<input class="checkNull" type="hidden" value="0"/>
								<div class="alert alert-danger" style="padding-left: 8rem; font-size: 25px;">
								  <a href="${pageContext.request.contextPath}/">
									<img alt="" src="${pageContext.request.contextPath}/images/mascot@2x.png">
									<strong>!Không </strong> có sản phẩm trong giỏ hàng
								  </a>
								</div>
								
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				
				<div class="row mb-6">
					<button id="dathangne" class="btn btn-warning col mb-6 col-lg-6 row-lg-2" data-toggle="modal" data-target="#myModal">
						<h3>Đặt hàng</h3>
					</button>
					<div class="modal fade" id="myModal" role="dialog">
					    <div class="modal-dialog modal-lg">
					      <div class="modal-content">
					        <div class="modal-header">
					          <h4 class="modal-title">Thông tin gửi hàng</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <div class="modal-body">
					          <form action="${pageContext.request.contextPath}/luu_don_hang " method="post" id="form-dathang">
								<div class="form-group invalid">
									<label for="phone">Điện thoại liên hệ:</label>
									<input type="text" class="form-control" placeholder="VD: 0378054xxx" id="phone" name="phone" onchange="checkPhone()">
									<span class="form-message alert-danger"></span>
								</div>
								<div class="form-group">
									<label for="name">Tên khách hàng:</label>
									<input type="text" class="form-control" placeholder="VD: Nguyễn Văn A" id="name" name="name">
									<span class="form-message alert-danger"></span>
								</div>
								<div class="form-group">
									<label for="address">Địa chỉ giao hàng:</label>
									<input type="text" class="form-control" placeholder="VD: 264, Trương Định, Tương Mai, Hoàng Mai, Hà Nội" id="address" name="address">
									<span class="form-message alert-danger"></span>
								</div>
								<div class="form-group">
									<label for="note">Ghi chú:</label>
									<textarea class="form-control" placeholder="VD: Lời nhắn giao hàng" id="note" name="note"></textarea>
									<span class="form-message"></span>
								</div>
								 <div class="modal-footer">
								 	<button type="submit" class="btn btn-warning">Gửi đơn hàng</button>
						          	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
							</form>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					<div id="lichsune" class="col mb-6 col-lg-6" style="margin-top: -5px;">
						<h4>Lịch sử mua hàng</h4>
						<form action="${pageContext.request.contextPath}/historyCart" method="post" class="input-group">
							<input type="text" class="form-control" style="width: 150px;"
								placeholder="Nhập số điện thoại" name="keyphone" id="keyphone">
							<button type="submit" id="btnClear" class="btn btn-success">
								<i class="fas fa-search"></i>
							</button>
						</form>
					</div>
				</div>
				
			</div>
			
			</div>
			
			<!-- end content -->
		</div>
		<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>
	 <script src="${pageContext.request.contextPath}/js/validator.js"></script>
	 <script>
	 $(document).ready(function(){
			var check = $('.checkNull').val();
			if(check == '0'){
				$('#dathangne').css("display", "none");
				$('#lichsune').removeClass('col-lg-6');
				$('#lichsune').removeClass('mb-6');
				$('#lichsune').addClass('col-lg-12');
			}
		});
      function quay_lai_trang_truoc(){
          history.back();
      }
  	</script>
  	<script type="text/javascript">
  		Validator({
  			form: '#form-dathang',
  			formGroupSelector: '.form-group',
  			errorSelector: '.form-message',
  			rules:[
  				Validator.isRequired('#phone', "Số điện thoại không được để trống!"),
  				Validator.isPhoneNumber('#phone', "Số điện thoại không hợp lệ!"),
  				Validator.isRequired('#name', "Họ tên không được để trống!"),
  				Validator.isRequired('#address', "Địa chỉ không được để trống!"),
  			],
  			/* onSubmit: function (data) {
  				// call API
  				console.log(data);
  			} */
  		});
  	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>Search</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
	<style type="text/css">
		.sale{
			position: absolute;
			top: 7px;
			right: 1em;
			font-size: 1em;
			font-weight: bold;
			color: red;
			z-index: 1;
			animation: xoayvong 3s linear 0s infinite;
		    -webkit-animation: xoayvong 3s linear 0s infinite;
		    -moz-animation: xoayvong 3s linear 0s infinite;
		    -o-animation: xoayvong 3s linear 0s infinite;
		}
		@-webkit-keyframes xoayvong{
			from{
			        -webkit-transform:rotateY(0deg);
			        -moz-transform:rotateY(0deg);
			        -o-transform:rotateY(0deg);
			    }
		    to{
			        -webkit-transform:rotateY(360deg);
			        -moz-transform:rotateY(360deg);
			        -o-transform:rotateY(360deg);
			}
		}
	</style>
</head>
<body>
<div class="wapper">
	<div class="go-to-top"> <button id="go-to-top"><i class="fas fa-chevron-up"></i></button></div>
		<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
			<div class="content">
				
				</div> 
				<!-- end banner -->
				<div class="ctgr-content">
					<div class="ctgr-content-left col-xl-3.5">
						<%-- <div class="category">
							<div class="category-top">DANH MỤC SẢN PHẨM</div>
							<ul class="drop-menu">
								<c:forEach var = "category" items = "${categories }">
									<li ><a  href="${pageContext.request.contextPath}/category/${category.seo}">${category.name }</a></li>
								</c:forEach>
							</ul>
						</div> --%>
					</div>
					<div class="ctgr-content-right col-xl-12">
						<div class="ctgr-product">
							<div class="ctgr-product-top" style="border-bottom: 2px solid #ff80aa;">
								<div style="font-weight: bold; margin-left: 10rem;">
								<label style="font-size: 25px;">Sắp xếp theo: </label>
								<label style="margin-left: 10rem;">Giá: </label>
									<select style="margin-left: 2rem;" id="sortSelect" onchange="sortS();">
										<option value="">----Chọn----</option>
										<option value="1">Giảm dần</option>
										<option value="2">Tăng dần</option>
									</select>
								</div>
								<input type="hidden" value="${key }" id="key">
								<input type="hidden" value="${selectS }" id="selectS">
							</div>
							<div class="ctgr-product-main" style="padding-top: 1rem;">
								<div class="body-main-product">
									<div class="product1">
										<div class="row">
											<c:forEach var = "productCustom" items = "${productCustom }">				
												<div class="col-lg-3 col-md-6 mb-4 product-cate">
													<c:if test="${productCustom.discount != 0 }">
														<p class="sale">- ${productCustom.discount}%</p>
													</c:if>
													
													<div class="card h-100">
														<div class="product-img">
															<a href="${pageContext.request.contextPath}/products/${productCustom.product.seo }">
																<c:choose>
																	<c:when test = "${empty productCustom.product.productImages }">
																		<img class="card-img-top" src="http://placehold.it/700x400" alt="">
																	</c:when>
																	<c:otherwise>
																		<img class="card-img-top" src="${pageContext.request.contextPath}/file/upload/${productCustom.product.productImages.get(0).path }" alt="">
																	</c:otherwise>
																</c:choose>
															</a>
														</div>
													<div class="card-body">
														<div class="product-title">
															<a href="${pageContext.request.contextPath}/products/${productCustom.product.seo }">${productCustom.product.title }</a>
														</div>
														<div class="product-price">
															<c:choose>
																<c:when test="${productCustom.discount == 0 }">
																	<p class="price-num">
																		<fmt:formatNumber type="number" maxIntegerDigits="13"
																			value="${productCustom.product.price }" /> đ
																	</p>
																</c:when>
																<c:otherwise>
																	<div style="font-size: 15px; display: flex; height: 2rem;">
																	<p style="margin: auto; text-decoration: line-through; font-size: 0.9rem; color: #999999 ; line-height: 2rem;" >
																		<fmt:formatNumber type="number" maxIntegerDigits="13"
																			value="${productCustom.product.price }" /> đ
																	</p>
																	
																	<p style=" margin: auto;font-size: 1.4rem; color: #ea5209;">
																		<fmt:formatNumber type="number" maxIntegerDigits="13"
																			value="${productCustom.price_sale }" /> đ
																	</p>
																	</div>
																</c:otherwise>
															</c:choose>
														</div>
													</div>
													<div class="card-footer" style="display: flex;">
														<small class="text-muted">&#9733; &#9733; &#9733;
															&#9734; &#9734;</small>
															<button class="btn btn-danger" type="button" style="margin-left: 30px;" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom.product.id}, 1);">Mua hàng</button>
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
								<div class="phantrang container col-xl-6" style="background: #ffffff;">
									<tag:paginate offset="${page.offset }"
										count="${page.count }" uri="${pageUrl}" />
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
				<!-- end content -->
			<jsp:include page="/WEB-INF/views/front-end/common/footer.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/front-end/common/js.jsp"></jsp:include>
	<script type="text/javascript">
	function sortS(){
		var a = $("#sortSelect option:selected").val();
		var key = $("#key").val();
		if(a == "1"){
			location.replace("${pageContext.request.contextPath}/search/desc/"+key+"");
		}
		if(a == "2"){
			location.replace("${pageContext.request.contextPath}/search/asc/"+key+"");
		}
	}
	$(document).ready(function() {
		var a = $('#selectS').val();
		if(a == "1"){
			$("#sortSelect").prop("selectedIndex", 1);
		}
		if(a == "2"){
			$("#sortSelect").prop("selectedIndex", 2);
		}
	});
	</script>
</body>
</html>




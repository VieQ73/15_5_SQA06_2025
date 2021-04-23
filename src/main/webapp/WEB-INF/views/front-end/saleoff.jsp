<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Paging -->
<%@ taglib prefix="tag" uri="/WEB-INF/taglib/pagingTagLibs.tld"%>

<!DOCTYPE html>
<html>
<head>
	<title>Sản phẩm saleoff</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
	<style type="text/css">
		.sale{
			position: absolute;
			top: 0.5rem;
			right: 1.5rem;
			z-index: 1;
			color: red;
			font-weight: bold;
			font-size: 1.5rem;
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
						<div class="category">
							<div class="category-top">DANH MỤC SẢN PHẨM</div>
							<ul class="drop-menu">
								<c:forEach var = "category" items = "${categories }">
									<li ><a  href="${pageContext.request.contextPath}/category/${category.seo}">${category.name }</a></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="ctgr-content-right col-xl-9">
						<div class="ctgr-product">
							<div class="ctgr-product-top">
								<a href="${pageContext.request.contextPath}/category/${category.seo}"><i class="fas fa-th"></i>Sản phẩm sale off
								</a>
							</div>
							<div class="ctgr-product-main">
								<div class="body-main-product">
									<div class="product1">
										<div class="row">
											<c:forEach var = "productCustom" items = "${productCustom }">				
												<div class="col-lg-4 col-md-6 mb-4 product-cate">
													<div >
														
														<p class="sale">- ${productCustom.discount}%</p>
									
													</div>						
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
														<div class="product-price" style="display: inline; width: 13rem;">
															<div style="font-size: 15px; display: flex; height: 2rem; background: #f0f0f0 !important; margin: auto; ">
																	<p style="margin-left: 0.3rem; text-decoration: line-through; font-size: 0.9rem; color: #424242; margin-right: 1rem; line-height: 2rem;" >
																		<fmt:formatNumber type="number" maxIntegerDigits="13"
																			value="${productCustom.product.price }" /> đ
																	</p>
																	
																	<p style="font-size: 1.2rem; color: #ea5209;">
																		<fmt:formatNumber type="number" maxIntegerDigits="13"
																			value="${productCustom.price_sale }" /> đ
																	</p>
															</div>
														</div>
													</div>
													<div class="card-footer" style="display: flex;">
														<small class="text-muted">&#9733; &#9733; &#9733;
															&#9733; &#9734;</small>
															<button class="btn btn-danger" type="button" style="margin-left: 30px;" onclick="Shop.chon_san_pham_dua_vao_gio_hang(${productCustom.product.id}, 1);">Mua hàng</button>
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
								<%-- <div class="phantrang container col-xl-6" style="background: #ffffff;">
									<tag:paginate offset="${page.offset }"
										count="${page.count }" uri="${pageUrl}" />
								</div> --%>
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
</body>
</html>




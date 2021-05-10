<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Paging -->
<%@ taglib prefix="tag" uri="/WEB-INF/taglib/pagingTagLibs.tld"%>
<%!
	public BigDecimal discount(BigDecimal gia, int giam) {
		 return gia.subtract(gia.multiply(new BigDecimal(giam).divide(new BigDecimal(100))));
	}
%>
<!DOCTYPE html>
<html>
<head>
	<title>Tin tức</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
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
							<div style="margin-top: 2rem;">
							<div class="category-top">SẢN PHẨM NỔI BẬT</div>
							<div style="border: 1px solid #d4d2d2; border-radius: 5px; background: #ffffff;">
								<c:forEach var = "spbc" items = "${spbc}">
									<div style="padding-top: 1rem; margin-left: 1rem;outline: 0px; border-bottom: 1px solid rgb(229, 229, 229); display: table; margin-bottom: 20px; padding-bottom: 20px; width: 236px; color: rgb(51, 51, 51); font-family: helvatica, &quot;Open Sans&quot;, sans-serif; font-size: 14px;">
										<div style="outline: 0px; overflow: hidden; position: relative; display: table-cell; width: 90px;">
											<a href="${pageContext.request.contextPath}/products/${spbc.product.seo }" class="product-thumb-link" style="outline: 0px; color: rgb(242, 121, 34); display: block; overflow: hidden; position: relative; width: 90px;">
												<img src="${pageContext.request.contextPath}/file/upload/${spbc.product.productImages.get(0).path }" alt="" class="H100" style="border: 1px solid black;outline: 0px; max-width: 160px; height: 108px; width: 90px; max-height: 192px;">
											</a>
										</div>
										<div style="outline: 0px; display: table-cell; padding-left: 20px; vertical-align: top;">
											<h3 style="outline: 0px; line-height: 18px; margin-right: auto; margin-left: auto; font-size: 14px; text-transform: capitalize; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; text-overflow: ellipsis; max-width: 210px; height: 3rem;">
												<a href="${pageContext.request.contextPath}/products/${spbc.product.seo }" title="${spbc.product.title }" class="menuproleftcs" style="outline: 0px; color: rgb(51, 51, 51); text-transform: none; display: block; overflow: hidden; text-overflow: ellipsis; transition-timing-function: ease-out; padding-right: 5px; padding-left: 5px;">${spbc.product.title}</a>
											</h3>
											<c:choose>
												<c:when test="${spbc.discount == 0 }">
													<div style="outline: 0px; margin-right: -5px; margin-left: -5px;">
														<span style="outline: 0px; font-size: 15px; margin-right: 5px; margin-left: 5px; font-weight: 700; color: rgb(242, 121, 34); display: block;">
															<fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${spbc.price_sale }" /> đ
														</span>
													</div>
												</c:when>
												<c:otherwise>
													<div style="outline: 0px; margin-right: -5px; margin-left: -5px;">
														<span style="outline: 0px; font-size: 13px; margin-right: 5px; margin-left: 5px; color: rgb(204, 204, 204); display: block; text-decoration-line: line-through;">
															<fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${spbc.product.price }" /> đ
														</span>
														<span style="outline: 0px; font-size: 15px; margin-right: 5px; margin-left: 5px; font-weight: 700; color: rgb(242, 121, 34); display: block;">
															<fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${spbc.price_sale }" /> đ
														</span>
													</div>
												</c:otherwise>
											</c:choose>
											
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						</div>
					</div>
					<div class="ctgr-content-right col-xl-9">
						<div class="ctgr-product">
							<div class="ctgr-product-top">
								<a href="${pageContext.request.contextPath}/news"><i class="fas fa-th"></i>Tin tức
								</a>
							</div>
							<div class="ctgr-product-main">
								<div class="body-main-product">
								<div style="margin: 20px">
								<ul class="listimg"
									style="outline: 0px; margin-bottom: 10px; color: rgb(51, 51, 51); font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 14px;">
										<c:forEach var = "news" items = "${news }">
											<li
											style="font-size: 15px; outline: 0px; list-style: none; display: inline-block; margin-bottom: 30px;">
											<a href="${pageContext.request.contextPath}/newsDetail/${news.id }"
											title="${news.title }" style="outline: 0px; color: rgb(51, 51, 51);">
											<img src="${pageContext.request.contextPath}/file/upload/${news.newsImages.get(0).path }"
												align="left"
												alt="${news.title }"
												title="${news.title }"
												style="outline: 0px; border-width: 1px; border-style: solid; border-color: rgb(221, 221, 221); max-width: 100%; width: 140px; margin-right: 15px; box-shadow: rgb(102, 102, 102) 1px 1px 2px 0px; border-radius: 5px; padding: 5px;">
												<span style="outline: 0px; font-weight: 700;">${news.title }</span>
													<br style="outline: 0px;">${news.short_news }
												</a>
											</li>
										</c:forEach>
										

									</ul>
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




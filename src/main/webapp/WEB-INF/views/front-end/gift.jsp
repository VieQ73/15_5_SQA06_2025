<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>Huong dan mua hang</title>
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
						</div>
					</div>
					<div class="ctgr-content-right col-xl-9">
						<div class="ctgr-product">
							<div class="ctgr-product-top">
								<a href="${pageContext.request.contextPath}/huongdanmuahang"><i class="fas fa-th"></i>Hướng dẫn mua hàng
								</a>
								
							</div><div class="line-bottom"></div>
							<div class="ctgr-product-main">
								<div class="body-main-product">
									<div class="" style="margin: 15px">
									<c:forEach var = "gift" items = "${gifts }">
										<div class="row">
											<p class="col-xl-4">
												<a href="#"
													style="outline: 0px; background-color: rgb(255, 255, 255); color: rgb(242, 121, 34); font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 18px;">
													<c:choose>
														<c:when test = "${empty gift.giftImages }">
															<img
																src="http://placehold.it/700x400"
																class="img" align="left"
																style="outline: 0px; border-width: 1px; border-style: solid; border-color: gray; max-width: 100%; border-radius: 5px; padding: 5px; margin-right: 15px; width: 280px;">
														</c:when>
														<c:otherwise>
															<img class="img" align="left"
																style="outline: 0px; border-width: 1px; border-style: solid; border-color: gray; max-width: 100%; border-radius: 5px; padding: 5px; margin-right: 15px; width: 280px;" src="${pageContext.request.contextPath}/file/upload/${gift.giftImages.get(0).path }" alt="">
														</c:otherwise>
													</c:choose>
												</a>
											</p>
											<div class="col-xl-8 gift-right">
												<h2
													style="outline: 0px; font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; color: rgb(51, 51, 51); margin-bottom: 10px; font-size: 20px;">
													<a class="gift-title"
														href="#"
														style="outline: 0px; color: rgb(0, 0, 0); font-weight: 700;">${gift.title}</a>
												</h2>
												<p class="progift"
												style="outline: 0px; margin-bottom: 10px; color: red; font-weight: 700; font-size: 16.8px; font-family: helvatica,&amp; quot;Open Sans&amp;quot;, sans-serif;">
													<fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${gift.price }" /> đ
												</p>
												<div class="cctent"
													style="outline: 0px; height: 94px; text-align: justify; overflow-y: hidden; font-size: 16px; color: rgb(51, 51, 51); font-family: helvatica,&amp; quot;Open Sans&amp;quot;, sans-serif;">
													<p style="outline: 0px; margin-bottom: 10px;">
														${gift.description }
													</p>
												</div>
												<div class="tpron"
													style="outline: 0px; color: blue; font-weight: 700; font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 16px;">
													<img src="${pageContext.request.contextPath}/images/ChiTay.png"
														alt="chọn sản phẩm" class="w29"
														style="outline: 0px; max-width: 100%; width: 29px;">&nbsp;Chọn
													ngay sản phẩm để nhận quà tặng này
												</div>
												<ul class="lipgift"
													style="outline: 0px; margin-bottom: 10px; list-style: none; color: rgb(51, 51, 51); font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 18px;">
													<li style="outline: 0px; float: left; text-align: center;"><a
														href="https://imua.com.vn/giai-phap-tri-tham-voi-cao-tinh-nghe-cao-vo-buoi.html"
														style="outline: 0px; color: red; font-weight: 700;"><img
															src="https://imua.com.vn/images/Product/Giai-phap-tri-tham-voi-Cao-Tinh-Nghe-Cao-Vo-Buoi-.jpg"
															alt="giai-phap-tri-tham-voi-cao-tinh-nghe-cao-vo-buoi"
															title="Giải pháp trị thâm với Cao Tinh Nghệ + Cao Vỏ Bưởi"
															class="img2"
															style="outline: 0px; border-width: 1px; border-style: solid; border-color: rgb(221, 221, 221); max-width: 100%; width: 128px; margin: 5px; border-radius: 5px;"><br
															style="outline: 0px;">190.000 đ</a></li>
												</ul>
											</div>
											<div class="line-gift"></div>
										</div>
									</c:forEach>
									<!-- <h2
										style="outline: 0px; font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; color: rgb(51, 51, 51); margin-bottom: 10px; font-size: 18px;">
										<a
											href="https://imua.com.vn/kho-qua-tang/kem-chong-nang-suncreen-50-coco-secret.html"
											style="outline: 0px; color: rgb(0, 0, 0); font-weight: 700;">Kem
											chống nắng Suncreen 50++ Coco Secret</a>
									</h2>
									<p class="progift"
										style="outline: 0px; margin-bottom: 10px; color: red; font-weight: 700; font-size: 16.8px; font-family: helvatica,&amp; quot;Open Sans&amp;quot;, sans-serif;">65.000
										đ</p>
									<div class="cctent"
										style="outline: 0px; height: 94px; text-align: justify; overflow-y: hidden; font-size: 16px; color: rgb(51, 51, 51); font-family: helvatica,&amp; quot;Open Sans&amp;quot;, sans-serif;">
										<p style="outline: 0px; margin-bottom: 10px;">
											&nbsp;<a
												href="https://imua.com.vn/kem-chong-nang-suncreen-50-coco-secret.html"
												target="_blank"
												style="outline: 0px; color: rgb(51, 51, 51);">Kem chống
												nắng Sunscreen 50++</a>&nbsp;có tác dụng<span
												style="outline: 0px; font-weight: 700;">&nbsp;</span>bảo vệ
											da, chống lại các tia cực tím và các tác nhân sinh ra sạm nám
											da. Sản phẩm sẽ tạo nên một lớp màng chắn giúp&nbsp;<span
												style="outline: 0px; color: rgb(255, 0, 0);">bảo vệ,
												phát tán và phản xạ tia UV&nbsp;</span>khiến chúng không thể xuyên
											qua da được. Khi thoa sản phẩm lên da, kem sẽ nằm trên bề mặt
											tạo thành một lớp áo, bức tường có khả năng chống lại tia cực
											tím rất hiệu quả.
										</p>
									</div>
									<div class="tpron"
										style="outline: 0px; color: blue; font-weight: 700; font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 18px;">
										<img src="https://imua.com.vn/images/ChiTay.png"
											alt="chọn sản phẩm" class="w29"
											style="outline: 0px; max-width: 100%; width: 29px;">&nbsp;Chọn
										ngay sản phẩm để nhận quà tặng này
									</div>
									<ul class="lipgift"
										style="outline: 0px; margin-bottom: 10px; list-style: none; color: rgb(51, 51, 51); font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 18px;">
										<li style="outline: 0px; float: left; text-align: center;"><a
											href="https://imua.com.vn/giai-phap-tri-tham-voi-cao-tinh-nghe-cao-vo-buoi.html"
											style="outline: 0px; color: red; font-weight: 700;"><img
												src="https://imua.com.vn/images/Product/Giai-phap-tri-tham-voi-Cao-Tinh-Nghe-Cao-Vo-Buoi-.jpg"
												alt="giai-phap-tri-tham-voi-cao-tinh-nghe-cao-vo-buoi"
												title="Giải pháp trị thâm với Cao Tinh Nghệ + Cao Vỏ Bưởi"
												class="img2"
												style="outline: 0px; border-width: 1px; border-style: solid; border-color: rgb(221, 221, 221); max-width: 100%; width: 128px; margin: 5px; border-radius: 5px;"><br
												style="outline: 0px;">190.000 đ</a></li>
									</ul> -->
									</div>
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
</body>
</html>




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
<div style="margin: 15px; padding-bottom: 30px;">
								<h1
									style="outline: 0px; margin-top: 20px; margin-bottom: 10px; font-size: 18px; font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-weight: 700; color: red;">Những
									cách trị sẹo rỗ từ thiên nhiên an toàn</h1>
								<h2
									style="outline: 0px; font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; color: rgb(51, 51, 51); margin-top: 20px; margin-bottom: 10px; font-size: 14px;">Sẹo
									rỗ luôn là nỗi ám ảnh của hầu hết chúng ta, chúng không chỉ
									khiến da tổn thương đau đớn mà còn khiến mất tự tin, khó sử
									dụng mỹ phẩm. Vậy làm sao để tìm được cách trị sẹo rỗ an toàn,
									hiệu quả bạn hãy cùng theo dõi qua bài viết sau nha:</h2>
								<div
									style="outline: 0px; color: rgb(51, 51, 51); font-family: helvatica,&amp; quot; Open Sans&amp;quot; , sans-serif; font-size: 14px;">
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">
										<span style="outline: 0px;"><span
											style="outline: 0px; color: rgb(0, 128, 0);"><span
												style="outline: 0px; font-weight: 700;">Nha đam
													(lô&nbsp; hội) trị sẹo, dưỡng da</span></span></span>
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">
										- Một trong những&nbsp;<span
											style="outline: 0px; font-weight: 700;">cách trị sẹo
											rỗ hiệu quả nhất&nbsp;</span>được nhiều người tin dùng nhất đó là sử
										dụng nha đam. Nha đam với thành phần chứa nhiều axit amin,
										vitamin, khoáng tố vi lượng nhất là axít gama linolenic có
										công dụng làm lành vết thương, kích thích tái tạo tế bào da
										nhanh chóng.
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: center;">
										<img alt="Nha đam là cách trị sẹo rỗ an toàn, hiệu quả"
											src="https://imua.com.vn/FileUploads/imgfile/hinh%20anh%20cach%20tri%20seo%20ro%20h1(1).jpg"
											title="Nha đam là cách trị sẹo rỗ an toàn, hiệu quả"
											style="outline: 0px; max-width: 100%; height: 305px; width: 500px;"><br
											style="outline: 0px;">
										<em style="outline: 0px;">Nha đam là cách trị sẹo rỗ an
											toàn, hiệu quả</em><br style="outline: 0px;">
										<br style="outline: 0px;">&nbsp;
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">Bạn
										chỉ cần dùng gel nha đam thoa&nbsp; đều lên vùng sẹo sau khi
										rửa sạch trong khoảng 15 phút để dưỡng chất thẩm thấu tốt hơn.</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">
										<span style="outline: 0px; color: rgb(0, 128, 0);"><span
											style="outline: 0px;"><span
												style="outline: 0px; font-weight: 700;">Rau má – loại
													bỏ sẹo đơn giản</span></span></span>
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">
										- Rau má là một trong những&nbsp;<em style="outline: 0px;">cách
											trị sẹo rỗ lâu năm</em>&nbsp;được lưu truyền trong dân gian và
										nhiều người tin dùng. Chúng có tác dụng chữa lành sẹo nhanh
										chóng, dễ kiếm nguyên liệu và dễ thực hiện. Với chiết xuất có
										chất triterpenoids kết hợp cùng các hợp chất khác sẽ ức chế sự
										sản sinh quá mức collagen trong các mô sẹo, ngăn sẹo phát
										triển.
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: center;">
										<a
											href="https://imua.com.vn/FileUploads/imgfile/hinh%20anh%20cach%20tri%20seo%20ro%20h2.jpg"
											target="_blank" style="outline: 0px; color: rgb(51, 51, 51);"><img
											alt="Cách trị sẹo rỗ từ rau má luôn được mọi người lựa chọn"
											src="https://imua.com.vn/FileUploads/imgfile/hinh%20anh%20cach%20tri%20seo%20ro%20h2.jpg"
											title="Cách trị sẹo rỗ từ rau má luôn được mọi người lựa chọn"
											style="outline: 0px; max-width: 100%; height: 375px; width: 500px;"></a><br
											style="outline: 0px;">
										<em style="outline: 0px;">Cách trị sẹo rỗ từ rau má luôn
											được mọi người lựa chọn</em><br style="outline: 0px;">&nbsp;
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">-
										Bạn xay nhuyễn rau má và thoa đều lên vùng da sẹo để da hấp
										thu dưỡng chất tốt hơn. Nên thực hiện 2-3 lần/tuần để nhận
										thấy hiệu quả.</p>
									<p style="outline: 0px; margin-bottom: 10px;">
										<span style="outline: 0px; color: rgb(0, 128, 0);"><span
											style="outline: 0px;"><span
												style="outline: 0px; font-weight: 700;">Nghệ tươi –
													thảo dược cho da mịn màng</span></span></span>
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">
										- Vốn dĩ nghệ được biết tới là&nbsp;<u style="outline: 0px;">cách
											trị sẹo rỗ lâu năm hiệu quả</u>&nbsp;nhất bởi trong nghệ có chứa
										nhiểu curcumin là một hoạt chất có khả năng tăng cường tuần
										hoàn máu ở da, hỗ trợ làm đầy và săn chắc vùng da sẹo rỗ cực
										kỳ hiệu quả.
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: center;">
										<a
											href="https://imua.com.vn/FileUploads/imgfile/hinh%20anh%20cach%20tri%20seo%20ro%20h3.jpg"
											target="_blank" style="outline: 0px; color: rgb(51, 51, 51);"><img
											alt="Nghệ chính là cách trị sẹo rỗ tại nhà bạn không nên bỏ qua"
											src="https://imua.com.vn/FileUploads/imgfile/hinh%20anh%20cach%20tri%20seo%20ro%20h3.jpg"
											title="Nghệ chính là cách trị sẹo rỗ tại nhà bạn không nên bỏ qua"
											style="outline: 0px; max-width: 100%; height: 207px; width: 500px;"></a><br
											style="outline: 0px;">
										<em style="outline: 0px;">Nghệ chính là cách trị sẹo rỗ
											tại nhà bạn không nên bỏ qua</em><br style="outline: 0px;">&nbsp;
									</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">Bạn
										dùng nghệ tươi giã nhuyễn và xoa lên vùng da sẹo thường xuyên
										và đều đặn 3 lần/tuần sẽ giúp nhận thấy hiệu quả tốt hơn.</p>
									<p
										style="outline: 0px; margin-bottom: 10px; text-align: justify;">Ngoài
										ra, để sở hữu cho mình cách trị sẹo rỗ lâu năm tại nhà bạn
										đừng ngại ngần hãy thử ngay với kem trị sẹo Scar Esthetique sẽ
										giúp bạn loại bỏ mọi loại sẹo, dưỡng da mềm mịn trắng sáng
										nhanh chóng.</p>
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




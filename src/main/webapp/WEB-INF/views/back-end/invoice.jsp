<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- SPRING FORM -->
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- SPRING FORM -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE HTML>
<html>
<head>
<title>Hóa đơn bán hàng</title>
<meta charset="utf-8">
<jsp:include page="/WEB-INF/views/back-end/commonAdmin/css.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.js"></script>
</head>
<body class="cbp-spmenu-push">
		<!-- main content start-->
		<div class="content-main" style="margin-top: -2rem;  max-width: 794px;">
			<div class="container d-flex justify-content-center mt-50 mb-50"  style="display:;">
		        <div class="row" id="invoice">
		            <div class="col-md-12">
		                <div class="card" id="invoice" style="max-width: 794px; padding: 2rem;">
		                    <div class="card-body">
		                    	<div style="display: flex;">
		                    		<div >
		                        		<img src="${pageContext.request.contextPath}/images/logo_beauty.jpg">
		                        	</div>
		                        	<div style="margin-left: 6rem;" >
		                        		<div class="mb-4 pull-left">
		                                    <ul class="list list-unstyled mb-0 text-left">
		                                        <li>Địa chỉ:  Bình Lợi, P.13, Q. Bình Thạnh, Tp. HCM</li>
		                                        <li>Website: beautyshop.com.vn</li>
		                                        <li>Hotline: 0949378132</li>
		                                    </ul>
		                                </div>
		                        	</div>
		                        </div>
		                    </div>
		                    <br>
		                    <div class="container row">
		                    	<h2 style="font-weight: bold; margin-left: 8rem;">HÓA ĐƠN BÁN HÀNG</h2>
		                    	<br>
		                    	<h4 id="ntn" style="font-style: italic; margin-left: 17rem; height: 30px;"></h4>
		                    	<h4 style="font-style: italic; margin-left: 17rem;">Số: ${order.id}</h4>
		                    </div>
		                    <br>
		                    <div class="card-body">
		                        <div class="row">
		                            <div class="col-sm-12">
		                                <div class="mb-4 ">
		                                    <div class="text-sm-left col-sm-8">
		                                        <ul class="list list-unstyled mb-0">
		                                            <li>Tên khách hàng: <span style="font-weight: bold;">${order.user.name}</span></li>
		                                            <li>Điện thoại : <span style="font-weight: bold;">${order.user.phone}</span></li>
		                                            <li>Địa chỉ: <span style="font-weight: bold;">${order.user.address}</span></li>
		                                        </ul>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                        <br>
			                    <div class="table-responsive" style="border: 1px solid black;">
			                        <table class="table table-lg">
			                            <thead>
			                                <tr>
			                                    <th>STT</th>
			                                    <th>Tên hàng</th>
			                                    <th>Số lượng</th>
			                                    <th>Đơn giá</th>
			                                    <th>Thành tiền</th>
			                                </tr>
			                            </thead>
			                            <tbody>
			                            	<c:forEach var="orderproduct" items="${orderproducts }" varStatus="loop">
												<tr class="tr-shadow" >
													<th scope="row">${loop.index + 1}</th>
													<td style="font-size: 18px;">
														${orderproduct.orderProduct.product.title}
													</td>
													<td style="font-size: 18px;"><span id="slT">${orderproduct.orderProduct.quality}</span></td>
													<td><fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${orderproduct.orderProduct.price}"/>đ</td>
													<td>
														<fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${orderproduct.thanhTien}"/>đ
													</td>
												</tr>
											</c:forEach>
			                                <tr class="tr-shadow">
			                                	<th scope="row"></th>
			                                	<td></td>
			                                	<td></td>
			                                	<td style="font-weight: bold;">Tổng:</td>
			                                	<td><fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${order.total}" />đ</td>
			                                </tr>
			                            </tbody>
			                        </table>
			                    </div>
			                    <div>
			                    	<input type="hidden" value="${order.total}" id="ttt">
			                    	<p>Tổng số tiền phải thanh toán: <span style="font-weight: bold;"><fmt:formatNumber type="number" maxIntegerDigits="13"
															value="${order.total}" />đ</span></p>
									<p>Bằng chữ: <span id="tChu" style="font-weight: bold;"></span> </p>
			                    </div>
			                    <br>
			                    <div class="card-body" style="clear: both;">
			                        <div style="display: flex;">
			                            <div style="margin-left: 2rem;" >
			                                <span><b>Khách hàng</b></span><br>
			                                <i>(ký, họ tên)</i>
			                            </div>
			                            <div  style="text-align: right; margin-left: 15rem;">
			                                <span><b>Nhân viên bán hàng</b></span><br>
			                                <p style="margin-right: 2rem;">(ký, họ tên)</p>
			                               
			                            </div>
			                        </div>
			                    </div>
			                   
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
		</div>
	
	<!-- js-->
	<jsp:include page="/WEB-INF/views/back-end/commonAdmin/js.jsp"></jsp:include>
	<script type="text/javascript">
	window.onload = function () {
	    document.getElementById("cmd")
	        .addEventListener("click", () => {
	            const invoice = this.document.getElementById("invoice");
	            console.log(invoice);
	            console.log(window);
	            var opt = {
	                margin: 0.2,
	                filename: 'myfile.pdf',
	                image: { type: 'jpeg', quality: 0.98 },
	                html2canvas: { scale: 2 },
	                jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
	            };
	            html2pdf(invoice).from(invoice).set(opt).save();
	            
	        })
	};
	
	</script>
	<script type="text/javascript">
		var mangso = ['không','một','hai','ba','bốn','năm','sáu','bảy','tám','chín'];
		function dochangchuc(so,daydu){
			var chuoi = "";
			chuc = Math.floor(so/10);
			donvi = so%10;
			if (chuc>1) {
			  	chuoi = " " + mangso[chuc] + " mươi";
			  	if (donvi==1) {
			   		chuoi += " mốt";
			  	}
			} else if (chuc==1) {
			  	chuoi = " mười";
			  	if (donvi==1) {
			   		chuoi += " một";
			  	}
			} else if (daydu && donvi>0) {
			  	chuoi = " lẻ";
			}
			if (donvi==5 && chuc>1) {
			  	chuoi += " lăm";
			} else if (donvi>1||(donvi==1&&chuc==0)) {
			  	chuoi += " " + mangso[ donvi ];
			}
			return chuoi;
		}
		function docblock(so,daydu)
		{
		 var chuoi = "";
		 tram = Math.floor(so/100);
		 so = so%100;
		 if (daydu || tram>0) {
		  chuoi = " " + mangso[tram] + " trăm";
		  chuoi += dochangchuc(so,true);
		 } else {
		  chuoi = dochangchuc(so,false);
		 }
		 return chuoi;
		}
		function dochangtrieu(so,daydu)
		{
		 	var chuoi = "";
			trieu = Math.floor(so/1000000);
			so = so%1000000;
			if (trieu>0) {
			  	chuoi = docblock(trieu,daydu) + " triệu";
			  	daydu = true;
			}
			nghin = Math.floor(so/1000);
			so = so%1000;
			if (nghin>0) {
			  	chuoi += docblock(nghin,daydu) + " nghìn";
			  	daydu = true;
			}
			if (so>0) {
			  	chuoi += docblock(so,daydu);
			}
			return chuoi;
		}
		function docso(so){
			if (so==0) return mangso[0];
		 	var chuoi = "", hauto = "";
		 	do {
		  		ty = so%1000000000;
		  		so = Math.floor(so/1000000000);
		  		if (so>0) {
		   			chuoi = dochangtrieu(ty,true) + hauto + chuoi;
		  		} else {
		   			chuoi = dochangtrieu(ty,false) + hauto + chuoi;
		  		}
		  		hauto = " tỷ";
		 	} while (so>0);
		 	return chuoi;
		}
		
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			var so = Number($('#ttt').val());
			$('#tChu').text(docso(so)+" đồng");
			var now = new Date();
		    var today = new Date().toISOString().substring(0, 10);
		    var thang = now.getMonth()+1;
		    var str = "Hà Nội, ngày "+ now.getDate() +" tháng "+ thang +" năm "+now.getFullYear();
		    var d = new Date(now.getFullYear(), now.getMonth()-1, now.getDate()).toISOString().substring(0, 10);
			$('#ntn').text(str);
			window.print();
		});
		
	</script>
</body>
</html>
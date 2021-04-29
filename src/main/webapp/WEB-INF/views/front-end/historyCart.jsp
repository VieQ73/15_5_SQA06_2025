<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>History Cart</title>
	<meta charset="utf-8">
	<jsp:include page="/WEB-INF/views/front-end/common/css.jsp"></jsp:include>
	<style type="text/css">
		.order-content__item:hover{
			background: #f0f0f0;
		}
		.active{
			color: black !important;
		}
	</style>
</head>
<body>
	<div class="wapper">
		<div class="go-to-top"> <button id="go-to-top"><i class="fas fa-chevron-up"></i></button></div>
		
		<jsp:include page="/WEB-INF/views/front-end/common/header.jsp"></jsp:include>
		<div class="bg">
		<div class="container">
			
			<div class="col-lg-12" style="min-height:30em;
			background-image:linear-gradient(180deg, rgba(237,104,193,0.8) 20%, rgba(255,255,255,1) 100%), url('${pageContext.request.contextPath}/images/history_cart.jpg');
			background-position: center;
		    background-size: cover;
		    background-attachment: fixed;
			">
				<div class="row mb-4">
					<div class="col">
						<nav>
						  <div class="nav nav-tabs" id="nav-tab" role="tablist">
						    <a class="nav-item nav-link active" style="margin-left: 2rem;" id="nav-dathang-tab" data-toggle="tab" href="#nav-dathang" role="tab" aria-controls="nav-dathang" aria-selected="true">Đặt hàng (${sl1})</a>
						    <a class="nav-item nav-link" style="margin-left: 2rem;" id="nav-giaohang-tab" data-toggle="tab" href="#nav-giaohang" role="tab" aria-controls="nav-giaohang" aria-selected="false">Giao hàng (${sl2})</a>
						    <a class="nav-item nav-link" style="margin-left: 2rem;" id="nav-danhan-tab" data-toggle="tab" href="#nav-danhan" role="tab" aria-controls="nav-danhan" aria-selected="false">Đã nhận (${sl3})</a>
						    <a class="nav-item nav-link" style="margin-left: 2rem;" id="nav-dahuy-tab" data-toggle="tab" href="#nav-dahuy" role="tab" aria-controls="nav-dahuy" aria-selected="false">Đã hủy (${sl4})</a>
						  </div>
						</nav>
						<div class="tab-content" id="nav-tabContent">
						  <div class="tab-pane fade show active" id="nav-dathang" role="tabpanel" aria-labelledby="nav-home-tab">
						  	<c:forEach var="historyCart" items="${historyCarts }">
							<div class="purchase-list-page__checkout-card-wrapper"
								style="margin-bottom: 0.625rem; margin-top: 0.75rem; color: rgba(0, 0, 0, 0.8); font-size: 15.75px; background-color: rgb(245, 245, 245);">
								<div class="order-card__container"
									style="border-bottom: none; box-shadow: rgba(0, 0, 0, 0.05) 0px 1px 1px 0px; border-radius: 0.125rem; overflow: visible;">
									<div class="order-card__content-wrapper"
										style="background: rgb(255, 255, 255);">
										<div class="order-card__content"
											style="padding-top: 1.25rem; padding-right: 1.25rem; padding-left: 1.25rem;">
											<div class="order-content__container">
												<div class="order-content__header"
													style="-webkit-box-pack: justify; justify-content: space-between; margin-bottom: 0.625rem; display: flex; -webkit-box-align: center; align-items: center;">
													<div class="order-content__header__seller"
														style="display: flex; -webkit-box-align: center; align-items: center; flex-shrink: 0;">
														<div class="order-content__header__seller__chat-btn"
															style="margin-right: 0.375rem; text-transform: capitalize; flex-shrink: 0; stroke: rgb(255, 255, 255);">
														</div>
														
													</div>
													<div class="order-content__header__flex-placeholder"
														style="-webkit-box-flex: 1; flex: 1 1 0%; min-width: 0.625rem; font-size: 1rem;">
														Ngày đặt: <fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" />
													</div>
													
													
												</div>
												<div class="order-content__item-list"
													style="border-top: 1px solid rgba(0, 0, 0, 0.09); font-size: 0.875rem; line-height: 1.1875rem;">
													<c:forEach var="historyCartDetail" items="${historyCart.saleOrderProducts}">
													<a class="order-content__item-wrapper a-hover"
														href="${pageContext.request.contextPath}/products/${historyCartDetail.product.seo }"
														style="color: rgba(0, 0, 0, 0.8); outline: 0px; text-decoration: none;">
														<div class="order-content__item" style="display: flex; flex-wrap: nowrap; overflow-wrap: break-word; border-bottom: 1px solid rgba(0, 0, 0, 0.09);">
															<div
																class="order-content__item__col order-content__item__detail"
																style="-webkit-box-flex: 1; flex: 1 1 auto; min-width: 0px; cursor: pointer; user-select: none;">
																<div class="order-content__item__product"
																	style="cursor: pointer; user-select: none; display: flex; flex-wrap: nowrap; padding-top: 0.625rem; padding-bottom: 0.625rem; height: 150px;">
																	<div class="order-content__item__image"
																		style="width: 5rem; height: 5rem; flex-shrink: 0; margin-right: 0.75rem;">
																		<div class="shopee-image__wrapper"
																			style="width: 110px; height: 130px; position: relative;">
																			
																			<div class="shopee-image__content rounded"
																				style="width: 110px; height: 130px; background-position: 50% center; background-size: cover; background-repeat: no-repeat; position: absolute; top: 0px; left: 10px;">
																				<c:choose>
																					<c:when test = "${empty historyCartDetail.product.productImages }">
																						<img style="width: 100px; height: 120px;" src="http://placehold.it/700x400" alt="">
																					</c:when>
																					<c:otherwise>
																						<img style="width: 100px; height: 120px;" src="${pageContext.request.contextPath}/file/upload/${historyCartDetail.product.productImages.get(0).path }" alt="">
																					</c:otherwise>
																				</c:choose>
																				<div class="shopee-image__content--blur"
																					style="width: 90px; height: 90px;"></div>
																			</div>
																		</div>
																	</div>
																	<div class="order-content__item__detail-content"
																		style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: start; align-items: flex-start; -webkit-box-flex: 1; flex-grow: 1; position: relative; min-width: 0px; overflow-wrap: break-word; pointer-events: none; margin-left: 1rem;">
																		<div class="order-content__item__name"
																			style="width: 694.875px; overflow: hidden; display: -webkit-box; text-overflow: ellipsis; -webkit-box-orient: vertical; -webkit-line-clamp: 2; margin-bottom: 0.3125rem; font-size: 25px; line-height: 2rem; max-height: 3rem;">
																			${historyCartDetail.product.title}
																		</div>
								
																		<div class="order-content__item__quantity" style="margin-top: 1rem;">
																			<span style="font-size: 20px;">Số lượng: <span style="color: red">${historyCartDetail.quality}</span></span>
																			
																		</div>
																	</div>
																</div>
															</div>
															<div
																class="order-content__item__price order-content__item__col order-content__item__col--small order-content__item__col--last"
																style="width: 8.125rem; overflow-wrap: break-word; -webkit-box-flex: 0; flex: 0 0 auto; display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: end; align-items: flex-end; -webkit-box-pack: center; justify-content: center; padding-left: 0.625rem; border-right: 0px !important;">
																<div class="order-content__item__price-text"
																	style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; font-size: 20px; margin-right: 2rem;">
																	<div class="shopee-price--primary"
																				style="color: rgb(238, 77, 45);"><fmt:formatNumber type="number" maxIntegerDigits="13" value="${historyCartDetail.price}" />đ</div>
																</div>
															</div>
														</div>
														</a>
													</c:forEach>
												</div>
											</div>
										</div>
										<div class="_1J7vLy"
											style="width: 989.625px; height: 0px; border-bottom: 1px dotted rgba(0, 0, 0, 0.09); position: relative;">
											<div class="DI-rNr tyOBoQ"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; left: 0px; transform: translate(-50%, -50%);"></div>
											<div class="DI-rNr _25igL4"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; right: 0px; transform: translate(50%, -50%);"></div>
										</div>
									</div>
									<div class="order-card__buttons-container">
										<div class="purchase-card-buttons__wrapper"
											style="background: rgb(255, 254, 251); padding-right: 1.5rem; padding-left: 1.5rem;">
											<div class="row" style="display: flex;">
												<div class="purchase-card-buttons__text-info col-8" style="margin-left: 0px;font-size: 1rem;">
													<p class="purchase-text-info">
														Địa chỉ nhận: ${historyCart.address}
													</p>
													<p class="purchase-text-info">
														Ghi chú: ${historyCart.note_by_customer}
													</p>
												</div>
												<div class="purchase-card-buttons__total-payment col-4"
													style="padding-top: 2.5rem; display: flex; -webkit-box-pack: end; justify-content: flex-end; -webkit-box-align: end; align-items: flex-end; margin-left: 65%;">
													<span class="purchase-card-buttons__label-price"
														style="font-size: 0.875rem; line-height: 1.25rem; margin-right: 0.625rem;">Tổng
														số tiền:</span>
													<span class="purchase-card-buttons__total-price"
													style="color: rgb(238, 77, 45); font-size: 1.875rem; line-height: 2rem;">
														<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${historyCart.total}"/> đ
													</span>
												</div>
											</div>
											
											<div class="purchase-card-buttons__container"
												style="display: flex; flex-wrap: nowrap; -webkit-box-pack: end; place-content: center flex-end; padding-top: 1.125rem; padding-bottom: 1.25rem;">
												<c:if test="${historyCart.status == 0}">
												<div class="purchase-card-buttons__show-button-wrapper"
													style="min-width: 10rem; height: 2.5rem; margin-left: 0.625rem; background: rgb(255, 255, 255); display: flex;">
													<button class="btn btn-danger" data-toggle="modal" data-target="#huy">
														Hủy Đơn Hàng
													</button>
													<!-- Modal -->
													<div class="modal fade" id="huy" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
													  <div class="modal-dialog" role="document">
													    <div class="modal-content">
													      <div class="modal-header">
													        <h5 class="modal-title" id="exampleModalLabel">Xác nhận hủy đơn hàng</h5>
													        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
													          <span aria-hidden="true">&times;</span>
													        </button>
													      </div>
													      <div class="modal-body">
													        Bạn muốn hủy đơn hàng này?
													      </div>
													      <div class="modal-footer">
													        <button type="button" class="btn btn-danger" onclick="Shop.huy_don_hang(${historyCart.id});">Hủy đơn hàng</button>
													      	 <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
													      </div>
													    </div>
													  </div>
													</div>
												</div></c:if>
											</div>
											
										</div>
									</div>
								</div>
								
							</div></c:forEach>
							
						  </div>
						  <div class="tab-pane fade" id="nav-giaohang" role="tabpanel" aria-labelledby="nav-giaohang-tab">
						  	<c:forEach var="historyCart" items="${historyCarts1 }">
							<div class="purchase-list-page__checkout-card-wrapper"
								style="margin-bottom: 0.625rem; margin-top: 0.75rem; color: rgba(0, 0, 0, 0.8); font-size: 15.75px; background-color: rgb(245, 245, 245);">
								<div class="order-card__container"
									style="border-bottom: none; box-shadow: rgba(0, 0, 0, 0.05) 0px 1px 1px 0px; border-radius: 0.125rem; overflow: visible;">
									<div class="order-card__content-wrapper"
										style="background: rgb(255, 255, 255);">
										<div class="order-card__content"
											style="padding-top: 1.25rem; padding-right: 1.25rem; padding-left: 1.25rem;">
											<div class="order-content__container">
												<div class="order-content__header"
													style="-webkit-box-pack: justify; justify-content: space-between; margin-bottom: 0.625rem; display: flex; -webkit-box-align: center; align-items: center;">
													<div class="order-content__header__seller"
														style="display: flex; -webkit-box-align: center; align-items: center; flex-shrink: 0;">
														<div class="order-content__header__seller__chat-btn"
															style="margin-right: 0.375rem; text-transform: capitalize; flex-shrink: 0; stroke: rgb(255, 255, 255);">
														</div>
														
													</div>
													<div class="order-content__header__flex-placeholder"
														style="-webkit-box-flex: 1; flex: 1 1 0%; min-width: 0.625rem; font-size: 1rem;">
														Ngày đặt: <fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" />
													</div>
													
													
												</div>
												<div class="order-content__item-list"
													style="border-top: 1px solid rgba(0, 0, 0, 0.09); font-size: 0.875rem; line-height: 1.1875rem;">
													<c:forEach var="historyCartDetail" items="${historyCart.saleOrderProducts}">
													<a class="order-content__item-wrapper a-hover"
														href="${pageContext.request.contextPath}/products/${historyCartDetail.product.seo }"
														style="color: rgba(0, 0, 0, 0.8); outline: 0px; text-decoration: none;">
														<div class="order-content__item" style="display: flex; flex-wrap: nowrap; overflow-wrap: break-word; border-bottom: 1px solid rgba(0, 0, 0, 0.09);">
															<div
																class="order-content__item__col order-content__item__detail"
																style="-webkit-box-flex: 1; flex: 1 1 auto; min-width: 0px; cursor: pointer; user-select: none;">
																<div class="order-content__item__product"
																	style="cursor: pointer; user-select: none; display: flex; flex-wrap: nowrap; padding-top: 0.625rem; padding-bottom: 0.625rem; height: 150px;">
																	<div class="order-content__item__image"
																		style="width: 5rem; height: 5rem; flex-shrink: 0; margin-right: 0.75rem;">
																		<div class="shopee-image__wrapper"
																			style="width: 110px; height: 130px; position: relative;">
																			
																			<div class="shopee-image__content rounded"
																				style="width: 110px; height: 130px; background-position: 50% center; background-size: cover; background-repeat: no-repeat; position: absolute; top: 0px; left: 10px;">
																				<c:choose>
																					<c:when test = "${empty historyCartDetail.product.productImages }">
																						<img style="width: 100px; height: 120px;" src="http://placehold.it/700x400" alt="">
																					</c:when>
																					<c:otherwise>
																						<img style="width: 100px; height: 120px;" src="${pageContext.request.contextPath}/file/upload/${historyCartDetail.product.productImages.get(0).path }" alt="">
																					</c:otherwise>
																				</c:choose>
																				<div class="shopee-image__content--blur"
																					style="width: 90px; height: 90px;"></div>
																			</div>
																		</div>
																	</div>
																	<div class="order-content__item__detail-content"
																		style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: start; align-items: flex-start; -webkit-box-flex: 1; flex-grow: 1; position: relative; min-width: 0px; overflow-wrap: break-word; pointer-events: none; margin-left: 1rem;">
																		<div class="order-content__item__name"
																			style="width: 694.875px; overflow: hidden; display: -webkit-box; text-overflow: ellipsis; -webkit-box-orient: vertical; -webkit-line-clamp: 2; margin-bottom: 0.3125rem; font-size: 25px; line-height: 2rem; max-height: 3rem;">
																			${historyCartDetail.product.title}
																		</div>
								
																		<div class="order-content__item__quantity" style="margin-top: 1rem;">
																			<span style="font-size: 20px;">Số lượng: <span style="color: red">${historyCartDetail.quality}</span></span>
																			
																		</div>
																	</div>
																</div>
															</div>
															<div
																class="order-content__item__price order-content__item__col order-content__item__col--small order-content__item__col--last"
																style="width: 8.125rem; overflow-wrap: break-word; -webkit-box-flex: 0; flex: 0 0 auto; display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: end; align-items: flex-end; -webkit-box-pack: center; justify-content: center; padding-left: 0.625rem; border-right: 0px !important;">
																<div class="order-content__item__price-text"
																	style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; font-size: 20px; margin-right: 2rem;">
																	<div class="shopee-price--primary"
																				style="color: rgb(238, 77, 45);"><fmt:formatNumber type="number" maxIntegerDigits="13" value="${historyCartDetail.price}" />đ</div>
																</div>
															</div>
														</div>
														</a>
													</c:forEach>
												</div>
											</div>
										</div>
										<div class="_1J7vLy"
											style="width: 989.625px; height: 0px; border-bottom: 1px dotted rgba(0, 0, 0, 0.09); position: relative;">
											<div class="DI-rNr tyOBoQ"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; left: 0px; transform: translate(-50%, -50%);"></div>
											<div class="DI-rNr _25igL4"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; right: 0px; transform: translate(50%, -50%);"></div>
										</div>
									</div>
									<div class="order-card__buttons-container">
										<div class="purchase-card-buttons__wrapper"
											style="background: rgb(255, 254, 251); padding-right: 1.5rem; padding-left: 1.5rem;">
											<div class="row" style="display: flex;">
												<div class="purchase-card-buttons__text-info col-8" style="margin-left: 0px;font-size: 1rem;">
													<p class="purchase-text-info">
														Địa chỉ nhận: ${historyCart.address}
													</p>
													<p class="purchase-text-info">
														Ghi chú: ${historyCart.note_by_customer}
													</p>
												</div>
												<div class="purchase-card-buttons__total-payment col-4"
													style="padding-top: 2.5rem; display: flex; -webkit-box-pack: end; justify-content: flex-end; -webkit-box-align: end; align-items: flex-end; margin-left: 65%;">
													<span class="purchase-card-buttons__label-price"
														style="font-size: 0.875rem; line-height: 1.25rem; margin-right: 0.625rem;">Tổng
														số tiền:</span>
													<span class="purchase-card-buttons__total-price"
													style="color: rgb(238, 77, 45); font-size: 1.875rem; line-height: 2rem;">
														<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${historyCart.total}"/> đ
													</span>
												</div>
											</div>
											
											<div class="purchase-card-buttons__container"
												style="display: flex; flex-wrap: nowrap; -webkit-box-pack: end; place-content: center flex-end; padding-top: 1.125rem; padding-bottom: 1.25rem;">
												<c:if test="${historyCart.status == 1}">
												<div class="purchase-card-buttons__show-button-wrapper"
													style="min-width: 10rem; height: 2.5rem; margin-left: 0.625rem; background: rgb(255, 255, 255); display: flex;">
													<button class="btn btn-primary" data-toggle="modal" data-target="#huy">
														Nhận hàng
													</button>
													<!-- Modal -->
													<div class="modal fade" id="huy" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
													  <div class="modal-dialog" role="document">
													    <div class="modal-content">
													      <div class="modal-header">
													        <h5 class="modal-title" id="exampleModalLabel">Xác nhận nhận hàng</h5>
													        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
													          <span aria-hidden="true">&times;</span>
													        </button>
													      </div>
													      <div class="modal-body">
													        Bạn đã nhận đơn hàng của mình?
													      </div>
													      <div class="modal-footer">
													        <button type="button" class="btn btn-primary" onclick="Shop.huy_don_hang(${historyCart.id});">Đồng ý</button>
													        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
													      </div>
													    </div>
													  </div>
													</div>
												</div></c:if>
											</div>
											
										</div>
									</div>
								</div>
								
							</div></c:forEach>
						  </div>
						  <div class="tab-pane fade" id="nav-danhan" role="tabpanel" aria-labelledby="nav-danhan-tab">
						  	<c:forEach var="historyCart" items="${historyCarts2 }">
							<div class="purchase-list-page__checkout-card-wrapper"
								style="margin-bottom: 0.625rem; margin-top: 0.75rem; color: rgba(0, 0, 0, 0.8); font-size: 15.75px; background-color: rgb(245, 245, 245);">
								<div class="order-card__container"
									style="border-bottom: none; box-shadow: rgba(0, 0, 0, 0.05) 0px 1px 1px 0px; border-radius: 0.125rem; overflow: visible;">
									<div class="order-card__content-wrapper"
										style="background: rgb(255, 255, 255);">
										<div class="order-card__content"
											style="padding-top: 1.25rem; padding-right: 1.25rem; padding-left: 1.25rem;">
											<div class="order-content__container">
												<div class="order-content__header"
													style="-webkit-box-pack: justify; justify-content: space-between; margin-bottom: 0.625rem; display: flex; -webkit-box-align: center; align-items: center;">
													<div class="order-content__header__seller"
														style="display: flex; -webkit-box-align: center; align-items: center; flex-shrink: 0;">
														<div class="order-content__header__seller__chat-btn"
															style="margin-right: 0.375rem; text-transform: capitalize; flex-shrink: 0; stroke: rgb(255, 255, 255);">
														</div>
														
													</div>
													<div class="order-content__header__flex-placeholder"
														style="-webkit-box-flex: 1; flex: 1 1 0%; min-width: 0.625rem; font-size: 1rem;">
														Ngày đặt: <fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" />
													</div>
													
													
												</div>
												<div class="order-content__item-list"
													style="border-top: 1px solid rgba(0, 0, 0, 0.09); font-size: 0.875rem; line-height: 1.1875rem;">
													<c:forEach var="historyCartDetail" items="${historyCart.saleOrderProducts}">
													<a class="order-content__item-wrapper a-hover"
														href="${pageContext.request.contextPath}/products/${historyCartDetail.product.seo }"
														style="color: rgba(0, 0, 0, 0.8); outline: 0px; text-decoration: none;">
														<div class="order-content__item" style="display: flex; flex-wrap: nowrap; overflow-wrap: break-word; border-bottom: 1px solid rgba(0, 0, 0, 0.09);">
															<div
																class="order-content__item__col order-content__item__detail"
																style="-webkit-box-flex: 1; flex: 1 1 auto; min-width: 0px; cursor: pointer; user-select: none;">
																<div class="order-content__item__product"
																	style="cursor: pointer; user-select: none; display: flex; flex-wrap: nowrap; padding-top: 0.625rem; padding-bottom: 0.625rem; height: 150px;">
																	<div class="order-content__item__image"
																		style="width: 5rem; height: 5rem; flex-shrink: 0; margin-right: 0.75rem;">
																		<div class="shopee-image__wrapper"
																			style="width: 110px; height: 130px; position: relative;">
																			
																			<div class="shopee-image__content rounded"
																				style="width: 110px; height: 130px; background-position: 50% center; background-size: cover; background-repeat: no-repeat; position: absolute; top: 0px; left: 10px;">
																				<c:choose>
																					<c:when test = "${empty historyCartDetail.product.productImages }">
																						<img style="width: 100px; height: 120px;" src="http://placehold.it/700x400" alt="">
																					</c:when>
																					<c:otherwise>
																						<img style="width: 100px; height: 120px;" src="${pageContext.request.contextPath}/file/upload/${historyCartDetail.product.productImages.get(0).path }" alt="">
																					</c:otherwise>
																				</c:choose>
																				<div class="shopee-image__content--blur"
																					style="width: 90px; height: 90px;"></div>
																			</div>
																		</div>
																	</div>
																	<div class="order-content__item__detail-content"
																		style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: start; align-items: flex-start; -webkit-box-flex: 1; flex-grow: 1; position: relative; min-width: 0px; overflow-wrap: break-word; pointer-events: none; margin-left: 1rem;">
																		<div class="order-content__item__name"
																			style="width: 694.875px; overflow: hidden; display: -webkit-box; text-overflow: ellipsis; -webkit-box-orient: vertical; -webkit-line-clamp: 2; margin-bottom: 0.3125rem; font-size: 25px; line-height: 2rem; max-height: 3rem;">
																			${historyCartDetail.product.title}
																		</div>
								
																		<div class="order-content__item__quantity" style="margin-top: 1rem;">
																			<span style="font-size: 20px;">Số lượng: <span style="color: red">${historyCartDetail.quality}</span></span>
																			
																		</div>
																	</div>
																</div>
															</div>
															<div
																class="order-content__item__price order-content__item__col order-content__item__col--small order-content__item__col--last"
																style="width: 8.125rem; overflow-wrap: break-word; -webkit-box-flex: 0; flex: 0 0 auto; display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: end; align-items: flex-end; -webkit-box-pack: center; justify-content: center; padding-left: 0.625rem; border-right: 0px !important;">
																<div class="order-content__item__price-text"
																	style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; font-size: 20px; margin-right: 2rem;">
																	<div class="shopee-price--primary"
																				style="color: rgb(238, 77, 45);"><fmt:formatNumber type="number" maxIntegerDigits="13" value="${historyCartDetail.price}" />đ</div>
																</div>
															</div>
														</div>
														</a>
													</c:forEach>
												</div>
											</div>
										</div>
										<div class="_1J7vLy"
											style="width: 989.625px; height: 0px; border-bottom: 1px dotted rgba(0, 0, 0, 0.09); position: relative;">
											<div class="DI-rNr tyOBoQ"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; left: 0px; transform: translate(-50%, -50%);"></div>
											<div class="DI-rNr _25igL4"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; right: 0px; transform: translate(50%, -50%);"></div>
										</div>
									</div>
									<div class="order-card__buttons-container">
										<div class="purchase-card-buttons__wrapper"
											style="background: rgb(255, 254, 251); padding-right: 1.5rem; padding-left: 1.5rem;">
											<div class="row" style="display: flex;">
												<div class="purchase-card-buttons__text-info col-8" style="margin-left: 0px;font-size: 1rem;">
													<p class="purchase-text-info">
														Địa chỉ nhận: ${historyCart.address}
													</p>
													<p class="purchase-text-info">
														Ghi chú: ${historyCart.note_by_customer}
													</p>
												</div>
												<div class="purchase-card-buttons__total-payment col-4"
													style="padding-top: 2.5rem; display: flex; -webkit-box-pack: end; justify-content: flex-end; -webkit-box-align: end; align-items: flex-end; margin-left: 65%;">
													<span class="purchase-card-buttons__label-price"
														style="font-size: 0.875rem; line-height: 1.25rem; margin-right: 0.625rem;">Tổng
														số tiền:</span>
													<span class="purchase-card-buttons__total-price"
													style="color: rgb(238, 77, 45); font-size: 1.875rem; line-height: 2rem;">
														<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${historyCart.total}"/> đ
													</span>
												</div>
											</div>
											
											<div class="purchase-card-buttons__container"
												style="display: flex; flex-wrap: nowrap; -webkit-box-pack: end; place-content: center flex-end; padding-top: 1.125rem; padding-bottom: 1.25rem;">
												
												<div class="purchase-card-buttons__show-button-wrapper"
													style="min-width: 10rem; height: 2.5rem; margin-left: 0.625rem; background: rgb(255, 255, 255); display: flex;">
													<button class="btn btn-success" style="width: 10rem; color: white;" onclick="Shop.mua_lai(${historyCart.id});">
														Mua lần nữa
													</button>
												</div>
											</div>
											
										</div>
									</div>
								</div>
								
							</div></c:forEach>
						  </div>
						   <div class="tab-pane fade" id="nav-dahuy" role="tabpanel" aria-labelledby="nav-dahuy-tab">
						  	<c:forEach var="historyCart" items="${historyCarts3 }">
							<div class="purchase-list-page__checkout-card-wrapper"
								style="margin-bottom: 0.625rem; margin-top: 0.75rem; color: rgba(0, 0, 0, 0.8); font-size: 15.75px; background-color: rgb(245, 245, 245);">
								<div class="order-card__container"
									style="border-bottom: none; box-shadow: rgba(0, 0, 0, 0.05) 0px 1px 1px 0px; border-radius: 0.125rem; overflow: visible;">
									<div class="order-card__content-wrapper"
										style="background: rgb(255, 255, 255);">
										<div class="order-card__content"
											style="padding-top: 1.25rem; padding-right: 1.25rem; padding-left: 1.25rem;">
											<div class="order-content__container">
												<div class="order-content__header"
													style="-webkit-box-pack: justify; justify-content: space-between; margin-bottom: 0.625rem; display: flex; -webkit-box-align: center; align-items: center;">
													<div class="order-content__header__seller"
														style="display: flex; -webkit-box-align: center; align-items: center; flex-shrink: 0;">
														<div class="order-content__header__seller__chat-btn"
															style="margin-right: 0.375rem; text-transform: capitalize; flex-shrink: 0; stroke: rgb(255, 255, 255);">
														</div>
														
													</div>
													<div class="order-content__header__flex-placeholder"
														style="-webkit-box-flex: 1; flex: 1 1 0%; min-width: 0.625rem; font-size: 1rem;">
														Ngày đặt: <fmt:formatDate pattern = "dd-MM-yyyy" 
         																			value = "${historyCart.createdDate}" />
													</div>
													
													
												</div>
												<div class="order-content__item-list"
													style="border-top: 1px solid rgba(0, 0, 0, 0.09); font-size: 0.875rem; line-height: 1.1875rem;">
													<c:forEach var="historyCartDetail" items="${historyCart.saleOrderProducts}">
													<a class="order-content__item-wrapper a-hover"
														href="${pageContext.request.contextPath}/products/${historyCartDetail.product.seo }"
														style="color: rgba(0, 0, 0, 0.8); outline: 0px; text-decoration: none;">
														<div class="order-content__item" style="display: flex; flex-wrap: nowrap; overflow-wrap: break-word; border-bottom: 1px solid rgba(0, 0, 0, 0.09);">
															<div
																class="order-content__item__col order-content__item__detail"
																style="-webkit-box-flex: 1; flex: 1 1 auto; min-width: 0px; cursor: pointer; user-select: none;">
																<div class="order-content__item__product"
																	style="cursor: pointer; user-select: none; display: flex; flex-wrap: nowrap; padding-top: 0.625rem; padding-bottom: 0.625rem; height: 150px;">
																	<div class="order-content__item__image"
																		style="width: 5rem; height: 5rem; flex-shrink: 0; margin-right: 0.75rem;">
																		<div class="shopee-image__wrapper"
																			style="width: 110px; height: 130px; position: relative;">
																			
																			<div class="shopee-image__content rounded"
																				style="width: 110px; height: 130px; background-position: 50% center; background-size: cover; background-repeat: no-repeat; position: absolute; top: 0px; left: 10px;">
																				<c:choose>
																					<c:when test = "${empty historyCartDetail.product.productImages }">
																						<img style="width: 100px; height: 120px;" src="http://placehold.it/700x400" alt="">
																					</c:when>
																					<c:otherwise>
																						<img style="width: 100px; height: 120px;" src="${pageContext.request.contextPath}/file/upload/${historyCartDetail.product.productImages.get(0).path }" alt="">
																					</c:otherwise>
																				</c:choose>
																				<div class="shopee-image__content--blur"
																					style="width: 90px; height: 90px;"></div>
																			</div>
																		</div>
																	</div>
																	<div class="order-content__item__detail-content"
																		style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: start; align-items: flex-start; -webkit-box-flex: 1; flex-grow: 1; position: relative; min-width: 0px; overflow-wrap: break-word; pointer-events: none; margin-left: 1rem;">
																		<div class="order-content__item__name"
																			style="width: 694.875px; overflow: hidden; display: -webkit-box; text-overflow: ellipsis; -webkit-box-orient: vertical; -webkit-line-clamp: 2; margin-bottom: 0.3125rem; font-size: 25px; line-height: 2rem; max-height: 3rem;">
																			${historyCartDetail.product.title}
																		</div>
								
																		<div class="order-content__item__quantity" style="margin-top: 1rem;">
																			<span style="font-size: 20px;">Số lượng: <span style="color: red">${historyCartDetail.quality}</span></span>
																			
																		</div>
																	</div>
																</div>
															</div>
															<div
																class="order-content__item__price order-content__item__col order-content__item__col--small order-content__item__col--last"
																style="width: 8.125rem; overflow-wrap: break-word; -webkit-box-flex: 0; flex: 0 0 auto; display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; -webkit-box-align: end; align-items: flex-end; -webkit-box-pack: center; justify-content: center; padding-left: 0.625rem; border-right: 0px !important;">
																<div class="order-content__item__price-text"
																	style="display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; flex-direction: column; font-size: 20px; margin-right: 2rem;">
																	<div class="shopee-price--primary"
																				style="color: rgb(238, 77, 45);"><fmt:formatNumber type="number" maxIntegerDigits="13" value="${historyCartDetail.price}" />đ</div>
																</div>
															</div>
														</div>
														</a>
													</c:forEach>
												</div>
											</div>
										</div>
										<div class="_1J7vLy"
											style="width: 989.625px; height: 0px; border-bottom: 1px dotted rgba(0, 0, 0, 0.09); position: relative;">
											<div class="DI-rNr tyOBoQ"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; left: 0px; transform: translate(-50%, -50%);"></div>
											<div class="DI-rNr _25igL4"
												style="background: rgb(245, 245, 245); width: 0.4375rem; height: 0.4375rem; border-radius: 50%; z-index: 1; position: absolute; top: 0px; right: 0px; transform: translate(50%, -50%);"></div>
										</div>
									</div>
									<div class="order-card__buttons-container">
										<div class="purchase-card-buttons__wrapper"
											style="background: rgb(255, 254, 251); padding-right: 1.5rem; padding-left: 1.5rem;">
											<div class="row" style="display: flex;">
												<div class="purchase-card-buttons__text-info col-8" style="margin-left: 0px;font-size: 1rem;">
													<p class="purchase-text-info">
														Địa chỉ nhận: ${historyCart.address}
													</p>
													<p class="purchase-text-info">
														Ghi chú: ${historyCart.note_by_customer}
													</p>
												</div>
												<div class="purchase-card-buttons__total-payment col-4"
													style="padding-top: 2.5rem; display: flex; -webkit-box-pack: end; justify-content: flex-end; -webkit-box-align: end; align-items: flex-end; margin-left: 65%;">
													<span class="purchase-card-buttons__label-price"
														style="font-size: 0.875rem; line-height: 1.25rem; margin-right: 0.625rem;">Tổng
														số tiền:</span>
													<span class="purchase-card-buttons__total-price"
													style="color: rgb(238, 77, 45); font-size: 1.875rem; line-height: 2rem;">
														<fmt:formatNumber type="number" maxIntegerDigits="13"
													value="${historyCart.total}"/> đ
													</span>
												</div>
											</div>
											
											<div class="purchase-card-buttons__container"
												style="display: flex; flex-wrap: nowrap; -webkit-box-pack: end; place-content: center flex-end; padding-top: 1.125rem; padding-bottom: 1.25rem;">
												
												<div class="purchase-card-buttons__show-button-wrapper"
													style="height: 2.5rem; margin-left: 0.625rem; background: rgb(255, 255, 255); display: flex;">
													<button class="btn btn-success" style="width: 10rem; color: white" onclick="Shop.mua_lai(${historyCart.id});">
														Đặt hàng lại
													</button>
												</div>
											</div>
											
										</div>
									</div>
								</div>
								
							</div></c:forEach>
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
</body>
</html>





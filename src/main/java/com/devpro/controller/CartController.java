package com.devpro.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devpro.conf.CustomSuccessHandler;
import com.devpro.entities.Cart;
import com.devpro.entities.MyConstants;
import com.devpro.entities.Product;
import com.devpro.entities.ProductInCart;
import com.devpro.entities.SaleOrder;
import com.devpro.entities.SaleOrderProducts;
import com.devpro.entities.User;
import com.devpro.model.AjaxResponse;
import com.devpro.repositories.ProductRepo;
import com.devpro.repositories.SaleOrderRepo;
import com.devpro.repositories.UserRepo;
import com.devpro.services.SaleOrderService;

@Controller
public class CartController extends BaseController{
	@Autowired ProductRepo productRepo;
	

	@Autowired SaleOrderRepo saleOrderRepo;
	
	@Autowired UserRepo userRepo;
	
	@Autowired
	private SaleOrderService saleOrderService;
	@RequestMapping(value = { "/cart" }, method = RequestMethod.GET)
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		return "front-end/cart";
	}
	
	
	
	
	@RequestMapping(value = { "/chon-san-pham-dua-vao-gio-hang" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> muaHang(@RequestBody ProductInCart sanPhamTrongGioHang,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		
		// lấy đối tượng SESSION trong memory dựa vào SESSION_ID có trong request.
		HttpSession httpSession = request.getSession();
		
		Cart gioHang = null;
		
		// kiểm tra xem SESSION đã có gio hàng chưa ?
		// nếu chưa có thì tạo mới 1 giỏ hàng và lưu vào SESSION.
		if (httpSession.getAttribute("GIO_HANG") != null) {
			gioHang = (Cart) httpSession.getAttribute("GIO_HANG");
		} else {
			gioHang = new Cart();
			httpSession.setAttribute("GIO_HANG", gioHang);
		}
		
		List<ProductInCart> _sanPhamTrongGioHangs = gioHang.getSanPhamTrongGioHangs();
		
		boolean sanPhamDaCoTrongGioHangPhaiKhong = false;
		
		// trường hợp đã có sản phẩm trong giỏ hàng.
		for(ProductInCart item : _sanPhamTrongGioHangs) {
			if(item.getProductId() == sanPhamTrongGioHang.getProductId()) {
				sanPhamDaCoTrongGioHangPhaiKhong = true;
				item.setSoluong(item.getSoluong() + sanPhamTrongGioHang.getSoluong());
			}
		}
		
		// nếu sản phẩm chưa có trong giỏ hàng.
		if(!sanPhamDaCoTrongGioHangPhaiKhong) {
			
			Product product = productRepo.getOne(sanPhamTrongGioHang.getProductId());
			sanPhamTrongGioHang.setTenSP(product.getTitle());
			sanPhamTrongGioHang.setGiaBan(product.getPrice());
			
			gioHang.getSanPhamTrongGioHangs().add(sanPhamTrongGioHang);
		}
		
//		httpSession.setAttribute("SL_SP_GIO_HANG", cart.getCartItems().size());
		
		return ResponseEntity.ok(new AjaxResponse(200, String.valueOf(gioHang.getSanPhamTrongGioHangs().size())));
	}
	
	@RequestMapping(value = { "/user/luu_don_hang" }, method = RequestMethod.POST)
	public String save(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		// lấy đối tượng SESSION trong memory dựa vào SESSION_ID có trong request.
		HttpSession httpSession = request.getSession();
		
		Cart gioHang = (Cart) httpSession.getAttribute("GIO_HANG");;
		
		
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		
		
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setCode(String.valueOf(System.currentTimeMillis()));
		saleOrder.setCreatedDate(LocalDateTime.now());
		saleOrder.setCustomerName(name);
		saleOrder.setCustomerAddress(address);
		saleOrder.setPhone(phone);
		saleOrder.setEmail(email);
		saleOrder.setUser(userRepo.getOne(8));
		saleOrder.setTotal(gioHang.getTotal(productRepo));
		
		for(ProductInCart sanPhamTrongGioHang : gioHang.getSanPhamTrongGioHangs()) {
			SaleOrderProducts saleOrderProducts = new SaleOrderProducts();
			saleOrderProducts.setProduct(productRepo.getOne(sanPhamTrongGioHang.getProductId()));
			saleOrderProducts.setQuality(sanPhamTrongGioHang.getSoluong());
			saleOrderProducts.setCreatedDate(LocalDateTime.now());
			saleOrder.addSaleOrderProducts(saleOrderProducts);
		}
		saleOrderRepo.save(saleOrder);
		// lưu xong xoá giỏ hàng đi
		httpSession.removeAttribute("GIO_HANG");

		return "redirect:/user/historyCart/?add=success";
	}
	
	@RequestMapping(value = { "/user/historyCart" }, method = RequestMethod.GET)
	public String saveProduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		model.addAttribute("historyCarts", saleOrderService.searchUser(8));
		return "front-end/historyCart";
	}
	
	
	@RequestMapping(value = { "/user/historyCart/{id}" }, method = RequestMethod.GET)
	public String saveProduct(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		SaleOrder saleOrder = saleOrderRepo.getOne(id);
		model.addAttribute("historyCart", saleOrder);
		model.addAttribute("historyCartDetails", saleOrderService.searchProduct(id));
		return "front-end/historyCartDetail";
	}
	
	@RequestMapping(value = { "/user/historyCartDetail/{id}" }, method = RequestMethod.GET)
	public String confirm_sale(@PathVariable("id") Integer id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		SaleOrder saleOrderInDP = saleOrderRepo.getOne(id);
		if(saleOrderInDP.getStatus_ok()==1)
		{
			saleOrderInDP.setStatus_ok(2);
			saleOrderRepo.save(saleOrderInDP);
		}
		else
		{
			saleOrderRepo.delete(saleOrderInDP);
		}
		
		model.addAttribute("historyCarts", saleOrderService.searchUser(8));
		return "front-end/historyCart";
	}
}

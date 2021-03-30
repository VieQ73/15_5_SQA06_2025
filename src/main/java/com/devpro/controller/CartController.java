package com.devpro.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.devpro.entities.Cart;

import com.devpro.entities.Product;
import com.devpro.entities.ProductInCart;
import com.devpro.entities.SaleOrder;
import com.devpro.entities.SaleOrderProducts;
import com.devpro.entities.User;
import com.devpro.model.AjaxResponse;
import com.devpro.model.luutru;
import com.devpro.repositories.ProductRepo;
import com.devpro.repositories.SaleOrderRepo;
import com.devpro.repositories.UserRepo;
import com.devpro.services.SaleOrderService;
import com.devpro.services.UserService;
import com.ibm.icu.util.Calendar;

import java.math.*;

@Controller
public class CartController extends BaseController{
	@Autowired ProductRepo productRepo;
	

	@Autowired SaleOrderRepo saleOrderRepo;
	
	@Autowired UserRepo userRepo;
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	@Autowired
	private UserService userService;
	@RequestMapping(value = { "/cart" }, method = RequestMethod.GET)
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		HttpSession httpSession = request.getSession();
		Cart gioHang = (Cart) httpSession.getAttribute("GIO_HANG");
		return "front-end/cart";
	}
	

	@RequestMapping(value = { "/chon-san-pham-dua-vao-gio-hang" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> muaHang(@RequestBody ProductInCart sanPhamTrongGioHang,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		
		/*
		 * Product productInDB=productRepo.getOne(sanPhamTrongGioHang.getProductId());
		 * int slCu = productInDB.getAmount(); if(slCu >=
		 * sanPhamTrongGioHang.getSoluong()) { productInDB.setAmount(slCu -
		 * sanPhamTrongGioHang.getSoluong());
		 * productInDB.setSelling(productInDB.getSelling()+sanPhamTrongGioHang.
		 * getSoluong()); productRepo.save(productInDB); } else {
		 * 
		 * }
		 */
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
			
			item.setTongGia(item.getGiaBan().multiply(new BigDecimal(item.getSoluong())));
		}
		
		// nếu sản phẩm chưa có trong giỏ hàng.
		if(!sanPhamDaCoTrongGioHangPhaiKhong) {
			
			Product product = productRepo.getOne(sanPhamTrongGioHang.getProductId());
			sanPhamTrongGioHang.setTenSP(product.getTitle());
			
			sanPhamTrongGioHang.setGiaBan(product.getPrice_sale());
			sanPhamTrongGioHang.setTongGia(product.getPrice_sale().multiply(new BigDecimal(sanPhamTrongGioHang.getSoluong())));
			gioHang.getSanPhamTrongGioHangs().add(sanPhamTrongGioHang);
			sanPhamTrongGioHang.setAmount(product.getAmount());
			sanPhamTrongGioHang.setSeo(product.getSeo());
		}
		BigDecimal sum = BigDecimal.ZERO;
		
		for(ProductInCart item : _sanPhamTrongGioHangs) {
			sum = sum.add(item.getTongGia());
			
		}
		
		httpSession.setAttribute("tong_gia", sum);
//		httpSession.setAttribute("SL_SP_GIO_HANG", cart.getCartItems().size());
		
		return ResponseEntity.ok(new AjaxResponse(200, String.valueOf(gioHang.getSanPhamTrongGioHangs().size())));
	}
	
	
	@RequestMapping(value = { "/luu_don_hang" }, method = RequestMethod.POST)
	public String save(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		// lấy đối tượng SESSION trong memory dựa vào SESSION_ID có trong request.
		HttpSession httpSession = request.getSession();
		Cart gioHang = (Cart) httpSession.getAttribute("GIO_HANG");;
		
		if(checkSL(gioHang) != "")
		{
			model.addAttribute("messsage", "<div class=\"alert alert-danger\">"
					+ "  <strong>Failed!</strong> Số lượng sản phẩm " +checkSL(gioHang) +" không đủ"+ "</div>");
			return "front-end/cart"; 
		}
		else {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String note = request.getParameter("note");
			
			SaleOrder saleOrder = new SaleOrder();
			Date d = Calendar.getInstance().getTime();
			saleOrder.setCreatedDate(d);
			saleOrder.setNote(note);
			saleOrder.setAddress(address);
			saleOrder.setTotal(gioHang.getTotal(productRepo));
			
			List<User> u = userService.searUserByPhone(phone);
			if(u.size()==0) {
				User user = new User();
				user.setAddress(address);
				user.setName(name);
				user.setPhone(phone);
				userRepo.save(user);
			}
			List<User> u2 = userService.searUserByPhone(phone);
			for (User user : u2) {
				user.setAddress(address);
				userRepo.save(user);
				saleOrder.setUser(user);
			}
			for(ProductInCart sanPhamTrongGioHang : gioHang.getSanPhamTrongGioHangs()) {
				Product prInDB = productRepo.getOne(sanPhamTrongGioHang.getProductId());
				SaleOrderProducts saleOrderProducts = new SaleOrderProducts();
				saleOrderProducts.setProduct(productRepo.getOne(sanPhamTrongGioHang.getProductId()));
				saleOrderProducts.setQuality(sanPhamTrongGioHang.getSoluong());
				prInDB.setAmount(prInDB.getAmount()-sanPhamTrongGioHang.getSoluong());
				productRepo.save(prInDB);
				saleOrder.addSaleOrderProducts(saleOrderProducts);
			}
			saleOrderRepo.save(saleOrder);
			// lưu xong xoá giỏ hàng đi
			httpSession.removeAttribute("GIO_HANG");
			httpSession.removeAttribute("tong_gia");
	
			return "redirect:/buy-success/?add=success";
		}
	}
	
	@RequestMapping(value = { "/buy-success" }, method = RequestMethod.GET)
	public String buy_success(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		HttpSession httpSession = request.getSession();
		Cart gioHang = (Cart) httpSession.getAttribute("GIO_HANG");
		return "front-end/buySuccess";
	}
	
	public String checkSL(Cart gioHang) {
		String err="";
		for(ProductInCart spInCart : gioHang.getSanPhamTrongGioHangs()) {
			Product spInDB = productRepo.getOne(spInCart.getProductId());
			if(spInDB.getAmount() < spInCart.getSoluong())
			{
				err+=spInCart.getTenSP()+" ";
			}
		}
		return err;
	}
	
	
	
	@RequestMapping(value = { "/historyCart" }, method = RequestMethod.GET)
	public String saveProduct( final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return "front-end/historyCart";
	}
	
	@RequestMapping(value = { "/user/historyCartDetail/{id}" }, method = RequestMethod.GET)
	public String confirm_sale(@PathVariable("id") Integer id, @RequestBody String phone, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		SaleOrder saleOrderInDP = saleOrderRepo.getOne(id);
		
		if(saleOrderInDP.getStatus()==1)
		{
			saleOrderInDP.setStatus(2);
			saleOrderRepo.save(saleOrderInDP);
		}
		else
		{
			List<SaleOrderProducts> saleOr = saleOrderInDP.getSaleOrderProducts();
			for (SaleOrderProducts item : saleOr) {
				Product productU = productRepo.getOne(item.getProduct().getId());
				productU.setAmount(productU.getAmount()+item.getQuality());
				productRepo.save(productU);
			}
			saleOrderRepo.delete(saleOrderInDP);
		}
		
		model.addAttribute("historyCarts", saleOrderService.searchUserPhone(phone));
		return "front-end/historyCart";
	}
	
	
	
	@RequestMapping(value = { "/xoa-sp-gio-hang" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> xoaSP_in_Cart(@RequestBody ProductInCart sanPhamTrongGioHang,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		HttpSession httpSession = request.getSession();
		Cart gioHang = (Cart) httpSession.getAttribute("GIO_HANG");
		ProductInCart itemRemove = new ProductInCart();
		for (ProductInCart item : gioHang.getSanPhamTrongGioHangs()) {
			if(item.getProductId() == sanPhamTrongGioHang.getProductId())
			{
				itemRemove = item;
			}
		}
		gioHang.getSanPhamTrongGioHangs().remove(itemRemove);
		BigDecimal sum = BigDecimal.ZERO;
		for(ProductInCart item : gioHang.getSanPhamTrongGioHangs()) {
			sum = sum.add(item.getTongGia());
		}
		httpSession.setAttribute("tong_gia", sum);
		return ResponseEntity.ok(new AjaxResponse(200, sum));
	}
	
	@RequestMapping(value = { "/update-sp-gio-hang" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> update_SP_in_Cart(@RequestBody ProductInCart sanPhamTrongGioHang,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		HttpSession httpSession = request.getSession();
		Cart gioHang = (Cart) httpSession.getAttribute("GIO_HANG");
		for (ProductInCart item : gioHang.getSanPhamTrongGioHangs()) {
			if(item.getProductId() == sanPhamTrongGioHang.getProductId())
			{			
				item.setSoluong(sanPhamTrongGioHang.getSoluong());
				item.setTongGia(item.getGiaBan().multiply(new BigDecimal(item.getSoluong())));
				sanPhamTrongGioHang.setTongGia(item.getTongGia());
			}
		}
		BigDecimal sum = BigDecimal.ZERO;
		for(ProductInCart item : gioHang.getSanPhamTrongGioHangs()) {
			sum = sum.add(item.getTongGia());
		}
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		String str2 =currencyVN.format(sum);
		String str3 = currencyVN.format(sanPhamTrongGioHang.getTongGia());
		luutru dta = new luutru();
		dta.setNgang(str3);
		dta.setDoc(str2);
		httpSession.setAttribute("tong_gia", sum);
		return ResponseEntity.ok(new AjaxResponse(200, dta));
	}
	
	@RequestMapping(value = { "/check-phone" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> checkPhone(@RequestBody Integer phone,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		User u= new User();
		
		List<User> ur=userService.searUserByPhone(phone.toString());
		if(ur.size()==0) {
			return ResponseEntity.ok(new AjaxResponse(200,u ));
		}
		System.out.println(ur.size());
		for (User user : ur) {
			u.setName(user.getName());
			u.setAddress(user.getAddress());
		}
		
		return ResponseEntity.ok(new AjaxResponse(200,u ));
	}
	
}

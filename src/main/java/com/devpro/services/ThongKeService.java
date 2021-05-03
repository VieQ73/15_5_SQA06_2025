package com.devpro.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpro.entities.OrderProducts;
import com.devpro.entities.ProductSale;
import com.devpro.model.ThongKe;
import com.devpro.repositories.OrderProductRepo;
import com.devpro.repositories.ProductSaleRepo;

@Service
public class ThongKeService {
	@Autowired
	private OrderProductRepo orderProductRepo;
	
	public boolean checkSP(List<ThongKe> listTK, Integer id) {
		for (ThongKe thongKe : listTK) {
			if(thongKe.getProduct().getId() == id)
				return true;
		}
		return false;
	}
	public List<ThongKe> thongKeTheoTime(String startD, String endD) throws ParseException{
		String nam = startD.substring(0, 4);
		String thang = startD.substring(5, 7);
		String ngay = startD.substring(8, 10);
		startD = ngay + "/" + thang + "/" + nam;
		
		String nam1 = endD.substring(0, 4);
		String thang1 = endD.substring(5, 7);
		String ngay1 = endD.substring(8, 10);
		endD = ngay1 + "/" + thang1 + "/" + nam1;
		
		Date datengaybd = new SimpleDateFormat("dd/MM/yyyy").parse(startD);
		Date datengaykt = new SimpleDateFormat("dd/MM/yyyy").parse(endD);
		Long ngayBD = datengaybd.getTime();
		Long ngayKT = datengaykt.getTime();
		
		List<ThongKe> listThongKe = new ArrayList<>();
		List<OrderProducts> listPS = orderProductRepo.findAll();
		for (OrderProducts item : listPS) {
			long n = item.getOrder().getUpdated_date().getTime();
			if(n <= ngayKT && n >= ngayBD && item.getOrder().getStatus() == 2) {
				if(!checkSP(listThongKe, item.getProduct().getId())) {
					ThongKe tk = new ThongKe();
					tk.setProduct(item.getProduct());
					tk.setTongSoLuong(item.getQuality());
					tk.setTongGia(item.getPrice().multiply(new BigDecimal(item.getQuality())));
					listThongKe.add(tk);
				}
				else {
					for (ThongKe thongKe : listThongKe) {
						if(thongKe.getProduct().getId() == item.getProduct().getId()) {
							thongKe.setTongSoLuong(thongKe.getTongSoLuong() + item.getQuality());
							BigDecimal t = item.getPrice().multiply(new BigDecimal(item.getQuality()));
							thongKe.setTongGia(t.add(thongKe.getTongGia()));
						}
					}
				}
				
			}
		}
		return listThongKe;
	}
}

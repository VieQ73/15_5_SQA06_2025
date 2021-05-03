package com.devpro.controller.admin;

import java.text.ParseException;
import java.util.List;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.devpro.model.ThongKe;
import com.devpro.services.ThongKeService;

@Controller
public class AdminThongkeController {

	@Autowired
	private ThongKeService thongKeService;
	
	@RequestMapping(value = { "/back-end/thongke" }, method = RequestMethod.GET)
	public Model saleadd(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Model model = new ExtendedModelMap();
		return model;
	}
	
	@RequestMapping(value = { "/admin/thongke/search" }, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView s(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("thongke"))
			return thongke(request);
		return null;
	}
	
	public ModelAndView thongke(final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			String bd = request.getParameter("ngayBD");
			String kt = request.getParameter("ngayKT");
			List<ThongKe> listTK = thongKeService.thongKeTheoTime(bd, kt);
			modelAndView.addObject("thongke", listTK);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("back-end/thongke");
		return modelAndView;
	}

}

package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.application.models.PurchaseOrder;
import com.application.repository.PurchaseOrderRepository;
//Controller da pagina que lista as compras
@Controller
public class ListController {
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@GetMapping("/list")
	public ModelAndView orderList() {
		ModelAndView mv = new ModelAndView();
		Iterable<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findAll();
		mv.addObject("purchaseOrder", purchaseOrder);
		return mv;
	}
}

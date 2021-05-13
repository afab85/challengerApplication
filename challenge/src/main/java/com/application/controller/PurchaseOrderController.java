package com.application.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.application.dto.RequestNewPurschaseOrder;
import com.application.models.PurchaseOrder;
import com.application.repository.PurchaseOrderRepository;
//Controller das ordens de Compra
@Controller
public class PurchaseOrderController {
	//Aqui se faz a injecao de dependencias
	@Autowired
	private PurchaseOrderRepository PurchaseOrderRepository;
	//na página new, é apresentado o formulario de ordens de compra
	@GetMapping("new")
	public String purchase(RequestNewPurschaseOrder requestNewPurschaseOrder) {
		return "new";
	}
	//Este metodo é responsavel por por pegar a submissao e salvar no banco, para processamento posterior
	@PostMapping("new")
	public String newpurchase(@Valid RequestNewPurschaseOrder requestNewPurschaseOrder, BindingResult result) {
		
		if (result.hasErrors()) {
			return "new";
		}
		
		PurchaseOrder order = requestNewPurschaseOrder.toPurschaseOrder(); 
		PurchaseOrderRepository.save(order);
		return "new";
	}
	//Este metodo deleta ordens de compra do repositorio
	@RequestMapping("/delete")
	public String deleteOrder (Long id) {
		PurchaseOrder purchaseOrder = PurchaseOrderRepository.findAllById(id);
		PurchaseOrderRepository.delete(purchaseOrder);
		return "redirect:/list";
	}
	//Este metodo detalha uma requisicao de compra, ja fazendo seu processamento para apresentação (output)
	//No momento ele só está funcionando para ordens de compras individuais
	@GetMapping("/{id}")
	public ModelAndView orderDetail(@PathVariable("id") Long id, Model model) {
		PurchaseOrder purchaseOrder = PurchaseOrderRepository.findAllById(id);
		
		ModelAndView mv = new ModelAndView("orderDetail");
		
		purchaseOrder.getTextOrder().replaceAll(" at ", " at#@#");
		
		List<String> purchaseOrderList = Arrays.asList(purchaseOrder.getTextOrder().split("#@#"));		
		
		double auxTotal = 0;
		
		for (int i = 0; i <  purchaseOrderList.size(); i++) {
			PurchaseOrder po = new PurchaseOrder();
			String aux = "";
					
			po.setTextOrder(purchaseOrderList.toString());
					
			aux = po.getTextOrder().replace("]", "");
			po.setTextOrder(aux.replace("[", ""));
			
			
			model.addAttribute("amount", po.amount());
			model.addAttribute("product", po.product());
			model.addAttribute("price", 
									  !po.isBasicTaxFree() && !po.isImport()  ? auxTotal = po.finalBasicTaxCalculator(Double.parseDouble(po.price()), (po.amount()))  :
									  !po.isBasicTaxFree() &&  po.isImport()  ? auxTotal = po.finalBothTaxCalculator(Double.parseDouble(po.price()), (po.amount()))  :
									   po.isBasicTaxFree() &&  po.isImport()  ? auxTotal = po.finalImportTaxCalculator(Double.parseDouble(po.price()), (po.amount())) :
									   po.isBasicTaxFree() && !po.isImport()  ? auxTotal = (Double.parseDouble(po.price())) : (Double.parseDouble(po.price())));
			model.addAttribute("taxes", 
									  !po.isBasicTaxFree() && !po.isImport() ? po.basicTaxCalculator(Double.parseDouble(po.price()), po.amount())  :
									  !po.isBasicTaxFree() && po.isImport()  ? po.bothTaxCalculator(Double.parseDouble(po.price()), po.amount())   :
									   po.isBasicTaxFree() && po.isImport()  ? po.importTaxCalculator(Double.parseDouble(po.price()), po.amount()) :
								 	   "");	
		    model.addAttribute("total", auxTotal);
		}

		return mv;
	}
}

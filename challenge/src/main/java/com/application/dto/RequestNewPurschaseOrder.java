package com.application.dto;

import com.application.models.PurchaseOrder;
import javax.validation.constraints.NotBlank;
import lombok.Data;
//Este DTO é responsavel por validar e fazer a comunicação entre back e front end
@Data
public class RequestNewPurschaseOrder {
	
	@NotBlank(message = "this field cannot be blank")
	private String purchase_order;
	
	public PurchaseOrder toPurschaseOrder() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setTextOrder(purchase_order);
		
		return purchaseOrder;
	}
	
}
